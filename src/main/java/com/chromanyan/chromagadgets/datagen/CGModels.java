package com.chromanyan.chromagadgets.datagen;

import com.chromanyan.chromagadgets.ChromaGadgets;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CGModels extends ItemModelProvider {
    public CGModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ChromaGadgets.MODID, existingFileHelper);
    }

    @SuppressWarnings("SameParameterValue")
    private void basicModel(String name) {
        this.singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
    }

    @Override
    public void registerModels() {
        basicModel("white_flag");
    }
}
