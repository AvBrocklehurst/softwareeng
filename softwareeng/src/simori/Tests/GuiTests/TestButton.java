package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.Button;
import simori.Tests.GuiTests.MockSimoriJFrame.MockButton;

/**
 * A class to test the Button class. As the rest of the suite
 * tests test the private gui methods when a given gui is set to 
 * it is redundant to test some of the methods in this class.
 * 
 * @author James
 * @version 1.0.2
 * @see Button.java
 *
 */
public class TestButton {
	
	private MockButton testbutt;
	
	@Before 
	public void setUp() {
		testbutt = new MockButton();
	}
	
	@After 
	public void tearDown(){
		testbutt = null;
	}
	
	
	@Test
	public void test_set_get_Text(){
		testbutt.setText("Hello World!");
		assertEquals("The text should be Hello World!", "Hello World!", testbutt.getText());
	}
	
	@Test
	public void test_setDefiniteSize_getPreferredSize(){
		testbutt.setDefiniteSize(new Dimension(5,5));
		assertEquals("The size was not set correctly!", new Dimension(5,5), testbutt.getPreferredSize());
	}
	
	@Test
	public void test_setDefiniteSize_getMaximumSize(){
		testbutt.setDefiniteSize(new Dimension(5,5));
		assertEquals("The size was not set correctly!", new Dimension(5,5), testbutt.getMaximumSize());
	}
	
	@Test
	public void test_setDefiniteSize_getMinimumSize(){
		testbutt.setDefiniteSize(new Dimension(5,5));
		assertEquals("The size was not set correctly!", new Dimension(5,5), testbutt.getMinimumSize());
	}
	
	
	
}
