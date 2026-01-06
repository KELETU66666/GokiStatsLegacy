package net.keletu.goki.stats;

import net.keletu.goki.DamageSourceProtectionStat;
import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatFeatherFall extends DamageSourceProtectionStat {
    public StatFeatherFall(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus(level * 0.026f);
    }

    @Override
    public float getSecondaryBonus(final int level) {
        return Stat.getFinalBonus(level * 0.1f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        final float height = Helper.getFallResistance(player) + Helper.trimDecimals(this.getSecondaryBonus(this.getPlayerStatLevel(player)), 1);
        return "Take " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% less damage from falling more than " + height + " blocks.";
    }

    @Override
    public String[] getDefaultDamageSources() {
        return new String[]{"fall"};
    }
}