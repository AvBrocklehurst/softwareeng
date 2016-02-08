package simori.Tests;

import static org.junit.Assert.*;

import java.util.Scanner;

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
 * These tests require a human to listen to the actual sound being played and confirm it passed/failed.
 * Tests with this condition will be labelled with something along the lines of 'ArbitarySoundTest'.
 *
 */
public class MIDISoundPlayerTest {
	MIDISoundPlayer player; // declare a MIDISoundPlayer
	byte[][] array; // declare an array to be used with play(array) tests.
	final byte[] goodNote = {0,0,80,60}; // channel:0 , instrument:0 (piano), velocity:80, pitch 60 (middle c).
	final byte[] badChannel = {20,0,80,60}; // channel is not between 0-15

	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * method is called before after any Junit test.
	 * @throws MidiUnavailableException 
	 */
	@Before
	public void setupUp() throws MidiUnavailableException{
		player = new MIDISoundPlayer(); // Instantiate player
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
		player = null; //remove player
		array = null; // remove array
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
	 * ArbitarySoundTest: User makes sure they hear a middle c piano note
	 * @throws InterruptedException 
	 */
	@Test
	public void testPlay() throws InvalidMidiDataException, InterruptedException{
		array = new byte[1][];
		array[0] = goodNote;
		player.play(array);
		didYouHearANoise("Piano", "80", "60");
		Thread.sleep(2000);// just wait to make sure we hear a noise!
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @throws InvalidMidiDataException 
	 * @throws InterruptedException 
	 */
	@Test(expected = InvalidMidiDataException.class)
	public void testPlayInvalidMidiDataException() throws InvalidMidiDataException, InterruptedException {
		Thread.sleep(2000);
		array = new byte[1][];
		array[0] = badChannel;
		player.play(array); 
		Thread.sleep(2000);
	}
	
	/**
	 * 
	 */
	private void didYouHearANoise(String instrument, String velocity, String pitch){
		Scanner userInput = new Scanner(System.in);
		System.out.println("Did you hear a "+instrument+" at a velocity of "+velocity+ " with a pitch of "+pitch+" ?");
		System.out.println("y if yes, n if no");
		String response = userInput.next();
		userInput.close();
		if (response.equalsIgnoreCase("n")){
			fail("person did not hear correct sound being played");
		}
		
		
	}
	//TODO test multiple channels (sprint 2).
	//TODO test multiple instruments (sprint2).
	//TODO test multiple velocities (sprint2).
	//TODO test stop(). Test in sprint 2.
}
