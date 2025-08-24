package wily.legacy.compat.controlify;

import dev.isxander.controlify.Controlify;
import net.minecraft.client.Minecraft;
import wily.legacy.Legacy4JClient;

/**
 * Configuration-based integration that allows users to choose which system handles controller input
 */
public class ControlifyCompatConfig {
    // Unified Controlify ownership: Legacy4J no longer handles controller input for GUI or gameplay.
    public static boolean USE_LEGACY4J_FOR_GUI = false;
    public static boolean USE_CONTROLIFY_FOR_GAMEPLAY = true; // retained for clarity
    
    public static void init() {
        if (isControlifyPresent()) {
            setupCompatibility();
        }
    }
    
    private static boolean isControlifyPresent() {
        try {
            Class.forName("dev.isxander.controlify.Controlify");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    private static void setupCompatibility() {
    // Conditional input mode disabled; Legacy4J defers completely to Controlify.
        
        // Initialize basic integration
        ControlifyIntegration.init();
    }
    
    /**
     * Check if both mods are present and handle conflicts
     */
    public static void checkForConflicts() {
        if (isControlifyPresent()) {
            System.out.println("Legacy4J: Controlify detected, conditional mode active");
        }
    }
}
