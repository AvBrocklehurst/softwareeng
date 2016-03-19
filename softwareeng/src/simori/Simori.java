package simori;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

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
	
	private static final byte GRID_SIZE = 16;
	private static final int PORT = 20160;
	
	/**
	 * The main method to run the whole Simori system. If MIDI is unavailable
	 * an exception is caught.
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @param args
	 * @version 3.0.0
	 */
	public static void main(String[] args) {
		InstrumentNamer.getInstance();
		try {
			new Simori();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (SimoriNonFatalException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs an instance of a Simori. 
	 * 
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @version 2.1.0
	 * @throws MidiUnavailableException If this system does not have MIDI
	 * @throws SimoriNonFatalException If the grid does not fit a QWERTY keyboard
	 * @throws IOException 
	 */
	public Simori() throws MidiUnavailableException, SimoriNonFatalException, IOException {
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
		gui.setVisible(true);
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