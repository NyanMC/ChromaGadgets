package com.chromanyan.chromagadgets.init;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.enchantments.EnchantmentFriction;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS_REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ChromaGadgets.MODID);

    public static final RegistryObject<Enchantment> FRICTION = ENCHANTMENTS_REGISTRY.register("friction", EnchantmentFriction::new);
}
