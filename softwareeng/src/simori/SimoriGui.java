package simori;

import java.util.EventObject;

import simori.Simori.PowerTogglable;

/**
 * Interface setting out the constraints that any implementation
 * of a graphical user interface for the Simori-ON must comply to.
 * @author Matt
 * @version 2.6.0
 */
public interface SimoriGui extends PowerTogglable {
	
	/**
	 * Sets the pattern of illuminated LEDs in the grid.
	 * The on/off state of the LEDs will correspond to the locations
	 * of true values in the given multidimensional boolean array.
	 * @param grid The pattern to display in the LED grid
	 */
	public void setGrid(boolean[][] grid);
	
	/** Switches off all LEDs in the grid */
	public void clearGrid();
	
	/**
	 * Sets the text shown on the Simori-ON's LCD screen.
	 * @param text String to display. May be null.
	 */
	public void setText(String text);
	
	/** @return The text displayed on the LCD screen, or null */
	public String getText();
	
	/**
	 * Sets whether to display letters on the grid of buttons,
	 * giving it the appearance of a keyboard. Button presses
	 * are reported with {@link GridButtonEvent}s as usual.
	 * The letter represented by a button can be determined
	 * using the {@link KeyboardMapping}. Passing false will remove
	 * the letters. Buttons do not illuminate in keyboard mode.
	 * @see #getKeyboardMapping
	 * @param shown true to show a keyboard, false to show LEDs.
	 * @return For converting pressed button coordinates to letters
	 */
	public void setKeyboardShown(boolean shown);
	
	/** @return The mapping from grid button coordinates to character */
	public KeyboardMapping getKeyboardMapping();
	
	/** @param Animation which will be played on the GUI's buttons */
	public void play(Animation toPlay);
	
	/** @return Number of rows / columns in the LED grid */
	public int getGridSize();
	
	/** @param true if the GUI should be visible */
	public void setVisible(boolean visible);
	
	/** Sets the listener to receive {@link GridButtonEvent}s */
	public void setGridButtonListener(GridButtonListener l);
	
	/** Sets the listener to receive {@link FunctionButtonEvent}s */
	public void setFunctionButtonListener(FunctionButtonListener l);
	
	/** Listener interface for {@link GridButtonEvent} */
	public interface GridButtonListener  {
		public void onGridButtonPress(GridButtonEvent e);
	}
	
	/** Listener interface for {@link FunctionButtonEvent} */
	public interface FunctionButtonListener {
		public void onFunctionButtonPress(FunctionButtonEvent e);
	}
	
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
	
	/**
	 * Specifies the positions of characters on the imagined keyboard
	 * for when the Simori-ON's grid of buttons is used to enter text.
	 * @author Matt
	 * @version 1.0.3
	 */
	public interface KeyboardMapping {
		
		/** @return the width of the button grid this keyboard is for */
		public byte getRows();
		
		/** @return the height of the button grid this keyboard is for */
		public byte getColumns();
		
		/**
		 * @param x Horizontal coordinate of a button in the grid
		 * @param y Vertical coordinate of a button in the grid
		 * @return The letter on the button at these coordinates
		 */
		public Character getLetterOn(byte x, byte y);
	}
	
	/**
	 * A splash screen which should be displayed on screen as soon as
	 * it is constructed, to indicate that the Simori-ON is starting up.
	 * Once the main {@link SimoriGui} is ready to be shown,
	 * {@link SplashScreen#swapFor} can be called to open it.
	 * @author Matt
	 * @version 1.2.0
	 */
	public interface SplashScreen {
		
		/**
		 * Hides the splash screen and shows the given Simori-ON GUI.
		 * If the splash has not already been on display for the specified
		 * amount of time, the remained of that time interval is waited first.
		 * @param gui Constructed and ready for {@link SimoriGui#setVisible}
		 * @param after Minimum total time splash screen should be displayed
		 */
		public void swapFor(SimoriGui gui, int after);
	}
}
