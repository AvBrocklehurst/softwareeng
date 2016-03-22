package simori.Tests;

import org.junit.*;

import simori.ExceptionManager;

public class TestExceptionManager {

	private ExceptionManager exman;
	private Thread thread;
	
	@Before
	public void setUp() {
		exman = new ExceptionManager();
		thread = new Thread();
	}
	
	@After
	public void tearDown() {
		exman = null;
	}
	
	@Test
	public void testUnnchaughtException() {
		
	}
	
}
