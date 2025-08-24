package wily.legacy.compat.controlify;

import dev.isxander.controlify.api.event.ControlifyEvents;
import net.minecraft.client.Minecraft;
import wily.legacy.Legacy4JClient;
import wily.legacy.client.controller.Controller;
import wily.legacy.client.controller.ControllerManager;

import java.lang.reflect.Method;

/**
 * Simple patch that conditionally disables Legacy4J's controller input processing
 * when Controlify should handle it (during gameplay).
 */
public class InputConditionalPatch {
    private static boolean controlifyPresent = false;
    private static boolean isInGameplay = true;
    private static boolean patched = false;
    
    public static void initialize() {
        try {
            Class.forName("dev.isxander.controlify.Controlify");
            controlifyPresent = true;
            setupPatch();
        } catch (ClassNotFoundException e) {
            controlifyPresent = false;
        }
    }
    
    private static void setupPatch() {
        if (patched) return;
        patched = true;
        
        // Monitor screen state changes
        ControlifyEvents.ACTIVE_CONTROLLER_TICKED.register(event -> {
            Minecraft mc = Minecraft.getInstance();
            boolean newInGameplay = mc.screen == null;
            
            if (newInGameplay != isInGameplay) {
                isInGameplay = newInGameplay;
                onScreenStateChanged();
            }
        });
        
        System.out.println("Legacy4J: Input conditional patch applied");
    }
    
    private static void onScreenStateChanged() {
        String mode = isInGameplay ? "Controlify (gameplay)" : "Legacy4J (UI)";
        System.out.println("Legacy4J: Input mode: " + mode);
        
        // If we switched to gameplay mode, clear any pending Legacy4J controller input
        if (isInGameplay) {
            clearLegacyInput();
        }
    }
    
    private static void clearLegacyInput() {
        // Send empty controller input to Legacy4J to clear any active bindings
        ControllerManager manager = Legacy4JClient.controllerManager;
        if (manager != null && manager.connectedController != null) {
            // Temporarily process with empty controller to clear state
            manager.updateBindings(Controller.EMPTY);
        }
    }
    
    /**
     * Check if Legacy4J should process controller input right now
     */
    public static boolean shouldLegacyProcessInput() {
        // Legacy4J should only process input when:
        // 1. Controlify is not present, OR  
        // 2. We're in a UI screen (not in gameplay)
        return !controlifyPresent || !isInGameplay;
    }
    
    /**
     * Check if we're currently in gameplay (no screen open)
     */
    public static boolean isInGameplay() {
        return isInGameplay;
    }
    
    /**
     * Check if Controlify is present
     */
    public static boolean isControlifyPresent() {
        return controlifyPresent;
    }
}
