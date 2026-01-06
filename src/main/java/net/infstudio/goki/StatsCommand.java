package net.infstudio.goki;

import net.infstudio.goki.lib.Reference;
import net.infstudio.goki.stats.Stat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class StatsCommand extends CommandBase {
    public String getName() {
        return "reloadGokiStats";
    }

    public void execute(MinecraftServer server, final ICommandSender icommandsender, final String[] astring) {
        Reference.configuration.load();
        Stat.loadOptions(Reference.configuration);
        Stat.loadAllStatsFromConfiguration(Reference.configuration);
        if (icommandsender instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) icommandsender;
        } else {
            FMLCommonHandler.instance().getMinecraftServerInstance().logInfo("Reloaded gokiStats configuration file.");
        }
    }

    public String getUsage(final ICommandSender icommandsender) {
        return null;
    }

    public int compareTo(final ICommand o) {
        return 0;
    }
}