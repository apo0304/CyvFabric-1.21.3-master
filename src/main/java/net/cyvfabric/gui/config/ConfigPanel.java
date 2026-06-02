package net.cyvfabric.gui.config;

import net.minecraft.client.gui.GuiGraphics;

public interface ConfigPanel {
    boolean mouseInBounds(double mouseX, double mouseY);
    void mouseClicked(double mouseX, double mouseY, int button);
    void charTyped(char input, int modifiers);
    default void keyPressed(int keyCode, int scanCode, int modifiers) {}
    void draw(GuiGraphics context, int mouseX, int mouseY, int scroll);
    default void update() {}
    void mouseDragged(double mouseX, double mouseY);
    void save();
    default void select() {}
    default void unselect() {}

    default void onValueChange() {}
}