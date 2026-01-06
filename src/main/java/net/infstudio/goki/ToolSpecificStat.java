package net.infstudio.goki;

import net.infstudio.goki.lib.Reference;
import net.infstudio.goki.stats.Stat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolSpecificStat extends Stat {
    public List<ItemIdMetadataTuple> supportedItems;

    public ToolSpecificStat(final int id, final String key, final int limit) {
        super(id, key, limit);
        this.supportedItems = new ArrayList<ItemIdMetadataTuple>();
    }

    public abstract String getConfigurationKey();

    public abstract String[] getDefaultSupportedItems();

    public void addSupportForItem(final ItemStack item) {
        this.loadFromConfigurationFile(Reference.configuration);
        if (item == null) {
            return;
        }
        final boolean hasSubtypes = item.getHasSubtypes();
        final int id = Item.getIdFromItem(item.getItem());
        int meta = 0;
        if (hasSubtypes) {
            meta = item.getMetadata();
        }
        final ItemIdMetadataTuple iimt = new ItemIdMetadataTuple(id, meta);
        if (!this.supportedItems.contains(iimt)) {
            this.supportedItems.add(iimt);
        }
        this.saveToConfigurationFile(Reference.configuration);
    }

    public void removeSupportForItem(final ItemStack item) {
        if (item != null) {
            final ItemIdMetadataTupleComparator iimtc = new ItemIdMetadataTupleComparator();
            this.loadFromConfigurationFile(Reference.configuration);
            final ItemIdMetadataTuple iimt = new ItemIdMetadataTuple(Item.getIdFromItem(item.getItem()), 0);
            if (item.getHasSubtypes()) {
                iimt.metadata = item.getMetadata();
            }
            for (int i = 0; i < this.supportedItems.size(); ++i) {
                final ItemIdMetadataTuple ii = this.supportedItems.get(i);
                if (iimtc.compare(iimt, ii) == 1) {
                    this.supportedItems.remove(ii);
                    --i;
                }
            }
            this.saveToConfigurationFile(Reference.configuration);
        }
    }

    @Override
    public void loadFromConfigurationFile(final Configuration config) {
        this.supportedItems.clear();
        final String[] configStrings = Reference.configuration.get("Support", this.getConfigurationKey(), this.getDefaultSupportedItems()).getStringList();
        for (int i = 0; i < configStrings.length; ++i) {
            this.supportedItems.add(new ItemIdMetadataTuple(configStrings[i]));
        }
    }

    @Override
    public String toConfigurationString() {
        String configString = "";
        for (int i = 0; i < this.supportedItems.size(); ++i) {
            configString = configString + "," + this.supportedItems.get(i).toConfigString();
        }
        return configString.substring(1);
    }

    @Override
    public void saveToConfigurationFile(final Configuration config) {
        final String[] toolIDs = new String[this.supportedItems.size()];
        for (int i = 0; i < toolIDs.length; ++i) {
            toolIDs[i] = this.supportedItems.get(i).toConfigString();
        }
        Reference.configuration.get("Support", this.getConfigurationKey(), this.getDefaultSupportedItems()).set(toolIDs);
    }

    @Override
    public void fromConfigurationString(final String configString) {
        this.supportedItems.clear();
        final String[] configStringSplit = configString.split(",");
        for (int i = 0; i < configStringSplit.length; ++i) {
            this.supportedItems.add(new ItemIdMetadataTuple(configStringSplit[i]));
        }
    }

    public boolean isItemSupported(final ItemStack item) {
        for (int i = 0; i < this.supportedItems.size(); ++i) {
            final ItemIdMetadataTuple iimt = this.supportedItems.get(i);
            if (Item.getIdFromItem(item.getItem()) == iimt.id) {
                if (item.getHasSubtypes() && item.getMetadata() == iimt.metadata) {
                    return true;
                }
                if (!item.getHasSubtypes()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int isAffectedByStat(final Object object) {
        if (object != null && object instanceof ItemStack) {
            final ItemStack item = (ItemStack) object;
            if (this.isItemSupported(item)) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public float getAppliedBonus(final EntityPlayer player, final Object object) {
        return this.getBonus(player) * this.isAffectedByStat(object);
    }
}