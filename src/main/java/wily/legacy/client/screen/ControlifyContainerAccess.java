package wily.legacy.client.screen;

import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

/**
 * Public bridge for Controlify to interact with legacy container screens
 * without reflection. Implemented by container-based legacy screens.
 */
public interface ControlifyContainerAccess {
    Slot controlify$getHoveredSlot();
    void controlify$setHoveredSlot(Slot slot);
    void controlify$slotClick(Slot slot, int slotId, int button, ClickType clickType);
}