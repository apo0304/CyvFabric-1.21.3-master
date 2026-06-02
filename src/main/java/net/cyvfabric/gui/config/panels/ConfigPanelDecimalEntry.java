package net.cyvfabric.gui.config.panels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import com.mojang.blaze3d.platform.Window;
import org.jetbrains.annotations.UnknownNullability;

public class ConfigPanelDecimalEntry implements ConfigPanel {
    public EditBox field;
    public String configOption;
    public String displayString;
    public final int index;
    public GuiModConfig screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    private double minBound = -Double.MAX_VALUE;
    private double maxBound = Double.MAX_VALUE;

    public ConfigPanelDecimalEntry(int index, String configOption, String displayString, double min, double max, GuiModConfig screenIn) {
        this(index, configOption, displayString, screenIn);
        this.minBound = min;
        this.maxBound = max;
    }

    public ConfigPanelDecimalEntry(int index, String configOption, String displayString, GuiModConfig screenIn) {
        this.index = index;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        Window sr = Minecraft.getInstance().getWindow();
        sizeX = screenIn.sizeX-20;
        sizeY = Minecraft.getInstance().font.lineHeight*3/2;
        this.xPosition = sr.getGuiScaledWidth()/2-screenIn.sizeX/2+10;
        this.yPosition = sr.getGuiScaledHeight()/2-screenIn.sizeY/2+10 + (index * Minecraft.getInstance().font.lineHeight * 2);

        this.field = new EditBox(Minecraft.getInstance().font, this.xPosition+this.sizeX/2+2, this.yPosition+this.sizeY/2-Minecraft.getInstance().font.lineHeight/2+1, this.sizeX/2-4, Minecraft.getInstance().font.lineHeight/2, Component.empty());
        this.field.setValue(CyvClientConfig.getDouble(configOption, 0)+"");
        this.field.setBordered(false);
        this.field.setVisible(true);

    }

    @Override
    public void draw(@UnknownNullability GuiGraphics context, int mouseX, int mouseY, int scroll) {
        context.drawString(Minecraft.getInstance().font, this.displayString, this.xPosition, this.yPosition+this.sizeY/2-Minecraft.getInstance().font.lineHeight/2+1-scroll, 0xFFFFFFFF);
        //bg
        GuiUtils.drawRoundedRect(context, this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.shade1 : CyvFabric.theme.shade2);


        this.field.setY(this.yPosition+this.sizeY/2-Minecraft.getInstance().font.lineHeight/2+1-scroll);
        this.field.render(context, mouseX, mouseY, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {
    }

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        if (mouseX > this.xPosition+this.sizeX/2 && mouseY > this.yPosition
                && mouseX < this.xPosition+this.sizeX && mouseY < this.yPosition+this.sizeY) return true;
        return false;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        this.field.mouseClicked(mouseX, mouseY, button);

        if (!(mouseX >= field.getX() && mouseX <= field.getX() + field.getWidth() && mouseY >= field.getY() && mouseY <= field.getY() + field.getHeight())) {
            this.unselect();
        } else {
            this.select();
        }
    }


    @Override
    public void charTyped(char input, int modifiers) {
        this.field.charTyped(input, modifiers);
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        this.field.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void save() {
        double val = 0;
        try {
            val = Mth.clamp(Double.valueOf(this.field.getValue()), this.minBound, this.maxBound);
            this.field.setValue(val+"");
            CyvClientConfig.set(this.configOption, val);
        } catch (Exception e) {}
    }

    @Override
    public void select() {
        this.field.setFocused(true);
    }

    @Override
    public void unselect() {
        this.field.setFocused(false);
        try {
            double val = Mth.clamp(Double.valueOf(this.field.getValue()), this.minBound, this.maxBound);
            this.field.setValue(val+"");
            CyvClientConfig.set(this.configOption, val);
        } catch (Exception e) {}
        onValueChange();
    }


}
