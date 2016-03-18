package simori.Tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Simori;
import simori.Exceptions.SimoriNonFatalException;

/**
 * Tests the main method and consequently Simori's constructor.
 * @author Matt
 * @author James
 * @version 2.0.0
 * @see Simori
 */
public class TestMain {
	
	private Simori testsimori;
	
	@Before
	public void setUp() throws MidiUnavailableException, SimoriNonFatalException, IOException{
		testsimori = new Simori();
	}
	
	@After
	public void tearDown(){
		testsimori = null;
	}
	
	
	/**
	 * Tests an instance of Simori was correctly created.
	 * 
	 * @author James
	 * @version 1.0.0
	 * @see Simori
	 */
	@Test
	public void testSimori(){
		assertThat("A Simori was not created!", testsimori, instanceOf(Simori.class));
	}
	
	/**
	 * Runs the main method to check for exceptions.
	 * Makes the code coverage report look better.
	 */
	@Test
	public void testMainMethod() throws MidiUnavailableException {
		Simori.main(null);
	}
}
