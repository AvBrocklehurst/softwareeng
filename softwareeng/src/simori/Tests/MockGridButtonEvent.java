package simori.Tests;

import simori.SimoriGui.GridButtonEvent;

/**
 * A Mock object of GridButtonEvent. A Mock is needed
 * to prevent the gui window from permanently rendering.
 * 
 * @author James
 * @version 1.0.0
 * @see GridButtonEvent, MockSimoriGui
 *
 */
public class MockGridButtonEvent extends GridButtonEvent{
	
	/**
	 * Super to GridButtonEvent to separate Gui's behaviour
	 * from test methods.
	 * 
	 * @param x
	 * @param y
	 * @version 1.0.0
	 * @see MockSimoriGui
	 */
	public MockGridButtonEvent(int x, int y) {
		super(new MockSimoriGui(16, 16),x, y);
		
	}



}
