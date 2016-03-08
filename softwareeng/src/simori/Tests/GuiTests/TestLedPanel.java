package simori.Tests.GuiTests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.KeyboardException;
import simori.SwingGui.Led;
import simori.SwingGui.PressableCircle;
import simori.Tests.GuiTests.MockSimoriJFrame.MockLedPanel;

/**
 * A class to test LedPanel.
 * 
 * @author James
 * @version 1.0.0
 * @see LedPanel.java
 */
public class TestLedPanel {
	
	private MockSimoriJFrame mockgui;
	private MockLedPanel mockpanel;
	private MockPressableCircle mockcircle;
	
	@Before
	public void setUp() throws KeyboardException{
		mockgui = new MockSimoriJFrame();
		mockpanel = mockgui.getLedPanel();
		mockcircle = new MockPressableCircle();
	}
	
	@After
	public void tearDown(){
		mockgui = null;
		mockpanel = null;
		mockcircle = null;
	}
	
	@Test
	public void test_onPress(){
		mockpanel.onPress(mockcircle);
		assertThat("lastPressed is not a PressableCircle as expected", mockpanel.getLastPressed(), instanceOf(PressableCircle.class));
	}
	
	
	@Test
	public void test_setGrid(){
		boolean[][] grid = new boolean[16][16];
		grid[0][0] = !grid[0][0];
		mockpanel.setGrid(grid);
		assertEquals("The grid was not set correctly", true, mockpanel.getLed((byte)0, (byte)0).getIlluminated());
	}
	
	
}
