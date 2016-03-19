package simori;

import java.io.IOException;

import simori.Animation.OnFinishListener;
import simori.Simori.PowerTogglable;
import simori.SimoriGui.FunctionButtonEvent;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.Mode;
import simori.Modes.NetworkMaster;
import simori.Modes.NetworkSlave;
import simori.Modes.PerformanceMode;
import simori.Modes.ShopBoyMode;

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
 * @author Adam
 * @version 1.7.0
 */
public class ModeController {
	
	private static final short DEFAULT_BPM = 88;
	
	private SimoriGui gui;
	private MatrixModel model;
	private NetworkMaster master;
	private NetworkSlave slave;
	private PowerTogglable[] toPowerToggle;
	private int port;
	protected Mode mode;
	private byte displayLayer;
	protected boolean on = true;
	
	/**
	 * Creates a ModeController to accept input from the
	 * given GUI and make changes to the given model.
	 * An instance of ModeController is needed to instantiate
	 * {@link NoteProcessor}, with which it also communicates.
	 * 
	 * @param gui For button input and LED output
	 * @param model To store information on the Simori-ON's state
	 */
	public ModeController(SimoriGui gui, MatrixModel model, int port, MIDISoundSystem player) {
		this.gui = gui;
		this.model = model;
		this.port = port;
	}
	
	/**
	 * Draws the clock hand in the specified column. If the current
	 * mode is not {@link PerformanceMode}, this has no effect.
	 * @param column x coordinate at which to draw clock hand
	 * @throws SimoriNonFatalException 
	 */
	public void tickThrough(byte column) throws SimoriNonFatalException {
			mode.tickerLight(column);
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
	 * Method to return the port to use.
	 * @author Adam
	 * @return  int containing the current port.
	 */
	public int getPort(){
		return port;
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
	
	/** Searches searches for another Simori-ON to copy the configuration to */
	public NetworkMaster startNetworkMaster() {
		if (master == null) return null;
		master.scan();
		return master;
	}
	
	/**
	 * Displays the name of the given instrument in the LCD screen.
	 * If the current mode is not PerformanceMode, this has no effect.
	 * @param num The number of the instrument to show the name of
	 */
	public void showInstrumentName(int num) {
		if (mode instanceof PerformanceMode) {
			String name = InstrumentNamer.getInstance().getName(num);
			getGui().setText(name);
		}
	}
	
	/**
	 * Sets the power state of the Simori-ON.
	 * @param on true to switch on, or false to switch off
	 */
	public void setOn(boolean on, boolean animated) {
		if (this.on == on) return;
		if (on) {
			bootUp(animated);
		} else {
			shutDown(animated);
		}
	}
	
	private void bootUp(boolean animated) {
		for (PowerTogglable p : toPowerToggle) p.ready(); //TODO in a different thread?
		if (!animated){
			switchOn();
			return;
		}
		OnFinishListener switchOn = new OnFinishListener() {
			@Override
			public void onAnimationFinished() {
				switchOn();
			}
		};
		gui.play(new Animation(gui.getGridWidth(), switchOn, true));
	}
	
	private void shutDown(boolean animated) {
		for (int i = toPowerToggle.length - 1; i >= 0; i--) {
			toPowerToggle[i].stop();
		}
		if (!animated) {
			switchOff();
			return;
		}
		OnFinishListener switchOff = new OnFinishListener() {
			@Override
			public void onAnimationFinished() {
				switchOff();
			}
		};
		gui.play(new Animation(gui.getGridWidth(), switchOff, false));
	}
	
	/**
	 * Switches the Simori-ON on by calling
	 * {@link PowerTogglable#switchOn} on relevant
	 * components, and entering {@link #PerformanceMode}.
	 * @author Matt
	 * @author Jurek
	 */
	private void switchOn() {
		for (PowerTogglable t : toPowerToggle) t.switchOn();
		on = true;	
		setMode(makeInitialPerformanceMode());
		model.setBPM(DEFAULT_BPM);
		slave = new NetworkSlave(port, this);
		try {
			master = new NetworkMaster(port, this, slave);
		} catch (IOException e) {}
		slave.switchOn();
	}
	
	/**
	 * Switches the Simori-ON off by calling
	 * {@link PowerTogglable#switchOff} on relevant
	 * components, and entering {@link #OffMode}.
	 * @author Matt
	 * @author Adam
	 */
	private void switchOff() {
		on = false;
		setMode(makeOffMode());
		if (toPowerToggle == null) return;
		for (int i = toPowerToggle.length - 1; i >= 0; i--) {
			toPowerToggle[i].switchOff();
		}
		if(master != null) master.stopRunning();
		if (slave != null) slave.switchOff();
		master = null;
		slave = null;
	}
	
	/**
	 * Creates a PerformanceMode which enters ShopBoyMode when OK is pressed.
	 * This should only be used when the Simori-ON has just been switched on.
	 * @return Modified {@link PerformanceMode}
	 */
	private Mode makeInitialPerformanceMode() {
		return new PerformanceMode(this) {
			@Override
			public void onFunctionButtonPress(FunctionButtonEvent e) {
				if (e.getFunctionButton().equals(FunctionButton.OK)) {
					setMode(new ShopBoyMode(ModeController.this));
				} else {
					super.onFunctionButtonPress(e);
				}
			}
		};
	}
	
	/**
	 * Creates a {@link Mode} which calls {@link #setOn} to switch on
	 * when the OK button is pressed, and ignores all other input.
	 * @return A mode to represent the Simori-ON's switched off state
	 */
	private Mode makeOffMode() {
		return new Mode(this) {
			@Override
			public void onFunctionButtonPress(FunctionButtonEvent e) {
				if (e.getFunctionButton() == FunctionButton.ON) {
					getModeController().setOn(true, true);
				}
			}
		};
	}
}
