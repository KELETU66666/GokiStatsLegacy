package net.keletu.goki.stats;

import net.keletu.goki.ToolSpecificStat;
import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class StatMining extends ToolSpecificStat {
    public StatMining(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getConfigurationKey() {
        return "Mining Tools";
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus((float) Math.pow(level, 1.3) * 0.01523f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return "Mine " + Helper.trimDecimals(this.getBonus(this.getPlayerStatLevel(player)) * 100.0f, 1) + "% faster.";
    }

    @Override
    public String[] getDefaultSupportedItems() {
        return new String[]{Item.getIdFromItem(Items.WOODEN_PICKAXE) + ":0", Item.getIdFromItem(Items.STONE_PICKAXE) + ":0", Item.getIdFromItem(Items.IRON_PICKAXE) + ":0", Item.getIdFromItem(Items.GOLDEN_PICKAXE) + ":0", Item.getIdFromItem(Items.DIAMOND_PICKAXE) + ":0"};
    }
}