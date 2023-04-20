package net.fabricmc.headtrackermod.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Camera.class)
public interface CameraPositionAccessor {
    @Accessor("pos")
    public void setCameraPos(Vec3d pos);
}
