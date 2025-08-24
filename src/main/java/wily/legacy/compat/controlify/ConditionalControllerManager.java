package wily.legacy.compat.controlify;

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
        
        // Wrap the controller handler to provide conditional controllers
        wrapControllerHandler();
        
        System.out.println("Legacy4J: Conditional controller manager initialized");
    }
    
    /**
     * Wrap the existing controller handler with our conditional handler
     */
    private static void wrapControllerHandler() {
        try {
            // Get the current handler from ControllerManager
            Controller.Handler currentHandler = getCurrentHandler();
            
            if (currentHandler != null && !(currentHandler instanceof ConditionalControllerHandler)) {
                // Wrap it with our conditional handler
                ConditionalControllerHandler conditionalHandler = new ConditionalControllerHandler(currentHandler);
                
                // Replace the handler in ControllerManager
                setControllerHandler(conditionalHandler);
                
                System.out.println("Legacy4J: Controller handler wrapped with conditional processing");
            }
        } catch (Exception e) {
            System.err.println("Legacy4J: Failed to wrap controller handler: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get the current controller handler using reflection
     */
    private static Controller.Handler getCurrentHandler() {
        try {
            java.lang.reflect.Field handlerField = ControllerManager.class.getDeclaredField("handler");
            handlerField.setAccessible(true);
            return (Controller.Handler) handlerField.get(null);
        } catch (Exception e) {
            System.err.println("Legacy4J: Failed to get controller handler: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Set the controller handler using reflection
     */
    private static void setControllerHandler(Controller.Handler handler) {
        try {
            java.lang.reflect.Field handlerField = ControllerManager.class.getDeclaredField("handler");
            handlerField.setAccessible(true);
            handlerField.set(null, handler);
        } catch (Exception e) {
            System.err.println("Legacy4J: Failed to set controller handler: " + e.getMessage());
        }
    }
}
