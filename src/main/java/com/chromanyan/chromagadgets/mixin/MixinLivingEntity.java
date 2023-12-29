package com.chromanyan.chromagadgets.mixin;

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

@Mixin(value = LivingEntity.class, remap = false)
public class MixinLivingEntity {
    @Unique
    private static final float DEFAULT_FRICTION = 0.6F; // at least i'm pretty sure?

    // can't mixin inject into interfaces, otherwise i would have opted for that
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    private float getFriction(BlockState instance, LevelReader levelReader, BlockPos blockPos, Entity entity) {
        float originalReturn = instance.getFriction(levelReader, blockPos, entity);
        if (
                originalReturn <= DEFAULT_FRICTION
                || !(entity instanceof LivingEntity livingEntity)
                || EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FRICTION.get(), livingEntity) == 0
        ) return originalReturn;

        return DEFAULT_FRICTION;
    }
}
