package com.chromanyan.chromagadgets.mixin;

import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.init.ModEnchantments;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LivingEntity.class)
public abstract class MixinLivingEntity {

    @Unique
    private static final ModConfig.Common chromaGadgets$config = ModConfig.COMMON;

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    private float getFriction(float original) {
        LivingEntity trueThis = (LivingEntity)(Object) this;

        if (
                original > chromaGadgets$config.defaultFriction.get().floatValue()
                        && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FRICTION.get(), trueThis) > 0
        ) return chromaGadgets$config.defaultFriction.get().floatValue();

        if (trueThis.getBlockSpeedFactor() > 1.0F && chromaGadgets$config.ignoreSpeedyBlocks.get()) return original;

        if (
                EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SLIPPERINESS.get(), trueThis) > 0
                        && original < chromaGadgets$config.slipperyFriction.get().floatValue()
        ) return chromaGadgets$config.slipperyFriction.get().floatValue();

        return original;
    }
}
