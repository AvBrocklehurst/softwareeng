package simori.Tests;
import static org.junit.Assert.*;

import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Clock;
import simori.MIDISoundPlayer;
import simori.MatrixModel;
import simori.PerformanceMode;
import simori.Simori;

public class TestClock {
	Simori simori;
	MatrixModel model;
	MIDISoundPlayer midi;
	PerformanceMode mode;
	Thread thread;
	Clock clock;
	
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
		clock = null;
		thread = null;
	}
	
	@Test
	public void testRun() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new Clock(model, midi, mode, 88);
		thread = new Thread(clock);
		thread.start();
	}
	
	@Test(expected = NullPointerException.class)
	public void testRunNullModel() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new Clock(null, midi, mode, 88);
		thread = new Thread(clock);
		thread.start();
	}
	
	@Test(expected = NullPointerException.class)
	public void testRunNullMIDI() {
		clock = new Clock(model, null, mode, 88);
		thread = new Thread(clock);
		thread.start();
	}
	
	@Test(expected = NullPointerException.class)
	public void testRunNullMode() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new Clock(model, midi, null, 88);
		thread = new Thread(clock);
		thread.start();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRunTempoNegative() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new Clock(model, midi, mode, -1);
		thread = new Thread(clock);
		thread.start();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRunTempoZero() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new Clock(model, midi, mode, 0);
		thread = new Thread(clock);
		thread.start();
	}
	@Test public void whatExactlyAmISuppposedToTest() {}
}
