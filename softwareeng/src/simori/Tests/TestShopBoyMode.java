package simori.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.MasterSlaveMode;
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
	private ModeController mode;
	private ShopBoyMode sbmode;
	private MIDISoundSystem midi;
	private AudioFeedbackSystem audio;
	
	@Before
	public void setUp() throws SimoriNonFatalException {
		gui = new MockSimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		midi = new MIDISoundSystem();
		audio = new AudioFeedbackSystem(midi, model);
		mode = new ModeController(gui, model, audio, 20160);
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
			assertNull(gui.getText());
			for(byte x=0;x<16;x++) {
				for(byte y=0;y<16;y++){
					assertFalse(gui.getLedPanel().getLed(x, y).getIlluminated());
				}
			}
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	//TODO finish
	
}
