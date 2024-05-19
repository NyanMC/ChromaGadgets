package com.chromanyan.chromagadgets.datagen.tags;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModBlocks;
import com.chromanyan.chromagadgets.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CGBlockTags extends BlockTagsProvider {

    public CGBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ChromaGadgets.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ASPHALT.get());

        tag(ModTags.Blocks.MINEABLE_WITH_PICKSHOVEL).addTag(BlockTags.MINEABLE_WITH_PICKAXE);
        tag(ModTags.Blocks.MINEABLE_WITH_PICKSHOVEL).addTag(BlockTags.MINEABLE_WITH_SHOVEL);
    }
}
