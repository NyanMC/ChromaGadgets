package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemSculkometer extends Item {

    private static int clientWarningLevel = 0;

    public ItemSculkometer() {
        super(new Item.Properties()
                .stacksTo(1));
    }

    // don't call this on the server it's a waste of your time
    public static void setClientWarningLevel(int clientWarningLevel) {
        ItemSculkometer.clientWarningLevel = clientWarningLevel;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.chromagadgets.sculkometer.1"));
    }

    public static boolean isValid(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("isValid");
        // prevents JEI cheating by forcing the item to be in the inventory for at least one tick
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        if (!(entity instanceof Player) || level.isClientSide()) {
            return;
        }

        CompoundTag tag = itemStack.getOrCreateTag();

        if (!tag.contains("isValid"))
            tag.putBoolean("isValid", true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerVariants() {
        ItemProperties.register(ModItems.SCULKOMETER.get(), new ResourceLocation(ChromaGadgets.MODID, "warning_level"),
                (stack, world, entity, thing) -> {
                    if (isValid(stack))
                        return clientWarningLevel;
                    return 0;
                });
    }
}
