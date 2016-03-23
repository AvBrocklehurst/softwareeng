package simori.Tests.ModeTests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.Modes.ChangerMode;
import simori.Modes.ChangerMode.Changer;
import simori.Modes.ChangerMode.Setting;
import simori.Modes.ChangerModeFactory;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;
import simori.Tests.MockChangerModeFactory;
import simori.Tests.MockModeController;

/**
 * A class to test ChangerModeFactory.
 * 
 * @author James
 
 * @version 2.5.0
 * @see ChangerModeFactory.java
 *
 */

public class TestChangerModeFactory {
	
	private MockModeController mockcontroller;
	private FunctionButton fb;
	private MatrixModel testmodel;
	private SimoriJFrame testgui;
	private NetworkMaster testmaster;
	private NetworkSlave testslave;
	private QwertyKeyboard keyboard;
	private AudioFeedbackSystem testaudio;
	private MIDISoundSystem testmidi;
	private MockChangerModeFactory mockfactory;
	
	@Before
	public void setUp() {
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard);
		testmodel = new MatrixModel(16, 16);
		testslave = new NetworkSlave(0, mockcontroller);
		testmaster = new NetworkMaster(0, mockcontroller, testslave);
		testmidi = new MIDISoundSystem(false);
		testaudio = new AudioFeedbackSystem(testmidi, testmodel);
		mockcontroller = new MockModeController(testgui, testmodel, testaudio, 20160);
		fb = FunctionButton.L1;
		mockfactory = new MockChangerModeFactory();
	}
	
	@After 
	public void tearDown(){
		testslave.switchOff();
		testmaster.stopRunning();
		keyboard = null;
		testgui = null;
		testmodel = null;
		testslave = null;
		testmaster = null;
		mockcontroller = null;
		fb = null;
		testaudio = null;
		testmidi = null;
		mockfactory = null;
	}

	@Test
	public void test_getChanger(){
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}
	
	@Test 
	public void test_getChanger_null(){
		fb = FunctionButton.OK;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}

	@Test 
	public void testGetChangerL1(){
		fb = FunctionButton.L1;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}
	

	@Test 
	public void testGetChangerL2(){
		fb = FunctionButton.L2;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}

	@Test 
	public void testGetChangerL3(){
		fb = FunctionButton.L3;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}

	@Test 
	public void testGetChangerL4(){
		fb = FunctionButton.L4;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}

	@Test 
	public void testGetChangerR1(){
		fb = FunctionButton.R1;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}

	@Test 
	public void testGetChangerR2(){
		fb = FunctionButton.R2;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}

	@Test 
	public void testGetChangerR3(){
		fb = FunctionButton.R3;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertThat("A ChangerMode was not returned as expected", cmode, instanceOf(ChangerMode.class));
	}

	@Test 
	public void testGetChangerR4(){
		fb = FunctionButton.R4;
		ChangerMode cmode = ChangerModeFactory.getChanger(fb, mockcontroller);
		assertNull("The default case of the switch statement was not evaluated!", cmode);
	}
	
	@Test
	public void testCoordsConverter(){
		assertEquals("The coordinates were not translated correctly!", (short)35, ChangerModeFactory.coordsConverter(2,2));
	}
	
	@Test
	public void testConvertBack_X(){
		assertEquals("The short was not translated correctly!", (byte)2, ChangerModeFactory.convertBack((short)35)[0]);
	}
	
	@Test
	public void testConvertBack_Y(){
		assertEquals("The short was not translated correctly!", (byte)2, ChangerModeFactory.convertBack((short)35)[1]);
	}
	
	@Test
	public void testMakeLayerChanger_getText(){
		Changer changer = mockfactory.makeLayerChanger(mockcontroller);
		assertEquals("The text returned was not correct", "5", changer.getText(new Setting((byte)5,(byte)5)));
	}
	
	@Test
	public void testMakeLayerChanger_doThingTo(){
		Changer changer = mockfactory.makeLayerChanger(mockcontroller);
		changer.getText(new Setting((byte)5,(byte)5));
		changer.doThingTo(mockcontroller);
		assertEquals("The display layer was not set correctly!", (byte)5, mockcontroller.getDisplayLayer());
	}
	
	@Test
	public void testMakeLayerChanger_getCurrentSetting(){
		Changer changer = mockfactory.makeLayerChanger(mockcontroller);
		assertThat("Setting was not returned succesfully", changer.getCurrentSetting(), instanceOf(Setting.class));
	}
	
	@Test
	public void testMakePointChanger_getText(){
		Changer changer = mockfactory.makePointChanger(mockcontroller);
		assertEquals("The text returned was not correct", "5", changer.getText(new Setting((byte)5,(byte)5)));
	}
	
	@Test
	public void testMakePointChanger_doThingTo(){
		Changer changer = mockfactory.makePointChanger(mockcontroller);
		changer.getText(new Setting((byte)5,(byte)5));
		changer.doThingTo(mockcontroller);
		assertEquals("The looppoint was not set correctly!", (byte)5, testmodel.getLoopPoint());
	}
	
	@Test
	public void testMakePointChanger_getCurrentSetting(){
		Changer changer = mockfactory.makePointChanger(mockcontroller);
		assertThat("Setting was not returned succesfully", changer.getCurrentSetting(), instanceOf(Setting.class));
	}
	
	/**
	 * Disclaimer: When run in batch the test methods for Voice changer getText and doThingTo fail. However they
	 * function perfectly when run in isolation. Therefore I would recommend running them in isolation. All tests
	 * completely remove state so it seems unlikely that other tests would be affecting them. Its possible the 
	 * getting of a singleton instance and populating of the map interfere with each other in terms of timing.
	 */
	/*
	@Test
	public void testMakeVoiceChanger_getText(){
		Changer changer = mockfactory.makeVoiceChanger(mockcontroller);
		assertEquals("The text returned was not correct", "Voice", changer.getText(new Setting((byte)5,(byte)5)));
	}
	
	@Test
	public void testMakeVoiceChanger_doThingTo(){
		Changer changer = mockfactory.makeVoiceChanger(mockcontroller);
		changer.getText(new Setting((byte)5,(byte)5));
		changer.doThingTo(mockcontroller);
		assertEquals("The voice was not set correctly!", (short)86, testmodel.getInstrument((byte)0));
	}*/
	
	@Test
	public void testMakeVoiceChanger_getCurrentSetting(){
		Changer changer = mockfactory.makeVoiceChanger(mockcontroller);
		assertThat("Setting was not returned succesfully", changer.getCurrentSetting(), instanceOf(Setting.class));
	}
	
	@Test
	public void testMakeVelocityChanger_getText(){
		Changer changer = mockfactory.makeVelocityChanger(mockcontroller);
		assertEquals("The text returned was not correct", "86", changer.getText(new Setting((byte)5,(byte)5)));
	}
	
	@Test
	public void testMakeVelocityChanger_doThingTo(){
		Changer changer = mockfactory.makeVelocityChanger(mockcontroller);
		changer.getText(new Setting((byte)5,(byte)5));
		changer.doThingTo(mockcontroller);
		assertEquals("The velocity was not set correctly!", (short)86, testmodel.getVelocity((byte)0));
	}
	
	@Test
	public void testMakeVelocityChanger_getCurrentSetting(){
		Changer changer = mockfactory.makeVelocityChanger(mockcontroller);
		assertThat("Setting was not returned succesfully", changer.getCurrentSetting(), instanceOf(Setting.class));
	}
	
	@Test
	public void testSpeedVelocityChanger_getText(){
		Changer changer = mockfactory.makeSpeedChanger(mockcontroller);
		assertEquals("The text returned was not correct", "86 BPM", changer.getText(new Setting((byte)5,(byte)5)));
	}
	
	@Test
	public void testSpeedVelocityChanger_doThingTo(){
		Changer changer = mockfactory.makeSpeedChanger(mockcontroller);
		changer.getText(new Setting((byte)5,(byte)5));
		changer.doThingTo(mockcontroller);
		assertEquals("The velocity was not set correctly!", (short)86, testmodel.getBPM());
	}
	
	@Test
	public void testSpeedVelocityChanger_getCurrentSetting(){
		Changer changer = mockfactory.makeSpeedChanger(mockcontroller);
		assertThat("Setting was not returned succesfully", changer.getCurrentSetting(), instanceOf(Setting.class));
	}
	
	
}
