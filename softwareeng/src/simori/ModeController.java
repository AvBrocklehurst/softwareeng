package simori;

import simori.Simori.PowerTogglable;
import simori.SwingGui.SimoriGui;
import simori.Exceptions.InvalidCoordinatesException;

public class ModeController {
	
	private SimoriGui gui;
	private MatrixModel model;
	private PowerTogglable[] toPowerToggle;
	
	private Mode mode;
	private byte displayLayer;
	private boolean on = true;
	
	public ModeController(SimoriGui gui, MatrixModel model) {
		this.gui = gui;
		this.model = model;
		setOn(false);
	}
	
	public void tickThrough(byte column) {
		try {
			mode.tickerLight(column);
		} catch (InvalidCoordinatesException e) {}
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
		on = false;
		setMode(new OffMode(this));
		if (toPowerToggle == null) return;
		for (PowerTogglable t : toPowerToggle) {
			t.switchOff();
		}
	}
}
