package simori.Modes;

import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.FunctionButtonListener;
import simori.SimoriGui.GridButtonListener;
import simori.FunctionButton;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui;
import simori.Exceptions.SimoriNonFatalException;

/**
 * An abstract class defining methods for general
 * use in the Mode subclasses. Mode handles the logic
 * of a given Function Button press.
 * 
 * Mode is an abstract class and so is tested through its concrete
 * implementations.
 * 
 * @author James
 * @author Matt
 * @version 3.0.0
 */
public abstract class Mode implements FunctionButtonListener,
											GridButtonListener {
	
	private ModeController controller; // Reference to controller managing mode
	
	/** Requires all implementations to take a ModeController */
	public Mode(ModeController controller){
		this.controller = controller;
	}
	
	/** @return The controller managing the current mode in effect */
	protected ModeController getModeController() {
		return controller;
	}
	
	/** @return The Simori-ON's interface with the user */
	protected SimoriGui getGui() {
		return controller.getGui();
	}
	
	/** @return The representation of the Simori-ON's state */
	protected MatrixModel getModel() {
		return controller.getModel();
	}
	
	/** @see ModeController#getDisplayLayer */
	protected byte getDisplayLayer() {
		return controller.getDisplayLayer();
	}
	
	/**
	 * Called when a mode comes into effect,
	 * so that it can apply the desired initial pattern and text.
	 * Default behaviour switches off every LED and clears the LCD screen.
	 */
	public void setInitialGrid() {
		getGui().clearGrid();
		getGui().setText(null);
	}
	
	/**
	 * Called when the clock hand changes column
	 * @param col The column the clock hand has just entered
	 */
	public void tickerLight(byte col) throws SimoriNonFatalException {}
	
	/**
	 * Gets the function button pressed and the source GUI and then
	 * changes the current mode based on a specified FunctionButton.
	 * 
	 * @param e  A FunctionButtonEvent representing the press of a function button to switch
	 * mode or switch it on
	 * @author James
	 * @version 2.0.0
	 * @see FunctionButton.getFunctionButton(), SimoriGui.getSource(), SimoriGui.setMode()
	 */
	public void onFunctionButtonPress(FunctionButtonEvent e){
		FunctionButton fb = e.getFunctionButton();
		switch (fb) {
		case OK:
			controller.setMode(new PerformanceMode(controller));
			break;
		case ON:
			controller.setOn(!controller.isOn());
			break;
		case R4:
			controller.setMode(new MasterSlaveMode(controller));
			break;
		default:
			controller.setMode(ChangerModeFactory.getChanger(fb, controller));
		}
	}
}