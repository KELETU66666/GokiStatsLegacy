package net.keletu.goki.stats;

import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatStealth extends StatLeaper {
    public StatStealth(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return getFinalBonus((float) Math.pow(level, 1.3416));
    }

    @Override
    public int isAffectedByStat(final Object object) {
        if (object instanceof EntityPlayer && ((EntityPlayer) object).isSneaking()) {
            return 1;
        }
        return 0;
    }

    @Override
    public float getSecondaryBonus(final int level) {
        return getFinalBonus((float) Math.pow(level, 1.4307));
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        final float speed = Helper.trimDecimals(this.getBonus(player), 1);
        final float reapBonus = Helper.trimDecimals(this.getSecondaryBonus(player), 1);
        final float reap = STAT_REAPER.getBonus(player) * 100.0f;
        final float newReap = Helper.trimDecimals(reap + reap * reapBonus / 100.0f, 1);
        return "Move " + speed + "% faster and reap " + reapBonus + "% more often (" + newReap + "%) when sneaking.";
    }
}
