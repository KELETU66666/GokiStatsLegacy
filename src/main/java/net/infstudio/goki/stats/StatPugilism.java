package net.infstudio.goki.stats;

import net.infstudio.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatPugilism extends Stat {
    public StatPugilism(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.03) * 0.1816f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Deal " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% more damage with your tree punchers.";
    }
}
