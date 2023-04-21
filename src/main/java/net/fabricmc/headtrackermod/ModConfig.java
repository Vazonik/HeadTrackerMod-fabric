package net.fabricmc.headtrackermod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.Vec3d;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class ModConfig {
    private static final String FILE_NAME = "config.json";
    private static final File CONFIG_FILE = new File(
            FabricLoader.getInstance().getConfigDir().resolve(HeadTrackerMod.MOD_ID).toFile(), FILE_NAME
    );

    public final float SENSITIVITY;
    public final Vec3d CONFIG_OFFSET;

    public ModConfig(String fileName) {
        JsonObject configJson = null;

        setup();
        try {
            configJson = (JsonObject) JsonParser.parseReader(new FileReader(CONFIG_FILE));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        assert configJson != null;

        JsonObject offsetJson = configJson.get("cameraOffset").getAsJsonObject();

        SENSITIVITY = configJson.get("sensitivity").getAsFloat();
        CONFIG_OFFSET = new Vec3d(
                offsetJson.get("x").getAsFloat(),
                offsetJson.get("y").getAsFloat(),
                offsetJson.get("z").getAsFloat()
        );
    }

    private void setup() {
        if (CONFIG_FILE.exists() && !CONFIG_FILE.isDirectory()) {
            HeadTrackerMod.LOGGER.info("The %s/%s config file found".formatted(HeadTrackerMod.MOD_ID, FILE_NAME));
        } else {
            File configDir = CONFIG_FILE.getParentFile();
            if (!configDir.exists()) {
                if (configDir.mkdir()) {
                    HeadTrackerMod.LOGGER.info(
                            "The %s config directory has been created".formatted(HeadTrackerMod.MOD_ID)
                    );
                } else {
                    throw new IllegalStateException(
                            "The %s config directory cannot be created".formatted(HeadTrackerMod.MOD_ID)
                    );
                }
            }
            try {
                Objects.requireNonNull(ModConfig.class.getResourceAsStream(
                        "/config/" + HeadTrackerMod.MOD_ID + "/" + FILE_NAME)
                ).transferTo(new FileOutputStream(CONFIG_FILE));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
