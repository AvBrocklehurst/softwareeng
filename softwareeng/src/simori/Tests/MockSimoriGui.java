package simori.Tests;

import simori.SimoriGui.GridButtonEvent;
import simori.SwingGui.Led;
import simori.SwingGui.SimoriGui;
import simori.SwingGui.OnPressListenerMaker.OnPressListener;

/**
 * A SimoriGui Mock object. A Mock is needed
 * to prevent the gui window from permanently rendering.
 * Also exposes LED array for testing.
 * @author James
 * @author Matt
 * @version 1.0.0
 * @see SimoriGui
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
		setVisible(false);
	}
	
	/** @return Reference to LED at specified grid coordinates */
	public Led getLed(int x, int y) {
		return leds[x][y];
	}
	
	/** @param Whether to show the GUI */
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	/** Resizes to the specified dimensions */
	public void setSize(int width, int height) {
		frame.setSize(width, height);
	}
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	/**
	 * Invokes the helper methods which
	 * build the edge GUI elements.
	 */
	public void makeEdgeButtons() {
		makeBottomButtons();
		makeTopButtons();
		makeLeftButtons();
		makeRightButtons();
	}
	
	/** Exposes makeListenerWith for testing */
	public OnPressListener makeListenerWith(GridButtonEvent e) {
		return super.getListener(e);
	}
}
