package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.InstrumentNamer;
import simori.Exceptions.SimoriNonFatalException;
/**
 * 
 * @author Josh
 * @author Jurek
 * @author Adam
 * @version 1.1.8
 * {@link simori.InstrumentNamer}
 * 
 * JUnit tests for the InstrumentNamer Class.
 */
public class TestInstrumentNamer {

	/**
	 * @version 1.0.1
	 * 
	 * test to see if an instance can be created.
	 */
	@Test
	public void testGetInstanceOnce() {
			InstrumentNamer instrumentNamer = InstrumentNamer.getInstance();
			assertNotNull(instrumentNamer);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * test to see if an instance can be created, but not multiple times.
	 */
	@Test
	public void testGetInstanceTwice() {
		InstrumentNamer instrumentNamer = InstrumentNamer.getInstance();
		instrumentNamer = InstrumentNamer.getInstance();
			assertNotNull(instrumentNamer);  
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * test to see if we get the correct instrument
	 */
	@Test
	public void testGetInstrument() {
		String instrumentName = InstrumentNamer.getInstance().getName(110);
		assertEquals("Bagpipe", instrumentName);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * test to see if we get the correct percussion instrument
	 */
	@Test
	public void testGetPercussionInstrument() {
		String instrumentName = InstrumentNamer.getInstance().getName(133);
			assertEquals("Hand Clap", instrumentName);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * since there is no instrument zero, we shouldnt get a value
	 */
	@Test
	public void testGetInstrumentZero() {
		String instrumentName = InstrumentNamer.getInstance().getName(0);
			assertNull(instrumentName);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * since there is no instrument 500, we shouldnt get a value
	 */
	@Test
	public void testGetInstrument500() {
		String instrumentName = InstrumentNamer.getInstance().getName(500);
			assertNull(instrumentName);
	}
	
	
	
}
