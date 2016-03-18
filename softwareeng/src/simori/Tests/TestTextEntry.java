package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.KeyboardException;
import simori.Modes.ChangerMode.Setting;
import simori.Modes.QwertyKeyboard;
import simori.Modes.TextEntry;
import simori.SwingGui.SimoriJFrame;

/**
 * 
 * @author Jurek
 *
 */
public class TestTextEntry {
	public class MockTextEntry extends TextEntry {
		public MockTextEntry(ModeController controller) {super(controller);}
		
		@Override
		public boolean useText(String text) {return test;}
		
		public StringBuilder getBuilder() {return builder;}
	}
	
	private SimoriJFrame gui;
	private MatrixModel model;
	private ModeController mode;
	private MockTextEntry text;
	private boolean test;
	
	@Before
	public void setUp() throws KeyboardException {
		gui = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		mode = new ModeController(gui, model, 20160);
		text = new MockTextEntry(mode);
		mode.setComponentsToPowerToggle(model, gui);
		mode.setOn(true);
		gui.setKeyboardShown(true);
	}
	
	@After
	public void tearDown() {
		mode.setOn(false);
		gui = null;
		model = null;
		mode = null;
		text = null;
	}
	
	@Test
	public void testGetText() {
		StringBuilder builder = text.getBuilder();
		assertEquals(builder.toString(), text.getText(new Setting((byte)0, (byte)0)));
	}
	@Test
	public void testDoThingTo() {
		test = true;
		assertTrue(text.doThingTo(mode));
		test = false;
		assertFalse(text.doThingTo(mode));
	}
	
	@Test
	public void testGetCurrentSetting() {
		assertNull(text.getCurrentSetting());
	}
}
