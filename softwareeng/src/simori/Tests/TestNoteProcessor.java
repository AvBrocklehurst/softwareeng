package simori.Tests;
import static org.junit.Assert.*;

import javax.sound.midi.MidiUnavailableException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.NoteProcessor;
import simori.MIDISoundPlayer;
import simori.MatrixModel;
import simori.ModeController;
import simori.PerformanceMode;
import simori.Simori;
import simori.Exceptions.InvalidCoordinatesException;
import simori.SwingGui.SimoriJFrame;


/**
 * 
 * @author Jurek
 * @author Adam
 */
public class TestNoteProcessor {
	private MatrixModel model;
	private ModeController modes;
	private MIDISoundPlayer midi;
	private NoteProcessor clock;
	private SimoriJFrame gui;
	private Thread thread;
	private Throwable e;
	
	@Before
	public void setUp() throws MidiUnavailableException, InvalidCoordinatesException {
		model = new MatrixModel(16,16);
		gui = new SimoriJFrame(16, 16);
		modes = new ModeController(gui, model);
		modes.setMode(new PerformanceMode(modes));
		model.updateButton((byte)0, (byte)1, (byte)5);
		midi = new MIDISoundPlayer();
		clock = new NoteProcessor(modes, model, midi);
		e = null;
	}
	
	@After
	public void tearDown() {
		clock.switchOff();
		model = null;
		gui = null;
		modes = null;
		midi = null;
		clock = null;
		thread = null;
		e = null;
	}
	
	private void setUpThread() {
		thread = new Thread(clock);  
		Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread t, Throwable e) {
		        TestNoteProcessor.this.e = e;
		    }
		};
		thread.setUncaughtExceptionHandler(h);
	}
	
	@Test
	public void testRun() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}
	/*
	@Test (expected=NullPointerException.class)
	public void testRunNullModel() throws MidiUnavailableException {
		clock = new NoteProcessor(modes, null, midi);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
		assertEquals(e.getClass(), NullPointerException.class);
	}
	*/
	@Test 
	public void testRunNullMIDI() {
		clock = new NoteProcessor(modes, model, null);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
		assertEquals(e.getClass(), NullPointerException.class);
	}
	
	@Test (expected=NullPointerException.class)
	public void testRunNullMode() throws MidiUnavailableException {
		new NoteProcessor(null, model, midi);
	}

	@Test
	public void testRunZeroBpm() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setBPM((short) 0);
		modes.notifyClock();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}

	@Test
	public void testRunLesserBpm() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setBPM((short) 1);
		modes.notifyClock();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}

	@Test
	public void testRunOn() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		clock.switchOff();
		clock.switchOn();
		
		assertNull(e);
	}

	@Test
	public void testRunPercussion() throws MidiUnavailableException, InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setInstrument((byte)0, (short)200);
		model.setBPM((short) 160);
		modes.notifyClock();
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}

	@Test
	public void testRunWrongInstrument() throws MidiUnavailableException, InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setInstrument((byte)0, (short)200);
		model.setBPM((short) 160);
		modes.notifyClock();
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}
	
	
	
	
	/*
	@Test 
	public void testRunTempoNegative() throws MidiUnavailableException {
		model.setBPM((byte)-1);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		assertEquals(e.getClass(), IllegalArgumentException.class);
	}
	
	*/
}
