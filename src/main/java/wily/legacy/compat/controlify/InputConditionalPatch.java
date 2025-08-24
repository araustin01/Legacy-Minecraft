package wily.legacy.compat.controlify;

/**
 * Deprecated shim retained only so older reflection hooks in Controlify resolve.
 * All controller input is now handled by Controlify; Legacy never processes it.
 */
public class InputConditionalPatch {
    public static void initialize() {}
    public static boolean shouldLegacyProcessInput() { return false; }
    public static boolean isSplitscreenPawn() { return false; }
    public static boolean isInGameplay() { return false; }
}
