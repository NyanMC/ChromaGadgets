package com.chromanyan.chromagadgets.init;

import com.chromanyan.chromagadgets.ChromaGadgets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        /**
            A combination of {@link BlockTags#MINEABLE_WITH_PICKAXE} and {@link BlockTags#MINEABLE_WITH_SHOVEL}.
         */
        public static final TagKey<Block> MINEABLE_WITH_PICKSHOVEL = BlockTags.create(new ResourceLocation(ChromaGadgets.MODID, "mineable/pickshovel"));
    }
}
