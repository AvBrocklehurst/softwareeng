package simori.Modes;

import simori.FunctionButton;
import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;

/**
 * Mode which simulates a powered-off state, ignoring
 * all inputs except the pressing of the ON button.
 * @author Matt
 * @version 1.1.2
 */
public class OffMode extends Mode {
	
	public OffMode(ModeController controller) {
		super(controller);
	}

	/** Ignores input from grid buttons whilst switched off */
	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws SimoriNonFatalException {}
	
	/** Ignores all buttons except ON, which is allowed to function normally */
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		if (e.getFunctionButton() == FunctionButton.ON) {
			getModeController().setOn(true);
		}
	}
}
