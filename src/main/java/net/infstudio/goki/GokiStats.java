package net.infstudio.goki;

import net.infstudio.goki.client.gui.GuiHandler;
import net.infstudio.goki.handler.*;
import net.infstudio.goki.lib.Reference;
import net.infstudio.goki.stats.Stat;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.MOD_NAME, version = "1.0.0")
public class GokiStats {
    public static final PacketPipeline packetPipeline = new PacketPipeline();
    @Mod.Instance(Reference.MODID)
    public static GokiStats instance;
    @SidedProxy(clientSide = "net.infstudio.goki.client.ClientProxy", serverSide = "net.infstudio.goki.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        GokiStats.instance = this;
        (Reference.configuration = new Configuration(event.getSuggestedConfigurationFile())).load();
        if (!Reference.configuration.get("Version", "Configuration Version", "v1").getString().equals("v1")) {
            System.err.println("gokiStats configuration file has changed! May cause errors! Delete the configuration file and relaunch.");
        }
        Stat.loadOptions(Reference.configuration);
        Stat.loadAllStatsFromConfiguration(Reference.configuration);
        GokiStats.proxy.registerKeybinding();
        GokiStats.proxy.registerHandlers();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        GokiStats.packetPipeline.initialise();
        GokiStats.packetPipeline.registerPacket(PacketStatSync.class);
        GokiStats.packetPipeline.registerPacket(PacketStatAlter.class);
        GokiStats.packetPipeline.registerPacket(PacketSyncXP.class);
        GokiStats.packetPipeline.registerPacket(PacketSyncStatConfig.class);
        MinecraftForge.EVENT_BUS.register(new Events());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        FMLCommonHandler.instance().bus().register(new TickHandler());
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        GokiStats.packetPipeline.postInitialise();
        if (Loader.isModLoaded("PlayerAPI")) {
            Reference.isPlayerAPILoaded = true;
            Stat.STAT_ATHLETICISM.enabled = false;
            Stat.STAT_CLIMBING.enabled = false;
        }
        Stat.saveAllStatsToConfiguration(Reference.configuration);
        Stat.saveGlobalMultipliers(Reference.configuration);
        Reference.configuration.save();
    }

    @Mod.EventHandler
    public void serverStart(final FMLServerStartingEvent event) {
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        final ICommandManager command = server.getCommandManager();
        final ServerCommandManager serverCommand = (ServerCommandManager) command;
        serverCommand.registerCommand(new StatsCommand());
    }
}
