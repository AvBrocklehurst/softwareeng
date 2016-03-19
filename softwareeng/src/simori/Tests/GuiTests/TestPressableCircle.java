package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static simori.SwingGui.GuiProperties.CIRCLE_NOT_PRESSED;
import static simori.SwingGui.GuiProperties.CIRCLE_BORDER;

import java.awt.event.MouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.OnPressListenerMaker.OnPressListener;
import simori.SwingGui.SimoriJFrame;
import simori.Tests.GuiTests.MockSimoriJFrame.MockPressableCircle;

/**
 * A class to test the abstract class PressableCircle.
 * 
 * @author James
 * @version 1.0.0
 * @see PressableCircle
 *
 */
public class TestPressableCircle {
	
	private MockPressableCircle mockcircle;
	private SimoriJFrame testgui;
	private OnPressListenerMaker onpress;
	private OnPressListener testlistener;
	private QwertyKeyboard keyboard;
	private MouseEvent testevent;
	
	@Before
	public void setUp() throws SimoriNonFatalException{
		mockcircle = new MockPressableCircle();
		keyboard = new QwertyKeyboard((byte)16, (byte)16);
		testgui = new SimoriJFrame(keyboard);
		onpress = new OnPressListenerMaker(testgui);
		testlistener = onpress.getListener(0, 0);
	}
	
	@After
	public void tearDown(){
		mockcircle = null;
		keyboard = null;
		testgui = null;
		onpress = null;
		testlistener = null;
	}
	
	@Test
	public void test_addAndRemove_onPressListener(){
		mockcircle.addOnPressListener(testlistener);
		boolean removed = mockcircle.removeOnPressListener(testlistener);
		assertEquals("The item was not removed and therefore was not present in the arraylist due to failure of add!", true, removed);
	}
	
	@Test
	public void test_getFillColour(){
		assertEquals("Fill colour was not succesfully retrieved", CIRCLE_NOT_PRESSED, mockcircle.getFillColour());
	}
	
	@Test 
	public void test_getBorderColour(){
		assertEquals("The border colour was not succesfully retrieved!", CIRCLE_BORDER, mockcircle.getBorderColour());
	}
	
	@Test 
	public void test_mouseEntered(){
		mockcircle.mouseEntered(testevent);
		assertEquals("Was not invoked when mouse entered a component", true, mockcircle.getMouseOver());
	}
	
	@Test
	public void test_mouseEnteredFalse(){
		assertEquals("No Invocation as expected", false, mockcircle.getMouseOver());
	}
	
	@Test
	public void test_mouseExited(){
		mockcircle.setPushed();
		mockcircle.mouseExited(testevent);
		assertEquals("Pushed was not set to false as expected!", false, mockcircle.getPushed());
	}
	
	@Test
	public void test_mouseExited2(){
		mockcircle.mouseExited(testevent);
		assertEquals("Mouseover was not set to false as expected", false, mockcircle.getMouseOver());
	}
	
	@Test
	public void test_mousePressed(){
		mockcircle.setMouseOver();
		mockcircle.mousePressed(testevent);
		assertEquals("Pushed was not set to true!", true, mockcircle.getPushed());
	}
	
	@Test
	public void test_mousePressedFalse(){
		mockcircle.mousePressed(testevent);
		assertEquals("Pushed should be false!", false, mockcircle.getPushed());
	}
	
	@Test
	public void test_mouseReleased(){
		mockcircle.setPushed();
		mockcircle.mouseReleased(testevent);
		assertEquals("Pushed was not set to false as expected", false, mockcircle.getPushed());
	}
	

}
