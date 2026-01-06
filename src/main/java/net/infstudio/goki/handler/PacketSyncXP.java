package net.infstudio.goki.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSyncXP extends AbstractPacket {
    float experience;
    int experienceLevel;
    int experienceTotal;

    public PacketSyncXP() {
    }

    public PacketSyncXP(final EntityPlayer player) {
        this.experience = player.experience;
        this.experienceLevel = player.experienceLevel;
        this.experienceTotal = player.experienceTotal;
    }

    public PacketSyncXP(final float experience, final int experienceLevel, final int experienceTotal) {
        this.experience = experience;
        this.experienceLevel = experienceLevel;
        this.experienceTotal = experienceTotal;
    }

    @Override
    public void encodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        buffer.writeFloat(this.experience);
        buffer.writeInt(this.experienceLevel);
        buffer.writeInt(this.experienceTotal);
    }

    @Override
    public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        this.experience = buffer.readFloat();
        this.experienceLevel = buffer.readInt();
        this.experienceTotal = buffer.readInt();
    }

    @Override
    public void handleClientSide(final EntityPlayer player) {
        player.experience = this.experience;
        player.experienceLevel = this.experienceLevel;
        player.experienceTotal = this.experienceTotal;
    }

    @Override
    public void handleServerSide(final EntityPlayer player) {
        player.experience = this.experience;
        player.experienceLevel = this.experienceLevel;
        player.experienceTotal = this.experienceTotal;
    }
}