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
	
	public boolean isOn() {
		return on;
	}
	
	public SimoriGui getGui(){
		return gui;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public MatrixModel getModel(){
		return model;
	}
	
	public int getDisplayLayer(){
		return displayLayer;
	}
	
	public void setDisplayLayer(int layno){
		this.displayLayer = layno;
	}
	
	public void setMode(Mode mode) {
		if (mode == null || mode.equals(this.mode)) return;
		this.mode = mode;
		gui.setGridButtonListener(mode);
		gui.setFunctionButtonListener(mode);
	}
	
	public void setOn(boolean on) {
		if (this.on == on) return;
		if (on) {
			switchOn();
		} else {
			switchOff();
		}
	}
	
	private void switchOn() {
		try {
			player = new MIDISoundPlayer();
		} catch (MidiUnavailableException e) {}
		setMode(new PerformanceMode(this, 0, 0, DEFAULT_LAYER));
		clock = new Clock(this, player, 88);
		new Thread(clock).start();
		on = true;
	}
	
	private void switchOff() {
		setMode(new OffMode(this));
		clock.off();
		try {
			player.stop();
		} catch (InvalidMidiDataException e) {}
		clock = null;
		player = null;
		on = false;
	}
}