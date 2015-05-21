package com.eyeball.ccframework;

import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public interface IModTemplate {

	@EventHandler
	public void preInit(FMLPreInitializationEvent event);

	@EventHandler
	public void init(FMLInitializationEvent event);

	@EventHandler
	public void postInit(FMLPostInitializationEvent event);

	@EventHandler
	public void textureStitchEvent(TextureStitchEvent event);

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event);

	@EventHandler
	public void processIMC(IMCEvent event);

	@EventHandler
	public void processMissingMappings(FMLMissingMappingsEvent event);

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event);

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event);

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event);

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event);

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event);

}
