package com.chromanyan.chromagadgets.enchantments;

import com.chromanyan.chromagadgets.init.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class EnchantmentSlipperiness extends Enchantment {
    public EnchantmentSlipperiness() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinCost(int p_45017_) {
        return 20;
    }

    @Override
    public int getMaxCost(int p_45027_) {
        return 35;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment otherEnchantment) {
        return super.checkCompatibility(otherEnchantment) && otherEnchantment != ModEnchantments.FRICTION.get();
    }
}
