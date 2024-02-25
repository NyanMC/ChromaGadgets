package com.chromanyan.chromagadgets.items;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ItemWanderingBundle extends BundleItem {
    private static final RandomSource random = RandomSource.create();
    private static final ModConfig.Common config = ModConfig.COMMON;

    public ItemWanderingBundle() {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_TOOLS)
                .rarity(Rarity.UNCOMMON)
                .stacksTo(1));
    }

    public static void addRandomTraderLoot(ItemStack bundle, Entity entity, int count) {
        VillagerTrades.ItemListing[] commonTrades = VillagerTrades.WANDERING_TRADER_TRADES.get(1);
        VillagerTrades.ItemListing[] rareTrades = VillagerTrades.WANDERING_TRADER_TRADES.get(2);
        if (commonTrades == null || rareTrades == null) return;

        for (int h = 0; h < count; h++) {
            VillagerTrades.ItemListing listing;
            if (random.nextInt(6) == 0) {
                int i = random.nextInt(rareTrades.length);
                listing = rareTrades[i];
            } else {
                int i = random.nextInt(commonTrades.length);
                listing = commonTrades[i];
            }

            MerchantOffer merchantOffer = listing.getOffer(entity, random);
            if (merchantOffer == null) {
                ChromaGadgets.LOGGER.warn("getOffer returned null when adding trader loot to Wandering Bundle, skipping one stack");
                continue;
            }
            ItemStack lootItemStack = merchantOffer.getResult();

            add(bundle, lootItemStack);
        }
    }

    public static void registerVariants() {
        ItemProperties.register(ModItems.WANDERING_BUNDLE.get(), new ResourceLocation(ChromaGadgets.MODID, "filled"),
                (stack, world, entity, thing) -> getFullnessDisplay(stack));
    }

    private static int getCapacity(@SuppressWarnings("unused") ItemStack itemStack) {
        return config.wanderingBundleCapacity.get();
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack p_150733_, @NotNull Slot p_150734_, @NotNull ClickAction p_150735_, @NotNull Player p_150736_) {
        if (p_150733_.getCount() != 1 || p_150735_ != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack itemstack = p_150734_.getItem();
            if (itemstack.isEmpty()) {
                this.playRemoveOneSound(p_150736_);
                removeOne(p_150733_).ifPresent((p_150740_) -> add(p_150733_, p_150734_.safeInsert(p_150740_)));
            } else if (itemstack.getItem().canFitInsideContainerItems()) {
                int i = (getCapacity(p_150733_) - getContentWeight(p_150733_)) / getWeight(itemstack);
                int j = add(p_150733_, p_150734_.safeTake(itemstack.getCount(), i, p_150736_));
                if (j > 0) {
                    this.playInsertSound(p_150736_);
                }
            }

            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack p_150742_, @NotNull ItemStack p_150743_, @NotNull Slot p_150744_, @NotNull ClickAction p_150745_, @NotNull Player p_150746_, @NotNull SlotAccess p_150747_) {
        if (p_150742_.getCount() != 1) return false;
        if (p_150745_ == ClickAction.SECONDARY && p_150744_.allowModification(p_150746_)) {
            if (p_150743_.isEmpty()) {
                removeOne(p_150742_).ifPresent((p_186347_) -> {
                    this.playRemoveOneSound(p_150746_);
                    p_150747_.set(p_186347_);
                });
            } else {
                int i = add(p_150742_, p_150743_);
                if (i > 0) {
                    this.playInsertSound(p_150746_);
                    p_150743_.shrink(i);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private static int add(ItemStack p_150764_, ItemStack p_150765_) {
        if (!p_150765_.isEmpty() && p_150765_.getItem().canFitInsideContainerItems()) {
            CompoundTag compoundtag = p_150764_.getOrCreateTag();
            if (!compoundtag.contains("Items")) {
                compoundtag.put("Items", new ListTag());
            }

            int i = getContentWeight(p_150764_);
            int j = getWeight(p_150765_);
            int k = Math.min(p_150765_.getCount(), (getCapacity(p_150764_) - i) / j);
            if (k == 0) {
                return 0;
            } else {
                ListTag listtag = compoundtag.getList("Items", 10);
                Optional<CompoundTag> optional = getMatchingItem(p_150765_, listtag);
                if (optional.isPresent() && ItemStack.of(optional.get()).getCount() + p_150765_.getCount() < p_150765_.getMaxStackSize()) {
                    CompoundTag compoundtag1 = optional.get();
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    itemstack.grow(k);
                    itemstack.save(compoundtag1);
                    listtag.remove(compoundtag1);
                    listtag.add(0, compoundtag1);
                } else {
                    ItemStack itemstack1 = p_150765_.copy();
                    itemstack1.setCount(k);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, compoundtag2);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getBarWidth(@NotNull ItemStack p_150771_) {
        return Math.min(1 + 12 * getContentWeight(p_150771_) / getCapacity(p_150771_), 13);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_150749_, @NotNull Level p_150750_, @NotNull List<Component> p_150751_, @NotNull TooltipFlag p_150752_) {
        p_150751_.add(Component.translatable("item.minecraft.bundle.fullness", getContentWeight(p_150749_), getCapacity(p_150749_)).withStyle(ChatFormatting.GRAY));
    }

    private static int getContentWeight(ItemStack p_150779_) {
        return getContents(p_150779_).mapToInt((p_186356_) -> getWeight(p_186356_) * p_186356_.getCount()).sum();
    }

    private static Stream<ItemStack> getContents(ItemStack p_150783_) {
        CompoundTag compoundtag = p_150783_.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList("Items", 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    private static int getWeight(ItemStack p_150777_) {
        if (p_150777_.getItem() instanceof BundleItem) {
            return 4 + getContentWeight(p_150777_);
        } else {
            if ((p_150777_.is(Items.BEEHIVE) || p_150777_.is(Items.BEE_NEST)) && p_150777_.hasTag()) {
                CompoundTag compoundtag = BlockItem.getBlockEntityData(p_150777_);
                if (compoundtag != null && !compoundtag.getList("Bees", 10).isEmpty()) {
                    return 64;
                }
            }

            return 64 / p_150777_.getMaxStackSize();
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack p_150757_, ListTag p_150758_) {
        return p_150757_.is(Items.BUNDLE) ? Optional.empty() : p_150758_.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((p_186350_) -> ItemStack.isSameItemSameTags(ItemStack.of(p_186350_), p_150757_)).findFirst();
    }

    private static Optional<ItemStack> removeOne(ItemStack p_150781_) {
        CompoundTag compoundtag = p_150781_.getOrCreateTag();
        if (!compoundtag.contains("Items")) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList("Items", 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    p_150781_.removeTagKey("Items");
                }

                return Optional.of(itemstack);
            }
        }
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack p_150775_) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getContents(p_150775_).forEach(nonnulllist::add);
        int weight = getContentWeight(p_150775_);
        // so the bundle tooltip is hardcoded to display an x if the weight is 64, no matter what. the solution? lie
        if (weight >= getCapacity(p_150775_)) {
            return Optional.of(new BundleTooltip(nonnulllist, 64));
        } else if (getCapacity(p_150775_) <= 64) {
            return Optional.of(new BundleTooltip(nonnulllist, getContentWeight(p_150775_))); // we don't need to modify behavior
        } else {
            return Optional.of(new BundleTooltip(nonnulllist, getContentWeight(p_150775_) / ((getCapacity(p_150775_) / 64) + 1))); // close enough
        }
    }

    private void playRemoveOneSound(Entity p_186343_) {
        p_186343_.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + p_186343_.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity p_186352_) {
        p_186352_.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + p_186352_.getLevel().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false; // we're not about to deal with bundle recursion
    }
}
