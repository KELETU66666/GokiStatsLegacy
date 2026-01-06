package net.keletu.goki.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class IDMDTuple {
    public int id;
    public int md;

    public IDMDTuple(final int bID, final int bMD) {
        this.id = bID;
        this.md = bMD;
    }

    public IDMDTuple(final Block block, final int bMD) {
        this.id = Block.getIdFromBlock(block);
        this.md = bMD;
    }

    public IDMDTuple(final Item item, final int bMD) {
        this.id = Item.getIdFromItem(item);
        this.md = bMD;
    }

    public String toConfigurationString() {
        return this.id + "_" + this.md;
    }

    public boolean fromConfigurationString(final String configString) {
        boolean successful = false;
        try {
            final String[] values = configString.split("_");
            this.id = Integer.parseInt(values[0]);
            this.md = Integer.parseInt(values[1]);
        } catch (final Exception e) {
            successful = false;
        }
        return successful;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof IDMDTuple) {
            final IDMDTuple entry = (IDMDTuple) object;
            if (entry.id == this.id && entry.md == this.md) {
                return true;
            }
        }
        return super.equals(object);
    }
}