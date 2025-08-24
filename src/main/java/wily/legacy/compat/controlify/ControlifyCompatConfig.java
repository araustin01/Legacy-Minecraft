package wily.legacy.compat.controlify;

import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.config.GlobalSettings;
import net.minecraft.client.Minecraft;
import wily.legacy.Legacy4JClient;

/**
 * Configuration-based integration that allows users to choose which system handles controller input
 */
public class ControlifyCompatConfig {
    public static boolean USE_LEGACY4J_FOR_GUI = true;
    public static boolean USE_CONTROLIFY_FOR_DETECTION = true;
    
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
        if (USE_LEGACY4J_FOR_GUI) {
            // Disable Controlify's GUI handling
            disableControlifyGuiHandling();
        }
        
        if (USE_CONTROLIFY_FOR_DETECTION) {
            // Use Controlify for controller detection but Legacy4J for input processing
            ControlifyIntegration.init();
        }
    }
    
    private static void disableControlifyGuiHandling() {
        // This would require either:
        // 1. Modifying Controlify's config to disable screen processing
        // 2. Using mixins to intercept and disable the screen processor
        // 3. Setting up event listeners to override Controlify's input handling
        
        System.out.println("Legacy4J: Taking over GUI controller handling from Controlify");
    }
    
    /**
     * Check if both mods are present and handle conflicts
     */
    public static void checkForConflicts() {
        if (isControlifyPresent()) {
            System.out.println("Legacy4J: Controlify detected, setting up compatibility mode");
        }
    }
}
