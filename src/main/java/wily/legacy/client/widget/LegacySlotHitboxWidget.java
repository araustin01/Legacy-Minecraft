package wily.legacy.client.widget;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

/**
 * Hitbox wrapper for a Slot so navigation systems can discover and focus it.
 * Does not consume events; the container screen still processes clicks/dragging.
 */
public class LegacySlotHitboxWidget extends LegacyHitboxWidget {
    private final AbstractContainerScreen<?> screen;
    private final Slot slot;

    public LegacySlotHitboxWidget(AbstractContainerScreen<?> screen, Slot slot, int screenLeft, int screenTop) {
        super(screenLeft + slot.x, screenTop + slot.y, 16, 16);
        this.screen = screen;
        this.slot = slot;
    }

    public Slot slot() { return slot; }
    public AbstractContainerScreen<?> screen() { return screen; }
}
