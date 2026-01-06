package net.infstudio.goki.lib;

import net.infstudio.goki.stats.Stat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class Helper {
    public static NBTTagCompound getPlayerPersistentNBT(final EntityPlayer player) {
        final NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
        if (!nbt.hasKey("gokiStats_Stats")) {
            nbt.setTag("gokiStats_Stats", (NBTBase) new NBTTagCompound());
            player.getEntityData().setTag("PlayerPersisted", (NBTBase) nbt);
        }
        return nbt;
    }

    public static int getPlayerStatLevel(final EntityPlayer player, final Stat stat) {
        final NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey("gokiStats_Stats")) {
            return ((NBTTagCompound) nbt.getTag("gokiStats_Stats")).getByte(stat.key);
        }
        return 0;
    }

    public static void setPlayerStatLevel(final EntityPlayer player, final Stat stat, final int level) {
        final NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey("gokiStats_Stats")) {
            ((NBTTagCompound) nbt.getTag("gokiStats_Stats")).setByte(stat.key, (byte) level);
        }
    }

    public static float trimDecimals(final float in, final int decimals) {
        final float f = (float) (in * Math.pow(10.0, decimals));
        final int i = (int) f;
        return i / (float) Math.pow(10.0, decimals);
    }

    public static void setPlayersExpTo(final EntityPlayer player, final int total) {
        player.experienceLevel = getLevelFromXPValue(total);
        player.experience = getCurrentFromXPValue(total);
    }

    public static int getXPTotal(final int xpLevel, final float current) {
        return (int) (getXPValueFromLevel(xpLevel) + getXPValueToNextLevel(xpLevel) * current);
    }

    public static int getXPTotal(final EntityPlayer player) {
        return (int) (getXPValueFromLevel(player.experienceLevel) + getXPValueToNextLevel(player.experienceLevel) * player.experience);
    }

    public static int getLevelFromXPValue(final int value) {
        int level = 0;
        if (value >= getXPValueFromLevel(30)) {
            level = (int) (0.07142857142857142 * (Math.sqrt(56.0 * value - 32511.0) + 303.0));
        } else if (value >= getXPValueFromLevel(15)) {
            level = (int) (0.16666666666666666 * (Math.sqrt(24.0 * value - 5159.0) + 59.0));
        } else {
            level = (int) (value / 17.0);
        }
        return level;
    }

    public static float getCurrentFromXPValue(final int value) {
        if (value == 0) {
            return 0.0f;
        }
        final int level = getLevelFromXPValue(value);
        final int needed = getXPValueFromLevel(level);
        final int next = getXPValueToNextLevel(level);
        final int difference = value - needed;
        final float current = difference / (float) next;
        return current;
    }

    public static int getXPValueFromLevel(final int xpLevel) {
        int val = 0;
        if (xpLevel >= 30) {
            val = (int) (3.5 * Math.pow(xpLevel, 2.0) - 151.5 * xpLevel + 2220.0);
        } else if (xpLevel >= 15) {
            val = (int) (1.5 * Math.pow(xpLevel, 2.0) - 29.5 * xpLevel + 360.0);
        } else {
            val = 17 * xpLevel;
        }
        return val;
    }

    public static int getXPValueToNextLevel(final int xpLevel) {
        int val = 0;
        if (xpLevel >= 30) {
            val = 7 * xpLevel - 148;
        } else if (xpLevel >= 15) {
            val = 3 * xpLevel - 28;
        } else {
            val = 17;
        }
        return val;
    }

    public static float getDamageDealt(final EntityPlayer player, final Entity target, final DamageSource source) {
        float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        float bonusDamage = 0.0f;
        final boolean targetIsLiving = target instanceof EntityLivingBase;
        boolean critical = false;
        if (targetIsLiving) {
            bonusDamage = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((EntityLivingBase) target).getCreatureAttribute());
        }
        if (damage > 0.0f || bonusDamage > 0.0f) {
            critical = (player.fallDistance > 0.0f && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && player.getRidingEntity() == null && targetIsLiving);
            if (critical && damage > 0.0f) {
                damage *= 1.5f;
            }
            damage += bonusDamage;
        }
        return damage;
    }

    public static float getFallResistance(final EntityLivingBase entity) {
        final float resistance = 3.0f;
        final PotionEffect potioneffect = entity.getActivePotionEffect(MobEffects.JUMP_BOOST);
        final float bonus = (potioneffect != null) ? ((float) (potioneffect.getAmplifier() + 1)) : 0.0f;
        return resistance + bonus;
    }
}
