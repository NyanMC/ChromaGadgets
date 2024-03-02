package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.triggers.DegravelerVeinTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemDegraveler extends ShovelItem {

    ModConfig.Common config = ModConfig.COMMON;

    public ItemDegraveler(Tier p_43114_, Properties p_43117_) {
        super(p_43114_, 1.5F, -3.0F, p_43117_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.chromagadgets.degraveler.1"));
    }

    private boolean shouldStopVeining(ItemStack itemStack, BlockState blockState) {
        return itemStack.getCount() <= 0 // the shovel hasn't broken
                || !(blockState.getBlock() instanceof FallingBlock) // the block is a falling block
                || !isCorrectToolForDrops(itemStack, blockState); // the block can be mined with a shovel
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull BlockState blockState, @NotNull BlockPos blockPos, @NotNull LivingEntity livingEntity) {
        // first, we super mineBlock. if this is false, we do not care about anything else
        if (!super.mineBlock(itemStack, level, blockState, blockPos, livingEntity)) return false;

        // next, we need to make sure we can actually veinmine, otherwise return true (since we *did* mine the original block)
        if (shouldStopVeining(itemStack, blockState) || !(livingEntity instanceof Player player)) return true;

        int count = 0;
        BlockPos lastBlockPos = blockPos;
        final int limit = config.degravelerVeinLimit.get() > -1 ? config.degravelerVeinLimit.get() : Integer.MAX_VALUE;

        count++;
        // there can't possibly be any more blocks past the max build height
        while (count + blockPos.getY() <= level.getMaxBuildHeight() && (config.degravelerVeinLimit.get() < limit)) {
            BlockPos newBlockPos = lastBlockPos.above();
            lastBlockPos = newBlockPos;

            BlockState newBlockState = level.getBlockState(newBlockPos);
            // make sure this is even a valid block for the degraveler
            if (shouldStopVeining(itemStack, newBlockState)) break;

            // if mining the block fails, break
            if (super.mineBlock(itemStack, level, newBlockState, newBlockPos, livingEntity)
                    && level.destroyBlock(newBlockPos, false, livingEntity)) {
                newBlockState.getBlock().playerDestroy(level, player, newBlockPos, newBlockState, level.getBlockEntity(newBlockPos), itemStack);
            } else {
                break;
            }

            count++;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            System.out.println(count);
            DegravelerVeinTrigger.INSTANCE.trigger(serverPlayer, count);
        }

        return true;
    }
}
