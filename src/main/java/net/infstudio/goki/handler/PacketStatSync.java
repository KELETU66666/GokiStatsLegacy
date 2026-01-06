package net.infstudio.goki.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.infstudio.goki.stats.Stat;
import net.infstudio.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class PacketStatSync extends AbstractPacket {
    int[] statLevels;

    public PacketStatSync() {
    }

    public PacketStatSync(final EntityPlayer player) {
        this.statLevels = new int[Stat.stats.size()];
        for (int i = 0; i < this.statLevels.length; ++i) {
            if (Stat.stats.get(i) != null) {
                this.statLevels[i] = Helper.getPlayerStatLevel(player, Stat.stats.get(i));
            }
        }
    }

    @Override
    public void encodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        for (int i = 0; i < this.statLevels.length; ++i) {
            buffer.writeInt(this.statLevels[i]);
        }
    }

    @Override
    public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        this.statLevels = new int[Stat.stats.size()];
        for (int i = 0; i < this.statLevels.length; ++i) {
            this.statLevels[i] = buffer.readInt();
        }
    }

    @Override
    public void handleClientSide(final EntityPlayer player) {
        for (int i = 0; i < this.statLevels.length; ++i) {
            Helper.setPlayerStatLevel(player, Stat.stats.get(i), this.statLevels[i]);
        }
    }

    @Override
    public void handleServerSide(final EntityPlayer player) {
    }
}