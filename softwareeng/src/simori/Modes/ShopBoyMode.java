package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;

/**
 * ShopBoyMode is a demonstration mode where a serious of songs
 * will be played to show the power of the Simori.
 * 
 * @author James
 * @author Matt
 * @version 1.0.0
 * @see Mode, ModeController
 */
public class ShopBoyMode extends Mode {

	public ShopBoyMode(ModeController controller) {
		super(controller);
	}
	
	@Override
	public void setInitialGrid() {
		getGui().clearGrid();
		getGui().setText("Shop boy mode (in development!)");
		((simori.SwingGui.SimoriJFrame) getGui()).testAnimation();
	}

	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws SimoriNonFatalException {} // No grid input in this mode
	
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e){
		switch(e.getFunctionButton()){
		case OK:
			super.onFunctionButtonPress(e);  //return to Performance Mode as normal
		
		default:
			break; //ignore all other function buttons
		}
	}
}
