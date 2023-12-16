package com.chromanyan.chromagadgets.init;

import com.chromanyan.chromagadgets.ChromaGadgets;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChromaGadgets.MODID);
}
