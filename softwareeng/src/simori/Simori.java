package simori;

import java.io.IOException;

import simori.SimoriGui.SplashScreen;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SplashJWindow;

/**
 * The main Simori class, runs the the whole Simori system.
 
 * @author Adam 
 * @author James 
 * @author Josh 
 * @author Jurek 
 * @author Matt  
 * 
 * @version 2.1.0
 */
public class Simori {
	
	private static final int MIN_SPLASH_TIME = 3000;
	private static final int PORT = 20160;
	private static final byte GRID_SIZE = 16;
	
	/**
	 * The main method to run the whole Simori system.
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @version 4.5.0
	 * @throws SimoriNonFatalException which is displayed in an error dialog
	 */
	public static void main(String[] args) throws SimoriNonFatalException {
		new Simori();
	}
	
	/**
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @version 2.1.0
	 * @throws IOException 
	 */
	public Simori() throws SimoriNonFatalException {
		SplashScreen splash = new SplashJWindow();
		InstrumentNamer.getInstance();
		splash.swapFor(assembleSimori(), MIN_SPLASH_TIME);
		splash = null;
	}
	
	private SimoriGui assembleSimori() throws SimoriNonFatalException {
		MatrixModel model = new MatrixModel(GRID_SIZE, GRID_SIZE);
		QwertyKeyboard keyboard = new QwertyKeyboard(GRID_SIZE, GRID_SIZE);
		SimoriJFrame gui = new SimoriJFrame(keyboard);
		MIDISoundSystem player = new MIDISoundSystem();
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player, model);
		ModeController modes = new ModeController(gui, model, afs, PORT);
		NoteProcessor clock = new NoteProcessor(modes, model, player);
		model.addObserver(clock);
		modes.setComponentsToPowerToggle(model, player, gui, clock);
		modes.setOn(false, false);
		return gui;
	}
	
	/**
	 * TODO rejavadoc
	 * @author Matt
	 * @version 2.0.0
	 */
	public interface PowerTogglable {
		
		public void ready();
		
		public void switchOn();
		
		public void stop();
		
		public void switchOff();
	}
}