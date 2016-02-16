package simori;

import simori.FunctionButton;
import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

public class OffMode extends Mode {
	
	public OffMode(ModeController controller) {
		super(controller);
	}

	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws InvalidCoordinatesException {}
	
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		if (e.getFunctionButton() == FunctionButton.POWER) {
			getModeController().setOn(true);
		}
	}
}
