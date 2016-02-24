package simori;

import javax.sound.midi.MidiUnavailableException;

import simori.SwingGui.SimoriJFrame;

/**
 * The main Simori class, runs the the whole Simori system.
 * @author Adam
 * @author James
 * @author Josh
 * @author Jurek
 * @author Matt 
 * @version 2.1.0
 */
public class Simori {
	
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;
	
	/**
	 * The main method to run the whole Simori system. If MIDI is unavailable
	 * an exception is caught.
	 * 
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @param args
	 * @version 3.0.0
	 */
	public static void main(String[] args) {
		try {
			new Simori();
		} catch (MidiUnavailableException e) {
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
	 * @version 2.0.0
	 * @throws MidiUnavailableException
	 */
	public Simori() throws MidiUnavailableException {
		MatrixModel model = new MatrixModel(GRID_WIDTH, GRID_HEIGHT);
		SimoriJFrame gui = new SimoriJFrame(GRID_WIDTH, GRID_HEIGHT);
		MIDISoundPlayer player = new MIDISoundPlayer();
		ModeController modes = new ModeController(gui, model);
		NoteProcessor clock = new NoteProcessor(modes, model, player);
		modes.setComponentsToPowerToggle(model, player, clock);
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