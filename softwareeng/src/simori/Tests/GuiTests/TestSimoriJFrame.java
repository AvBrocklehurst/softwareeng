package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.KeyboardMapping;
import simori.Modes.Mode;
import simori.Modes.NetworkMaster;
import simori.Modes.OffMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.Button;
import simori.SwingGui.PressableCircle;
import simori.Tests.GuiTests.MockSimoriJFrame.MockPressableCircle;

/**
 * Class that tests SimoriJFrame
 * It uses the MockSimoriJFrame to access the normally private/protected fields
 * @author Jurek
 */
public class TestSimoriJFrame {
	MockSimoriJFrame jframe;
	KeyboardMapping mapping;
	
	@Before
	public void setUp() throws Exception {
		mapping = new QwertyKeyboard((byte)16, (byte)16);
		jframe = new MockSimoriJFrame(mapping);
		
	}

	@After
	public void tearDown() throws Exception {
		jframe = null;
		mapping = null;
	}
	
	@Test
	public void testSetGrid() {
		boolean[][] grid = new boolean[16][16];
		grid[5][5] = true;
		jframe.setGrid(grid);
		
		//check that only 5,5 LED got changed
		assertTrue(jframe.getLedPanel().getLed((byte)5, (byte)5).getIlluminated());
		assertFalse(jframe.getLedPanel().getLed((byte)0, (byte)0).getIlluminated());
	}
	
	@Test
	public void testClearGrid() {
		boolean[][] grid = new boolean[16][16];
		grid[5][5] = true;
		jframe.setGrid(grid);
		jframe.clearGrid();
		assertFalse(jframe.getLedPanel().getLed((byte)5, (byte)5).getIlluminated());
	}
	
	/**
	 * Since both tests would be the same (as there is no other way
	 * of getting or settings the text field), and the methods in question
	 * are simply an assignment and a return, this unit test tests
	 * both setText and getText at the same time.
	 * @author Jurek
	 */
	@Test
	public void testSetAndGetText() {
		jframe.setText("test");
		assertEquals(jframe.getText(), "test");
	}
	
	@Test
	public void testSwitchOn() {
		jframe.switchOn();
		
		Button b = jframe.getTopBar().getButtons()[0];
		PressableCircle c = (PressableCircle)b;
		MockPressableCircle m = (MockPressableCircle)c;
		//top button
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)((PressableCircle)(jframe.getTopBar().getButtons()[0]))).getFillColour());
		//bottom buttons
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getBottomBar().getButtons()[0]).getFillColour());
		//left buttons
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getLeftBar().getButtons()[0]).getFillColour());
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getLeftBar().getButtons()[1]).getFillColour());
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getLeftBar().getButtons()[2]).getFillColour());
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getLeftBar().getButtons()[3]).getFillColour());
		//right buttons
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getRightBar().getButtons()[0]).getFillColour());
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getRightBar().getButtons()[1]).getFillColour());
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getRightBar().getButtons()[2]).getFillColour());
		assertNotEquals(new Color(0xDDDDDD), ((MockPressableCircle)(PressableCircle)jframe.getRightBar().getButtons()[3]).getFillColour());
	}
	
	@Test
	public void testSwitchOff() {
		fail();
	}
	
	@Test
	public void testSetAndGetGridButtonListener() {
		MatrixModel model = new MatrixModel(16, 16);
		Mode mode = null;
		try{mode = new OffMode(new ModeController(jframe, model, 20160, new NetworkMaster(20160, model)));}catch(IOException e){fail();}
		jframe.setGridButtonListener(mode);
		assertEquals(mode, jframe.getGridButtonListener());
	}
	
	@Test
	public void testSetAndGetFunctionButtonListener() {
		MatrixModel model = new MatrixModel(16, 16);
		Mode mode = null;
		try{mode = new OffMode(new ModeController(jframe, model, 20160, new NetworkMaster(20160, model)));}catch(IOException e){fail();}
		jframe.setFunctionButtonListener(mode);
		assertEquals(mode, jframe.getFunctionButtonListener());
	}
	
	@Test
	public void testSetKeyboardShown() {
		jframe.setKeyboardShown(true);
		String keyboardShownTrue = jframe.getGridPanel().getComponent(0).toString();
		jframe.setKeyboardShown(false);
		String keyboardShownFalse = jframe.getGridPanel().getComponent(0).toString();
		assertNotEquals(keyboardShownTrue, keyboardShownFalse);
	}
	
	@Test
	public void testGetKeyboardMapping() {
		assertEquals(mapping, jframe.getKeyboardMapping());
	}

	@Test
	public void testMouseDragged() {
		Point p = jframe.getLocation();
		jframe.mouseDragged(new MouseEvent(jframe, 0, 0, 0, p.x+5, p.y+5, 1, false));
		assertEquals(new Point(p.x+5, p.y+5), jframe.getLocation());
	}

	@Test
	public void testMouseMovedNotValid() {
		Point p = jframe.getTopBar().getLocation();
		jframe.mouseMoved(new MouseEvent(jframe, 0, 0, 0, p.x-5, p.y-5, 1, false));
		assertEquals
	}
	
	@Test
	public void testMouseValid() {
		
	}	
}
