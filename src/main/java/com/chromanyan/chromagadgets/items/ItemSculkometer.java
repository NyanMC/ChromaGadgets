package com.chromanyan.chromagadgets.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
}
