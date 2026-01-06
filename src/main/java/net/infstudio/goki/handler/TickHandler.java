package net.infstudio.goki.handler;

import net.infstudio.goki.lib.Helper;
import net.infstudio.goki.lib.Reference;
import net.infstudio.goki.stats.Stat;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.UUID;

public class TickHandler {
    public static final UUID knockbackResistanceID = UUID.randomUUID();
    public static final UUID stealthSpeedID = UUID.randomUUID();
    public static final UUID swimSpeedID = UUID.randomUUID();

    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if (player.isInWater() && !Reference.isPlayerAPILoaded) {
            final float multiplier = Math.max(0.0f, Stat.STAT_ATHLETICISM.getBonus(player));
            player.move(MoverType.SELF, player.motionX * multiplier, player.motionY * multiplier, player.motionZ * multiplier);
        }
        if (player.isOnLadder() && !Reference.isPlayerAPILoaded && !player.isSneaking() && player.collidedHorizontally) {
            final float multiplier = Stat.STAT_CLIMBING.getBonus(player);
            player.move(MoverType.SELF, player.motionX, player.motionY * multiplier, player.motionZ);
        }
        IAttributeInstance atinst = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        AttributeModifier mod = new AttributeModifier(TickHandler.stealthSpeedID, "SneakSpeed", Stat.STAT_STEALTH.getBonus(player) / 100.0f, 1);
        if (player.isSneaking()) {
            if (atinst.getModifier(TickHandler.stealthSpeedID) == null) {
                atinst.applyModifier(mod);
            }
        } else if (atinst.getModifier(TickHandler.stealthSpeedID) != null) {
            atinst.removeModifier(mod);
        }
        atinst = player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        mod = new AttributeModifier(TickHandler.knockbackResistanceID, "KnockbackResistance", Stat.STAT_STEADY_GUARD.getBonus(player), 0);
        if (player.isActiveItemStackBlocking()) {
            if (atinst.getModifier(TickHandler.knockbackResistanceID) == null) {
                atinst.applyModifier(mod);
            }
        } else if (atinst.getModifier(TickHandler.knockbackResistanceID) != null) {
            atinst.removeModifier(mod);
        }
        if (Helper.getPlayerStatLevel(player, Stat.STAT_FURNACE_FINESSE) > 0) {
            final int tickBonus = (int) Stat.STAT_FURNACE_FINESSE.getBonus(player);
            final float timeBonus = (float) (int) Stat.STAT_FURNACE_FINESSE.getSecondaryBonus(player);
            final ArrayList<TileEntityFurnace> furnacesAroundPlayer = new ArrayList<TileEntityFurnace>();
            for (final Object listEntity : player.world.loadedTileEntityList) {
                if (listEntity instanceof TileEntity) {
                    final TileEntity tileEntity = (TileEntity) listEntity;
                    if (!(tileEntity instanceof TileEntityFurnace) || player.getDistance(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ()) >= 4.0) {
                        continue;
                    }
                    furnacesAroundPlayer.add((TileEntityFurnace) tileEntity);
                }
            }
            for (final TileEntityFurnace furnace : furnacesAroundPlayer) {
                if (player.getRNG().nextFloat() < 0.3f) {
                    player.world.spawnParticle(EnumParticleTypes.REDSTONE, furnace.getPos().getX() + 0.5, furnace.getPos().getY() + 1, furnace.getPos().getZ() + 0.5, 1.0, 1.0, 0.0);
                }
                if (furnace != null && furnace.isBurning()) {
                    if (furnace.getField(2) < 200) {
                        if (player.getRNG().nextInt(100) >= timeBonus) {
                            continue;
                        }
                        final TileEntityFurnace tileEntityFurnace = furnace;
                        tileEntityFurnace.setField(2, tileEntityFurnace.getField(2) + tickBonus);
                    } else {
                        furnace.setField(2, 199);
                    }
                }
            }
        }
    }
}