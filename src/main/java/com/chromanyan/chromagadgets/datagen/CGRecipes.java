package com.chromanyan.chromagadgets.datagen;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
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

        ShapedRecipeBuilder.shaped(ModItems.WHITE_FLAG.get(), 1)
                .pattern("sww")
                .pattern("sew")
                .pattern("s  ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('w', Items.WHITE_WOOL)
                .define('e', Tags.Items.GEMS_EMERALD)
                .unlockedBy("has_bad_omen", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.effects().and(MobEffects.BAD_OMEN)))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "white_flag"));

        ShapedRecipeBuilder.shaped(ModItems.SHRIEKER_HORN.get(), 1)
                .pattern("hs")
                .pattern("sg")
                .define('s', Items.SCULK)
                .define('h', Items.SCULK_SHRIEKER)
                .define('g', Items.GOAT_HORN)
                .unlockedBy("has_shrieker", has(Items.SCULK_SHRIEKER))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "shrieker_horn"));
    }
}
