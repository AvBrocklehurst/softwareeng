package simori.Tests.GuiTests;

import simori.SwingGui.Led;

import java.awt.Color;

/**
 * A MockObject to allow access to Led methods and
 * attributes
 * 
 * @author James
 * @version 1.0.0
 * @see Led.java
 *
 */
public class MockLed extends Led{
	
	public boolean getIlluminated(){
		return lit;
	}
	
	public boolean getMouseDown(){
		return mouseDown;
	}
	
	public boolean getPushed(){
		return pushed;
	}
	
	public void setMouseDown(){
		mouseDown = true;
	}
	
	@Override
	public Color getFillColour(){
		return super.getFillColour();
	}
	
	@Override
	public Color getBorderColour(){
		return super.getBorderColour(); 
	}

}
