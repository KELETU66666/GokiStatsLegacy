package net.keletu.goki.stats;

import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatSteadyGuard extends Stat {
    public StatSteadyGuard(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return Math.min(getFinalBonus((float) Math.pow(level, 1.3615)), 100.0f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return Helper.trimDecimals(this.getBonus(player), 1) + "% less knockback when blocking.";
    }
}
