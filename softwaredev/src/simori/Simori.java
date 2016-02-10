package simori;

import javax.sound.midi.MidiUnavailableException;

/**
 * The main Simori class, runs the the whole Simori system.
 * @author Adam
 * @author James
 * @author Josh
 * @author Jurek
 * @author Matt 
 * @version 1.0.1
 */
public class Simori {
	
	/* 
	 * FIXME These are only used by SimoriGui. Should be used in MatrixModel too.
	 * 		 Grid is accidentally trasposed in several places so may crash if
	 * 		 GRID_WIDTH != GRID_HEIGHT. Maybe use just one GRID_DIMENSION constant.
	 */
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;
	
	private SimoriGui gui;
	private MatrixModel model;
	
	/**
	 * Instantiates all the required classes to render and use a Simori.
	 * @author Adam
	 * @author James
	 * @author Josh
	 * @author Jurek
	 * @author Matt
	 * @param args unused
	 * @throws MidiUnavailableException
	 * @version 1.0.0
	 */
	public static void main(String[] args) throws MidiUnavailableException {
		Simori simori = new Simori();
		simori.model = new MatrixModel();
		simori.gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		PerformanceMode mode = new PerformanceMode(simori,0,0,(byte)0);
		simori.gui.setMode(mode); //TODO Off mode by default
		MIDISoundPlayer midi = new MIDISoundPlayer();
		Clock clock = new Clock(simori.model, midi, mode, 88);
		Thread thread = new Thread(clock);
		thread.start();
	}
	
	/**
	 * @author James
	 * @return the GUI in use
	 * @version 1.0.0
	 */
	public SimoriGui getGui(){
		return gui;
	}
	
	/**
	 * @author James
	 * @return the model in use
	 * @version 1.0.0
	 */
	public MatrixModel getModel(){
		return model;
	}
	 
	/**
	 * Allows setting of the model for testing classes,
	 * as general instantiation is done in the main method
	 * on run.
	 * @author James
	 * @param model
	 * @version 1.0.0
	 * @see TestPerformanceMode
	 */
	public void setModel(MatrixModel model){
		this.model = model; 
	}
	
	/**
	 * Allows setting of the model for testing classes,
	 * as general instantiation is done in the main method
	 * on run.
	 * @author James
	 * @param gui
	 * @version 1.0.0
	 * @see TestPerformanceMode
	 */
	public void setGui(SimoriGui gui){
		this.gui = new SimoriGui(16, 16);
	}
}
