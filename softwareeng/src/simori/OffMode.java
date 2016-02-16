package simori;

import simori.FunctionButton;
import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

public class OffMode extends Mode {
	
	Simori simori;

	public OffMode(Simori simori) {
		super(simori);
		this.simori = simori;
	}

	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws InvalidCoordinatesException {}
	
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		if (e.getFunctionButton() == FunctionButton.POWER) {
			simori.setOn(true);
		}
	}
}
