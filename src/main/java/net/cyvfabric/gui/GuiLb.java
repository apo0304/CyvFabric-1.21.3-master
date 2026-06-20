package net.cyvfabric.gui;

import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.parkour.LandingAxis;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.UnknownNullability;

public class GuiLb extends CyvGui {
    LandingBlock lb;
    Button landingModeButton;
    Button axisButton;
    Button calculateWalls;
    Button resetWalls;

    Button bbToggle;
    Button condToggle;

    public GuiLb(LandingBlock b) {
        super("Landing/Momentum Gui");
        this.lb = b;
    }

    @Override
    public void init() {
        if (lb == null) this.onClose();

        this.landingModeButton = Button.builder(Component.nullToEmpty("Landing Mode: " + lb.mode.toString()), (widget) -> {
                    landingModeButtonPressed();
                }).bounds(this.width - 155, 5, 150, 20)
                .build();

        this.axisButton = Button.builder(Component.nullToEmpty("Axis: " + lb.axis.toString()), (widget) -> {
                    axisButtonPressed();
                }).bounds(this.width - 155, 30, 150, 20)
                .build();

        this.calculateWalls = Button.builder(Component.nullToEmpty("Calculate Walls"), (widget) -> {
                    lb.calculateWalls();
                }).bounds(this.width - 155, 105, 150, 20)
                .build();

        this.resetWalls = Button.builder(Component.nullToEmpty("Reset Walls"), (widget) -> {
                    lb.xMinWall = null;
                    lb.xMaxWall = null;
                    lb.zMinWall = null;
                    lb.zMaxWall = null;
                }).bounds(this.width - 155, 130, 150, 20)
                .build();

        this.bbToggle = Button.builder(Component.nullToEmpty(getBlockLabel() + " BB Visible: " + getBlockVisible()), (widget) -> {
                    CyvClientConfig.set(getBlockVisibleConfigKey(), !getBlockVisible());
                    bbToggle.setMessage(Component.nullToEmpty(getBlockLabel() + " BB Visible: " + getBlockVisible()));
                }).bounds(this.width - 155, 55, 150, 20)
                .build();

        this.condToggle = Button.builder(Component.nullToEmpty(getBlockLabel() + " Cond Visible: " + getCondVisible()), (widget) -> {
                    CyvClientConfig.set(getCondVisibleConfigKey(), !getCondVisible());
                    condToggle.setMessage(Component.nullToEmpty(getBlockLabel() + " Cond Visible: " + getCondVisible()));
                }).bounds(this.width - 155, 80, 150, 20)
                .build();

        if (lb.axis.equals(LandingAxis.both)) {
            lb.axis = LandingAxis.both;
            this.axisButton.setMessage(Component.nullToEmpty("Axis: Both"));
        } else if (lb.axis.equals(LandingAxis.z)) {
            lb.axis = LandingAxis.z;
            this.axisButton.setMessage(Component.nullToEmpty("Axis: Z"));
        } else {
            lb.axis = LandingAxis.x;
            this.axisButton.setMessage(Component.nullToEmpty("Axis: X"));
        }

        if (lb.mode.equals(LandingMode.landing)) {
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Landing"));
        } else if (lb.mode.equals(LandingMode.hit)) {
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Hit"));
        } else if (lb.mode.equals(LandingMode.z_neo)) {
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Z Neo"));
        } else {
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Enter"));
        }

    }

    void landingModeButtonPressed() {
        LandingMode mode = lb.mode;
        if (mode.equals(LandingMode.landing)) {
            lb.mode = LandingMode.hit;
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Hit"));
        } else if (mode.equals(LandingMode.hit)) {
            lb.mode = LandingMode.z_neo;
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Z Neo"));
        } else if (mode.equals(LandingMode.z_neo)) {
            lb.mode = LandingMode.enter;
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Enter"));
        } else {
            lb.mode = LandingMode.landing;
            this.landingModeButton.setMessage(Component.nullToEmpty("Landing Mode: Landing"));
        }
    }

    void axisButtonPressed() {
        LandingAxis mode = lb.axis;
        if (mode.equals(LandingAxis.both)) {
            lb.axis = LandingAxis.z;
            this.axisButton.setMessage(Component.nullToEmpty("Axis: Z"));
        } else if (mode.equals(LandingAxis.z)) {
            lb.axis = LandingAxis.x;
            this.axisButton.setMessage(Component.nullToEmpty("Axis: X"));
        } else {
            lb.axis = LandingAxis.both;
            this.axisButton.setMessage(Component.nullToEmpty("Axis: Both"));
        }
    }

    private boolean isMomentumBlock() {
        return lb == ParkourTickListener.momentumBlock;
    }

    private String getBlockLabel() {
        return isMomentumBlock() ? "MM" : "LB";
    }

    private String getBlockVisibleConfigKey() {
        return isMomentumBlock() ? "highlightMomentum" : "highlightLanding";
    }

    private String getCondVisibleConfigKey() {
        return isMomentumBlock() ? "highlightMomentumCond" : "highlightLandingCond";
    }

    private boolean getBlockVisible() {
        return CyvClientConfig.getBoolean(getBlockVisibleConfigKey(), false);
    }

    private boolean getCondVisible() {
        return CyvClientConfig.getBoolean(getCondVisibleConfigKey(), false);
    }

    @Override
    public void render(@UnknownNullability GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        this.renderTransparentBackground(context);

        landingModeButton.render(context, mouseX, mouseY, partialTicks);
        axisButton.render(context, mouseX, mouseY, partialTicks);
        calculateWalls.render(context, mouseX, mouseY, partialTicks);
        resetWalls.render(context, mouseX, mouseY, partialTicks);

        bbToggle.render(context, mouseX, mouseY, partialTicks);
        condToggle.render(context, mouseX, mouseY, partialTicks);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        landingModeButton.mouseClicked(mouseX, mouseY, button);
        axisButton.mouseClicked(mouseX, mouseY, button);
        calculateWalls.mouseClicked(mouseX, mouseY, button);
        resetWalls.mouseClicked(mouseX, mouseY, button);
        bbToggle.mouseClicked(mouseX, mouseY, button);
        condToggle.mouseClicked(mouseX, mouseY, button);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        if (lb == null) this.onClose();
    }

}
