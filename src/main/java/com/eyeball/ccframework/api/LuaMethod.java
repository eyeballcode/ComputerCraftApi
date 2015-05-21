package com.eyeball.ccframework.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Use this annotation on methods to show that they are lua methods.
 * 
 * If taking numbers, take a double, not an int. IMPORTANT!
 * 
 * Otherwise it will complain it wants double when given int.
 * 
 * Make sure you take a TileEntity as the first argument.
 * 
 * @author eyeballcode
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LuaMethod {

	/**
	 * 
	 * Get the method's name, to be used in lua.
	 * 
	 * @return
	 */
	String name();

	/**
	 * 
	 * Get a description of the method.
	 * 
	 * @return
	 */
	String description();
}
