package simori.Tests;

import static org.junit.Assert.*;
//package simori.Tests;

import javax.sound.midi.ShortMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import simori.MIDISoundSystem;

/**
 * 
 * @author Josh
 * @version 1.0.4
 * @see MIDISoundSystem
 * Junit test for the MIDISoundSystem.
 * 
 * Please note: All protected methods are tested implicitly through TestMIDIMessengerSystem, TestSimoriSoundSystem and TestAudioFeedback.
 * Please note: These test take some time because the constructor has to load the soundbank.
 */
public class TestMidiSoundSystem {
	MIDISoundSystem midi;
	ShortMessage message;

	
	@Before
	public void setUp() throws Exception {
		midi = new MIDISoundSystem();
		message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, 0, 60, 80);
	}

	@After
	public void tearDown() throws Exception {
		midi = null; 
		message = null;
	}

	/* Test constructor
	 * NOTE: It will be extremely difficult for constructor to fail.
	 * I'm not sure how I could get my test to fail retrieving a synthesiser, without un-installing a sound driver (or something similar).
	 */
	@Test
	public void testMIDISoundSystem() {
		assertNotNull(midi); 
	}
	
	//Test switch on
	@Test
	public void testSwitchOn() {
		midi.switchOn();
	}
	
	//Test switch on
	@Test
	public void testSwitchOff() {
		midi.switchOff();
	}
	
	//Test switch off then on
	@Test
	public void testSwitchOffAndOn() {
		midi.switchOff();
		midi.switchOn();
	}
	
}
