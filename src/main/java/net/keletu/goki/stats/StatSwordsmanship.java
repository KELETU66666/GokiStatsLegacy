package net.keletu.goki.stats;

import net.keletu.goki.ToolSpecificStat;
import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class StatSwordsmanship extends ToolSpecificStat {
    public StatSwordsmanship(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getConfigurationKey() {
        return "Swordsmanship Tools";
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.0895) * 0.03f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Deal " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% more damage with swords.";
    }

    @Override
    public String[] getDefaultSupportedItems() {
        return new String[]{Item.getIdFromItem(Items.WOODEN_SWORD) + ":0", Item.getIdFromItem(Items.STONE_SWORD) + ":0", Item.getIdFromItem(Items.IRON_SWORD) + ":0", Item.getIdFromItem(Items.GOLDEN_SWORD) + ":0", Item.getIdFromItem(Items.DIAMOND_SWORD) + ":0"};
    }
}