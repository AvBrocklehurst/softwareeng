package simori.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.ShortMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.Exceptions.SimoriNonFatalException;


/**
 * 
 * @author Josh
 * @version 1.0.2
 * Class that tests MIDIMessengerSystem.
 */
public class TestMIDIMessengerSystem {
	MockMIDIMessengerSystem mms;
	ShortMessage message;
	MockMIDISoundSystem mock;
	
	@Before
	public void setUp() {
		mock = new MockMIDISoundSystem(false);
		mms = new MockMIDIMessengerSystem(mock, new MatrixModel(16, 16));
	}

	@After
	public void tearDown() {
		mock.switchOff();
		mms = null;
	}
	
	//constructor
	@Test
	public void testMIDIMessengerSystem() {
		assertNotNull(mms);
	}

	// Test normal PROGRAM_CHANGE Message
	@Test
	public void testCreatePROGRAM_CHANGEMessage() {
		message = mms.createMessage(0, 110);
		assertEquals(ShortMessage.PROGRAM_CHANGE, message.getCommand());
		assertEquals(0, message.getChannel());
		assertEquals(110, message.getData1());
		assertEquals(0, message.getData2());
	}

	// Test bad channel PROGRAM_CHANGE Message
	@Test(expected = SimoriNonFatalException.class)
	public void testCreatePROGRAM_CHANGEMessageBadChannel() {
		message = mms.createMessage(100, 110);
	}
	
	// Test bad instrument PROGRAM_CHANGE Message
	@Test(expected = SimoriNonFatalException.class)
	public void testCreatePROGRAM_CHANGEMessageBadInstrument() {
		message = mms.createMessage(0, 250);
	}
	
	
	// Test normal NOTE_ON Message
	@Test
	public void testCreateNOTE_ONMessage() {
		message = mms.createMessage(0, 60, 80);
		assertEquals(ShortMessage.NOTE_ON, message.getCommand());
		assertEquals(0, message.getChannel());
		assertEquals(60, message.getData1());
		assertEquals(80, message.getData2());
	}

	// Test bad channel NOTE_ON Message
	@Test(expected = SimoriNonFatalException.class)
	public void testCreateNOTE_ONMessageBadChannel() {
		message = mms.createMessage(100, 60, 80);
	}
	
	// Test bad instrument NOTE_ON Message
	@Test(expected = SimoriNonFatalException.class)
	public void testCreateNOTE_ONMessageBadInstrument() {
		message = mms.createMessage(0, 250,80);
	}
	
	// Test bad velocity NOTE_ON Message
	@Test(expected = SimoriNonFatalException.class)
	public void testCreateNOTE_ONMessageBadvelocity() {
		message = mms.createMessage(0, 60,200);
	}
	
	// Test isOpen
	@Test
	public void testIsOpen(){
		assertTrue(mms.isOpen());
	}
	
	//Test stop
	@Test
	public void testStopPlay() {
		mms.stopPlay();
	}

}
