package simori.Tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.MidiUnavailableException;

import simori.NoteProcessor;
import simori.SimoriSoundSystem;
import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

import java.io.IOException;
import java.security.Permission;

/**
 * 
 * @author Jurek
 */
public class TestNoteProcessor {
	private MatrixModel model;
	private QwertyKeyboard keyboard;
	private SimoriJFrame gui;
	private SimoriSoundSystem midi;
	private ModeController modes;
	private NoteProcessor clock;
	private Thread thread;
	private Throwable e;
	private MIDISoundSystem midisystem;
	private AudioFeedbackSystem audio;
	
	/**
	 * 
	 * @author Jurek
	 *
	 */
    protected static class ExitException extends SecurityException {
        public final int status;
        public ExitException(int status) {
            super("Attempted to System.exit...");
            this.status = status;
        }
    }

    /**
     * 
     * @author Jurek
     *
     */
    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {}
        @Override
        public void checkPermission(Permission perm, Object context) {}
        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
	
	/**
	 * 
	 * @author Jurek
	 * @throws MidiUnavailableException
	 * @throws InvalidCoordinatesException
	 * @throws SimoriNonFatalException
	 * @throws IOException 
	 */
	@Before
	public void setUp() throws MidiUnavailableException, SimoriNonFatalException, IOException {
		model = new MatrixModel(16, 16);
		keyboard = new QwertyKeyboard((byte)16, (byte)16);
		gui = new SimoriJFrame(keyboard);
		midisystem = new MIDISoundSystem();
		midi = new SimoriSoundSystem(midisystem);
		audio = new AudioFeedbackSystem(midisystem, model);
		modes = new ModeController(gui, model, audio, 20160);
		clock = new NoteProcessor(modes, model, midisystem);
		model.addObserver(clock);
		modes.setComponentsToPowerToggle(model, midisystem, gui, clock);
		modes.setOn(false, false);
		e = null;
        System.setSecurityManager(new NoExitSecurityManager());
		modes.setOn(true, false);
	}
	
	/**
	 * @author Jurek
	 * @throws SimoriNonFatalException 
	 */
	@After
	public void tearDown() throws SimoriNonFatalException {
		modes.setOn(false, false);
		model = null;
		keyboard = null;
		gui = null;
		midi = null;
		modes = null;
		clock.switchOff();
		clock = null;
		e = null;
        System.setSecurityManager(null);
	}
	
	/**
	 * @author Jurek
	 * @throws SimoriNonFatalException 
	 */
	private void setUpThread(ModeController modes, MatrixModel model, MIDISoundSystem midisystem) {
		clock.switchOff();
		model.deleteObserver(clock);
		clock = new NoteProcessor(modes, model, midisystem);
		model.addObserver(clock);
		model.setBPM((short) 88);
		
		thread = new Thread(clock);  
		Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread t, Throwable e) {
		        TestNoteProcessor.this.e = e;
		    }
		};
		thread.setUncaughtExceptionHandler(h);
		thread.start();
	}
	
	/**
	 * @author Jurek
	 * @param timestamp
	 * @param period
	 */
	private void letRun(long period) {
		long timestamp = System.currentTimeMillis();
		while(timestamp+period>System.currentTimeMillis()) {
			try{	Thread.sleep(period);	}catch(InterruptedException e){}
		}
	}
	
	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRun() throws MidiUnavailableException {
		
		setUpThread(modes, model, midisystem);
		
		letRun(2000);
		
		assertNull(e);
	}
	
	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test (expected=NullPointerException.class)
	public void testRunNullModel() throws MidiUnavailableException {
		setUpThread(modes, null, midisystem);
		letRun(2000);
	}
	
	
	/**
	 * @author Jurek
	 */
	@Test (expected=NullPointerException.class)
	public void testRunNullMIDI() {
		setUpThread(modes, model, null);
		
		letRun(2000);
		
		e.getClass();
	}
	
	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRunNullMode() throws MidiUnavailableException {
		
		setUpThread(null, model, midisystem);
		
		letRun(2000);

		assertEquals(e.getClass(), NullPointerException.class);
	}

	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRunZeroBpm() throws MidiUnavailableException {
		
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		model.setBPM((short) 0);
		letRun(1000);
		
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
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		model.setBPM((short) 1);
		byte column = model.getCurrentColumn();
		letRun(1000);
		model.setBPM((short) 80);
		letRun(1000);
		
		assertThat(column, not(model.getCurrentColumn()));
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 */
	@Test
	public void testRunRestart() throws MidiUnavailableException {
		
		setUpThread(modes, model, midisystem);
		
		letRun(2000);
		clock.switchOff();
		clock.switchOn();
		letRun(2000);
		
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws MidiUnavailableException
	 * @throws SimoriNonFatalException 
	 */
	@Test
	public void testRunPercussion() throws MidiUnavailableException, SimoriNonFatalException {
		
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		model.setInstrument((byte)0, (short)175);
		model.setBPM((short) 160);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		letRun(1000);
		
		assertNull(e);
	}
	
	
	/**
	 * @author Jurek
	 * @throws SimoriNonFatalException
	 */
	@Test
	public void testRunBoundaryInstrument() throws SimoriNonFatalException {
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		
		//lower-bound
		model.setInstrument((byte)0, (short)0);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		letRun(1000);
		
		//upper-bound
		model.setInstrument((byte)0, (short)127);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws SimoriNonFatalException 
	 */
	@Test
	public void testRunBoundaryVelocity() throws SimoriNonFatalException {
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		
		//lower-bound
		model.setVelocity((byte)0, (byte)0);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		letRun(1000);
		
		//upper-bound
		model.setVelocity((byte)0, (byte)127);
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)4);
		}
		letRun(1000);
		
		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws SimoriNonFatalException
	 */
	@Test
	public void testRunBoundaryPitch() throws SimoriNonFatalException {
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		
		for(int i=0; i<16; i++) {
			model.updateButton((byte)0, (byte)i, (byte)0);
			model.updateButton((byte)0, (byte)i, (byte)15);
		}
		letRun(1000);

		assertNull(e);
	}

	/**
	 * @author Jurek
	 * @throws SimoriNonFatalException 
	 */
	@Test
	public void testRunExtreme() throws SimoriNonFatalException {
		setUpThread(modes, model, midisystem);
		
		letRun(1000);
		
		//populate the entire simori
		for(int laynum=0; laynum<16; laynum++) {
			for(int col=0; col<16; col++){
				for(int row=0; row<16; row++){
					model.updateButton((byte)laynum, (byte)col, (byte)row);
				}
			}
		}
		letRun(1000);
		//change velocity, instrument
		

		assertNull(e);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testRunTempoNegative() throws MidiUnavailableException {
		setUpThread(modes, model, midisystem);
		letRun(1000);
		model.setBPM((byte)-1);
	}
	
}
