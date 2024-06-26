package com.chromanyan.chromagadgets.datagen;

import com.chromanyan.chromagadgets.ChromaGadgets;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CGModels extends ItemModelProvider {
    public CGModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ChromaGadgets.MODID, existingFileHelper);
    }

    private void basicModel(String name) {
        this.singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name));
    }

    private void heldModel(String name) {
        this.singleTexture(name, mcLoc("item/handheld"), "layer0", modLoc("item/" + name));
    }

    @Override
    public void registerModels() {
        heldModel("white_flag");
        basicModel("arcane_reroll");
        heldModel("mossy_mirror");
        basicModel("bastion_apple");
        heldModel("degraveler");
        heldModel("archaeologist_pick");
    }
}
