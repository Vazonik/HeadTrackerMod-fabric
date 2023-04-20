package net.fabricmc.headtrackermod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeadTrackerMod implements ModInitializer {
	public static final String MOD_ID = "headtrackermod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final KeyBinding testKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key." + MOD_ID + ".test",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_R,

			"category." + MOD_ID + ".test"
	));

	public static Vec3d cameraOffset = Vec3d.ZERO;

	@Override
	public void onInitialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			while (testKeyBinding.wasPressed()) {
				cameraOffset = cameraOffset.add(new Vec3d(0.0, 1.0, 0.0));
			}
		});
	}
}
