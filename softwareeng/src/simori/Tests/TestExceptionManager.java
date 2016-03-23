package simori.Tests;

import static org.junit.Assert.*;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import simori.AudioFeedbackSystem;
import simori.ExceptionManager;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.SimoriGui;
import simori.SimoriGui.SplashScreen;
import simori.Exceptions.SimoriFatalException;
import simori.Exceptions.SimoriNonFatalException;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SplashJWindow;
import simori.Tests.GuiTests.MockSimoriJFrame;
import simori.Tests.TestNoteProcessor.ExitException;

public class TestExceptionManager {

	class MockExceptionManager extends ExceptionManager {
		public MockExceptionManager() {
			super();
		}
		
		public List<Throwable> getQueue() {
			return queue;
		}
		
		public boolean getDialogOpen() {
			return dialogOpen;
		}
		
		public SimoriGui getGui() {
			return gui;
		}
	}

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
    
	private MockExceptionManager exman;
	private Thread thread;
	private MockSimoriJFrame gui;
	private MIDISoundSystem midi;
	private AudioFeedbackSystem audio;
	private MatrixModel model;
	
	@Before
	public void setUp() {
		exman = new MockExceptionManager();
		midi = new MIDISoundSystem(false);
		model = new MatrixModel(16, 16);
		audio = new AudioFeedbackSystem(midi, model);
		gui = new MockSimoriJFrame();
		thread = new Thread();
        System.setSecurityManager(new NoExitSecurityManager());
	}
	
	@After
	public void tearDown() {
		exman = null;
		midi = null;
		model = null;
		audio = null;
		gui = null;
		thread = null;
        System.setSecurityManager(null);
	}

	
	@Test
	public void testUnnchaughtExceptionNonFatal() {
		exman.uncaughtException(thread, new SimoriNonFatalException());
		assertEquals(exman.getQueue().size(), 1);
		exman.simoriReady(gui, audio);
		assertTrue(exman.getDialogOpen());
		
	}
	
	@Test
	public void testUnnchaughtExceptionFatal() {
		try{
			exman.uncaughtException(thread, new SimoriFatalException());
			assertEquals(exman.getQueue().size(), 1);
			exman.simoriReady(gui, audio);
			assertTrue(exman.getDialogOpen());
		} catch (ExitException e) {}
	}
	
}
