package com.eyeball.ccframework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.eyeball.ccframework.api.LuaMethod;
import com.eyeball.ccframework.api.PeripheralTarget;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

public class PeripheralFinder implements IPeripheralProvider {

	static ArrayList<Class<?>> targets = new ArrayList<Class<?>>();

	public static void addTarget(Class<?> target) {
		targets.add(target);
	}

	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null)
			return null;
		for (Class<?> target : targets) {
			try {
				String name;
				Class<?> peripheralTargetClass;
				String attach, detach;
				if (target.getAnnotation(PeripheralTarget.class) != null) {
					PeripheralTarget p = target
							.getAnnotation(PeripheralTarget.class);
					name = p.getPeripheralName();
					peripheralTargetClass = p.getTargetClass();
					attach = p.attachMethod();
					detach = p.detachMethod();
				} else
					continue;
				if (te.getClass().getName()
						.equals(peripheralTargetClass.getName())) {
					Method[] methods = target.getMethods();
					Hashtable<Method, String> luaMethods = new Hashtable<Method, String>();
					for (Method method : methods) {
						if (method.getAnnotation(LuaMethod.class) != null) {
							luaMethods.put(method,
									method.getAnnotation(LuaMethod.class)
											.description());
						}

					}
					LinkedList<String> luaMethodNames = new LinkedList<String>();
					for (Method m : luaMethods.keySet()) {
						luaMethodNames.add(m.getName());
					}
					luaMethodNames.add("doc");

					Object ins = target.newInstance();

					return new PeripheralWrapper(
							luaMethodNames.toArray(new String[luaMethodNames
									.size()]), name, luaMethods.keySet()
									.toArray(new Method[luaMethods.size()]),
							ins, te, attach, detach);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
}
