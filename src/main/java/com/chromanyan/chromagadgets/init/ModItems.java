package com.chromanyan.chromagadgets.init;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.items.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChromaGadgets.MODID);

    public static final RegistryObject<Item> SCULKOMETER = ITEMS_REGISTRY.register("sculkometer", ItemSculkometer::new);
    public static final RegistryObject<Item> WHITE_FLAG = ITEMS_REGISTRY.register("white_flag", ItemWhiteFlag::new);
    public static final RegistryObject<Item> SHRIEKER_HORN = ITEMS_REGISTRY.register("shrieker_horn", ItemShriekerHorn::new);
    public static final RegistryObject<Item> ARCANE_REROLL = ITEMS_REGISTRY.register("arcane_reroll", ItemArcaneReroll::new);
    public static final RegistryObject<Item> MOSSY_MIRROR = ITEMS_REGISTRY.register("mossy_mirror", ItemMossyMirror::new);
    public static final RegistryObject<Item> BASTION_APPLE = ITEMS_REGISTRY.register("bastion_apple", ItemBastionApple::new);
    public static final RegistryObject<Item> WANDERING_BUNDLE = ITEMS_REGISTRY.register("wandering_bundle", ItemWanderingBundle::new);
    public static final RegistryObject<Item> DEGRAVELER = ITEMS_REGISTRY.register("degraveler",
            () -> new ItemDegraveler(Tiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> ARCHAEOLOGIST_PICK = ITEMS_REGISTRY.register("archaeologist_pick",
            () -> new ItemArchaeologistPick(ModToolMaterials.ARCHAEOLOGIST, new Item.Properties()));

    // blockitems
    public static final RegistryObject<Item> ASPHALT = ITEMS_REGISTRY.register("asphalt",
            () -> new BlockItem(ModBlocks.ASPHALT.get(),
                    new Item.Properties().stacksTo(64)));
}
