package simori.Tests.GuiTests;

import java.awt.Color;

import simori.SwingGui.PressableCircle;

/**
 * A Mock object for PressableCircle. Required as PressableCircle
 * is an abstract class. Provides getters and setters needed in testing.
 * 
 * @author James
 * @version 1.0.0
 * @see PressableCircle.java, TestPressableCircle
 *
 */

public class MockPressableCircle extends PressableCircle{
	
	public MockPressableCircle(){
		super();
	}
	
	public boolean getPushed(){
		return pushed;
	}
	
	public void setPushed(){
		pushed = true;
	}
	
	public boolean getMouseOver(){
		return mouseOver;
	}
	
	public void setMouseOver(){
		mouseOver = true;
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
