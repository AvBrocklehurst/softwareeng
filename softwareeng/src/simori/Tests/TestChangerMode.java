package simori.Tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.FunctionButton;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.ChangerMode;
import simori.Modes.ChangerMode.Changer;
import simori.Modes.ChangerMode.Setting;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * A class to test ChangerMode. As ChangerMode is
 * simply an extension of Mode which links closely 
 * to Mode and other classes itself, it cannot be tested closely.
 * The use of local variables also makes it more difficult to 
 * test accurately.
 * 
 * @author James
 * @author Jurek
 * @version 1.0.2
 * @see ChangerMode.java
 *
 */
public class TestChangerMode {
	
	private ModeController testcontroller;
	private SimoriJFrame testgui;
	private MatrixModel testmodel;
	private NetworkMaster testmaster;
	private NetworkSlave testslave;
	private QwertyKeyboard keyboard;
	private ChangerMode testcmode;
	private FunctionButtonEvent fbevent;
	private FunctionButton fb;
	private GridButtonEvent gbevent;
	private AudioFeedbackSystem testaudio;
	private MIDISoundSystem testmidi;
	
	private Changer testChanger(){
		return new Changer(){

			@Override
			public String getText(Setting setting) {
				return "Hello World!";
			}

			@Override
			public boolean doThingTo(ModeController controller) {
				return false;
			}

			@Override
			public Setting getCurrentSetting() {
				return null;
			}
			
			public String produceNullText(){
				return null;
			}
			
		};
	}
	
	@Before
	public void setUp() throws IOException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
		testgui = new SimoriJFrame(keyboard);
		testmodel = new MatrixModel(16, 16);
		testmidi = new MIDISoundSystem(false);
		testaudio = new AudioFeedbackSystem(testmidi, testmodel);
		testcontroller = new ModeController(testgui, testmodel, testaudio, 0);
		testslave = new NetworkSlave(0, testcontroller);
		testmaster = new NetworkMaster(0, testcontroller, testslave);
		testcmode = new ChangerMode(testcontroller, testChanger(), true, true);
		fb = FunctionButton.OK;
		fbevent = new FunctionButtonEvent(testgui,fb);
		gbevent = new GridButtonEvent(testgui, 0, 0);
	}
	
	@After
	public void tearDown(){
		testmaster.stopRunning();
		testslave.switchOff();
		keyboard = null;
		testgui = null;
		testmodel = null;
		testslave = null;
		testmaster = null;
		testcontroller = null;
		testcmode = null;
		fb = null;
		fbevent = null;
		gbevent = null;
		testaudio = null;
		testmidi = null;
	}
	
	/**onFunctionButtonPress is tested in Mode, and ChangerMode simply calls super to Mode. Therefore there is little
	 * reason to test this feature further from ChangerMode as it is simply an extension of Mode.
	 */
	@Test
	public void call_onFunctionButtonPress(){
		testcmode.onFunctionButtonPress(fbevent);
	}
	
	@Test
	public void test_onGridButtonPress() {
		testcmode.onGridButtonPress(gbevent);    //coverage call
		Setting s = new Setting((byte)gbevent.getX(), (byte)gbevent.getY());  //replicate method functionality
		String t = testChanger().getText(s);
		assertEquals("The method functionality did not behave as expected!", "Hello World!", t);
	}
	
	@Test
	public void test_setInitialGrid(){
		testcmode.setInitialGrid(); //coverage call
		Setting current = testChanger().getCurrentSetting(); //again the only way to test here was to replicate a degree of functionality
		assertNull("The current setting passed should be null!", current);
		
	}
	
	@Test
	public void test_getText(){
		Setting s = new Setting((byte)0, (byte)0);
		assertEquals("The text should be Hello World!", "Hello World!", testChanger().getText(s));
	}
	
	@Test
	public void test_doThingTo(){
		assertEquals("A thing was not done!", false, testChanger().doThingTo(testcontroller));
	}
	
	@Test
	public void test_getCurrentSetting(){
		assertNull("The current setting should be null!", testChanger().getCurrentSetting());
	}
	
	@Test
	public void test_settingConstructor(){
		assertThat("A Setting was not correctly created!", new Setting((byte)0, (byte)0), instanceOf(Setting.class));
	}
	
	@Test
	public void test_setting_noArgumentConstructor(){
		assertThat("A no argument Setting was not correctly created!", new Setting(), instanceOf(Setting.class));
	}
	
}
