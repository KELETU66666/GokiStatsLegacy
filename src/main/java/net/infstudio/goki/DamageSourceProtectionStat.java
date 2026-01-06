package net.infstudio.goki;

import net.infstudio.goki.lib.Reference;
import net.infstudio.goki.stats.Stat;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public abstract class DamageSourceProtectionStat extends Stat {
    public List<String> damageSources;

    public DamageSourceProtectionStat(final int id, final String key, final int limit) {
        super(id, key, limit);
        this.damageSources = new ArrayList<String>();
    }

    @Override
    public int isAffectedByStat(final Object object) {
        if (object != null && object instanceof DamageSource) {
            final DamageSource source = (DamageSource) object;
            for (int i = 0; i < this.damageSources.size(); ++i) {
                if (source.damageType.equals(this.damageSources.get(i))) {
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public void loadFromConfigurationFile(final Configuration config) {
        this.damageSources.clear();
        final String[] sources = Reference.configuration.get("Support", this.name + " Sources", this.getDefaultDamageSources()).getStringList();
        for (int i = 0; i < sources.length; ++i) {
            this.damageSources.add(sources[i]);
        }
    }

    @Override
    public String toConfigurationString() {
        String configString = "";
        for (final String s : this.damageSources) {
            configString = configString + "," + s;
        }
        return configString.substring(1);
    }

    @Override
    public void saveToConfigurationFile(final Configuration config) {
        final String[] sources = new String[this.damageSources.size()];
        for (int i = 0; i < sources.length; ++i) {
            sources[i] = this.damageSources.get(i);
        }
        Reference.configuration.get("Support", this.name + " Sources", this.getDefaultDamageSources()).set(sources);
    }

    @Override
    public void fromConfigurationString(final String configString) {
        this.damageSources.clear();
        final String[] arr$;
        final String[] configStringSplit = arr$ = configString.split(",");
        for (final String s : arr$) {
            this.damageSources.add(s);
        }
    }

    public abstract String[] getDefaultDamageSources();
}
