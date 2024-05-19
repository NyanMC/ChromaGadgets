package com.chromanyan.chromagadgets.datagen;

import com.chromanyan.chromagadgets.ChromaGadgets;
import com.chromanyan.chromagadgets.init.ModItems;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CGRecipes extends RecipeProvider {

    public CGRecipes(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCULKOMETER.get(), 1)
                .pattern(" s ")
                .pattern("srs")
                .pattern(" s ")
                .define('s', Items.SCULK)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_sculk", has(Items.SCULK))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "sculkometer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WHITE_FLAG.get(), 1)
                .pattern("sww")
                .pattern("sew")
                .pattern("s  ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('w', Items.WHITE_WOOL)
                .define('e', Tags.Items.GEMS_EMERALD)
                .unlockedBy("has_bad_omen", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.effects().and(MobEffects.BAD_OMEN)))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "white_flag"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHRIEKER_HORN.get(), 1)
                .pattern("hs")
                .pattern("sg")
                .define('s', Items.SCULK)
                .define('h', Items.SCULK_SHRIEKER)
                .define('g', Items.GOAT_HORN)
                .unlockedBy("has_shrieker", has(Items.SCULK_SHRIEKER))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "shrieker_horn"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ARCHAEOLOGIST_PICK.get(), 1)
                .pattern("pds")
                .pattern(" w ")
                .pattern(" w ")
                .define('d', ItemTags.DECORATED_POT_SHERDS)
                .define('p', Items.STONE_PICKAXE)
                .define('s', Items.STONE_SHOVEL)
                .define('w', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_sherd", has(ItemTags.DECORATED_POT_SHERDS))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "archaeologist_pick"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOSSY_MIRROR.get(), 1)
                .pattern(" mr")
                .pattern("mgm")
                .pattern("sm ")
                .define('m', Items.MOSS_BLOCK)
                .define('r', Items.RECOVERY_COMPASS)
                .define('g', Tags.Items.GLASS)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_rcompass", has(Items.RECOVERY_COMPASS))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "mossy_mirror"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.ASPHALT.get(), 4)
                .requires(Items.BLACK_CONCRETE_POWDER)
                .requires(Items.BLACK_CONCRETE_POWDER)
                .requires(Tags.Items.COBBLESTONE)
                .requires(Tags.Items.COBBLESTONE)
                .requires(Items.CHARCOAL)
                .unlockedBy("has_black_concrete_powder", has(Items.BLACK_CONCRETE_POWDER))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "asphalt"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BASTION_APPLE.get())
                .requires(Items.APPLE)
                .requires(Items.GILDED_BLACKSTONE)
                .unlockedBy("has_gilded_blackstone", has(Items.GILDED_BLACKSTONE))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "bastion_apple"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.DEGRAVELER.get())
                .requires(Items.FLINT)
                .requires(Items.IRON_SHOVEL)
                .unlockedBy("degraveler", has(Items.IRON_SHOVEL))
                .save(consumer, new ResourceLocation(ChromaGadgets.MODID, "degraveler"));
    }
}
