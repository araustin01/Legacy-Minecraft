package wily.legacy.compat.controlify;

import wily.legacy.Legacy4JClient;
import wily.legacy.client.controller.Controller;
import wily.legacy.client.controller.ControllerManager;

/**
 * Manager for conditionally processing controller input
 */
public class ConditionalControllerManager {
    
    /**
     * Initialize the conditional controller system
     */
    public static void initialize() {
        // Initialize the input conditional patch system
        InputConditionalPatch.initialize();
        
        System.out.println("Legacy4J: Conditional controller manager initialized");
    }
    
    /**
     * Check if Legacy4J should handle input based on current state
     */
    public static boolean shouldHandleInput() {
        return InputConditionalPatch.shouldLegacyProcessInput();
    }
    
    /**
     * Conditionally process controller input for Legacy4J
     */
    public static void conditionalUpdateBindings(ControllerManager manager, Controller controller) {
        // Check if we should process input
        if (!shouldHandleInput()) {
            // Don't process input - Controlify should handle it
            return;
        }
        
        // Process input normally
        manager.updateBindings(controller);
    }
}
