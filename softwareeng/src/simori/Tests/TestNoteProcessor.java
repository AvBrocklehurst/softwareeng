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
		clock = new NoteProcessor(modes, model, midi);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}
	
	@Test (expected=NullPointerException.class)
	public void testRunNullModel() throws MidiUnavailableException {
		clock = new NoteProcessor(modes, null, midi);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
		assertEquals(e.getClass(), NullPointerException.class);
	}
	
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

//	@Test 
//	public void testRunWrongMIDI() {
//		clock = new NoteProcessor(modes, model, midi);
//		setUpThread();
//		thread.start();
//		try{Thread.sleep(2000);} catch (InterruptedException e) {}
//		
//		assertNull(e);
//	}
	
	@Test
	public void testRunDimensions() throws MidiUnavailableException {
		model = new MatrixModel(2,2);
		modes = new ModeController(new SimoriJFrame(2, 2), model);
		clock = new NoteProcessor(modes, model, midi);
		modes.setMode(new PerformanceMode(modes));
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
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
