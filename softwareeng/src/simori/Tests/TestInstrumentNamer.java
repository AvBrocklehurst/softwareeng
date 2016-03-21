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
 * @version 1.1.8
 * {@link simori.InstrumentNamer}
 * 
 * JUnit tests for the InstrumentNamer Class.
 */
public class TestInstrumentNamer {
	// check zero doesn't work
	
	InstrumentNamer instrumentNamer;
	String instrumentName;
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		instrumentNamer = null;
	}

	/**
	 * @author Josh
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 * test to see if an instance can be created.
	 */
	@Test
	public void testGetInstanceOnce() {
		try {
			instrumentNamer = InstrumentNamer.getInstance();
			assertNotNull(instrumentNamer);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	/**
	 * @author Josh
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 * test to see if an instance can be created, but not multiple times.
	 */
	@Test
	public void testGetInstanceTwice() {
		try {
			instrumentNamer = InstrumentNamer.getInstance();
			instrumentNamer = InstrumentNamer.getInstance();
			assertNotNull(instrumentNamer);  
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	/**
	 * @author Josh
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 * test to see if we get the correct instrument
	 */
	@Test
	public void testGetInstrument() {
		try {
			instrumentName = InstrumentNamer.getInstance().getName(110);
			assertEquals("Bagpipe", instrumentName);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	/**
	 * @author Josh
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 * test to see if we get the correct percussion instrument
	 */
	@Test
	public void testGetPercussionInstrument() {
		try {
			instrumentName = InstrumentNamer.getInstance().getName(133);
			assertEquals("Hand Clap", instrumentName);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	/**
	 * @author Josh
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 * since there is no instrument zero, we shouldnt get a value
	 */
	@Test
	public void testGetInstrumentZero() {
		try {
			instrumentName = InstrumentNamer.getInstance().getName(0);
			assertNull(instrumentName);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	/**
	 * @author Josh
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 * since there is no instrument 500, we shouldnt get a value
	 */
	@Test
	public void testGetInstrument500() {
		try {
			instrumentName = InstrumentNamer.getInstance().getName(500);
			assertNull(instrumentName);
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	
	
}
