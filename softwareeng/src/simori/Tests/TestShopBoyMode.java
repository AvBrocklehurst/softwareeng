package simori.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.MasterSlaveMode;
import simori.Modes.QwertyKeyboard;
import simori.Modes.ShopBoyMode;
import simori.Tests.GuiTests.MockSimoriJFrame;

public class TestShopBoyMode {

	private MockSimoriJFrame gui;
	private MatrixModel model;
	private ModeController mode;
	private ShopBoyMode sbmode;
	
	@Before
	public void setUp() throws SimoriNonFatalException {
		gui = new MockSimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		mode = new ModeController(gui, model, 20160);
		mode.setComponentsToPowerToggle(model, gui);
		sbmode = new ShopBoyMode(mode);
		mode.setOn(true);
	}
	
	@After
	public void tearDown() {
		mode.setOn(false);
		gui = null;
		model = null;
		mode = new ModeController(gui, model, 20160);
	}
	
	@Test
	public void testSetInitialGrid() {
		sbmode.setInitialGrid();
		assertNull(gui.getText());
		for(byte x=0;x<16;x++) {
			for(byte y=0;y<16;y++){
				assertFalse(gui.getLedPanel().getLed(x, y).getIlluminated());
			}
		}
	}
	
	
	
}
