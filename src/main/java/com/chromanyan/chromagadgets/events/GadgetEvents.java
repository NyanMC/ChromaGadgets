package com.chromanyan.chromagadgets.events;

import com.chromanyan.chromagadgets.config.ModConfig;
import com.chromanyan.chromagadgets.init.ModItems;
import com.chromanyan.chromagadgets.items.ItemWanderingBundle;
import com.chromanyan.chromagadgets.packets.CGPacketHandler;
import com.chromanyan.chromagadgets.packets.client.PacketWarningLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("unused") // intellij is dead-set on thinking all subscribe events are unused
public class GadgetEvents {
    public static HashMap<UUID, Integer> playerWarningLevels = new HashMap<>();

    private static final Random rand = new Random();
    private static final ModConfig.Common config = ModConfig.COMMON;

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT)
            return;
        Player player = event.player;
        UUID uuid = player.getUUID();
        if (player.getWardenSpawnTracker().isEmpty()) return;
        int warningLevel = player.getWardenSpawnTracker().get().getWarningLevel();

        if (player instanceof ServerPlayer serverPlayer) {
            if (!playerWarningLevels.containsKey(uuid)) {
                playerWarningLevels.put(uuid, warningLevel);
                CGPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PacketWarningLevel(warningLevel));
            } else if (playerWarningLevels.get(uuid) != warningLevel) {
                playerWarningLevels.replace(uuid, warningLevel);
                CGPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PacketWarningLevel(warningLevel));
            }
        }
    }

    @SubscribeEvent
    public void villagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            event.getTrades().get(4).add(new BasicItemListing(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(Items.BOOK),
                    new ItemStack(ModItems.ARCANE_REROLL.get()),
                    6,
                    3,
                    0.2F
            ));
        }
    }

    @SubscribeEvent
    public void mobDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof WanderingTrader && config.dropWanderingBundle.get()) {
            ItemStack bundle = new ItemStack(ModItems.WANDERING_BUNDLE.get());
            int min = config.wanderingBundleMinItemsGenerated.get();
            int max = config.wanderingBundleMaxItemsGenerated.get();
            if (max > 0) {
                int count;
                if (min >= max) {
                    count = min;
                } else {
                    count = rand.nextInt(min, max + 1);
                }

                ItemWanderingBundle.addRandomTraderLoot(bundle, entity, count);
            }
            event.getDrops().add(entity.spawnAtLocation(bundle));
        }
    }
}
