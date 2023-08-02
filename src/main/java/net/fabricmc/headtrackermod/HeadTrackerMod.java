package net.fabricmc.headtrackermod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HeadTrackerMod implements ModInitializer {
	public static final String MOD_ID = "headtrackermod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModConfig CONFIG = new ModConfig();

	public static Vec3d cameraOffset = Vec3d.ZERO;
	public static float[] receivedFloats = { 0.0f, 0.0f, 0.0f };


	public static Vec3d calculateCameraPosition(Vec3d actualPosition) {
		return actualPosition.add(
				cameraOffset.x * CONFIG.getSensitivity().x + CONFIG.getOffset().x,
				cameraOffset.y * CONFIG.getSensitivity().y + CONFIG.getOffset().y,
				cameraOffset.z * CONFIG.getSensitivity().z + CONFIG.getOffset().z
		);
	}

	public static void beforeCameraUpdate() {
		// TODO: rotate the coordinate system according to the player's rotation.
		cameraOffset = new Vec3d(
			receivedFloats[0],
			receivedFloats[1],
			receivedFloats[2]
		);
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

		Thread udpReceiver = new Thread(() -> {
			final int BUFFER_SIZE = 12;

			byte[] buffer = new byte[BUFFER_SIZE];

			try (DatagramSocket socket = new DatagramSocket(8080)) {
				DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);

				while (true) {
					socket.receive(packet);

					ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
					DataInputStream dataStream = new DataInputStream(byteStream);

					for (int i = 0; i < 3; i++) {
						receivedFloats[i] = dataStream.readFloat();
					}

					dataStream.close();
					byteStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		udpReceiver.start();
	}
}
