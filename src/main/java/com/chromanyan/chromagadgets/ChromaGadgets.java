package com.chromanyan.chromagadgets;

import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.datagen.CGAdvancements;
import com.chromanyan.chromagadgets.datagen.CGModels;
import com.chromanyan.chromagadgets.datagen.CGRecipes;
import com.chromanyan.chromagadgets.datagen.tags.CGBlockTags;
import com.chromanyan.chromagadgets.datagen.tags.CGItemTags;
import com.chromanyan.chromagadgets.events.GadgetEvents;
import com.chromanyan.chromagadgets.init.ModBlocks;
import com.chromanyan.chromagadgets.init.ModEnchantments;
import com.chromanyan.chromagadgets.init.ModItems;
import com.chromanyan.chromagadgets.items.ItemSculkometer;
import com.chromanyan.chromagadgets.items.ItemShriekerHorn;
import com.chromanyan.chromagadgets.items.ItemWanderingBundle;
import com.chromanyan.chromagadgets.packets.CGPacketHandler;
import com.chromanyan.chromagadgets.packets.client.PacketWarningLevel;
import com.chromanyan.chromagadgets.triggers.DegravelerVeinTrigger;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChromaGadgets.MODID)
public class ChromaGadgets {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "chromagadgets";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChromaGadgets() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::addCreative);

        ModEnchantments.ENCHANTMENTS_REGISTRY.register(modEventBus);
        ModBlocks.BLOCKS_REGISTRY.register(modEventBus);
        ModItems.ITEMS_REGISTRY.register(modEventBus);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.commonSpec);
        modEventBus.register(ModConfig.class);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new CGModels(output, efh));
        gen.addProvider(event.includeServer(), new CGRecipes(output));
        gen.addProvider(event.includeServer(), new CGAdvancements(output, lookupProvider, efh));

        CGBlockTags blockTags = new CGBlockTags(output, lookupProvider, efh);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new CGItemTags(output, lookupProvider, blockTags.contentsGetter(), efh));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new GadgetEvents());

        CriteriaTriggers.register(DegravelerVeinTrigger.INSTANCE);

        CGPacketHandler.INSTANCE.registerMessage(0, PacketWarningLevel.class, PacketWarningLevel::encode, PacketWarningLevel::decode, PacketWarningLevel::handle);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemSculkometer.registerVariants();
        ItemShriekerHorn.registerVariants();
        ItemWanderingBundle.registerVariants();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            event.accept(ModItems.ASPHALT.get());
        }
        if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(ModItems.ARCANE_REROLL.get());
            event.accept(ModItems.WHITE_FLAG.get());
            event.accept(ModItems.MOSSY_MIRROR.get());
            event.accept(ModItems.SCULKOMETER.get());
            event.accept(ModItems.SHRIEKER_HORN.get());
            event.accept(ModItems.DEGRAVELER.get());
            event.accept(ModItems.WANDERING_BUNDLE.get());
            event.accept(ModItems.ARCHAEOLOGIST_PICK.get());
        }
        if (event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            event.accept(ModItems.BASTION_APPLE.get());
        }
    }
}
