package simori.Tests;

import static org.junit.Assert.*;
import org.junit.*;

import simori.FunctionButton;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.MasterSlaveMode;
import simori.Modes.QwertyKeyboard;
import simori.Tests.GuiTests.MockSimoriJFrame;

/**
 * 
 * @author Jurek
 *
 */
public class TestMasterSlaveMode {

	private MockSimoriJFrame gui;
	private MatrixModel model;
	private ModeController mode;
	private MasterSlaveMode msmode;
	
	@Before
	public void setUp() throws SimoriNonFatalException {
		gui = new MockSimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		model = new MatrixModel(16, 16);
		mode = new ModeController(gui, model, 20160);
		mode.setComponentsToPowerToggle(model, gui);
		msmode = new MasterSlaveMode(mode);
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
		assertEquals(gui.getText(), "Scanning tests IP range...");
	}
	
	@Test
	public void testOnCompletion() {
		msmode.onCompletion(false);
		assertEquals(gui.getText(), "Could not find slave!");
		msmode.onCompletion(true);
		assertEquals(gui.getText(), "Slave located!");
	}
	
	/**
	 * Empty test clause that ensures the exception
	 * doesn't get thrown
	 * @author Jurek
	 */
	@Test
	public void testOnGridButtonPress() {
		try {
			msmode.onGridButtonPress(new GridButtonEvent(gui, 0, 0));
		} catch (InvalidCoordinatesException e) {
			fail();
		}
	}
}
