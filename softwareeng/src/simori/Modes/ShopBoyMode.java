package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

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
			throws InvalidCoordinatesException {} // No grid input in this mode
	
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
