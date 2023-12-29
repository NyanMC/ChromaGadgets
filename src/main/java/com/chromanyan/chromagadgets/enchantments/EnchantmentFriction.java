package com.chromanyan.chromagadgets.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentFriction extends Enchantment {
    public EnchantmentFriction() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    public int getMinCost(int p_45017_) {
        return p_45017_ * 10;
    }

    public int getMaxCost(int p_45027_) {
        return this.getMinCost(p_45027_) + 15;
    }

    public boolean isTreasureOnly() {
        return true;
    }

}
