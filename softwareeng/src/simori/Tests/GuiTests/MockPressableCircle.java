package simori.Tests.GuiTests;

import simori.SwingGui.PressableCircle;

public class MockPressableCircle extends PressableCircle{
	
	public MockPressableCircle(){
		super();
	}
	
	public boolean getPushed(){
		return pushed;
	}
	
	public boolean getMouseOver(){
		return mouseOver;
	}
	
	
	

}
