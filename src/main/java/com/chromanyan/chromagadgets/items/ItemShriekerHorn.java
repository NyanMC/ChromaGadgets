package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModItems;
import com.chromanyan.chromagadgets.util.WardenSpawnHandler;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemShriekerHorn extends Item {
    public ItemShriekerHorn() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_MISC)
                .stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.chromagadgets.shrieker_horn.1"));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_41454_) {
        return 200;
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack p_220133_) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.startUsingItem(hand);
        level.playSound(player, player, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.RECORDS, 16, 1.0F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
        player.getCooldowns().addCooldown(this, 200);

        if (!level.getBiome(player.blockPosition()).is(Biomes.DEEP_DARK)) {
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }

        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            if (WardenSpawnTracker.tryWarn(serverLevel, player.blockPosition(), serverPlayer).isPresent()) {
                int warningLevel = serverPlayer.getWardenSpawnTracker().getWarningLevel();
                level.gameEvent(GameEvent.SHRIEK, player.position(), GameEvent.Context.of(player));
                WardenSpawnHandler.tryRespond(serverLevel, player.blockPosition(), warningLevel);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerVariants() {
        ItemProperties.register(ModItems.SHRIEKER_HORN.get(), new ResourceLocation(ChromaGadgets.MODID, "tooting"),
                (stack, world, entity, thing) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
