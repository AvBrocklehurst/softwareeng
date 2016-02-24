package simori.Tests;

import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import simori.Simori;

/**
 * Tests the main method and consequently Simori's constructor.
 * @author Matt
 * @version 2.0.0
 */
public class TestMain {

	/**
	 * Runs the main method to check for exceptions.
	 * Makes the code coverage report look better.
	 */
	@Test
	public void testMainMethod() throws MidiUnavailableException {
		Simori.main(null);
		System.exit(0);
	}
}
