package com.kabryxis.kabutils.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Com {
	
	String name() default "";
	
	String[] aliases() default {};
	
	String description() default "";
	
	String usage() default "";
	
	String permission() default "";
	
	String args() default "";
	
}
