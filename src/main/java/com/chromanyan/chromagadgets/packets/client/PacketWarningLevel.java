package com.chromanyan.chromagadgets.packets.client;

import com.chromanyan.chromagadgets.items.ItemSculkometer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketWarningLevel {

    @SuppressWarnings("FieldMayBeFinal")
    private int warningLevel;

    public PacketWarningLevel(int warningLevel) {
        this.warningLevel = warningLevel;
    }

    public static void encode(PacketWarningLevel msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.warningLevel);
    }

    public static PacketWarningLevel decode(FriendlyByteBuf buf) {
        return new PacketWarningLevel(buf.readInt());
    }

    public static void handle(PacketWarningLevel msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ItemSculkometer.setClientWarningLevel(msg.warningLevel));
        ctx.get().setPacketHandled(true);
    }
}
