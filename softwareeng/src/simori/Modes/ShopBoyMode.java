package simori.Modes;

import simori.ModeController;
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
}
