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
	// get instance once
	// get instance twice
	// get instrument once
	// get instrument twice
	// check I get right instrument
	// check I get right percussion
	// check zero doesn't work
	
	InstrumentNamer instrumentNamer;
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		instrumentNamer = null;
	}

	
	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}
}
