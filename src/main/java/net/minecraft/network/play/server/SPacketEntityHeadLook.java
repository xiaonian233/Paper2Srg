package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityHeadLook implements Packet<INetHandlerPlayClient> {

    private int entityId;
    private byte yaw;

    public SPacketEntityHeadLook() {}

    public SPacketEntityHeadLook(Entity entity, byte b0) {
        this.entityId = entity.getEntityId();
        this.yaw = b0;
    }

    public void readPacketData(PacketBuffer packetdataserializer) throws IOException {
        this.entityId = packetdataserializer.readVarInt();
        this.yaw = packetdataserializer.readByte();
    }

    public void writePacketData(PacketBuffer packetdataserializer) throws IOException {
        packetdataserializer.writeVarInt(this.entityId);
        packetdataserializer.writeByte(this.yaw);
    }

    public void processPacket(INetHandlerPlayClient packetlistenerplayout) {
        packetlistenerplayout.handleEntityHeadLook(this);
    }
}
