package wily.legacy.compat.controlify;

import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.api.event.ControlifyEvents;
import dev.isxander.controlify.controller.ControllerEntity;
import wily.legacy.client.controller.Controller;
import wily.legacy.client.controller.ControllerBinding;
import wily.legacy.client.controller.BindingState;
import wily.legacy.client.controller.ControllerManager;
import wily.legacy.client.ControlType;
import wily.legacy.Legacy4JClient;

/**
 * Alternative approach: Bridge Controlify controller input to Legacy4J's system
 */
public class ControlifyControllerBridge implements Controller {
    private final ControllerEntity controlifyController;
    private ControllerManager legacyManager;
    
    public ControlifyControllerBridge(ControllerEntity controlifyController) {
        this.controlifyController = controlifyController;
    }
    
    public static void setupBridge() {
        ControlifyEvents.ACTIVE_CONTROLLER_TICKED.register(event -> {
            // Get the Legacy4J controller manager
            ControllerManager legacyManager = Legacy4JClient.controllerManager;
            
            // Create a bridge controller and update Legacy4J bindings
            ControlifyControllerBridge bridge = new ControlifyControllerBridge(event.controller());
            bridge.legacyManager = legacyManager;
            
            // Update Legacy4J with this bridged controller
            legacyManager.updateBindings(bridge);
        });
    }
    
    @Override
    public String getName() {
        return controlifyController.name();
    }
    
    @Override
    public ControlType getType() {
        // Map Controlify controller types to Legacy4J types
        String typeName = controlifyController.info().type().friendlyName();
        return switch (typeName.toLowerCase()) {
            case "ps4", "playstation 4" -> ControlType.PS4;
            case "ps5", "playstation 5" -> ControlType.PS5;
            case "xbox 360" -> ControlType.x360;
            case "xbox one" -> ControlType.xONE;
            case "nintendo switch" -> ControlType.SWITCH;
            default -> ControlType.x360; // Default fallback
        };
    }
    
    @Override
    public boolean buttonPressed(int i) {
        // Map Legacy4J button requests to Controlify input state
        return controlifyController.input()
            .map(input -> {
                // This would need mapping between Legacy4J buttons and Controlify input IDs
                // For now, returning false as example
                return false;
            })
            .orElse(false);
    }
    
    @Override
    public float axisValue(int i) {
        // Map Legacy4J axis requests to Controlify input state
        return controlifyController.input()
            .map(input -> {
                // This would need mapping between Legacy4J axes and Controlify input IDs
                // For now, returning 0.0f as example
                return 0.0f;
            })
            .orElse(0.0f);
    }

    @Override
    public boolean hasButton(ControllerBinding.Button button) {
        // Check if the controller has this button
        return true; // Simplified for now
    }

    @Override
    public boolean hasAxis(ControllerBinding.Axis axis) {
        // Check if the controller has this axis
        return true; // Simplified for now
    }

    @Override
    public Handler getHandler() {
        // Return a dummy handler for the bridge
        return Handler.EMPTY;
    }

    // Implement other Controller interface methods...
    @Override
    public void connect(ControllerManager manager) {
        // Handle connection logic
    }
    
    @Override
    public void disconnect(ControllerManager manager) {
        // Handle disconnection logic
    }
    
    @Override
    public void manageBindings(Runnable run) {
        run.run();
    }
}
