package net.keletu.goki.stats;

import net.minecraft.entity.player.EntityPlayer;

public interface IStat {
    int isAffectedByStat(final Object p0);

    int isAffectedByStat(final Object p0, final Object p1);

    int isAffectedByStat(final Object p0, final Object p1, final Object p2);

    String getSimpleDescriptionString();

    String getAppliedDescriptionString(final EntityPlayer p0);

    float getBonus(final EntityPlayer p0);

    float getBonus(final int p0);

    float getSecondaryBonus(final int p0);

    float getSecondaryBonus(final EntityPlayer p0);

    float getAppliedBonus(final EntityPlayer p0, final Object p1);

    int getCost(final int p0);

    int getLimit();
}
