package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
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

    public ItemSculkometer() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_TOOLS)
                .stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.chromagadgets.sculkometer.1"));
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains("warningLevel")){
            int warningLevel = tag.getInt("warningLevel");
            list.add(Component.translatable("tooltip.chromagadgets.sculkometer.level." + warningLevel));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        if (!(entity instanceof Player player) || level.isClientSide()) {
            return;
        }

        WardenSpawnTracker wardenSpawnTracker = player.getWardenSpawnTracker();
        CompoundTag tag = itemStack.getOrCreateTag();
        int warningLevel = wardenSpawnTracker.getWarningLevel();

        tag.putInt("warningLevel", warningLevel);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerVariants() {
        ItemProperties.register(ModItems.SCULKOMETER.get(), new ResourceLocation(ChromaGadgets.MODID, "warning_level"),
                (stack, world, entity, thing) -> stack.getOrCreateTag().getInt("warningLevel"));
        // getInt() returns 0 if the tag doesn't exist, which is what we want for the default value anyway
    }
}
