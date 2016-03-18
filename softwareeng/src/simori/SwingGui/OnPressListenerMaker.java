package simori.SwingGui;

import simori.FunctionButton;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;


/**
 * Encapsulates the creation of {@link OnPressListener} objects
 * designed to adapt callbacks from {@link PressableCircle}s to
 * {@link GridButtonEvent} and {@link FunctionButtonEvent} callbacks
 * from a {@link simori.SimoriGui} to its listeners.
 * @see simori.SimoriGui.GridButtonListener
 * @see simori.SimoriGui.FunctionButtonListener
 * @author Matt
 * @version 2.1.2
 */
public class OnPressListenerMaker {
	
	private SimoriJFrame gui;
	
	/**
	 * Creates a maker capable of creating {@link OnPressListener}s
	 * which notify the listeners of the given GUI when triggered.
	 * The GUI will be set as the source in the event objects generated.
	 * @param gui The GUI whose listeners to inform
	 */
	public OnPressListenerMaker(SimoriJFrame gui) {
		this.gui = gui;
	}
	
	/**
	 * Returns an {@link OnPressListener} which, when triggered,
	 * notifies the GUI's {@link simori.SimoriGui.GridButtonListener}
	 * of a grid button press at the given coordinates.
	 * @param x horizontal coordinate to report in the event
	 * @param y vertical coordinate to report in the event
	 */
	public OnPressListener getListener(final int x, final int y) {
		return new OnPressListener() {
			public void onPress(PressableCircle circle) throws SimoriNonFatalException {
					final GridButtonEvent e = new GridButtonEvent(gui, x, y);
					gui.getGridButtonListener().onGridButtonPress(e);
			}
		};
	}
	
	/**
	 * Returns an {@link OnPressListener} which, when triggered,
	 * notifies the GUI's {@link simori.SimoriGui.FunctionButtonListener}
	 * that a {@link simori.FunctionButton} of the specified type was pressed.
	 * @param btn
	 */
	public OnPressListener getListener(final FunctionButton btn) {
		return new OnPressListener() {
			@Override
			public void onPress(PressableCircle circle) {
				gui.getFunctionButtonListener().onFunctionButtonPress(
						new FunctionButtonEvent(gui, btn));
			}
		};
	}
	
	/**
	 * Callback interface for listening for
	 * presses from {@link PressableCircle}s.
	 * @author Matt
	 * @version 1.0.0
	 */
	public interface OnPressListener {
		
		/**
		 * Called when the circle is pressed.
		 * @param circle Reference to the circle which was pressed
		 * @throws SimoriNonFatalException 
		 */
		public void onPress(PressableCircle circle) throws SimoriNonFatalException;
	}
}