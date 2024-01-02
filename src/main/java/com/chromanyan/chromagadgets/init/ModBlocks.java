package com.chromanyan.chromagadgets.init;

import com.chromanyan.chromagadgets.ChromaGadgets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ChromaGadgets.MODID);

    public static final RegistryObject<Block> ASPHALT = BLOCKS_REGISTRY.register("asphalt", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F).speedFactor(1.2F)));
}
