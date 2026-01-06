package net.infstudio.goki.stats;

import net.infstudio.goki.lib.TreasureFinderEntry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class StatTreasureFinder extends Stat {
    public static List<TreasureFinderEntry> entries = new ArrayList<TreasureFinderEntry>();
    public static String[] defaultEntries = new String[]{new TreasureFinderEntry(Blocks.SAND, 0, Items.GOLD_NUGGET, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.ROTTEN_FLESH, 0, 1, 40).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.BONE, 0, 1, 20).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.SPIDER_EYE, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.GUNPOWDER, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.SLIME_BALL, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.GLASS_BOTTLE, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.CLOCK, 0, 1, 5).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.COMPASS, 0, 1, 5).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.CLAY_BALL, 0, 1, 20).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.BOWL, 0, 1, 6).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.POTATO, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.CARROT, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.APPLE, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.LEAVES, 0, Items.APPLE, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.LEAVES2, 0, Items.APPLE, 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Item.getItemFromBlock((Block) Blocks.RED_MUSHROOM), 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Item.getItemFromBlock((Block) Blocks.BROWN_MUSHROOM), 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Item.getItemFromBlock((Block) Blocks.RED_FLOWER), 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Item.getItemFromBlock((Block) Blocks.YELLOW_FLOWER), 0, 1, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.STICK, 0, 1, 100).toConfigurationString(), new TreasureFinderEntry(Blocks.TALLGRASS, 0, Items.PUMPKIN_SEEDS, 0, 1, 30).toConfigurationString(), new TreasureFinderEntry(Blocks.TALLGRASS, 0, Items.MELON_SEEDS, 0, 1, 30).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.BEEF, 0, 2, 50).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.PORKCHOP, 0, 2, 50).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.CHICKEN, 0, 2, 50).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.IRON_INGOT, 0, 2, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.GOLD_INGOT, 0, 2, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.LEATHER, 0, 2, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.FEATHER, 0, 2, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Item.getItemFromBlock(Blocks.WOOL), 0, 2, 10).toConfigurationString(), new TreasureFinderEntry(Blocks.SAND, 0, Items.REDSTONE, 0, 2, 50).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.BUCKET, 0, 3, 20).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_11, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_13, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_BLOCKS, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_CAT, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_CHIRP, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_FAR, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_MALL, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_MELLOHI, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_STAL, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_STRAD, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_WAIT, 0, 3, 2).toConfigurationString(), new TreasureFinderEntry(Blocks.DIRT, 0, Items.RECORD_WARD, 0, 3, 2).toConfigurationString()};


    public StatTreasureFinder(final int id, final String key, final int limit) {
        super(id, key, limit);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        if (this.getPlayerStatLevel(player) == 0) {
            return "Chance to find additional items when harvesting blocks.";
        }
        return "Chance to find more rare additional items when harvesting blocks.";
    }

    @Override
    public void loadFromConfigurationFile(final Configuration config) {
        StatTreasureFinder.entries.clear();
        final String[] entryStrings = config.get("Treasure Finder", "Entries", StatTreasureFinder.defaultEntries).getStringList();
        for (int i = 0; i < entryStrings.length; ++i) {
            StatTreasureFinder.entries.add(new TreasureFinderEntry(entryStrings[i]));
        }
    }

    @Override
    public void saveToConfigurationFile(final Configuration config) {
        final String[] entryStrings = new String[StatTreasureFinder.entries.size()];
        for (int i = 0; i < entryStrings.length; ++i) {
            entryStrings[i] = StatTreasureFinder.entries.get(i).toConfigurationString();
        }
        config.get("Treasure Finder", "Entries", StatTreasureFinder.defaultEntries).set(entryStrings);
    }

    public List<ItemStack> getApplicableItemStackList(final IBlockState state, final int level) {
        final List<ItemStack> items = new ArrayList<ItemStack>();
        for (final TreasureFinderEntry tfe : StatTreasureFinder.entries) {
            if (tfe.miniumLevel <= level && ((tfe.block == null && (state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS)) || (tfe.block == state.getBlock() && tfe.blockMD == state.getBlock().getMetaFromState(state)))) {
                items.add(new ItemStack(tfe.item, 1, tfe.itemMD));
            }
        }
        return items;
    }

    public List<Integer> getApplicableChanceList(final IBlockState state, final int level) {
        final List<Integer> chance = new ArrayList<Integer>();
        for (final TreasureFinderEntry tfe : StatTreasureFinder.entries) {
            if (tfe.miniumLevel <= level && ((tfe.block == null && (state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS)) || (tfe.block == state.getBlock() && tfe.blockMD == state.getBlock().getMetaFromState(state)))) {
                chance.add(tfe.chance);
            }
        }
        return chance;
    }

    @Override
    public String toConfigurationString() {
        String configString = "";
        for (int i = 0; i < StatTreasureFinder.entries.size(); ++i) {
            configString = configString + "," + StatTreasureFinder.entries.get(i).toConfigurationString();
        }
        return configString;
    }

    @Override
    public void fromConfigurationString(final String configString) {
        StatTreasureFinder.entries.clear();
        final String[] configStringSplit = configString.split(",");
        for (int i = 0; i < configStringSplit.length; ++i) {
            StatTreasureFinder.entries.add(new TreasureFinderEntry(configStringSplit[i]));
        }
    }

    @Override
    public int getCost(final int level) {
        int cost = 170;
        if (level == 1) {
            cost = 370;
        } else if (level == 2) {
            cost = 820;
        }
        return cost;
    }

    @Override
    public int getLimit() {
        return 3;
    }
}
