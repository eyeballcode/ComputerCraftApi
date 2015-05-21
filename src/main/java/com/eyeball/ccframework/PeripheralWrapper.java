package com.eyeball.ccframework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.tileentity.TileEntity;

import com.eyeball.ccframework.api.LuaMethod;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

public class PeripheralWrapper implements IPeripheral {

	private String name;
	private String[] methods = null;

	private Object instance;

	private Method[] luaMethods;

	private TileEntity te;

	private String attach, detatch;

	private Class<?> instanceClass;

	public PeripheralWrapper(String[] methods, String name, Method[] methods2,
			Object instance, TileEntity te, String attach, String detach) {
		this.methods = methods;
		this.name = name;
		luaMethods = methods2;
		this.instance = instance;
		this.te = te;
		this.attach = attach;
		this.detatch = detach;
		try {
			instanceClass = instance.getClass();
		} catch (Exception e) {
		}
	}

	@Override
	public String getType() {
		return name;
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws LuaException,
			InterruptedException {
		Method methodToRun = null;
		if (method <= luaMethods.length)
			try {
				methodToRun = luaMethods[method];
				methodToRun.setAccessible(true);
				Object result = methodToRun.invoke(instance, te, arguments);
				if (result == null)
					return new Object[] { true };
				else
					return new Object[] { true, result };
			} catch (IllegalAccessException e) {
				return new Object[] { false,
						"Could not run method. Report to modder", 1 };
			} catch (IllegalArgumentException e) {
				Class<?>[] params = methodToRun.getParameterTypes();
				for (int i = 0; i < arguments.length; i++) {
					ComputerCraftFramework.LOGGER.info(params[i].getName());
					ComputerCraftFramework.LOGGER.info(arguments[i].getClass()
							.getName());
					String argType = arguments[i].getClass().getName();
					if (!(params[i].getName().equals(argType)))
						return new Object[] {
								false,
								"Bad argument #" + i + 1 + ", expected "
										+ params[i].getSimpleName() };
					else if (method == luaMethods.length + 1) {
						String mName = arguments[0].toString();
						for (int c = 0; c < methods.length; c++) {
							if (methods[i].equals(mName)) {
								return new Object[] { getLuaDescription(luaMethods[c]) };
							}
						}
					}
				}
			} catch (InvocationTargetException e) {
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Object[] { false,
						"Could not run method. Report to modder" };
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		return null;
	}

	private String getLuaDescription(Method method) {
		return method.getAnnotation(LuaMethod.class).description();
	}

	@Override
	public void attach(IComputerAccess computer) {
		try {
			if (!detatch.isEmpty())

				instanceClass.getMethod(attach).invoke(instance);
		} catch (Exception e) {
			ComputerCraftFramework.LOGGER.warn("Could not invoke method "
					+ attach);
		}

	}

	@Override
	public void detach(IComputerAccess computer) {
		try {
			if (!detatch.isEmpty())

				instanceClass.getMethod(detatch).invoke(instance);
			ComputerCraftFramework.LOGGER.warn("Could not invoke method "
					+ detatch);

		} catch (Exception e) {
		}
	}

	@Override
	public boolean equals(IPeripheral other) {
		return other == this;
	}

}
