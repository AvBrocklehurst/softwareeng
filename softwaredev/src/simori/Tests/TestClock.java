package simori.Tests;
import static org.junit.Assert.*;

import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MIDISoundPlayer;
import simori.MatrixModel;
import simori.PerformanceMode;
import simori.Simori;

public class TestClock {
	Simori simori;
	MatrixModel model;
	MIDISoundPlayer midi;
	PerformanceMode mode;
	
	@Before
	public void setUp() {
		simori = new Simori();
		model = new MatrixModel();
		mode = new PerformanceMode(simori, 0, 0, (byte)0);
	}
	
	@After
	public void tearDown() {
		simori = null;
		model = null;
		midi = null;
		mode = null;
	}
	
	@Test
	public void whatExactlyAmISuppposedToTest() {
		
	}
	
}
