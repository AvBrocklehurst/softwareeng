package simori.Tests;

import static org.junit.Assert.*;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MIDISoundPlayer;

/**
 * @author Josh
 * @version 1.0.0
 * {@link simori.MIDIPlayer}
 * {@link simori.MIDISoundPlayer}
 * 
 * JUnit tests for the MIDISoundPlayer Class.
 * NOTE: Since this class has no error checking, technically any invalid data (such as an instrument number over 127) wont be caught by this class.
 * Instead that should be checked in the class that calls this class.
 * As a result any tests that should fail, may actually pass! I will note these below.
 * 
 * NOTE2: since the .play(array) method doesn't actually return anything the only way to check it works is to hear what sound it plays.
 * Tests with this condition will be labelled with something along the lines of 'ArbitarySoundTest'.
 *
 */
public class TestMIDISoundPlayer {
	MIDISoundPlayer player; // declare a MIDISoundPlayer.
	byte[][] array; // declare an array to be used with play(array) tests.
	
	final byte[] goodNote = {0,0,80,60}; // channel:0 , instrument:0 (piano), velocity:80, pitch 60 (middle c).
	final byte[] secondGoodNote = {0,0,80,64}; // channel:0 , instrument:0 (piano), velocity:80, pitch 64.
	final byte[] thirdGoodNote = {0,0,80,67}; // channel:0 , instrument:0 (piano), velocity:80, pitch 67.
	
	final byte[] badChannel = {20,0,80,60}; // channel is not between 0-15.
	final byte[] badInstrument = {0,-50,80,60}; // instrument not between 0-127.
	final byte[] badVelcoity = {0,0,-50,60}; // velocity not between 0-127.
	final byte[] badPitch = {0,0,80,-50}; // pitch not between 0-127.
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * method is called before after any Junit test.
	 * @throws MidiUnavailableException 
	 */
	@Before
	public void setupUp() throws MidiUnavailableException{
		player = new MIDISoundPlayer(); // Instantiate player.
		// no need to instantiate array as it will need to have different data (and length) depending on test.
	}
	
	
	/**
	 * @author Josh 
	 * @version 1.0.0
	 * 
	 * method is called just after any Junit test.
	 */
	@After
	public void tearDown(){
		player = null; //remove player.
		array = null; // remove array.
		}

	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * Checks that constructor works properly.
	 * NOTE: It will be extremely difficult for constructor to fail.
	 * I'm not sure how I could get my test to fail retrieving a synthesiser, without un-installing a sound driver (or something similar).
	 */
	@Test
	public void testMIDISoundPlayer(){
		assertNotNull(player);
	}


	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * Play a normal note. 
	 */
	@Test
	public void testPlay() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = goodNote;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * Play a multiple notes
	 */
	@Test
	public void testPlayMultiple() throws InvalidMidiDataException {
		array = new byte[3][];
		array[0] = goodNote;
		array[1] = secondGoodNote;
		array[2] = thirdGoodNote;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * throw exception if channel is bad
	 */
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayBadChannel() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = badChannel;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * throw exception if instrument is bad
	 */
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayBadInstrument() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = badInstrument;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * throw exception if velocity is bad
	 */
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayBadVelocity() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = badVelcoity;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * throw exception if pitch is bad
	 */
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayBadPitch() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = badPitch;
		player.play(array); 
	}
	
	
	
	
	
	
	//TODO test multiple channels (sprint 2).
	//TODO test multiple instruments (sprint2).
	//TODO test multiple velocities (sprint2).
	//TODO test stop(). Test in sprint 2.
}
