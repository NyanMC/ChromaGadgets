package com.chromanyan.chromagadgets.datagen;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CGRecipes extends RecipeProvider {
    public CGRecipes(DataGenerator gen) {
        super(gen);
    }

    @Override
    public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.SCULKOMETER.get(), 1)
                .pattern(" s ")
                .pattern("srs")
                .pattern(" s ")
                .define('s', Items.SCULK)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_sculk", has(Items.SCULK))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "sculkometer"));
    }
}
