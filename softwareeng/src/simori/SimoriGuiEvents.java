package simori;
import java.util.EventObject;

import simori.Exceptions.InvalidCoordinatesException;

/**
 * Defines listener interfaces and event types
 * for events passed from the GUI to the Mode.
 * @author Matt
 * @version 1.1.0
 */
public class SimoriGuiEvents {
	
	/**
	 * Event generated when an LED button in the grid is pressed.
	 * @see GridButtonListener
	 * @author Matt
	 * @version 1.0.1
	 */
	public static class GridButtonEvent extends EventObject {
		
		private int x, y;
		private SimoriGui src;
		
		/**
		 * @param src The SimoriGui the button belongs to
		 * @param x Horizontal coordinate of button pressed
		 * @param y Vertical coordinate of button pressed
		 */
		public GridButtonEvent(SimoriGui src, int x, int y) {
			super(src);
			this.src = src;
			this.x = x;
			this.y = y;
		}
		
		/** @return Horizontal grid coordinate of button pressed */
		public int getX() {
			return x;
		}
		
		/** @return Vertical grid coordinate of button pressed */
		public int getY() {
			return y;
		}
		
		/** @return GUI instance containing button pressed */
		public SimoriGui getSource() {
			return src;
		}
	}
	
	/**
	 * Event generated when any button not part of the grid is pressed.
	 * This includes ON, OK and the numbered L and R buttons.
	 * @see FunctionButtonListener
	 * @see FunctionButton
	 * @author Matt
	 * @version 1.2.0
	 */
	public static class FunctionButtonEvent extends EventObject {
		
		private FunctionButton btn;
		private SimoriGui src;
		
		/**
		 * @param src The GUI containing the button pressed
		 * @param btn enum identifying button pressed
		 */
		public FunctionButtonEvent(SimoriGui src, FunctionButton btn) {
			super(src);
			this.src = src;
			this.btn = btn;
		}
		
		/** @return enum identifying button pressed */
		public FunctionButton getFunctionButton() {
			return btn;
		}
		
		/** @return GUI instance containing button pressed */
		public SimoriGui getSource() {
			return src;
		}
	}
	
	/** Listener interface for {@link GridButtonEvent} */
	public interface GridButtonListener  {
		public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException;
	}
	
	/** Listener interface for {@link FunctionButtonEvent} */
	public interface FunctionButtonListener {
		public void onFunctionButtonPress(FunctionButtonEvent e);
	}
}