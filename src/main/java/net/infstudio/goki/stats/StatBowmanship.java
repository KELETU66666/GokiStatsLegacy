package net.infstudio.goki.stats;

import net.infstudio.goki.ToolSpecificStat;
import net.infstudio.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class StatBowmanship extends ToolSpecificStat {
    public StatBowmanship(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getConfigurationKey() {
        return "Bowmanship Tools";
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.0895) * 0.03f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Deal " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% more damage with a bow.";
    }

    @Override
    public String[] getDefaultSupportedItems() {
        return new String[]{Item.getIdFromItem((Item) Items.BOW) + ":0"};
    }
}