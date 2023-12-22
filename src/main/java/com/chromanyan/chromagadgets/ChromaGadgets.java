package com.chromanyan.chromagadgets;

import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.datagen.CGAdvancements;
import com.chromanyan.chromagadgets.datagen.CGModels;
import com.chromanyan.chromagadgets.datagen.CGRecipes;
import com.chromanyan.chromagadgets.events.GadgetEvents;
import com.chromanyan.chromagadgets.init.ModItems;
import com.chromanyan.chromagadgets.items.ItemSculkometer;
import com.chromanyan.chromagadgets.items.ItemShriekerHorn;
import com.chromanyan.chromagadgets.packets.CGPacketHandler;
import com.chromanyan.chromagadgets.packets.client.PacketWarningLevel;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
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
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.commonSpec);
        modEventBus.register(ModConfig.class);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new CGModels(gen, efh));
        gen.addProvider(event.includeServer(), new CGRecipes(gen));
        gen.addProvider(event.includeServer(), new CGAdvancements(gen, efh));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new GadgetEvents());

        CGPacketHandler.INSTANCE.registerMessage(0, PacketWarningLevel.class, PacketWarningLevel::encode, PacketWarningLevel::decode, PacketWarningLevel::handle);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemSculkometer.registerVariants();
        ItemShriekerHorn.registerVariants();
    }
}
