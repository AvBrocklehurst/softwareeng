package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import simori.Simori.PowerTogglable;

public class ModeController {
	
	private SimoriGui gui;
	private MatrixModel model;
	private PowerTogglable[] toPowerToggle;
	
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
	
	public void setComponentsToPowerToggle(PowerTogglable... s) {
		toPowerToggle = s;
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
		for (PowerTogglable t : toPowerToggle) {
			t.switchOn();
		}
		on = true;
		setMode(new PerformanceMode(this));
	}
	
	private void switchOff() {
		for (PowerTogglable t : toPowerToggle) {
			t.switchOff();
		}
		on = false;
		setMode(new OffMode(this));
	}
}
