package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class ModeController {
	
	private SimoriGui gui;
	private MatrixModel model;
	
	private Mode mode;
	private byte displayLayer;
	private boolean on;
	
	public ModeController(SimoriGui gui, MatrixModel model) {
		this.gui = gui;
		this.model = model;
	}
	
	public MatrixModel getModel() {
		return model;
	}
	
	public SimoriGui getGui() {
		return gui;
	}
	
	public boolean isOn() {
		return on;
	}
	
	public byte getDisplayLayer(){
		return displayLayer;
	}
	
	public void setDisplayLayer(byte layerNum){
		this.displayLayer = layerNum;
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
