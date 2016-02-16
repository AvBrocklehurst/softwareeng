package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class ModeController {
	
	public boolean isOn() {
		return on;
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
