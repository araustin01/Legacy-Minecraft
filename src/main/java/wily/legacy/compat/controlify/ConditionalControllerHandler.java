package wily.legacy.compat.controlify;

import net.minecraft.network.chat.Component;
import wily.legacy.client.controller.Controller;
import wily.legacy.client.controller.ControllerBinding;
import wily.legacy.client.controller.ControllerManager;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Wrapper for controller handlers that provides conditional controllers
 */
public class ConditionalControllerHandler implements Controller.Handler {
    private final Controller.Handler delegate;
    
    public ConditionalControllerHandler(Controller.Handler original) {
        this.delegate = original;
    }
    
    @Override
    public Component getName() {
        return delegate.getName();
    }
    
    @Override
    public void init() {
        delegate.init();
    }
    
    @Override
    public boolean update() {
        return delegate.update();
    }
    
    @Override
    public void setup(ControllerManager manager) {
        // Initialize our conditional input system
        InputConditionalPatch.initialize();
        
        // Set up the original handler
        delegate.setup(manager);
        
        System.out.println("Legacy4J: Conditional controller handler setup complete");
    }
    
    @Override
    public Controller getController(int jid) {
        Controller original = delegate.getController(jid);
        if (original != null && original != Controller.EMPTY) {
            // Wrap the controller to make it conditional
            return new ConditionalController(original);
        }
        return original;
    }
    
    @Override
    public boolean isValidController(int jid) {
        return delegate.isValidController(jid);
    }
    
    @Override
    public int getButtonIndex(ControllerBinding.Button button) {
        return delegate.getButtonIndex(button);
    }
    
    @Override
    public int getAxisIndex(ControllerBinding.Axis axis) {
        return delegate.getAxisIndex(axis);
    }
    
    @Override
    public void applyGamePadMappingsFromBuffer(BufferedReader reader) throws IOException {
        delegate.applyGamePadMappingsFromBuffer(reader);
    }
}
