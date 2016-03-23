package simori.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.InstrumentNamer;
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
	
	InstrumentNamer instrumentNamer;
	String instrumentName;
	

	@Before
	public void setUp(){}

	@After
	public void tearDown() {
		instrumentNamer = null;
	}

	/**
	 * @version 1.0.1
	 * 
	 * test to see if an instance can be created.
	 */
	@Test
	public void testGetInstanceOnce() {
			instrumentNamer = InstrumentNamer.getInstance();
			assertNotNull(instrumentNamer);
	}
	
	/**
	 * @version 1.0.1
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
	 * @version 1.0.1
	 * 
	 * test to see if we get the correct instrument
	 */
	@Test
	public void testGetInstrument() {
		instrumentName = InstrumentNamer.getInstance().getName(110);
		assertEquals("Bagpipe", instrumentName);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * test to see if we get the correct percussion instrument
	 */
	@Test
	public void testGetPercussionInstrument() {
			instrumentName = InstrumentNamer.getInstance().getName(133);
			assertEquals("Hand Clap", instrumentName);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * since there is no instrument zero, we shouldnt get a value
	 */
	@Test
	public void testGetInstrumentZero() {
			instrumentName = InstrumentNamer.getInstance().getName(0);
			assertNull(instrumentName);
	}
	
	/**
	 * @version 1.0.1
	 * 
	 * since there is no instrument 500, we shouldnt get a value
	 */
	@Test
	public void testGetInstrument500() {
			instrumentName = InstrumentNamer.getInstance().getName(500);
			assertNull(instrumentName);
	}
	
	
	
}
