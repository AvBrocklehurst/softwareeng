package simori.Tests;

import simori.SwingGui.Led;

import static simori.SwingGui.GuiProperties.LED_COLOUR_OFF;
import static simori.SwingGui.GuiProperties.LED_COLOUR_OFF_IN;
import static simori.SwingGui.GuiProperties.LED_COLOUR_ON;
import static simori.SwingGui.GuiProperties.LED_COLOUR_ON_IN;

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

}
