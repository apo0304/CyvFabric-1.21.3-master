package net.cyvfabric.keybinding;

import net.cyvfabric.command.mpk.CommandSetlb;
import net.cyvfabric.util.CyvKeybinding;
import org.lwjgl.glfw.GLFW;

public class KeybindingSetLandingBlockTarget extends CyvKeybinding {
    public KeybindingSetLandingBlockTarget() {
        super("key.cyvfabric.setlandingblocktarget", GLFW.GLFW_KEY_UNKNOWN);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandSetlb.run(new String[]{"target"});
        }
    }
}
