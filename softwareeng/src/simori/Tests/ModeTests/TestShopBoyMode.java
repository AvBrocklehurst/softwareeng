package simori.Tests.ModeTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Observable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.SimoriGui.FunctionButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.Modes.ShopBoyMode;
import simori.Tests.MockModeController;
import simori.Tests.GuiTests.MockSimoriJFrame;

/**
 * 
 * @author Jurek
 *
 */
public class TestShopBoyMode {

	private MockSimoriJFrame gui;
	private MatrixModel model;
	private MockModeController mode;
	private ShopBoyMode sbmode;
	private MIDISoundSystem midi;
	private AudioFeedbackSystem audio;
	
	@Before
	public void setUp() throws SimoriNonFatalException {
		gui = new MockSimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		midi = new MIDISoundSystem(false);
		audio = new AudioFeedbackSystem(midi, model);
		mode = new MockModeController(gui, model, audio, 20160);
		mode.setComponentsToPowerToggle(model, gui);
		sbmode = new ShopBoyMode(mode);
		mode.setOn(true, false); //TODO
	}
	
	@After
	public void tearDown() throws SimoriNonFatalException {
		mode.setOn(false, false);
		gui = null;
		model = null;
		mode = null;
		audio = null;
		midi = null;
	}
	
	@Test
	public void testSetInitialGrid() {
		sbmode.setInitialGrid();
		//assertEquals(gui.getText(), <MusicMan>);
		for(byte x=0;x<16;x++) {
			for(byte y=0;y<16;y++){
				assertFalse(gui.getLedPanel().getLed(x, y).getIlluminated());
			}
		}
	}
	
	@Test
	public void testOnFunctionButtonPressON() {
		sbmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.ON));
		assertEquals(false, !mode.isOn());
	}

	@Test
	public void testOnFunctionButtonPressOK() {
		sbmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.OK));
		assertEquals(mode.getMode().getClass(), PerformanceMode.class);
	}
	
	@Test
	public void testUpdate() {
		sbmode.update(new Observable(), new Object());
		//TODO i require the files to be able to play this
	}
}
