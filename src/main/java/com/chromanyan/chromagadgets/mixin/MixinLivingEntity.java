package com.chromanyan.chromagadgets.mixin;

import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.init.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LivingEntity.class)
public abstract class MixinLivingEntity {

    @Unique
    private static final ModConfig.Common chromaGadgets$config = ModConfig.COMMON;

    // can't mixin inject into interfaces, otherwise i would have opted for that
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    private float getFriction(BlockState instance, LevelReader levelReader, BlockPos blockPos, Entity entity) {
        float originalReturn = instance.getFriction(levelReader, blockPos, entity);
        if (!(entity instanceof LivingEntity livingEntity)) return originalReturn;

        if (
                originalReturn > chromaGadgets$config.defaultFriction.get().floatValue()
                && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FRICTION.get(), livingEntity) > 0
        ) return chromaGadgets$config.defaultFriction.get().floatValue();

        if (livingEntity.getBlockSpeedFactor() > 1.0F) return originalReturn;

        if (
                EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SLIPPERINESS.get(), livingEntity) > 0
                && originalReturn < chromaGadgets$config.slipperyFriction.get().floatValue()
        ) return chromaGadgets$config.slipperyFriction.get().floatValue();

        return originalReturn;
    }
}
