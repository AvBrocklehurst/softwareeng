package simori.Tests;
import static org.junit.Assert.*;

import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.NoteProcessor;
import simori.MIDISoundPlayer;
import simori.MatrixModel;
import simori.PerformanceMode;
import simori.Simori;
import simori.Exceptions.InvalidCoordinatesException;
import simori.SwingGui.SimoriGui;

public class TestClock {
	Simori simori;
	PerformanceMode mode;
	MIDISoundPlayer midi;
	NoteProcessor clock;
	Thread thread;
	Throwable e;
	
	@Before
	public void setUp() throws MidiUnavailableException, InvalidCoordinatesException {
		simori = new Simori();
		simori.setModel(new MatrixModel());
		simori.setGui(new SimoriGui(16, 16));
		mode = new PerformanceMode(simori,0,0,(byte)0);
		simori.getGui().setMode(mode);
		simori.getModel().updateButton((byte)0, (byte)1, (byte)5);
		midi = new MIDISoundPlayer();
		e = null;
	}
	
	private void setUpThread() {
		thread = new Thread(clock);
		Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread t, Throwable e) {
		        TestClock.this.e = e;
		    }
		};
		thread.setUncaughtExceptionHandler(h);
	}
	
	@After
	public void tearDown() {
		clock.off();
		simori = null;
		mode = null;
		midi = null;
		clock = null;
		thread = null;
		e = null;
	}
	
	@Test
	public void testRun() throws MidiUnavailableException {
		clock = new NoteProcessor(simori.getModel(), midi, mode, 88);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertNull(e);
	}
	
	@Test
	public void testRunNullModel() throws MidiUnavailableException {
		midi = new MIDISoundPlayer();
		clock = new NoteProcessor(null, midi, mode, 88);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertEquals(e.getClass(), NullPointerException.class);
	}
	
	@Test
	public void testRunNullMIDI() {
		clock = new NoteProcessor(simori.getModel(), null, mode, 88);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertEquals(e.getClass(), NullPointerException.class);
	}
	
	@Test
	public void testRunNullMode() throws MidiUnavailableException {
		clock = new NoteProcessor(simori.getModel(), midi, null, 88);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertEquals(e.getClass(), NullPointerException.class);
	}
	
	@Test
	public void testRunTempoNegative() throws MidiUnavailableException {
		clock = new NoteProcessor(simori.getModel(), midi, mode, -1);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertEquals(e.getClass(), IllegalArgumentException.class);
	}
	
	@Test
	public void testRunTempoZero() throws MidiUnavailableException {
		clock = new NoteProcessor(simori.getModel(), midi, mode, 0);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertEquals(e.getClass(), IllegalArgumentException.class);
	}


}
