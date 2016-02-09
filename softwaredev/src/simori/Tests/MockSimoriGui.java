package simori.Tests;

import simori.SimoriGui;

/**
 * A SimoriGui Mock object. A Mock is needed
 * to prevent the gui window from permanently rendering.
 * 
 * @author James
 * @version 1.0.0
 * @see SimoriGui
 *
 */

public class MockSimoriGui extends SimoriGui{
	
	/**
	 * Super to SimoriGui to separate gui's behaviour
	 * from test methods.
	 * 
	 * @param rows
	 * @param columns
	 * @version 1.0.0
	 */
	public MockSimoriGui(int rows, int columns) {
		super(rows, columns);
	}

}
