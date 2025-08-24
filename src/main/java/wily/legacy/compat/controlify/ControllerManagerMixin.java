package wily.legacy.compat.controlify;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wily.legacy.client.controller.Controller;
import wily.legacy.client.controller.ControllerManager;

/**
 * Mixin to intercept Legacy4J's controller input processing and make it conditional
 */
@Mixin(ControllerManager.class)
public class ControllerManagerMixin {
    // All gameplay & UI controller input is delegated to Controlify now; Legacy never processes controller bindings.
    @Inject(method = "updateBindings(Lwily/legacy/client/controller/Controller;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void onUpdateBindings(Controller controller, CallbackInfo ci) {
        ci.cancel();
    }
}
