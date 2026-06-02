package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.gui.GuiGraphics;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class JumpTurnHistoryHUD extends DraggableHUDElement {
    private static final int LINE_HEIGHT = 9;

    public JumpTurnHistoryHUD() {
        super("jumpTurnHistory", "turnangle", false, new ScreenPosition(0, 130));
    }

    @Override
    public void render(GuiGraphics context, ScreenPosition pos) {
        renderLines(context, pos, false);
    }

    @Override
    public void renderDummy(GuiGraphics context, ScreenPosition pos) {
        renderLines(context, pos, true);
    }

    private void renderLines(GuiGraphics context, ScreenPosition pos, boolean dummy) {
        int lines = getDisplayCount();
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;

        DecimalFormat format = createFormat();
        for (int i = 0; i < lines; i++) {
            String prefix = (i + 1) + ": ";
            String value = dummy || i >= ParkourTickListener.jumpTurnAnglesRecorded
                    ? format.format(0.0D)
                    : format.format(ParkourTickListener.jumpTurnAngles[i]);
            int y = pos.getAbsoluteY() + 1 + (i * LINE_HEIGHT);

            text(context, prefix, pos.getAbsoluteX() + 1, y, color1);
            text(context, value, pos.getAbsoluteX() + 1 + mc.font.width(prefix), y, color2);
        }
    }

    @Override
    public int getWidth() {
        return mc.font.width("20: -180.0000000000000000") + 2;
    }

    @Override
    public int getHeight() {
        return getDisplayCount() * LINE_HEIGHT + 2;
    }

    private static int getDisplayCount() {
        int count = CyvClientConfig.getInt("turnangle", 20);
        return Math.max(1, Math.min(ParkourTickListener.JUMP_TURN_HISTORY_SIZE, count));
    }

    private static DecimalFormat createFormat() {
        int decimals = Math.max(1, Math.min(16, CyvClientConfig.getInt("turnangleDecimals", 16)));
        StringBuilder pattern = new StringBuilder("0");
        if (decimals > 0) {
            pattern.append(".");
            for (int i = 0; i < decimals; i++) {
                pattern.append("0");
            }
        }

        DecimalFormat format = new DecimalFormat(pattern.toString());
        if (CyvClientConfig.getBoolean("turnangleTrimZeroes", false)) {
            format.setMinimumFractionDigits(0);
        }
        format.setMaximumFractionDigits(decimals);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        return format;
    }
}
