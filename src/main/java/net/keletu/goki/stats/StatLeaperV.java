package net.keletu.goki.stats;

import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatLeaperV extends StatLeaper {
    public StatLeaperV(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Jump " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% higher when sprinting.";
    }
}