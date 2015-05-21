package com.eyeball.ccframework;

import java.util.ArrayList;

import net.minecraftforge.client.event.TextureStitchEvent;

import org.apache.logging.log4j.Logger;

import com.eyeball.ccframework.api.PeripheralTarget;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import dan200.computercraft.api.ComputerCraftAPI;

@Mod(modid = "ComputerCraftFramework", dependencies = "required-after:ComputerCraft")
public class ComputerCraftFramework implements IModTemplate {

	static Logger LOGGER;

	private ArrayList<Class<?>> peripheralClasses = new ArrayList<Class<?>>();

	@EventHandler
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER = event.getModLog();
	}

	@EventHandler
	@Override
	public void init(FMLInitializationEvent event) {
		ComputerCraftAPI.registerPeripheralProvider(new PeripheralFinder());
	}

	@EventHandler
	@Override
	public void postInit(FMLPostInitializationEvent event) {

		for (Class<?> target : peripheralClasses) {
			try {

				PeripheralFinder.addTarget(target);
			} catch (Exception e) {
				LOGGER.warn("Error in registering target");
			}
		}

	}

	@EventHandler
	@Override
	public void textureStitchEvent(TextureStitchEvent event) {
	}

	@EventHandler
	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {

	}

	@EventHandler
	@Override
	public void processIMC(IMCEvent event) {

		for (IMCMessage message : event.getMessages()) {
			if (message.key.equals("addPeripheralTarget")) {
				LOGGER.info("Trying to reflect in " + message.getSender());
				String className = message.getStringValue();
				try {
					Class<?> clazz = Class.forName(className);
					if (clazz.getAnnotation(PeripheralTarget.class) != null) {
						peripheralClasses.add(clazz);
						LOGGER.info("Success in adding " + className);
						continue;
					}
					throw new Exception();
				} catch (Exception e) {
					LOGGER.warn("Failure in adding " + className);
					e.printStackTrace();
				}
			}
		}
	}

	@EventHandler
	@Override
	public void processMissingMappings(FMLMissingMappingsEvent event) {

	}

	@EventHandler
	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {

	}

	@EventHandler
	@Override
	public void serverStarting(FMLServerStartingEvent event) {

	}

	@EventHandler
	@Override
	public void serverStarted(FMLServerStartedEvent event) {

	}

	@EventHandler
	@Override
	public void serverStopping(FMLServerStoppingEvent event) {

	}

	@EventHandler
	@Override
	public void serverStopped(FMLServerStoppedEvent event) {

	}

}
