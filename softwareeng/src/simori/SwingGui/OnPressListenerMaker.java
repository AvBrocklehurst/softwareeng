package simori.SwingGui;

import simori.FunctionButton;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

public class OnPressListenerMaker {
	
	private SimoriJFrame gui;
	
	public OnPressListenerMaker(SimoriJFrame gui) {
		this.gui = gui;
	}
		
	public OnPressListener getListener(final int x, final int y) {
		return new OnPressListener() {
			public void onPress() {
				try {
					final GridButtonEvent e = new GridButtonEvent(gui, x, y);
					gui.getGridButtonListener().onGridButtonPress(e);
				} catch (InvalidCoordinatesException ex) {}
			}
		};
	}
	
	public OnPressListener getListener(final FunctionButton btn) {
		return new OnPressListener() {
			@Override
			public void onPress() {
				gui.getFunctionButtonListener().onFunctionButtonPress(
						new FunctionButtonEvent(gui, btn));
			}
		};
	}
	
	public interface OnPressListener {
		public void onPress();
	}
}