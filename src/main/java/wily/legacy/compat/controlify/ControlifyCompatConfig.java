package wily.legacy.compat.controlify;

import dev.isxander.controlify.Controlify;
import net.minecraft.client.Minecraft;
import wily.legacy.Legacy4JClient;

/**
 * Configuration-based integration that allows users to choose which system handles controller input
 */
public class ControlifyCompatConfig {
    public static boolean USE_LEGACY4J_FOR_GUI = true;
    public static boolean USE_CONTROLIFY_FOR_GAMEPLAY = true;
    
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
        if (USE_LEGACY4J_FOR_GUI && USE_CONTROLIFY_FOR_GAMEPLAY) {
            // Set up conditional input handling
            ConditionalControllerManager.initialize();
            System.out.println("Legacy4J: Conditional input handling enabled");
            System.out.println("  - Controlify handles gameplay input");  
            System.out.println("  - Legacy4J handles UI/menu input");
        }
        
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
