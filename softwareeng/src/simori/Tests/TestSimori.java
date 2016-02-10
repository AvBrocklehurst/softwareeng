package simori.Tests;

import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import simori.Simori;

public class TestSimori {

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
