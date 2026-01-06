package net.keletu.goki.stats;

import net.keletu.goki.DamageSourceProtectionStat;
import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatToughSkin extends DamageSourceProtectionStat {
    public StatToughSkin(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus(level * 0.026f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Take " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% less damage from explosions.";
    }

    @Override
    public String[] getDefaultDamageSources() {
        return new String[]{"explosion", "explosion.player"};
    }
}