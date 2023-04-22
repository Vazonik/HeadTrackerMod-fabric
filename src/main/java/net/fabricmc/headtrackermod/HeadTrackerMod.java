package net.fabricmc.headtrackermod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeadTrackerMod implements ModInitializer {
	public static final String MOD_ID = "headtrackermod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModConfig CONFIG = new ModConfig();

	public static Vec3d cameraOffset = Vec3d.ZERO;


	public static Vec3d calculateCameraPosition(Vec3d actualPosition) {
		return actualPosition.add(cameraOffset.add(CONFIG.getOffset()).multiply(CONFIG.getSensitivity()));
	}

	public static void beforeCameraUpdate() {
		// TODO: Get camera offset live from tracker

		if (ModKeyBindings.moveCameraUp.isPressed()) {
			cameraOffset = cameraOffset.add(0, CONFIG.getSensitivity() * 0.2, 0);
		}

		if (ModKeyBindings.moveCameraDown.isPressed()) {
			cameraOffset = cameraOffset.add(0, CONFIG.getSensitivity() * -0.2, 0);
		}
	}


	@Override
	public void onInitialize() {
		ModKeyBindings.registerBindings();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {

				while (ModKeyBindings.reloadSettings.wasPressed()) {
					client.player.sendMessage(Text.of("Reloading the Head Tracker Mod config file..."));
					CONFIG.reload();
				}
			}
		});
	}
}
