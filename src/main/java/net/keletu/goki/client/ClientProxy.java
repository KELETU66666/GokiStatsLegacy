package net.keletu.goki.client;

import net.keletu.goki.CommonProxy;
import net.keletu.goki.handler.GokiKeyHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerKeybinding() {
        final GokiKeyHandler keyHandler = new GokiKeyHandler();
        FMLCommonHandler.instance().bus().register(keyHandler);
    }

    @Override
    public void registerSounds() {
    }
}
