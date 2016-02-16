package simori;

import javax.sound.midi.InvalidMidiDataException;
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
	private static final byte DEFAULT_LAYER = 0;
	
	private SimoriGui gui;
	private Mode mode;
	private MatrixModel model;
	private Clock clock;
	private MIDISoundPlayer player;
	
	private int displayLayer;
	private boolean on;
	
	public static void main(String[] args) {
		Simori s = new Simori();
		s.setMode(new OffMode(s));
	}
	
	public Simori() {
		model = new MatrixModel(GRID_WIDTH, GRID_HEIGHT);
		gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
	}
	
	
	
	
}