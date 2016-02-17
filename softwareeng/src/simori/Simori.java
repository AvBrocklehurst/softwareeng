package simori;

import javax.sound.midi.MidiUnavailableException;

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
	
	public static void main(String[] args) {
		try {
			new Simori();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public Simori() throws MidiUnavailableException {
		MatrixModel model = new MatrixModel(GRID_WIDTH, GRID_HEIGHT);
		SimoriGui gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		MIDISoundPlayer player = new MIDISoundPlayer();
		ModeController modes = new ModeController(gui, model);
		Clock clock = new Clock(modes, model, player);
		modes.setComponentsToPowerToggle(model, clock, player);
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