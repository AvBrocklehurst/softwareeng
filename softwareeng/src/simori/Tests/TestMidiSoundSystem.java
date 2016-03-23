package simori.Tests;

import static org.junit.Assert.*;

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
	MockMIDISoundSystem midi;
	ShortMessage message;
	ShortMessage message2;
	
	@Before
	public void setUp() throws Exception {
		midi = new MockMIDISoundSystem(false);
		message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, 0, 60, 80);
		message2 = new ShortMessage();
		message2.setMessage(ShortMessage.NOTE_ON, 9, 50, 100);
	}

	@After
	public void tearDown() {
		midi.switchOff();
		midi = null;
		message = null;
		message2 = null;
	}

	/* Test constructor
	 * NOTE: It will be extremely difficult for constructor to fail.
	 * I'm not sure how I could get my test to fail retrieving a synthesiser, without un-installing a sound driver (or something similar).
	 */
	@Test
	public void testMIDISoundSystem() {
		assertNotNull(midi); 
		assertTrue(midi.hasActiveReceivers());
		assertTrue(midi.isSynthOn());
	}
	
	// Test constructor loading in soundbank
	@Test
	public void testConstructorSoundbank(){
		MockMIDISoundSystem withSoundbank = new MockMIDISoundSystem(true);
		assertNotNull(withSoundbank); 
		assertTrue(withSoundbank.hasActiveReceivers());
		assertTrue(withSoundbank.isSynthOn());
		withSoundbank = null;
	}
	
	
	// Test sendCommad
	@Test public void testSendCommand(){
		midi.sendCommand(message);
	}
	
	// Test sendCommads
	@Test
	public void testSendCommands(){
		ShortMessage[] messages = {message, message2};
		midi.sendCommands(messages);
	}
	
	//Test stopSound
	@Test public void testStopSound() {
		midi.stopSound();
		}
	
	//Test stopSound when something has been sent
	@Test public void testStopSoundSentMessage() {
		midi.sendCommand(message);
		midi.stopSound();
		}
	
	//Test switch on
	@Test
	public void testSwitchOn() {
		midi.switchOn();
		assertTrue(midi.isSynthOn());
		assertTrue(midi.hasActiveReceivers());
	}
	
	//Test switch off
	@Test
	public void testSwitchOff() {
		midi.switchOff();
		assertFalse(midi.isSynthOn());
		assertFalse(midi.hasActiveReceivers());
	}
	
	//Test switch off then on
	@Test
	public void testSwitchOffAndOn() {
		midi.switchOff();
		midi.switchOn();
		assertTrue(midi.isSynthOn());
		assertTrue(midi.hasActiveReceivers());
	}
	
	//Test stop
	@Test
	public void testStop(){midi.stop();}
	
	//Test to make sure you cant send a message when turned off
	@Test(expected = IllegalStateException.class)
	public void testSwitchOffSendMessage() {
		midi.switchOff();
		assertFalse(midi.isSynthOn());
		assertFalse(midi.hasActiveReceivers());
		midi.sendCommand(message);
	}
}
