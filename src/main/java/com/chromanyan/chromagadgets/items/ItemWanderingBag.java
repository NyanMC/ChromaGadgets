package com.chromanyan.chromagadgets.items;

import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemWanderingBag extends Item {
    private final RandomSource random = RandomSource.create();

    public ItemWanderingBag() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_MISC)
                .stacksTo(64)
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (level.isClientSide()) return InteractionResultHolder.success(itemstack);

        VillagerTrades.ItemListing[] commonTrades = VillagerTrades.WANDERING_TRADER_TRADES.get(1);
        VillagerTrades.ItemListing[] rareTrades = VillagerTrades.WANDERING_TRADER_TRADES.get(2);
        if (commonTrades == null || rareTrades == null) return InteractionResultHolder.fail(itemstack);

        VillagerTrades.ItemListing listing;
        if (this.random.nextInt(6) == 0) {
            int i = this.random.nextInt(rareTrades.length);
            listing = rareTrades[i];
        } else {
            int i = this.random.nextInt(commonTrades.length);
            listing = commonTrades[i];
        }

        MerchantOffer merchantOffer = listing.getOffer(player, random);
        if (merchantOffer == null) return InteractionResultHolder.fail(itemstack);

        ItemStack lootItemStack = merchantOffer.getResult();

        if (!player.getInventory().add(lootItemStack)) { // thanks farmer's delight
            player.drop(lootItemStack, false);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.consume(itemstack);
    }
}
