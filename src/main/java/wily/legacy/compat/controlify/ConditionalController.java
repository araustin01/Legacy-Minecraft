package wily.legacy.compat.controlify;

import wily.legacy.client.controller.Controller;
import wily.legacy.client.controller.ControllerBinding;
import wily.legacy.client.controller.ControllerManager;
import wily.legacy.client.ControlType;

/**
 * Wrapper controller that conditionally processes input based on game state
 */
public class ConditionalController implements Controller {
    private final Controller delegate;
    
    public ConditionalController(Controller original) {
        this.delegate = original;
    }
    
    @Override
    public String getName() {
        return delegate.getName();
    }
    
    @Override
    public ControlType getType() {
        return delegate.getType();
    }
    
    @Override
    public boolean buttonPressed(int i) {
        return shouldProcessInput() ? delegate.buttonPressed(i) : false;
    }
    
    @Override
    public float axisValue(int i) {
        return shouldProcessInput() ? delegate.axisValue(i) : 0.0f;
    }
    
    @Override
    public boolean hasButton(ControllerBinding.Button button) {
        return delegate.hasButton(button);
    }
    
    @Override
    public boolean hasAxis(ControllerBinding.Axis axis) {
        return delegate.hasAxis(axis);
    }
    
    @Override
    public Handler getHandler() {
        return delegate.getHandler();
    }
    
    @Override
    public void connect(ControllerManager manager) {
        delegate.connect(manager);
        System.out.println("Legacy4J: Conditional controller connected");
    }
    
    @Override
    public void disconnect(ControllerManager manager) {
        delegate.disconnect(manager);
        System.out.println("Legacy4J: Conditional controller disconnected");
    }
    
    @Override
    public void manageBindings(Runnable run) {
        if (shouldProcessInput()) {
            delegate.manageBindings(run);
        } else {
            // Don't run the binding management if Controlify should handle input
            // This prevents Legacy4J from processing controller input during gameplay
        }
    }
    
    private boolean shouldProcessInput() {
        return InputConditionalPatch.shouldLegacyProcessInput();
    }
    
    // Delegate all other methods
    @Override
    public void rumble(char low_frequency_rumble, char high_frequency_rumble, int duration_ms) {
        delegate.rumble(low_frequency_rumble, high_frequency_rumble, duration_ms);
    }
    
    @Override
    public void rumbleTriggers(char left_rumble, char right_rumble, int duration_ms) {
        delegate.rumbleTriggers(left_rumble, right_rumble, duration_ms);
    }
    
    @Override
    public boolean hasLED() {
        return delegate.hasLED();
    }
    
    @Override
    public void setLED(byte r, byte g, byte b) {
        delegate.setLED(r, g, b);
    }
}
