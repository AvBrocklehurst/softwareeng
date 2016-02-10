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
import simori.SimoriGui;
import simori.Exceptions.InvalidCoordinatesException;

public class TestClock {
	Simori simori;
	PerformanceMode mode;
	MIDISoundPlayer midi;
	Clock clock;
	Thread thread;
	
	@Before
	public void setUp() throws MidiUnavailableException, InvalidCoordinatesException {
		simori = new Simori();
		simori.setModel(new MatrixModel());
		simori.setGui(new SimoriGui(16, 16));
		mode = new PerformanceMode(simori,0,0,(byte)0);
		simori.getGui().setMode(mode);
		simori.getModel().updateButton((byte)0, (byte)1, (byte)5);
		midi = new MIDISoundPlayer();
	}
	
	@After
	public void tearDown() {
		clock.off();
		simori = null;
		mode = null;
		midi = null;
		clock = null;
		thread = null;
	}
	
	@Test
	public void testRun() throws MidiUnavailableException {
		clock = new Clock(simori.getModel(), midi, mode, 88);
		thread = new Thread(clock);
		thread.start();
		try{Thread.sleep(5000);} catch (InterruptedException e) {}
	}
	
	@Test(expected = NullPointerException.class)
	public void testRunNullModel() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new Clock(null, midi, mode, 88);
		thread = new Thread(clock);
		thread.start();
		try{Thread.sleep(5000);} catch (InterruptedException e) {}
	}
	
	@Test(expected = NullPointerException.class)
	public void testRunNullMIDI() {
		clock = new Clock(simori.getModel(), null, mode, 88);
		thread = new Thread(clock);
		thread.start();
		try{Thread.sleep(5000);} catch (InterruptedException e) {}
	}
	
	@Test(expected = NullPointerException.class)
	public void testRunNullMode() throws MidiUnavailableException {
		clock = new Clock(simori.getModel(), midi, null, 88);
		thread = new Thread(clock);
		thread.start();
		try{Thread.sleep(5000);} catch (InterruptedException e) {}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRunTempoNegative() throws MidiUnavailableException {
		clock = new Clock(simori.getModel(), midi, mode, -1);
		thread = new Thread(clock);
		thread.start();
		try{Thread.sleep(5000);} catch (InterruptedException e) {}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRunTempoZero() throws MidiUnavailableException {
		clock = new Clock(simori.getModel(), midi, mode, 0);
		thread = new Thread(clock);
		thread.start();
		try{Thread.sleep(5000);} catch (InterruptedException e) {}
	}
		
	@Test
	public void whatExactlyAmISuppposedToTest() {
		
	}
	
}
