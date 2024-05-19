package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemArchaeologistPick extends DiggerItem {
    public ItemArchaeologistPick(Tier p_204110_, Properties p_204112_) {
        super(1, -2.8F, p_204110_, ModTags.Blocks.MINEABLE_WITH_PICKSHOVEL, p_204112_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.chromagadgets.archaeologist_pick.1"));
        list.add(Component.translatable("tooltip.chromagadgets.archaeologist_pick.2"));
    }

    private boolean wontBreak(@NotNull Player player) {
        player.displayClientMessage(Component.translatable("message.chromagadgets.archaeologist_pick.wont_break"), true);
        return false;
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player) {
        if (blockState.getBlock() instanceof BrushableBlock) return wontBreak(player);

        BlockPos lastBlockPos = blockPos;

        while (lastBlockPos.getY() + 1 < level.getMaxBuildHeight()) {
            BlockPos newBlockPos = lastBlockPos.above();
            lastBlockPos = newBlockPos;

            BlockState newBlockState = level.getBlockState(newBlockPos);

            if (newBlockState.getBlock() instanceof BrushableBlock) return wontBreak(player);

            if (!(newBlockState.getBlock() instanceof Fallable)) break;
        }

        return super.canAttackBlock(blockState, level, blockPos, player);
    }
}
