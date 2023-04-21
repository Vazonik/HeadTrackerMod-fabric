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
	public static final ModConfig CONFIG = new ModConfig("config.json");

	public static Vec3d cameraOffset = Vec3d.ZERO;


	public static Vec3d calculateCameraPosition(Vec3d actualPosition) {
		return actualPosition.add(cameraOffset.add(CONFIG.CONFIG_OFFSET).multiply(CONFIG.SENSITIVITY));
	}


	@Override
	public void onInitialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				// TODO: Get camera offset live from tracker
			}
		});
	}
}
