package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;
import static simori.SwingGui.GuiProperties.LED_COLOUR_OFF;
import static simori.SwingGui.GuiProperties.LED_COLOUR_ON;

import java.awt.event.MouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.GuiProperties;
import simori.SwingGui.Led;
import simori.Tests.GuiTests.MockSimoriJFrame.MockLed;

/**
 * The class that tests Led.java. 
 * 
 * @author James
 * @version 1.0.0
 * @see Led.java
 */
public class TestLed {
	
	private MockLed testled;
	private MouseEvent testevent;
	
	
	@Before
	public void setUp(){
		testled = new MockLed(); 
	}
	
	@After
	public void tearDown(){
		testled = null;
	}

	@Test
	public void test_setIlluminated(){
		testled.setIlluminated(true);
		assertEquals("The Led was not turned on!", true, testled.getIlluminated());
	}
	
	@Test 
	public void test_getFillColour_litFalse(){
		assertEquals("The fill colour should be a non illuminated led", LED_COLOUR_OFF, testled.getFillColour());
	}
	
	@Test
	public void test_getFillColour_litTrue(){
		testled.setIlluminated(true);
		assertEquals("The fill colour should be an illuminated led", LED_COLOUR_ON, testled.getFillColour());
	}
	
	@Test 
	public void test_mouseEntered(){
		testled.setMouseDown();
		testled.mouseEntered(testevent);
		assertEquals("Mouse was not pressed and entered in the Led", true, testled.getPushed());
		
	}
	
	@Test
	public void test_mousePressed(){
		testled.mousePressed(testevent);
		assertEquals("The mouse press was not successful!", true, testled.getMouseDown());
	}
	
	@Test
	public void test_mouseReleased(){
		testled.mouseReleased(testevent);
		assertEquals("The mouse release was not succesful!", false, testled.getMouseDown());
	}
}
