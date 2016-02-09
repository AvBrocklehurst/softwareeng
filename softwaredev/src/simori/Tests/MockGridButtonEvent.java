package simori.Tests;

import simori.SimoriGui;
import simori.SimoriGuiEvents.GridButtonEvent;


public class MockGridButtonEvent extends GridButtonEvent{
	
	public MockGridButtonEvent(int x, int y) {
		super(new MockSimoriGui(16, 16),x, y);
		
	}



}
