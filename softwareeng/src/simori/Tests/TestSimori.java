package simori.Tests;

import javax.sound.midi.MidiUnavailableException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Simori;
import simori.Exceptions.KeyboardException;

/**
 * A class to test that A simori object is correctly created on
 * calling of the constructor. Unfortunately testing this will render
 * a gui window as we cannot alter the behaviour of the simori constructor
 * itself. However testing it allows us to see that a simori is 
 * created as expected.
 * 
 * @author James
 * @version 1.0.0
 * @see Simori.java
 *
 */
public class TestSimori {
	
	private Simori testsimori;
	
	@Before
	public void setUp() throws MidiUnavailableException, KeyboardException{
		testsimori = new Simori();
	}
	
	@After
	public void tearDown(){
		testsimori = null;
	}
	
	@Test
	public void testSimori(){
		assertThat("A Simori was not created!", testsimori, instanceOf(Simori.class));
	}
}
