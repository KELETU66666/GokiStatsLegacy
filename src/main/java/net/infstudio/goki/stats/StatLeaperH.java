package net.infstudio.goki.stats;

import net.infstudio.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;

public class StatLeaperH extends StatLeaper {
    public StatLeaperH(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Jump " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% farther when sprinting.";
    }
}
