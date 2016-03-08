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
import simori.Simori;
import simori.Exceptions.InvalidCoordinatesException;
import simori.Exceptions.KeyboardException;
import simori.Modes.PerformanceMode;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

import java.lang.System;

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
	
	/**
	 * 
	 * @author Jurek
	 * @throws MidiUnavailableException
	 * @throws InvalidCoordinatesException
	 * @throws KeyboardException
	 */
	@Before
	public void setUp() throws MidiUnavailableException, InvalidCoordinatesException, KeyboardException {
		model = new MatrixModel(16,16);
		gui = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		modes = new ModeController(gui, model, 0);
		modes.setMode(new PerformanceMode(modes));
		model.updateButton((byte)0, (byte)1, (byte)5);
		midi = new MIDISoundPlayer();
		clock = new NoteProcessor(modes, model, midi);
		e = null;
	}
	
	/**
	 * @author Jurek
	 */
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
	
	/**
	 * @author Jurek
	 */
	private void setUpThread() {
		thread = new Thread(clock);  
		Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread t, Throwable e) {
		        TestNoteProcessor.this.e = e;
		    }
		};
		thread.setUncaughtExceptionHandler(h);
	}
	
	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
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
	
	/**
	 * @author Jurek
	 */
	@Test 
	public void testRunNullMIDI() {
		clock = new NoteProcessor(modes, model, null);
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		
		assertEquals(e.getClass(), NullPointerException.class);
	}
	
	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test (expected=NullPointerException.class)
	public void testRunNullMode() throws MidiUnavailableException {
		new NoteProcessor(null, model, midi);
	}

	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRunZeroBpm() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setBPM((short) 0);
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}

	/**
	 * This test tests that a BPM change is immediate, as opposed to
	 * waiting for the tick make over
	 * It does this by confirming that the columns have changed after a second,
	 * as opposed to after the 60 seconds it would take under the BPM of 1
	 * @author Jurek
	 * @version 1.0.0
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRunLowerBpm() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setBPM((short) 1);
		byte column = model.getCurrentColumn();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setBPM((short) 120);
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNotEquals(column, model.getCurrentColumn());
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRunOn() throws MidiUnavailableException {
		setUpThread();
		thread.start();
		try{Thread.sleep(2000);} catch (InterruptedException e) {}
		clock.switchOff();
		clock.switchOn();
		
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 * @throws InvalidCoordinatesException
	 */
	@Test
	public void testRunPercussion() throws MidiUnavailableException, InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setInstrument((byte)0, (short)175);
		model.setBPM((short) 160);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}
	
	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 * @throws InvalidCoordinatesException
	 */
	@Test
	public void testRunWrongInstrument() throws MidiUnavailableException, InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		model.setInstrument((byte)0, (short)200);
		model.setBPM((short) 160);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}
	
	/**
	 * @author Jurek
	 * @throws InvalidCoordinatesException
	 */
	@Test
	public void testRunBoundaryInstrument() throws InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		//lower-bound
		model.setInstrument((byte)0, (short)0);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		//upper-bound
		model.setInstrument((byte)0, (short)127);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws InvalidCoordinatesException
	 */
	@Test
	public void testRunBoundaryVelocity() throws InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		//lower-bound
		model.setVelocity((byte)0, (byte)0);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		//upper-bound
		model.setVelocity((byte)0, (byte)127);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws InvalidCoordinatesException
	 */
	@Test
	public void testRunBoundaryPitch() throws InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)0);
			model.updateButton((byte)0, (byte)i, (byte)15);
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}

		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws InvalidCoordinatesException
	 */
	@Test
	public void testRunExtreme() throws InvalidCoordinatesException {
		setUpThread();
		thread.start();
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		
		//populate the entire simori
		for(int laynum=0; laynum<16; laynum++) {
			for(int col=0; col<16; col++){
				for(int row=0; row<16; row++){
					model.updateButton((byte)laynum, (byte)col, (byte)row);
				}
			}
		}
		try{Thread.sleep(1000);} catch (InterruptedException e) {}
		//change velocity, instrument
		

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
