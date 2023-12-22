package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.config.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ItemMossyMirror extends Item {

    ModConfig.Common config = ModConfig.COMMON;

    private static final DamageSource RECALL = new DamageSource("chromagadgets.recall").bypassArmor().bypassMagic().bypassEnchantments().bypassInvul();
    // Do you remember how you got here?

    public ItemMossyMirror() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_TOOLS)
                .stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.getBoolean("charged")) {
            list.add(Component.translatable("tooltip.chromagadgets.mossy_mirror.charged.1").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("tooltip.chromagadgets.mossy_mirror.charged.2").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("tooltip.chromagadgets.mossy_mirror.charged.3").withStyle(ChatFormatting.RED));
            return;
        }

        list.add(Component.translatable("tooltip.chromagadgets.mossy_mirror.1"));

        if (!nbt.contains("deathCoordsDisplay")) {
            return;
        }

        list.add(Component.translatable("tooltip.chromagadgets.mossy_mirror.2").withStyle(ChatFormatting.GRAY));
        list.add(Component.literal(nbt.getString("deathCoordsDisplay")).withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("tooltip.chromagadgets.mossy_mirror.3").withStyle(ChatFormatting.AQUA));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        CompoundTag nbt = itemStack.getOrCreateTag();

        if (!nbt.getBoolean("charged")) {
            return handleCharge(level, player, itemStack);
        }

        Optional<GlobalPos> deathPosOptional = player.getLastDeathLocation();
        if (deathPosOptional.isEmpty()) return InteractionResultHolder.fail(itemStack);

        GlobalPos deathPos = deathPosOptional.get();
        player.teleportTo(deathPos.pos().getX(), deathPos.pos().getY(), deathPos.pos().getZ());
        level.playSound(null, player.blockPosition(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 0.5F, 1.0F);

        player.hurt(RECALL, player.getMaxHealth() * config.mossyMirrorDamage.get().floatValue());
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, config.mossyMirrorWeaknessDuration.get(), config.mossyMirrorWeaknessAmplifier.get()));

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    private static InteractionResultHolder<ItemStack> handleCharge(@NotNull Level level, @NotNull Player player, @NotNull ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();

        if (!level.getBiome(player.blockPosition()).is(Tags.Biomes.IS_SWAMP)) {
            return InteractionResultHolder.fail(itemStack);
        }

        level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.5F, 1.0F);
        if (level.isClientSide()) {
            return InteractionResultHolder.success(itemStack);
        }

        nbt.putBoolean("charged", true);
        nbt.remove("deathCoordsDisplay");
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.getBoolean("charged")) return;
        if (!(entity instanceof Player player)) return;

        Optional<GlobalPos> deathPosOptional = player.getLastDeathLocation();
        if (deathPosOptional.isEmpty()) return;

        GlobalPos deathPos = deathPosOptional.get();
        nbt.putString("deathCoordsDisplay", deathPos.pos().toShortString());
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("charged");
    }
}
