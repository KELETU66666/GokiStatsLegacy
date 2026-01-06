package net.infstudio.goki.stats;

import net.infstudio.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public abstract class StatLeaper extends Stat {
    public StatLeaper(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.065) * 0.0195f);
    }

    @Override
    public float getSecondaryBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.1) * 0.0203f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Jump " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% higher and " + Helper.trimDecimals(this.getSecondaryBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% farther when sprinting.";
    }
}