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
import simori.ModeController;
import simori.SimoriGui.GridButtonEvent;

public class TestChangerMode {
	
	private MockGridButtonEvent mockevent;
	private MockSimoriGui mockgui;
	private ChangerMode testcmode;
	private ModeController testcontroller;
	
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
		testcontroller = new ModeController();
		testcmode = new ChangerMode();
		
		
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void testConstructor(){
		
	}
}
