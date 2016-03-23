package simori.Tests.ModeTests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.SimoriGui.FunctionButtonEvent;
import simori.Modes.MasterSlaveMode;
import simori.Modes.QwertyKeyboard;
import simori.Tests.MockModeController;
import simori.Tests.GuiTests.MockSimoriJFrame;

/**
 * 
 * @author Jurek
 *
 */
public class TestMasterSlaveMode {

	private MockSimoriJFrame gui;
	private MatrixModel model;
	private MockModeController mode;
	private MasterSlaveMode msmode;
	private AudioFeedbackSystem audio;
	private MIDISoundSystem midi;
	
	@Before
	public void setUp() {
		gui = new MockSimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		midi = new MIDISoundSystem(false);
		audio = new AudioFeedbackSystem(midi, model);
		mode = new MockModeController(gui, model, audio, 20160);
		msmode = new MasterSlaveMode(mode);
		mode.setComponentsToPowerToggle(model, gui);
		mode.setOn(false, false);
		mode.setOn(true, false);
		mode.setMode(msmode);
	}
	
	@After
	public void tearDown() {
		mode.setOn(false, false);
		gui = null;
		model = null;
		midi = null;
		audio = null;
		mode = null;
		msmode = null;
	}
	
	@Test
	public void testSetInitialGrid() {
		msmode.setInitialGrid();
		assertNull(gui.getText());
		for(byte x=0;x<16;x++) {
			for(byte y=0;y<16;y++){
				assertFalse(gui.getLedPanel().getLed(x, y).getIlluminated());
			}
		}
	}
	
	@Test
	public void testOnLocalIpCheck() {
		msmode.onLocalIpCheck();
		assertEquals(gui.getText(), "Obtaining IP range...");
	}
	
	@Test
	public void testOnRangeChanged() {
		msmode.onRangeChanged("test");
		assertEquals(gui.getText(), "Scanning test IP range...");
	}
	
	@Test
	public void testOnCompletion() {
		msmode.onCompletion(false);
		assertEquals(gui.getText(), "Could not find slave!");
		msmode.onCompletion(true);
		assertEquals(gui.getText(), "Slave located!");
	}
	
	@Test
	public void testOnFunctionButtonPressOK() {
		msmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.OK));
		assertThat(mode.getMode().getClass(), not(instanceOf(MasterSlaveMode.class)));
	}
	
	@Test
	public void testOnFunctionButtonPressON() {
		msmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.ON));
		assertThat(mode.getMode().getClass(), not(instanceOf(MasterSlaveMode.class)));
	}
	
	@Test
	public void testOnFunctionButtonPressAnyother() {
		msmode.onFunctionButtonPress(new FunctionButtonEvent(gui, FunctionButton.L1));
		assertEquals(mode.getMode().getClass(), MasterSlaveMode.class);
		
	}
}
