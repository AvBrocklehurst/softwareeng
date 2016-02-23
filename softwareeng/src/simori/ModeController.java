package simori;

import simori.Simori.PowerTogglable;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * Manages the current {@link Mode} of the Simori-ON.
 * Modes interpret and respond to input from the GUI.
 * Since they are created and destroyed as necessary,
 * this class holds reference to the mode currently
 * in memory. It provides methods for switching modes,
 * including powering on and off the Simori-ON.
 * It holds reference to the GUI and Model, so that
 * the currently active Mode may access these through
 * getters in the ModeController it is constructed with.
 * 
 * @author Matt
 * @version 1.6.0
 */
public class ModeController {
	
	private SimoriGui gui;
	private MatrixModel model;
	private PowerTogglable[] toPowerToggle;
	
	private Mode mode;
	private byte displayLayer;
	private boolean on = true;
	
	/**
	 * Creates a ModeController to accept input from the
	 * given GUI and make changes to the given model.
	 * An instance of ModeController is needed to instantiate
	 * {@link NoteProcessor}, with which it also communicates.
	 * 
	 * @param gui For button input and LED output
	 * @param model To store information on the Simori-ON's state
	 */
	public ModeController(SimoriGui gui, MatrixModel model) {
		this.gui = gui;
		this.model = model;
		setOn(false); //Starting state is off
	}
	
	/**
	 * Draws the clock hand in the specified column. If the current
	 * mode is not {@link PerformanceMode}, this has no effect.
	 * @param column x coordinate at which to draw clock hand
	 */
	public void tickThrough(byte column) {
		try {
			mode.tickerLight(column);
		} catch (InvalidCoordinatesException e) {}
	}
	
	/** @return Model instance encapsulating state of Simori-ON */
	public MatrixModel getModel() {
		return model;
	}
	
	/** @return The on-screen representation of the Simori-ON */
	public SimoriGui getGui() {
		return gui;
	}
	
	/** @return false if the Simori-ON is in {@link OffMode} */
	public boolean isOn() {
		return on;
	}
	
	/**
	 * The Simori-ON has a number of conceptual 'layers'
	 * to its grid, of which one is displayed at a time.
	 * @return The most recently displayed layer
	 */
	public byte getDisplayLayer(){
		return displayLayer;
	}
	
	/**
	 * Sets the value used by {@link PerformanceMode} to
	 * select which layer from the {@link MatrixModel} to
	 * display and update.
	 * @param layerNum The layer to display
	 */
	public void setDisplayLayer(byte layerNum){
		this.displayLayer = layerNum;
	}
	
	/**
	 * Changes the {@link Mode} in effect.
	 * The existing mode will subsequently be garbage collected.
	 * The GUI is automatically set to pass events to the new mode.
	 * @param mode A mode to switch to
	 */
	public void setMode(Mode mode) {
		if (mode == null || mode.equals(this.mode)) return;
		this.mode = mode;
		gui.setGridButtonListener(mode);
		gui.setFunctionButtonListener(mode);
		mode.setInitialGrid(); //Request LED grid update
	}
	
	/**
	 * Sets the list of {@link PowerTogglable} components to be notified
	 * when the Simori-ON's power state switches between on and off.
	 * Components will be notified in the order they are passed.
	 */
	public void setComponentsToPowerToggle(PowerTogglable... s) {
		toPowerToggle = s;
	}
	
	/**
	 * Sets the power state of the Simori-ON.
	 * @param on true to switch on, or false to switch off
	 */
	public void setOn(boolean on) {
		if (this.on == on) return;
		if (on) {
			switchOn();
		} else {
			switchOff();
		}
	}
	
	/**
	 * Switches the Simori-ON on by calling
	 * {@link PowerTogglable#switchOn} on relevant
	 * components, and entering {@link #PerformanceMode}.
	 */
	private void switchOn() {
		for (PowerTogglable t : toPowerToggle) {
			t.switchOn();
		}
		on = true;	
		setMode(new PerformanceMode(this));
	}
	
	/**
	 * Switches the Simori-ON off by calling
	 * {@link PowerTogglable#switchOff} on relevant
	 * components, and entering {@link #OffMode}.
	 */
	private void switchOff() {
		on = false;
		setMode(new OffMode(this));
		if (toPowerToggle == null) return;
		for (PowerTogglable t : toPowerToggle) {
			t.switchOff();
		}
	}
	
	/*
	 * Kerry lands
	 * Abandon all hope ye who enter here
	 */
	private Object bpmLock;
	
	/**
	 * Sets the bpmLock used to notify the clock about the bpm changes
	 * @author Jurek
	 * @version 1.0.0
	 * @param bpmLock
	 */
	public void setBpmLock(Object bpmLock) {
		this.bpmLock = bpmLock;
	}
	
	/**
	 * Notifies clock about the bpm change
	 * @author Jurek
	 * @version 1.0.0
	 */
	public void notifyClock() {
		synchronized(bpmLock){
			bpmLock.notify();
		}
	}
}
