package net.infstudio.goki.stats;

import net.infstudio.goki.lib.Helper;
import net.infstudio.goki.lib.IDMDTuple;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import java.util.ArrayList;
import java.util.List;

public class StatMiningMagician extends Stat {
    public static List<IDMDTuple> blockEntries = new ArrayList<IDMDTuple>();

    private static IDMDTuple[] defaultBlockEntries = new IDMDTuple[]{new IDMDTuple(Blocks.COAL_ORE, 0), new IDMDTuple(Blocks.DIAMOND_ORE, 0), new IDMDTuple(Blocks.EMERALD_ORE, 0), new IDMDTuple(Blocks.GOLD_ORE, 0), new IDMDTuple(Blocks.IRON_ORE, 0), new IDMDTuple(Blocks.LAPIS_ORE, 0), new IDMDTuple(Blocks.QUARTZ_ORE, 0), new IDMDTuple(Blocks.REDSTONE_ORE, 0)};

    public static List<IDMDTuple> itemEntries = new ArrayList<IDMDTuple>();

    private static IDMDTuple[] defaultItemEntries = new IDMDTuple[]{new IDMDTuple(Items.COAL, 0), new IDMDTuple(Items.DIAMOND, 0), new IDMDTuple(Items.EMERALD, 0), new IDMDTuple(Items.GOLD_INGOT, 0), new IDMDTuple(Items.IRON_INGOT, 0), new IDMDTuple(Items.DYE, 4), new IDMDTuple(Items.QUARTZ, 0), new IDMDTuple(Items.REDSTONE, 0)};


    public StatMiningMagician(final int id, final String key, final int limit) {
        super(id, key, limit);
        for (final IDMDTuple mme : StatMiningMagician.defaultBlockEntries) {
            StatMiningMagician.blockEntries.add(mme);
        }
        for (final IDMDTuple mme : StatMiningMagician.defaultItemEntries) {
            StatMiningMagician.itemEntries.add(mme);
        }
    }

    @Override
    public float getBonus(final int level) {
        return Stat.getFinalBonus(level * 0.3f);
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return Helper.trimDecimals(this.getBonus(player), 1) + "% chance to transform an ore when harvesting.";
    }

    @Override
    public int isAffectedByStat(final Object object) {
        if (object instanceof IDMDTuple) {
            final IDMDTuple idmd = (IDMDTuple) object;
            for (final IDMDTuple entry : StatMiningMagician.blockEntries) {
                if (idmd.equals(entry)) {
                    return 1;
                }
            }
            for (final IDMDTuple entry : StatMiningMagician.itemEntries) {
                if (idmd.equals(entry)) {
                    return 1;
                }
            }
        }
        return super.isAffectedByStat(object);
    }

}