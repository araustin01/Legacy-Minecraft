package wily.legacy.compat.controlify;

import dev.isxander.controlify.api.event.ControlifyEvents;
import net.minecraft.client.Minecraft;

/**
 * Monitors game state and provides conditional logic for when Legacy4J should process input
 */
public class InputConditionalPatch {
    private static boolean isInitialized = false;
    private static boolean isInGameplay = false;
    private static boolean isSplitscreenPawn = false;
    // Hysteresis counters to avoid rapid flipping when transient screens appear/disappear (e.g. brief config or overlay screens)
    private static int uiTicks = 0;
    private static int gameplayTicks = 0;
    private static final int HYSTERESIS_TICKS = 3; // number of consecutive ticks a state must persist before switching
    private static String lastScreenClass = null;
    
    public static void initialize() {
        if (isInitialized) return;
        isInitialized = true;
        
        // Check if this is a splitscreen pawn instance
        checkSplitscreenStatus();
        
        // Listen for Controlify events to track state
        try {
            ControlifyEvents.ACTIVE_CONTROLLER_TICKED.register(event -> {
                updateGameplayState();
            });
            
            System.out.println("Legacy4J: Input conditional patch applied");
        } catch (Exception e) {
            System.err.println("Legacy4J: Failed to initialize input conditional patch: " + e.getMessage());
        }
    }
    
    /**
     * Check if this is a splitscreen pawn instance
     */
    private static void checkSplitscreenStatus() {
        try {
            // Check if the current Minecraft instance is a pawn
            // This can be determined by checking if the user has a suffix like ".1", ".2", etc.
            Minecraft mc = Minecraft.getInstance();
            if (mc.getUser() != null && mc.getUser().getName().contains(".")) {
                isSplitscreenPawn = true;
                System.out.println("Legacy4J: Detected splitscreen pawn instance - disabling Legacy4J input completely");
            }
        } catch (Exception e) {
            System.err.println("Legacy4J: Failed to detect splitscreen status: " + e.getMessage());
        }
    }
    
    /**
     * Update the current gameplay state
     */
    private static void updateGameplayState() {
        Minecraft mc = Minecraft.getInstance();
        boolean hasScreen = mc.screen != null;

        // Track the current top-level screen class name for debugging transitions
        String currentScreenClass = mc.screen == null ? "<none>" : mc.screen.getClass().getName();
        if (lastScreenClass == null) lastScreenClass = currentScreenClass;

        // Hysteresis logic: require a screen (UI) or lack thereof (gameplay) to persist for a few ticks before toggling
        if (hasScreen) {
            uiTicks++;
            gameplayTicks = 0;
            if (isInGameplay && uiTicks >= HYSTERESIS_TICKS) {
                isInGameplay = false;
                logInputMode();
            }
        } else {
            gameplayTicks++;
            uiTicks = 0;
            if (!isInGameplay && gameplayTicks >= HYSTERESIS_TICKS) {
                isInGameplay = true;
                logInputMode();
            }
        }

        // Additional debug: if the screen class itself is thrashing, print once to help diagnose
        if (!currentScreenClass.equals(lastScreenClass)) {
            System.out.println("Legacy4J: Screen changed -> " + currentScreenClass);
            lastScreenClass = currentScreenClass;
        }
    }
    
    /**
     * Check if Legacy4J should process input right now
     */
    public static boolean shouldLegacyProcessInput() {
    // Deprecated: Legacy no longer processes controller input; Controlify owns all input paths.
    public static boolean shouldLegacyProcessInput() { return false; }
    }
    
    /**
     * Check if currently in gameplay (no screen open)
     */
    public static boolean isInGameplay() {
        return isInGameplay;
    }
    
    /**
     * Check if this is a splitscreen pawn instance
     */
    public static boolean isSplitscreenPawn() {
    public static boolean isSplitscreenPawn() { return false; }
    }
    
    /**
     * Log the current input mode for debugging
     */
    private static void logInputMode() {
        if (isSplitscreenPawn) {
            System.out.println("Legacy4J: Input mode: Controlify (splitscreen pawn)");
        } else if (isInGameplay) {
            System.out.println("Legacy4J: Input mode: Controlify (gameplay)");
        } else {
            System.out.println("Legacy4J: Input mode: Legacy4J (UI)");
        }
    }
}
