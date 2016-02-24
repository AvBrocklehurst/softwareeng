package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.InstrumentNamer;
/**
 * 
 * @author Josh
 * @version 1.1.7
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
	 * @version 1.0.0
	 * 
	 * test to see if an instance can be created.
	 */
	@Test
	public void testGetInstanceOnce() {
		instrumentNamer = InstrumentNamer.getInstance();
		assertNotNull(instrumentNamer);
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * test to see if an instance can be created, but not multiple times.
	 */
	@Test
	public void testGetInstanceTwice() {
		instrumentNamer = InstrumentNamer.getInstance();
		instrumentNamer = InstrumentNamer.getInstance();
		assertNotNull(instrumentNamer);  
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * test to see if we get the correct instrument
	 */
	@Test
	public void testGetInstrument() {
		instrumentName = InstrumentNamer.getInstance().getName(110);
		assertEquals("Bagpipe", instrumentName);
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * test to see if we get the correct percussion instrument
	 */
	@Test
	public void testGetPercussionInstrument() {
		instrumentName = InstrumentNamer.getInstance().getName(133);
		assertEquals("Hand Clap", instrumentName);
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * since there is no instrument zero, we shouldnt get a value
	 */
	@Test
	public void testGetInstrumentZero() {
		instrumentName = InstrumentNamer.getInstance().getName(0);
		assertNull(instrumentName);
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * since there is no instrument 500, we shouldnt get a value
	 */
	@Test
	public void testGetInstrument500() {
		instrumentName = InstrumentNamer.getInstance().getName(500);
		assertNull(instrumentName);
	}
	
	
	
}
