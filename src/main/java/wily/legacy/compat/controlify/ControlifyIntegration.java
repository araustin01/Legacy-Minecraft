package wily.legacy.compat.controlify;

import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.api.event.ControlifyEvents;
import dev.isxander.controlify.controller.ControllerEntity;
import net.minecraft.client.gui.screens.Screen;
import wily.legacy.client.controller.ControllerManager;
import wily.legacy.Legacy4JClient;

/**
 * Integration layer that allows Legacy4J to take control of screen navigation
 * while still using Controlify for controller detection and management.
 */
public class ControlifyIntegration {
    private static boolean initialized = false;
    
    public static void init() {
        if (initialized) return;
        initialized = true;
        
        // Listen for Controlify controller events and map them to Legacy4J
        ControlifyEvents.CONTROLLER_CONNECTED.register(event -> {
            // When Controlify detects a controller, we can optionally sync it with Legacy4J
            onControlifyControllerConnected(event.controller());
        });
        
        ControlifyEvents.CONTROLLER_DISCONNECTED.register(event -> {
            onControlifyControllerDisconnected(event.controller());
        });
        
        // Note: Screen processing override is no longer available in newer Controlify versions
        // Legacy4J will handle screen navigation directly through its own input system
        System.out.println("Legacy4J: Controlify integration initialized - using parallel input handling");
    }
    
    private static void onControlifyControllerConnected(ControllerEntity controller) {
        // Optionally bridge controller information to Legacy4J
        System.out.println("Controlify controller connected: " + controller.name());
    }
    
    private static void onControlifyControllerDisconnected(ControllerEntity controller) {
        System.out.println("Controlify controller disconnected: " + controller.name());
    }
}
