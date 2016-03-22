
package simori;

import simori.SimoriGui.SplashScreen;
import simori.Exceptions.SimoriFatalException;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SplashJWindow;

/**
 * Class containing the main method necessary
 * to construct and display a Simori-ON.
 * @author Adam 
 * @author James 
 * @author Josh 
 * @author Jurek 
 * @author Matt  
 * @version 3.0.0
 */
public class Simori {
	
	/** The splash screen is displayed for at least this many milliseconds */
	private static final int MIN_SPLASH_TIME = 3000;
	
	/** The Simori-ON communicates with others through this port */
	private static final int PORT = 20160;
	
	/** The Simori-ON's grid measures this many LEDs in each dimension */
	private static final byte GRID_SIZE = 16;
	
	/**
	 * Constructs a Simori, which will display a splash screen,
	 * and then a GUI representation of a Simori-ON, ready to use.
	 * The exception thrown is caught using an UncaughtExceptionHandler
	 * and an error message is displayed in a dialog.
	 * @version 4.5.0
	 */
	public static void main(String[] args) {
		new Simori();
	}
	
	/**
	 * Displays a splash screen, then constructs and connects
	 * the various components of the Simori-ON system.
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @version 4.0.0
	 */
	public Simori() {
		ExceptionManager errors = new ExceptionManager(); //Start catching
		SplashScreen splash = new SplashJWindow(); // Displays immediately
		InstrumentNamer.getInstance(); // Proved costly to initialise later
		MatrixModel model = new MatrixModel(GRID_SIZE, GRID_SIZE);
		QwertyKeyboard keyboard = new QwertyKeyboard(GRID_SIZE, GRID_SIZE);
		SimoriJFrame gui = new SimoriJFrame(keyboard); // Swing implementation
		MIDISoundSystem player = new MIDISoundSystem(true);
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player, model);
		ModeController modes = new ModeController(gui, model, afs, PORT);
		NoteProcessor clock = new NoteProcessor(modes, model, player);
		model.addObserver(clock);
		modes.setComponentsToPowerToggle(model, player, gui, clock);
		modes.setOn(false, false); // Initially off without animation
		splash.swapFor(gui, MIN_SPLASH_TIME, errors, afs); // GUI and errors
		splash = null; // Allow garbage collector to reclaim splash screen
		
		throw new SimoriFatalException("This is a fatal test!");
	}

	/**
	 * Interface for components to be notified of startup and
	 * shutdown events, so that they may perform the required actions.
	 * @author Matt
	 * @version 2.0.0
	 */
	public interface PowerTogglable {
		
		/**
		 * Called as soon as the Simori-ON is switched on,
		 * before the welcome animation and sound play.
		 * Components required for the welcome sequence
		 * should initialise themselves here.
		 */
		public void ready();
		
		/**
		 * Called once the Simori-ON has properly started
		 * up, after the welcome animation and sound.
		 * Components which would otherwise interfere with
		 * the welcome sequence should initialise here.
		 */
		public void switchOn();
		
		/**
		 * Called as soon as the Simori-ON is switched off,
		 * before the goodbye animation and sound play.
		 * Components which would otherwise interfere with
		 * the goodbye sequence should halt themselves here.
		 */
		public void stop();
		
		/**
		 * Called once the Simori-ON has properly shut down,
		 * after the goodbye animation and sound.
		 * Components required for the goodbye sequence may
		 * deactivate themselves here.
		 */
		public void switchOff();
	}
}