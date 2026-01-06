package net.keletu.goki.stats;

import net.keletu.goki.lib.Helper;
import net.keletu.goki.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;

public class StatClimbing extends Stat {
    public StatClimbing(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.1) * 0.029f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        if (Reference.isPlayerAPILoaded) {
            return "I do nothing when PlayerAPI is loaded!";
        }
        return "Climb " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% faster.";
    }
}
