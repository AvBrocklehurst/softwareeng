package simori.Modes;

import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.FunctionButtonListener;
import simori.SimoriGui.GridButtonListener;
import simori.FunctionButton;
import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriGui;
import simori.Exceptions.InvalidCoordinatesException;

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
 * @version 2.0.0
 */
public abstract class Mode implements FunctionButtonListener, GridButtonListener {
	
	private ModeController controller;     //current mode controller
	
	public Mode(ModeController controller){
		this.controller = controller;
	}
	
	protected ModeController getModeController() {
		return controller;
	}
	
	protected SimoriGui getGui() {
		return controller.getGui();
	}
	
	protected MatrixModel getModel() {
		return controller.getModel();
	}
	
	protected byte getDisplayLayer() {
		return controller.getDisplayLayer();
	}
	
	public void setInitialGrid() {
		getGui().clearGrid();
		getGui().setText(null);
	}
	
	public void tickerLight(byte col) throws InvalidCoordinatesException {}
	
	/**
	 * Gets the function button pressed and the source Gui and then
	 * changes the current mode based on a specified FunctionButton.
	 * 
	 * @param e  A FunctionButtonEvent representing the press of a function button to switch
	 * mode or switch it on
	 * @author James
	 * @version 1.0.1
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
		default:
			controller.setMode(ChangerModeFactory.getChanger(fb, controller));
		}
	}
}