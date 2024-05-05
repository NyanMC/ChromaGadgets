package com.chromanyan.chromagadgets.items;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBastionApple extends Item {
    public ItemBastionApple() {
        super(new Item.Properties()
                .stacksTo(64)
                .food(new FoodProperties.Builder()
                        .nutrition(8)
                        .saturationMod(0.7F)
                        .alwaysEat()
                        // seconds * TPS
                        .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 10 * 20, 2), 1)
                        .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 20 * 20), 0.8F)
                        .build()
                ));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("tooltip.chromagadgets.bastion_apple.1"));
        list.add(Component.translatable("tooltip.chromagadgets.bastion_apple.2"));
    }

    private boolean handlePiglinOrHoglin(@NotNull LivingEntity livingEntity, boolean isClient) {
        // if this is run from the client, only return the checks, don't actually do anything
        if (isClient) return livingEntity instanceof AbstractPiglin || livingEntity instanceof Hoglin;

        if (livingEntity instanceof AbstractPiglin piglin) {
            piglin.setImmuneToZombification(true);
            piglin.setPersistenceRequired();
            return true;
        }

        if (livingEntity instanceof Hoglin hoglin) {
            hoglin.setImmuneToZombification(true);
            hoglin.setPersistenceRequired();
            return true;
        }

        return false;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        boolean isClient = player.getCommandSenderWorld().isClientSide();

        if (handlePiglinOrHoglin(livingEntity, isClient)) {
            if (isClient) return InteractionResult.SUCCESS;

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            player.getCommandSenderWorld().playSound(null, player.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.HOSTILE, 0.5F, 1.0F);
            player.awardStat(Stats.ITEM_USED.get(this));

            return InteractionResult.CONSUME;
        }

        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}
