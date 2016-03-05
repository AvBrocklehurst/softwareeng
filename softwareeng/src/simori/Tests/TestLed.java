package simori.Tests;

import static org.junit.Assert.assertEquals;



import java.awt.event.MouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.Led;

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
	public void test_getFillColour(){
		
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
