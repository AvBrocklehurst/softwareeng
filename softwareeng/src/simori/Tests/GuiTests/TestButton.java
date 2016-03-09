package simori.Tests.GuiTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.Button;
import simori.SwingGui.SimoriJFrame;
import simori.Tests.GuiTests.MockSimoriJFrame.MockButton;

public class TestButton {
	
	private MockButton testbutt;
	private MockSimoriJFrame testgui;
	private QwertyKeyboard keyboard;
	
	@Before 
	public void setUp() throws KeyboardException{
		testbutt = new MockButton();
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new MockSimoriJFrame(keyboard);
		Button[] buttons = testgui.getLeftBar().getButtons();
		buttons[0] = testbutt;
	}
	
	@After 
	public void tearDown(){
		testbutt = null;
		keyboard = null;
		testgui = null;
	}
	
	@Test
	public void test_resized(){
		testbutt.resized();
		assertEquals("Resized was not set to true!", true, testbutt.getResized());
	}
	
	@Test
	public void test_set_get_Text(){
		testbutt.setText("Hello World");
		assertEquals("The text should be Hello World!", "Hello World!", testbutt.getText());
	}
	
	@Test
	public void test_updateSize(){
		
	}
	
	
	
	
}
