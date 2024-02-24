package com.chromanyan.chromagadgets.mixin;

import com.chromanyan.chromagadgets.init.ModEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodOnAStickItem.class)
public class MixinFoodOnAStickItem {

    /**
     * Injects after the user has boosted, and just before the ItemStack takes damage.
     */
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private void additionalUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!(player.getVehicle() instanceof LivingEntity livingVehicle)) return; // the vehicle should always be living, unless mods do weird things

        int mountHealingLevel = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.MOUNT_HEALING.get(), player.getItemInHand(hand));

        livingVehicle.heal(mountHealingLevel * 2F); //TODO make this configurable
    }
}
