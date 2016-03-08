package simori.Tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.FunctionButton;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Exceptions.KeyboardException;
import simori.Modes.Mode;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

public class TestMode{
	
	private SimoriJFrame testgui;
	private MatrixModel testmodel;
	private TesterMode testermode;
	private NetworkMaster testmaster;
	private NetworkSlave testslave;
	private QwertyKeyboard keyboard;
	private ModeController testcontroller;
	private FunctionButtonEvent testfbevent;
	private FunctionButton testfb;
	
	private class TesterMode extends Mode{

		public TesterMode(ModeController controller) {
			super(controller);
		}

		@Override
		public void onGridButtonPress(GridButtonEvent e)
				throws InvalidCoordinatesException {
		}
		
		@Override 
		public ModeController getModeController(){
			return super.getModeController();
		}
		
		@Override
		public SimoriGui getGui(){
			return super.getGui();
		}
		
		@Override
		public MatrixModel getModel(){
			return super.getModel();
		}
		
		@Override 
		public byte getDisplayLayer(){
			return super.getDisplayLayer();
		}

		
	}
	
	@Before
	public void setUp() throws KeyboardException, IOException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard);
		testfb = FunctionButton.L1;
		testfbevent = new FunctionButtonEvent(testgui, testfb);
		testmodel = new MatrixModel(16, 16);
		testcontroller = new ModeController(testgui, testmodel, 0, testmaster);
		testslave = new NetworkSlave(0, testcontroller);
		testmaster = new NetworkMaster(0, testmodel, testslave);
		testermode = new TesterMode(testcontroller);
	}
	
	@After
	public void tearDown(){
		keyboard = null;
		testgui = null;
		testfbevent = null;
		testfb = null;
		testmodel = null;
		testcontroller = null;
		testslave = null;
		testmaster = null;
		testermode = null;
	}
	
	@Test
	public void test_getModeController(){
		assertThat("The retrieved object is not a mode controller!", testermode.getModeController(), instanceOf(Mode.class));
	}
	
	@Test
	public void test_getGui(){
		assertThat("The retrieved object is not a SimoriGui!", testermode.getGui(), instanceOf(SimoriGui.class));
	}
	
	@Test
	public void test_getModel(){
		assertThat("The retrieved object is not a MatrixModel", testermode.getModel(), instanceOf(MatrixModel.class));
	}
	
	@Test
	public void test_getDisplayLayer(){
		testermode.getModeController().setDisplayLayer((byte)0);
		assertEquals("That is not the correct layer", (byte)0, testermode.getDisplayLayer());
	}
	
	@Test
	public void test_onFunctionButtonPress(){
		
	}

}
