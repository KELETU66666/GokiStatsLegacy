package net.infstudio.goki.stats;

import net.minecraft.entity.player.EntityPlayer;

public class StatFurnaceFinesse extends Stat
{
    public StatFurnaceFinesse(final int id, final String key, final int limit) {
        super(id, key, limit);
    }
    
    @Override
    public float getBonus(final int level) {
        return Math.min(Stat.getFinalBonus(level / 5.0f), 199.0f) + 1.0f;
    }
    
    @Override
    public float getSecondaryBonus(final int level) {
        return Math.min(Stat.getFinalBonus((float)level), 100.0f);
    }
    
    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        final int level = this.getPlayerStatLevel(player);
        return "Furnaces around you smelt " + this.getBonus(level) + " ticks faster " + this.getSecondaryBonus(level) + "% of the time.";
    }
}
