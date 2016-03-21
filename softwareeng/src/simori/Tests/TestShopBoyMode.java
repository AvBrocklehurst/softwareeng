package simori.Tests;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Observable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.MasterSlaveMode;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.Modes.ShopBoyMode;
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
		midi = new MIDISoundSystem();
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
		try {
			sbmode.setInitialGrid();
			//assertEquals(gui.getText(), <MusicMan>);
			for(byte x=0;x<16;x++) {
				for(byte y=0;y<16;y++){
					assertFalse(gui.getLedPanel().getLed(x, y).getIlluminated());
				}
			}
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void testOnFunctionButtonPressON() {
		try {
			boolean current = mode.isOn();
			sbmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.ON));
			assertEquals(mode.isOn(), !current);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}

	@Test
	public void testOnFunctionButtonPressOK() {
		try {
			sbmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.OK));
			assertEquals(mode.getMode().getClass(), PerformanceMode.class);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	@Test
	public void testUpdate() {
		sbmode.update(new Observable(), new Object());
		//TODO i require the files to be able to play this
	}
	
}
