package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.SwingGui.SimoriJFrame;

public class TestSimoriJFrame {

	private SimoriJFrame gui;

	@Before
	public void setUp() {
		SimoriJFrame gui = new SimoriJFrame(16,16);
	}

	@After
	public void tearDown() {
		gui = null;
	}

	@Test
	public void testSetUpWindow(){
		
	}

}
