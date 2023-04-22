package net.fabricmc.headtrackermod;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    private static final String CATEGORY_NAME = "control";

    public static KeyBinding reloadSettings, moveCameraUp, moveCameraDown;

    private static KeyBinding create(String name, int key) {
        final String translationKey = "key." + HeadTrackerMod.MOD_ID + name;
        final String category = "category." + HeadTrackerMod.MOD_ID + "." + CATEGORY_NAME;

        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey, InputUtil.Type.KEYSYM, key, category
        ));
    }

    public static void registerBindings() {
        reloadSettings = create("reloadHTSettings", GLFW.GLFW_KEY_V);
        moveCameraUp = create("moveCameraUp", GLFW.GLFW_KEY_R);
        moveCameraDown = create("moveCameraDown", GLFW.GLFW_KEY_F);
    }

    private ModKeyBindings() {}
}
