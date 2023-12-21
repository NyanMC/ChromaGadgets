package com.chromanyan.chromagadgets.events;

import com.chromanyan.chromagadgets.init.ModItems;
import com.chromanyan.chromagadgets.packets.CGPacketHandler;
import com.chromanyan.chromagadgets.packets.client.PacketWarningLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused") // intellij is dead-set on thinking all subscribe events are unused
public class GadgetEvents {
    public static HashMap<UUID, Integer> playerWarningLevels = new HashMap<>();

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT)
            return;
        Player player = event.player;
        UUID uuid = player.getUUID();
        int warningLevel = player.getWardenSpawnTracker().getWarningLevel();

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
}
