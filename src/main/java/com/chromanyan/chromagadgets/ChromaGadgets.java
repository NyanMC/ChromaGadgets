package com.chromanyan.chromagadgets;

import com.chromanyan.chromagadgets.datagen.CGModels;
import com.chromanyan.chromagadgets.datagen.CGRecipes;
import com.chromanyan.chromagadgets.events.GadgetEvents;
import com.chromanyan.chromagadgets.init.ModItems;
import com.chromanyan.chromagadgets.items.ItemSculkometer;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChromaGadgets.MODID)
public class ChromaGadgets {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "chromagadgets";
    // Directly reference a slf4j logger
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public ChromaGadgets() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::gatherData);

        ModItems.ITEMS_REGISTRY.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new CGModels(gen, efh));
        gen.addProvider(event.includeServer(), new CGRecipes(gen));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new GadgetEvents());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemSculkometer.registerVariants();
    }
}
