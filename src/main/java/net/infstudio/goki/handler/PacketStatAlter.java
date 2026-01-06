package net.infstudio.goki.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.infstudio.goki.GokiStats;
import net.infstudio.goki.lib.Helper;
import net.infstudio.goki.stats.Stat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketStatAlter extends AbstractPacket {
    int stat;
    int amount;

    public PacketStatAlter() {
    }

    public PacketStatAlter(final int stat, final int amount) {
        this.stat = stat;
        this.amount = amount;
    }

    @Override
    public void encodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        buffer.writeInt(this.stat);
        buffer.writeInt(this.amount);
    }

    @Override
    public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        this.stat = buffer.readInt();
        this.amount = buffer.readInt();
    }

    @Override
    public void handleClientSide(final EntityPlayer player) {
    }

    @Override
    public void handleServerSide(final EntityPlayer player) {
        if (player != null) {
            if (this.amount > 0) {
                final Stat stat = Stat.stats.get(this.stat);
                final int level = Helper.getPlayerStatLevel(player, stat);
                final int cost = stat.getCost(level + this.amount - 1);
                final int currentXP = Helper.getXPTotal(player.experienceLevel, player.experience);
                if (stat.enabled) {
                    if (level + this.amount > stat.getLimit()) {
                        this.amount = 0;
                    }
                    if (currentXP >= cost && this.amount != 0) {
                        Helper.setPlayerStatLevel(player, stat, level + this.amount);
                        if (this.amount > 0) {
                            Helper.setPlayersExpTo(player, currentXP - cost);
                        }
                    }
                }
            } else if (this.amount < 0) {
            }
            GokiStats.packetPipeline.sendTo(new PacketStatSync(player), (EntityPlayerMP) player);
            GokiStats.packetPipeline.sendTo(new PacketSyncXP(player), (EntityPlayerMP) player);
        }
    }
}