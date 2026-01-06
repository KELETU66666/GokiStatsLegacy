package net.keletu.goki.stats;

import net.keletu.goki.lib.Helper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;

public class Stat implements IStat {
    public static boolean loseStatsOnDeath = true;
    public static float globalCostMultiplier = 1.0f;
    public static float globalLimitMultiplier = 2.5f;
    public static float globalBonusMultiplier = 1.0f;
    public static ArrayList<Stat> stats = new ArrayList<Stat>(32);
    public static int totalStats = 0;

    public static final StatMining STAT_MINING = new StatMining(0, "grpg_Mining", 10);
    public static final StatDigging STAT_DIGGING = new StatDigging(1, "grpg_Digging", 10);
    public static final StatChopping STAT_CHOPPING = new StatChopping(2, "grpg_Chopping", 10);
    public static final StatTrimming STAT_TRIMMING = new StatTrimming(3, "grpg_Trimming", 10);
    public static final StatProtection STAT_PROTECTION = new StatProtection(4, "grpg_Protection", 10);
    public static final StatTempering STAT_TEMPERING = new StatTempering(5, "grpg_Tempering", 10);
    public static final StatToughSkin STAT_TOUGH_SKIN = new StatToughSkin(6, "grpg_ToughSkin", 10);
    public static final StatFeatherFall STAT_FEATHER_FALL = new StatFeatherFall(7, "grpg_FeatherFall", 10);
    public static final StatLeaperH STAT_LEAPERH = new StatLeaperH(8, "grpg_LeaperH", 10);
    public static final StatLeaperV STAT_LEAPERV = new StatLeaperV(9, "grpg_LeaperV", 10);
    public static final StatAthleticism STAT_ATHLETICISM = new StatAthleticism(10, "grpg_Athleticism", 10);
    public static final StatClimbing STAT_CLIMBING = new StatClimbing(11, "grpg_Climbing", 10);
    public static final StatSwordsmanship STAT_SWORDSMANSHIP = new StatSwordsmanship(13, "grpg_Swordsmanship", 10);
    public static final StatBowmanship STAT_BOWMANSHIP = new StatBowmanship(14, "grpg_Bowmanship", 10);
    public static final StatReaper STAT_REAPER = new StatReaper(15, "grpg_Reaper", 10);
    public static final StatTreasureFinder STAT_TREASURE_FINDER = new StatTreasureFinder(16, "grpg_Treasure_Finder", 3);
    public static final StatFurnaceFinesse STAT_FURNACE_FINESSE = new StatFurnaceFinesse(17, "grpg_Furnace_Finesse", 10);
    public static final StatSteadyGuard STAT_STEADY_GUARD = new StatSteadyGuard(18, "grpg_Steady_Guard", 10);
    public static final StatStealth STAT_STEALTH = new StatStealth(19, "grpg_Stealth", 10);
    public static final StatMiningMagician STAT_MINING_MAGICIAN = new StatMiningMagician(20, "grpg_Mining_Magician", 10);
    public int imageID;
    private int limit;
    public String key;
    public String name;
    public float costMultiplier;
    public float limitMultiplier;
    public float bonusMultiplier;
    public boolean enabled;

    public Stat() {
        this.costMultiplier = 1.0f;
        this.limitMultiplier = 1.0f;
        this.bonusMultiplier = 1.0f;
        this.enabled = true;
        this.imageID = -1;
        this.limit = 0;
        this.key = "Dummy";
        this.name = "Dummy Stat";
    }

    public Stat(final int id, final String key, final int limit) {
        this.costMultiplier = 1.0f;
        this.limitMultiplier = 1.0f;
        this.bonusMultiplier = 1.0f;
        this.enabled = true;
        this.imageID = id;
        this.limit = limit;
        this.key = key;
        this.name = I18n.translateToLocal(key + ".name");
        Stat.stats.add(this);
        ++Stat.totalStats;
    }

    public static void loadOptions(final Configuration config) {
        Stat.globalCostMultiplier = (float) config.get("Global Modifiers", "Cost Muliplier", 1.0, "A flat multiplier on the cost to upgrade all stats.").getDouble(1.0);
        Stat.globalLimitMultiplier = (float) config.get("Global Modifiers", "Limit Muliplier", 2.5, "A flat multiplier on the level limit of all stats.").getDouble(1.0);
        Stat.globalBonusMultiplier = (float) config.get("Global Modifiers", "Bonus Muliplier", 1.0, "A flat multiplier on the bonus all stats gives.").getDouble(1.0);
        Stat.loseStatsOnDeath = config.get("Options", "Death Loss", true, "Lose stats on death?").getBoolean(true);
    }

    public static void saveGlobalMultipliers(final Configuration config) {
        config.get("Global Modifiers", "Cost Muliplier", 1.0, "A flat multiplier on the cost to upgrade all stats.").set(Stat.globalCostMultiplier);
        config.get("Global Modifiers", "Limit Muliplier", 2.5, "A flat multiplier on the level limit of all stats.").set(Stat.globalLimitMultiplier);
        config.get("Global Modifiers", "Bonus Muliplier", 1.0, "A flat multiplier on the bonus all stats gives.").set(Stat.globalBonusMultiplier);
        config.get("Options", "Death Loss", true, "Lose stats on death?").set(Stat.loseStatsOnDeath);
    }

    @Override
    public float getBonus(final int level) {
        return 0.0f;
    }

    @Override
    public float getBonus(final EntityPlayer player) {
        return this.getBonus(Helper.getPlayerStatLevel(player, this));
    }

    @Override
    public int isAffectedByStat(final Object object) {
        return 0;
    }

    @Override
    public String getSimpleDescriptionString() {
        return null;
    }

    @Override
    public String getAppliedDescriptionString(final EntityPlayer player) {
        return null;
    }

    public static Stat getStat(final int n) {
        return Stat.stats.get(n);
    }

    @Override
    public int getCost(final int level) {
        return (int) ((Math.pow(level, 1.6) + 6.0 + level) * Stat.globalCostMultiplier);
    }

    public static float getFinalBonus(final float currentBonus) {
        return currentBonus * Stat.globalBonusMultiplier;
    }

    @Override
    public float getSecondaryBonus(final int level) {
        return 0.0f;
    }

    @Override
    public float getSecondaryBonus(final EntityPlayer player) {
        return this.getSecondaryBonus(Helper.getPlayerStatLevel(player, this));
    }

    protected int getPlayerStatLevel(final EntityPlayer player) {
        return Helper.getPlayerStatLevel(player, this);
    }

    public void loadFromConfigurationFile(final Configuration config) {
    }

    public void fromConfigurationString(final String configString) {
    }

    public void saveToConfigurationFile(final Configuration config) {
    }

    public String toConfigurationString() {
        return "!";
    }

    public static void loadAllStatsFromConfiguration(final Configuration config) {
        for (int i = 0; i < Stat.totalStats; ++i) {
            Stat.stats.get(i).loadFromConfigurationFile(config);
        }
    }

    public static void saveAllStatsToConfiguration(final Configuration config) {
        for (int i = 0; i < Stat.totalStats; ++i) {
            Stat.stats.get(i).saveToConfigurationFile(config);
        }
    }

    @Override
    public float getAppliedBonus(final EntityPlayer player, final Object object) {
        return this.getBonus(player) * this.isAffectedByStat(object);
    }

    @Override
    public int isAffectedByStat(final Object object1, final Object object2) {
        return this.isAffectedByStat(object1);
    }

    @Override
    public int isAffectedByStat(final Object object1, final Object object2, final Object object3) {
        if (object1 instanceof IBlockAccess && object2 instanceof BlockPos && object3 instanceof ItemStack) {
            final IBlockAccess stack = (IBlockAccess) object1;
            final BlockPos block = (BlockPos) object2;
            final ItemStack metadata = (ItemStack) object3;
            if (ForgeHooks.isToolEffective(stack, block, metadata)) {
                return 1;
            }
        }
        return this.isAffectedByStat(object1);
    }

    @Override
    public int getLimit() {
        if (Stat.globalLimitMultiplier <= 0.0f) {
            return 127;
        }
        return (int) (this.limit * Stat.globalLimitMultiplier);
    }
}
