package net.keletu.goki.stats;

import net.keletu.goki.lib.Helper;
import net.keletu.goki.lib.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

public class StatReaper extends Stat {
    public static float healthLimit = 20.0f;

    public StatReaper(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return getFinalBonus((float) Math.pow(level, 1.0768) * 0.0025f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% chance to instantly kill enemies with less than " + StatReaper.healthLimit + " health.";
    }

    @Override
    public int isAffectedByStat(final Object object) {
        if (object != null && !(object instanceof EntityPlayer) && object instanceof EntityLivingBase) {
            final EntityLivingBase target = (EntityLivingBase) object;
            if (target.getMaxHealth() <= StatReaper.healthLimit) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void loadFromConfigurationFile(final Configuration config) {
        StatReaper.healthLimit = (float) Reference.configuration.get("Support", "Reaper Limit", StatReaper.healthLimit).getDouble(20.0);
    }

    @Override
    public void saveToConfigurationFile(final Configuration config) {
        Reference.configuration.get("Support", "Reaper Limit", StatReaper.healthLimit).set(StatReaper.healthLimit);
    }

    @Override
    public String toConfigurationString() {
        return "" + StatReaper.healthLimit;
    }

    @Override
    public void fromConfigurationString(final String configString) {
        try {
            StatReaper.healthLimit = Float.parseFloat(configString);
        } catch (final Exception ex) {
        }
    }
}
