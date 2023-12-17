package com.chromanyan.chromagadgets.init;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.items.ItemSculkometer;
import com.chromanyan.chromagadgets.items.ItemWhiteFlag;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChromaGadgets.MODID);

    public static final RegistryObject<Item> SCULKOMETER = ITEMS_REGISTRY.register("sculkometer", ItemSculkometer::new);
    public static final RegistryObject<Item> WHITE_FLAG = ITEMS_REGISTRY.register("white_flag", ItemWhiteFlag::new);
}
