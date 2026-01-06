package net.keletu.goki.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class TreasureFinderEntry {
    public Block block;
    public Item item;
    public int blockMD;
    public int itemMD;
    public int miniumLevel;
    public int chance;

    public TreasureFinderEntry(final Block block, final int bMD, final Item item, final int iMD, final int mL, final int c) {
        this.blockMD = 0;
        this.itemMD = 0;
        this.miniumLevel = 0;
        this.chance = 0;
        this.block = block;
        this.item = item;
        this.blockMD = bMD;
        this.itemMD = iMD;
        this.miniumLevel = mL;
        this.chance = c;
    }

    public TreasureFinderEntry(final String configString) {
        this.blockMD = 0;
        this.itemMD = 0;
        this.miniumLevel = 0;
        this.chance = 0;
        this.fromConfigurationString(configString);
    }

    public String toConfigurationString() {
        return Block.getIdFromBlock(this.block) + "_" + this.blockMD + "_" + Item.getIdFromItem(this.item) + "_" + this.itemMD + "_" + this.miniumLevel + "_" + this.chance;
    }

    public boolean fromConfigurationString(final String configString) {
        boolean successful = false;
        try {
            final String[] values = configString.split("_");
            final int blockID = Integer.parseInt(values[0]);
            this.block = Block.getBlockById(blockID);
            this.blockMD = Integer.parseInt(values[1]);
            final int itemID = Integer.parseInt(values[2]);
            this.item = Item.getItemById(itemID);
            this.itemMD = Integer.parseInt(values[3]);
            this.miniumLevel = Integer.parseInt(values[4]);
            this.chance = Integer.parseInt(values[5]);
        } catch (final Exception e) {
            successful = false;
        }
        return successful;
    }
}