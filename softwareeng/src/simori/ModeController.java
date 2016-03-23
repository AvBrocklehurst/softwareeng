package simori;

import simori.Simori.PowerTogglable;
import simori.SimoriGui.Animation;
import simori.SimoriGui.Animation.OnFinishListener;
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
 * @version 1.9.0
 */
public class ModeController {
	
	/** Tempo at which clock should start */
	private static final short DEFAULT_BPM = 88;
	
	/** Duration of startup animation and sound, in milliseconds */
	private static final long STARTUP_DURATION = 2700;
	
	/** Duration of shutdown animation and sound, in milliseconds */
	private static final long SHUTDOWN_DURATION = 3650;
	
	// Model and views this component sits between
	private MatrixModel model;
	private SimoriGui gui;
	private AudioFeedbackSystem afs;
	
	// Networking subcomponents
	private NetworkMaster master;
	private NetworkSlave slave;
	private int port;
	
	// List of components to give power state callbacks to
	private PowerTogglable[] toPowerToggle;
	
	//State information
	protected boolean on = true; // Power state of Simori-ON
	protected Mode mode; // Mode currently in effect
	private byte displayLayer; // Latest layer displayed in PerformanceMode
	private boolean isAnimating; // Flag set whilst animations are playing
	
	/**
	 * Creates a ModeController to accept input from the
	 * given GUI and make changes to the given model.
	 * An instance of ModeController is needed to instantiate
	 * {@link NoteProcessor}, with which it also communicates.
	 * 
	 * @param gui For button input and LED output
	 * @param model To store information on the Simori-ON's state
	 */
	public ModeController(SimoriGui gui, MatrixModel model,
			AudioFeedbackSystem afs, int port) {
		this.gui = gui;
		this.model = model;
		this.port = port;
		this.afs = afs;
	}
	
	/**
	 * Draws the clock hand in the specified column. If the current
	 * mode is not {@link PerformanceMode}, this has no effect.
	 * @param column x coordinate at which to draw clock hand
	 */
	public void tickThrough(byte column) {
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
	
	/** @return true if the Simori-ON is powered on */
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
	 * @throws SimoriNonFatalException 
	 */
	public void setMode(Mode mode){
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
	 * Searches searches for another Simori-ON on the network,
	 * and attempts to copy the current configuration to it.
	 * Does not restart the scan if one is already underway.
	 * @see NetworkMaster.ScanProgressListener
	 * @return Component which can be used to register for callbacks
	 */
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
	
	/** Plays a sound meant to indicate success */
	public void happySound() {
		afs.play(AudioFeedbackSystem.Sound.HAPPY);
	}
	
	/** Plays a sound meant to indicate failure */
	public void sadSound() {
		afs.play(AudioFeedbackSystem.Sound.SAD);
	}
	
	/**
	 * Sets the power state of the Simori-ON.
	 * Will not switch on correctly if not explicitly switched off beforehand.
	 * Whilst the Simori-ON is animating between on and
	 * off states, calls to this method are ignored.
	 * @param on true to switch on, or false to switch off
	 */
	public void setOn(boolean on, boolean animated) {
		if (isAnimating || this.on == on) return;
		if (on) {
			bootUp(animated);
		} else {
			shutDown(animated);
		}
	}
	
	/**
	 * Informs the listening components that the Simori-ON
	 * is powering up, then either switches on instantly
	 * or calls {@link #playBootSequence} to switch on after
	 * the startup animation and sound have played, depending
	 * on the value of the animated parameter.
	 */
	private void bootUp(boolean animated) {
		for (PowerTogglable p : toPowerToggle) p.ready();
		if (!animated){
			switchOn();
			return;
		}
		playBootSequence();
	}
	
	/**
	 * Plays the startup animation and sound on worker threads.
	 * After the animation has completed, {@link #switchOn} is
	 * called to fully switch on the Simori-ON. Handles the
	 * setting and clearing of the {@link #isAnimating} flag.
	 * Currently the animation and sound are not automatically
	 * synchronised, so the specified animation duration should
	 * be manually set to at least the duration of the sound.
	 */
	private void playBootSequence() {
		afs.play(AudioFeedbackSystem.Sound.WELCOME);
		OnFinishListener switchOn = new OnFinishListener() {
			@Override
			public void onAnimationFinished() {
				isAnimating = false;
				switchOn();
			}
		};
		Animation startUp =
				new GreyCentreWipe(true, true, true, true, gui.getGridSize());
		gui.play(startUp, STARTUP_DURATION, switchOn);
		isAnimating = true;
	}
	
	/**
	 * Informs the listening components that the Simori-ON
	 * is shutting down, then either switches off instantly
	 * or calls {@link #playShutDownSequence} to switch off
	 * after the shutdown animation and sound have played,
	 * depending on the value of the animated parameter.
	 */
	private void shutDown(boolean animated) {
		for (int i = toPowerToggle.length - 1; i >= 0; i--) {
			toPowerToggle[i].stop();
		}
		if (!animated) {
			switchOff();
			return;
		}
		playShutDownSequence();
	}
	
	/**
	 * Plays the shutdown animation and sound on worker threads.
	 * After the animation has completed, {@link #switchOff} is
	 * called to fully switch off the Simori-ON. Handles the
	 * setting and clearing of the {@link #isAnimating} flag.
	 * Currently the animation and sound are not automatically
	 * synchronised, so the specified animation duration should
	 * be manually set to at least the duration of the sound to
	 * avoid errors with the MIDI receiver closing too soon!
	 */
	private void playShutDownSequence() {
		afs.play(AudioFeedbackSystem.Sound.GOODBYE);
		OnFinishListener switchOff = new OnFinishListener() {
			@Override
			public void onAnimationFinished() {
				isAnimating = false;
				switchOff();
			}
		};
		Animation shutDown =
				new GreyCentreWipe(false, false, false, false, gui.getGridSize());
		gui.setText(null); // Turn of LCD before animation
		gui.play(shutDown, SHUTDOWN_DURATION, switchOff);
		isAnimating = true;
	}
	
	/**
	 * Finished switching the Simori-ON on,
	 * updating its state and notifying listeners accordingly.
	 */
	private void switchOn() {
		for (PowerTogglable t : toPowerToggle) t.switchOn();
		on = true;	
		setMode(makeInitialPerformanceMode());
		model.setBPM(DEFAULT_BPM);
		model.setPlaying();
		slave = new NetworkSlave(port, this);
		master = new NetworkMaster(port, this, slave);
		slave.switchOn();
	}
	
	/**
	 * Finishes switching the Simori-ON off,
	 * updating its state and notifying listeners accordingly.
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
					getController().setOn(true, true);
				}
			}
		};
	}
}
