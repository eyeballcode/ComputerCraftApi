package com.eyeball.ccframework.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Implement this on a class, then during init, run <br>
 * <br>
 * <code>
 *   FMLInterModComms.sendMessage("ComputerCraftFramework",
				"addPeripheralTarget", "fullyQualifiedPathToClass");
 * </code>
 * 
 * <br>
 * <br>
 * 
 * All classes using this will have a <code>doc</code> method. It returns the
 * String set in the method's
 * 
 * @author eyeballcode
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface PeripheralTarget {

	/**
	 * 
	 * Get the class you want to be a peripheral.
	 * 
	 * Must be the TileEntity.
	 * 
	 * @return
	 */
	public Class<?> getTargetClass();

	/**
	 * 
	 * Get the peripheral's name, for <code>
	 * peripheral.wrap("nameHere")
	 * </code>
	 * 
	 * @return
	 */
	public String getPeripheralName();

	/**
	 * 
	 * The method to call when the peripheral is attached
	 * 
	 * @return
	 */
	public String attachMethod() default "";

	/**
	 * 
	 * The method to call when the peripheral is detached
	 * 
	 * @return
	 */
	public String detachMethod() default "";

}
