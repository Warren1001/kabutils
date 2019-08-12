package com.kabryxis.kabutils.swing;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.EventListener;

public class Swings {
	
	public static <T extends Component> T addListener(T component, EventListener... listeners) {
		for(EventListener listener : listeners) {
			boolean added = false;
			if(listener instanceof KeyListener) {
				component.addKeyListener((KeyListener)listener);
				added = true;
			}
			if(listener instanceof MouseListener) {
				component.addMouseListener((MouseListener)listener);
				added = true;
			}
			if(listener instanceof MouseMotionListener) {
				component.addMouseMotionListener((MouseMotionListener)listener);
				added = true;
			}
			if(listener instanceof MouseWheelListener) {
				component.addMouseWheelListener((MouseWheelListener)listener);
				added = true;
			}
			if(!added) System.out.println(String.format("Unable to handle class %s listener in %s", listener.getClass(), Swings.class.getName()));
		}
		return component;
	}
	
}
