package net.infstudio.goki.stats;

import net.infstudio.goki.ToolSpecificStat;
import net.infstudio.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class StatTrimming extends ToolSpecificStat {
    public StatTrimming(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getConfigurationKey() {
        return "Trimming Tools";
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus(level * 0.1f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Trim " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% faster.";
    }

    @Override
    public String[] getDefaultSupportedItems() {
        return new String[]{Item.getIdFromItem((Item) Items.SHEARS) + ":0"};
    }
}