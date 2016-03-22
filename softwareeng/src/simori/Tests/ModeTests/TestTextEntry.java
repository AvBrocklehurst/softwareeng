package simori.Tests.ModeTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
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
	private MIDISoundSystem midi;
	private AudioFeedbackSystem audio;
	
	@Before
	public void setUp() throws SimoriNonFatalException {
		gui = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		midi = new MIDISoundSystem(false);
		audio = new AudioFeedbackSystem(midi, model);
		mode = new ModeController(gui, model, audio, 20160);
		text = new MockTextEntry(mode);
		mode.setComponentsToPowerToggle(model, gui);
		mode.setOn(true, false);
		gui.setKeyboardShown(true);
	}
	
	@After
	public void tearDown() throws SimoriNonFatalException {
		mode.setOn(false, false);
		gui = null;
		model = null;
		mode = null;
		text = null;
		audio = null;
		midi = null;
	}
	
	@Test
	public void testGetText() {
		StringBuilder builder = text.getBuilder();
		assertEquals(builder.toString(), text.getText(new Setting((byte)1, (byte)1)));
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
