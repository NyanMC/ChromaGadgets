package com.chromanyan.chromagadgets.events;

import com.chromanyan.chromagadgets.packets.CGPacketHandler;
import com.chromanyan.chromagadgets.packets.client.PacketWarningLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
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
}
