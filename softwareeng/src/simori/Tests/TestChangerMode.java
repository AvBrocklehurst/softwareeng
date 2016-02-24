package simori.Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import simori.ChangerMode;
import simori.ChangerMode.Changer;
import simori.ChangerMode.Setting;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.GridButtonEvent;
import simori.SwingGui.SimoriJFrame;


/**
 * 
 * @author Adam
 * @author James
 *
 */
public class TestChangerMode {
	
	private ChangerMode testcmode;
	private ModeController testcontroller;
	private SimoriJFrame gui;
	private MatrixModel model;
	
	private Changer makeTestChanger() {
		return new Changer() {
		
			@Override
			public String getText(Setting s) {
				return null;
			}
			
			@Override
			public boolean doThingTo(ModeController controller) {
				return true; 
			}

			@Override
			public Setting getCurrentSetting() {
				return null;  
			}
		};
	}
	
	
	
	
	@Before 
	public void setUp(){
		gui = new SimoriJFrame(16,16);
		model = new MatrixModel(16,16);
		testcontroller = new ModeController(gui, model);
		testcmode = new ChangerMode(testcontroller, null, false, false);
		
		
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void testConstructor(){
		
	}
}
