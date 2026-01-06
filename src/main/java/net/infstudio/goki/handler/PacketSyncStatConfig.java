package net.infstudio.goki.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import net.infstudio.goki.stats.Stat;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class PacketSyncStatConfig extends AbstractPacket {
    boolean deathLoss;
    float newBonus;
    float newCost;
    float newLimit;
    String[] statConfigStrings;

    public PacketSyncStatConfig() {
        this.deathLoss = true;
        this.newBonus = 1.0f;
        this.newCost = 1.0f;
        this.newLimit = 1.0f;
        this.statConfigStrings = new String[Stat.stats.size()];
    }

    public PacketSyncStatConfig(final boolean deathLoss, final float newBonus, final float newCost, final float newLimit) {
        this.deathLoss = deathLoss;
        this.newBonus = newBonus;
        this.newCost = newCost;
        this.newLimit = newLimit;
        this.statConfigStrings = new String[Stat.stats.size()];
        for (int i = 0; i < Stat.stats.size(); ++i) {
            this.statConfigStrings[i] = Stat.stats.get(i).toConfigurationString();
        }
    }

    @Override
    public void encodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        final ByteBufOutputStream bbos = new ByteBufOutputStream(buffer);
        try {
            bbos.writeBoolean(this.deathLoss);
            bbos.writeFloat(this.newBonus);
            bbos.writeFloat(this.newCost);
            bbos.writeFloat(this.newLimit);
            for (int i = 0; i < this.statConfigStrings.length; ++i) {
                if (this.statConfigStrings[i] != "") {
                    bbos.writeUTF(this.statConfigStrings[i]);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        final ByteBufInputStream bbis = new ByteBufInputStream(buffer);
        try {
            this.deathLoss = bbis.readBoolean();
            this.newBonus = bbis.readFloat();
            this.newCost = bbis.readFloat();
            this.newLimit = bbis.readFloat();
            for (int i = 0; i < this.statConfigStrings.length; ++i) {
                this.statConfigStrings[i] = bbis.readUTF();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleClientSide(final EntityPlayer player) {
        Stat.loseStatsOnDeath = this.deathLoss;
        Stat.globalBonusMultiplier = this.newBonus;
        Stat.globalCostMultiplier = this.newCost;
        Stat.globalLimitMultiplier = this.newLimit;
        for (int i = 0; i < this.statConfigStrings.length; ++i) {
            Stat.stats.get(i).fromConfigurationString(this.statConfigStrings[i]);
        }
    }

    @Override
    public void handleServerSide(final EntityPlayer player) {
    }
}