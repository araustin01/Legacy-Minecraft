package wily.legacy.client.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Invisible, non-intercepting hitbox widget that exposes bounds/focus for navigation.
 * It never consumes input; screen-level logic remains unchanged.
 */
public class LegacyHitboxWidget extends AbstractWidget {
    public LegacyHitboxWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        this.active = true;
        this.visible = true;
        this.setFocused(false);
    }

    @Override
    protected void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // Intentionally invisible. Uncomment to debug:
        // gfx.fill(getX(), getY(), getX() + width, getY() + height, 0x40FFFFFF);
    }

    // Do not consume events; let the screen handle input.
    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) { return false; }
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { return false; }
    @Override public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) { return false; }
    //? if >1.20.1 {
    @Override public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) { return false; }
    //?} else {
    /*@Override public boolean mouseScrolled(double mouseX, double mouseY, double delta) { return false; }*/
    //?}
    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) { return false; }
    @Override public boolean charTyped(char codePoint, int modifiers) { return false; }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput out) {
        // Optionally: out.add(NarratedElementType.TITLE, Component.literal("Hitbox"));
    }
}
