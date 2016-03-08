package simori;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import simori.Exceptions.KeyboardException;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
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
	
	private static final byte GRID_WIDTH = 16, GRID_HEIGHT = 16;
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
		} catch (KeyboardException e) {
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
	 * @throws KeyboardException If the grid does not fit a QWERTY keyboard
	 * @throws IOException 
	 */
	public Simori() throws MidiUnavailableException, KeyboardException, IOException {
		MatrixModel model = new MatrixModel(GRID_WIDTH, GRID_HEIGHT);
		QwertyKeyboard keyboard = new QwertyKeyboard(GRID_WIDTH, GRID_HEIGHT);
		SimoriJFrame gui = new SimoriJFrame(keyboard);
		MIDISoundPlayer player = new MIDISoundPlayer();
		NetworkSlave slave = new NetworkSlave(PORT, model);
		NetworkMaster master = new NetworkMaster(PORT, model, slave);
		ModeController modes = new ModeController(gui, model, PORT, master);
		NoteProcessor clock = new NoteProcessor(modes, model, player);
		model.addObserver(clock);
		modes.setComponentsToPowerToggle(model, player, slave, gui, clock);
		modes.setOn(false);
		gui.setVisible(true);
	}
	
	/**
	 * @author Josh
	 * @author Matt  
	 * @version 1.0.0
	 * interface that tells classes that have statuses (that are not needed) to close and open
	 */
	public interface PowerTogglable {
		
		/**
		 * @author Josh
		 * @author Matt
		 * @version 1.0.0
		 * method continues execution.
		 */
		public void switchOn();
		
		/**
		 * @author Josh
		 * @author Matt
		 * @version 1.0.0
		 * method pauses and resets all functionality
		 * It should not destroy any instances however
		 */
		public void switchOff();
	}
	
}