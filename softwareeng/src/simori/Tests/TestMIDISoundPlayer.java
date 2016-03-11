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
 * @version 2.0.1
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
	
	final byte[] singleNote = {0,0,80,60}; // channel:0 , instrument:0 (piano), velocity:80, pitch 60 (middle c).
	final byte[] multiNotes = {0,0,80,60,64,67}; // multiple notes (c chord).
	final byte[] maximum1Layer = {0,0,80,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75}; // 16 notes.
	
	final byte[] badChannel = {20,0,80,60}; // channel is not between 0-15.
	final byte[] badInstrument = {0,-50,80,60}; // instrument not between 0-127.
	final byte[] badVelcoity = {0,0,-50,60}; // velocity not between 0-127.
	final byte[] badPitch = {0,0,80,-50}; // pitch not between 0-127.
	
	final byte[] differentInstrument = {0,110,80,60}; // channel:0 , instrument:110 (bagpipes), velocity:80, pitch 60 (middle c).
	final byte[] percussionInstrument = {9,0,80,60}; // channel:9 , instrument:0 (arbitrary), velocity:80, pitch 39 (handclap).
	final byte[] differentVelocity = {0,0,120,60}; // channel:9 , instrument:0 (piano), velocity:120, pitch 60 (middle c).
	
	final byte[][] maximumLayersAndNotes = { // used with testPlayMaximumLayersAndNotes()
			{0,10,10,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{9,0,80,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50}, //percussion layer
			{0,15,15,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,20,20,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,25,25,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,30,30,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,35,35,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{9,0,80,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50}, //percussion layer
			{0,40,40,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,45,45,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,50,50,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,55,55,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,60,60,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{0,65,65,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75},
			{9,0,80,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50}, //percussion layer
			{9,0,80,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50}, //percussion layer
	};
	
	
	
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
		player.switchOff();
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
		array[0] = singleNote;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * Play a multiple notes.
	 */
	@Test
	public void testPlayMultiple() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = multiNotes;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * throw exception if channel is bad.
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
	 * throw exception if instrument is bad.
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
	 * throw exception if velocity is bad.
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
	 * throw exception if pitch is bad.
	 */
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayBadPitch() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = badPitch;
		player.play(array); 
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * test sound stops playing.
	 */
	@Test
	public void testSwitchOff() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = singleNote;
		player.play(array); 
		player.switchOff();
		assertNotNull(player); // we do not want the sound player to be destroyed when it is switched off.
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException
	 * 
	 * ArbitarySoundTest
	 * test sound stops playing and that another note cannot be played when it turned off.
	 */
	@Test(expected = IllegalStateException.class)
	public void testSwitchOffThenPlayNote() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = singleNote;
		player.play(array); 
		player.switchOff();
		player.play(array); // should fail is synth and reciever are closed
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * test that we can play sound after turning the synth back on.
	 */
	@Test
	public void testSwitchOn() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = singleNote;
		player.switchOff();
		player.switchOn();
		player.play(array); 
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * play a different instrument.
	 */
	@Test
	public void testPlayDifferentInstrument() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = differentInstrument;
		player.play(array); 
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * play a percussion instrument.
	 */
	@Test
	public void testPlayPercussionInstrument() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = percussionInstrument;
		player.play(array); 
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * play a different velocity.
	 */
	@Test
	public void testPlayDifferentVelocity() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = differentVelocity;
		player.play(array); 
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * play a multiple layers.
	 */
	@Test
	public void testPlayMultiplelayers() throws InvalidMidiDataException {
		array = new byte[3][];
		array[0] = singleNote;
		array[1] = multiNotes;
		array[2] = maximum1Layer;
		player.play(array); 
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * The ultimate test! 16 layers, 16 notes, each of a different instrument, each with a different velocity.
	 */
	@Test(timeout = 10000) // dont want it to take too long to play all the notes simultaneously, otherwise it will go out of sync!
	public void testPlayMaximumLayersAndNotes() throws InvalidMidiDataException {
		player.play(maximumLayersAndNotes); 
	}
	
	
	/**
	 * @author Josh 
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * Test the stop play method with a usual input
	 */
	@Test
	public void testStopPlay() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = singleNote;
		player.play(array); 
		player.stopPlay();
	}
	
	
	/**
	 * @author Josh 
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * Test the stop play method with multiple inputs
	 */
	@Test
	public void testStopPlayMultiple() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = multiNotes;
		player.play(array); 
		player.stopPlay();
	}
	
	
	/**
	 * @author Josh 
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * Test the stop play method with multiple layers
	 */
	@Test
	public void testStopPlayMultipleLayers() throws InvalidMidiDataException {
		array = new byte[3][];
		array[0] = singleNote;
		array[1] = multiNotes;
		array[2] = maximum1Layer;
		player.play(array); 
		player.stopPlay();
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * ArbitarySoundTest
	 * The ultimate test! 16 layers, 16 notes, each of a different instrument, each with a different velocity.
	 * Does it stop all the notes?
	 */
	@Test(timeout = 100) // dont want it to take too long to play all the notes simultaneously, otherwise it will go out of sync!
	public void testStopPlayMaximumLayersAndNotes() throws InvalidMidiDataException {
		player.play(maximumLayersAndNotes); 
		player.stopPlay();
	}
	
	/**
	 * @author Josh 
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * 
	 * 
	 * Test that stop doesnt work if there is nothing has been played
	 */
	@Test(expected = NullPointerException.class)
	public void testStopPlayNothingToStop() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = multiNotes;
		player.stopPlay();
	}
}
