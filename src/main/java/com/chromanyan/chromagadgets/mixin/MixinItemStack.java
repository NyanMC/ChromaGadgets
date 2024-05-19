package com.chromanyan.chromagadgets.mixin;

import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// i'm so sorry
@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @SuppressWarnings({"UnreachableCode", "DataFlowIssue"})
    @Inject(method = "is(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    private void is(Item p_150931_, CallbackInfoReturnable<Boolean> cir) {
        // we only care about bundles
        if (!p_150931_.equals(Items.BUNDLE)) return;

        Item item = ((ItemStack)(Object)this).getItem();
        cir.setReturnValue(item == Items.BUNDLE || item == ModItems.WANDERING_BUNDLE.get());
    }

}
