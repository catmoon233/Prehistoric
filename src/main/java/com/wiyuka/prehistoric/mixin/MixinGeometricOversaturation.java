package com.wiyuka.prehistoric.mixin;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Frustum.class)
public class MixinGeometricOversaturation {
    @Unique
    private final Random prehistoric$chaoticEntropy = new Random();

    @Inject(method = "isVisible(Lnet/minecraft/world/phys/AABB;)Z", at = @At("HEAD"), cancellable = true)
    public void onRebuild(AABB aabb, CallbackInfoReturnable<Boolean> cir) {
        double totalEntropy = 0;

        for (double x = aabb.minX; x <= aabb.maxX; x += (aabb.maxX - aabb.minX)) {
            for (double y = aabb.minY; y <= aabb.maxY; y += (aabb.maxY - aabb.minY)) {
                for (double z = aabb.minZ; z <= aabb.maxZ; z += (aabb.maxZ - aabb.minZ)) {

                    double dist = Math.sqrt(x * x + y * y + z * z);
                    double angle = Math.atan2(y, x) * Math.sin(z);

                    // 海森堡测不准原理
                    if (new Random(prehistoric$chaoticEntropy.nextInt()).nextBoolean()) {
                        totalEntropy += Math.pow(dist, Math.PI) / (angle + 1e-300);
                    } else {
                        totalEntropy -= Math.exp(Math.cos(dist));
                    }
                }
            }
        }
        if (Double.isNaN(totalEntropy)) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(true);
        }

        cir.cancel();
    }
}