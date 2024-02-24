package com.chromanyan.chromagadgets.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class EnchantmentMountHealing extends Enchantment {
    public EnchantmentMountHealing() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int p_45017_) {
        return 10 + (5 * p_45017_);
    }

    @Override
    public int getMaxCost(int p_45027_) {
        return super.getMinCost(p_45027_) + 30;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack itemStack) {
        return itemStack.getItem() instanceof FoodOnAStickItem;
    }
}
