package com.github.ysbbbbbb.kaleidoscopetavern.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class IncenseParticle extends SingleQuadParticle {
    protected final float baseAlpha;

    protected IncenseParticle(ClientLevel level, double x, double y, double z,
                              double xSpeed, double ySpeed, double zSpeed,
                              SpriteSet sprites) {
        super(level, x, y, z, sprites.get(level.random));
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.lifetime = 40 + level.random.nextInt(20);
        this.gravity = -0.002F;
        this.quadSize = 0.15F + level.random.nextFloat() * 0.05F;
        this.baseAlpha = 0.8F;
        this.alpha = this.baseAlpha;
        this.hasPhysics = false;
    }

    @Override
    public @NotNull SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.xd += (this.random.nextDouble() - 0.5) * 0.001;
        this.zd += (this.random.nextDouble() - 0.5) * 0.001;

        float progress = (float) this.age / this.lifetime;
        if (progress > 0.75F) {
            this.alpha = this.baseAlpha * (1.0F - (progress - 0.75F) * 4.0F);
        }

        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.95;
        this.zd *= 0.95;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public SingleQuadParticle createParticle(
                @NotNull SimpleParticleType type, @NotNull ClientLevel level,
                double x, double y, double z,
                double xSpeed, double ySpeed, double zSpeed,
                @NotNull RandomSource randomSource
        ) {
            return new IncenseParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}
