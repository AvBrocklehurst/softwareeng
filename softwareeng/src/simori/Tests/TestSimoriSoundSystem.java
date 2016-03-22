package simori.Tests;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SimoriSoundSystem;
/**
 * 
 * @author Josh
 * @version 3.1.5
 * {@link simori.SimoriSoundSystem}
 * 
 * JUnit tests for the MIDISoundPlayer Class.
 * NOTE: Since this class has no error checking, technically any invalid data (such as an instrument number over 127) wont be caught by this class.
 * Instead that should be checked in the class that calls this class.
 * 
 * NOTE2: since the .play(array) method doesn't actually return anything the only way to check it works is to hear what sound it plays.
 * As a result most of these tests are Tests are arbitrary sounds Tests.
 *
 */
public class TestSimoriSoundSystem {
	SimoriSoundSystem player;
	
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
	
	@Before
	public void setUp() throws Exception {
		player = new SimoriSoundSystem(new MockMIDISoundSystem(false));
	}

	@After
	public void tearDown() throws Exception {
		player = null;
	}

	@Test
	public void testSimoriSoundSystem() {
		assertNotNull(player);
	}

	// Test normal play
	@Test
	public void testPlay() {
		array = new byte[1][];
		array[0] = singleNote;
		player.play(array); 
	}
	
	//Test multiple notes in same layer
	@Test
	public void testPlayMultiple(){
		array = new byte[1][];
		array[0] = multiNotes;
		player.play(array); 
	}

	//Test multiple notes in different layers
	@Test
	public void testPlayMultiplelayers(){
		array = new byte[3][];
		array[0] = singleNote;
		array[1] = multiNotes;
		array[2] = maximum1Layer;
		player.play(array); 
	}
	

	

	 //play a different instrument.
	@Test
	public void testPlayDifferentInstrument(){
		array = new byte[1][];
		array[0] = differentInstrument;
		player.play(array); 
	}
	

	//play a percussion instrument.
	@Test
	public void testPlayPercussionInstrument(){
		array = new byte[1][];
		array[0] = percussionInstrument;
		player.play(array); 
	}
	
	

	//play a different velocity.
	@Test
	public void testPlayDifferentVelocity(){
		array = new byte[1][];
		array[0] = differentVelocity;
		player.play(array); 
	}
	
	//Test all 16 layers
	@Test(timeout = 5000) // dont want it to take too long to play all the notes simultaneously, otherwise it will go out of sync!
	public void testPlayMaximumLayersAndNotes() throws InvalidMidiDataException {
		player.play(maximumLayersAndNotes); 
	}
	
	//Test stop
	@Test
	public void testStopPlayer(){
		player.stopPlay();
	}
	
	//Test the stop play method with a usual input
	@Test
	public void testStopPlay() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = singleNote;
		player.play(array); 
		player.stopPlay();
	}
	
	

	//Test the stop play method with multiple inputs
	@Test
	public void testStopPlayMultiple() throws InvalidMidiDataException {
		array = new byte[1][];
		array[0] = multiNotes;
		player.play(array); 
		player.stopPlay();
	}
	
	

	//Test the stop play method with multiple layers
	@Test
	public void testStopPlayMultipleLayers() throws InvalidMidiDataException {
		array = new byte[3][];
		array[0] = singleNote;
		array[1] = multiNotes;
		array[2] = maximum1Layer;
		player.play(array); 
		player.stopPlay();
	}
	
	

	//The ultimate test! 16 layers, 16 notes, each of a different instrument, each with a different velocity.
	// Does it stop all the notes?
	@Test(timeout = 1000) // dont want it to take too long to play all the notes simultaneously, otherwise it will go out of sync!
	public void testStopPlayMaximumLayersAndNotes() throws InvalidMidiDataException {
		player.play(maximumLayersAndNotes); 
		player.stopPlay();
	}
	
	
	
	
	
}
