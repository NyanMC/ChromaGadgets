package com.chromanyan.chromagadgets.mixin;

import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "org.violetmoon.quark.content.management.module.ExpandedItemInteractionsModule", remap = false)
public class MixinExpandedItemInteractionsModule {

    @Redirect(method = "rotateBundle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean isBundleOnRotateBundle(ItemStack instance, Item p_150931_) {
        return instance.getItem() instanceof BundleItem; // apply duct tape to solve problem
    }
}
