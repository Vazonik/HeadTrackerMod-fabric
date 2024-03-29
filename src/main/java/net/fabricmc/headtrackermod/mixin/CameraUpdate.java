package net.fabricmc.headtrackermod.mixin;

import net.fabricmc.headtrackermod.HeadTrackerMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraUpdate {
    @Inject(at = @At("TAIL"), method = "update()V")
    private void update(CallbackInfo info) {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        HeadTrackerMod.beforeCameraUpdate();
        ((CameraPositionAccessor) camera).setCameraPos(HeadTrackerMod.calculateCameraPosition(camera.getPos()));
    }
}
