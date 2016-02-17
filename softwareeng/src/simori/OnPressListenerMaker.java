package simori;

import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

public class OnPressListenerMaker {
	
	private SimoriGui gui;
	
	public OnPressListenerMaker(SimoriGui gui) {
		this.gui = gui;
	}
		
	public OnPressListener getListener(final int x, final int y) {
		return new OnPressListener() {
			public void onPress() {
				try {
					final GridButtonEvent e = new GridButtonEvent(gui, x, y);
					gui.getGridButtonListener().onGridButtonPress(e);
				} catch (InvalidCoordinatesException ex) {
					//TODO Add handling in case this is actually possible to trigger
				}
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