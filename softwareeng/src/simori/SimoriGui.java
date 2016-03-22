package simori;

import java.util.EventObject;
import java.util.Map;

import simori.Simori.PowerTogglable;
import simori.SimoriGui.Animation.OnFinishListener;
import simori.Modes.Mode;

/**
 * Interface setting out the constraints that any implementation of a graphical
 * user interface for the Simori-ON must adhere to. It is a thin / dumb GUI in
 * that it forwards all inputs to the {@link Mode} to handle the logic.
 * Button presses are reported as events, and LEDs are set using
 * {@link #setPattern}. There is currently no way to toggle a single LED on.
 * Also features an LCD screen which can display {@link #setText}.
 * The grid is capable of displaying a keyboard, which can be customised with a
 * {@link KeyboardMapping}. All buttons can be animated with {@link #play}.
 * Methods inherited from {@link PowerTogglable} should be used to grey out
 * buttons when the Simori-ON is switched off. {@link SplashScreen} defines a
 * splash screen which may be displayed when the program is started, until the
 * Simori-ON is ready to use, at which point {@link SplashScreen#swapFor} can
 * be used to hide the splash screen and show the SimoriGui (by calling its
 * {@link #setVisible}). GUIs should be able to display error information as
 * specified in {@link #reportError} for the user to view. When an error
 * message is dismissed, its {@link OnErrorDismissListener} is notified.
 * 
 * @author Matt
 * @version 3.3.0
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
	
	/**
	 * Displays each {@link Animation.Frame} of the given animation.
	 * Interpolates the time for each frame so that the complete
	 * animation takes the specified amount of time.
	 * @param toPlay Source of frames
	 * @param duration Total milliseconds duration of animation
	 * @param l to notify on completion
	 */
	public void play(Animation toPlay, long duration, OnFinishListener l);
	
	/**
	 * Displays the given information as an error message.
	 * If the error was fatal, the dialog should force the user to exit.
	 * @param shortMessage User-friendly summary of error (optionally HTML)
	 * @param longMessage Long text detailing error (no formatting)
	 * @param title Title for error dialog
	 * @param l To receive callback when the user dismisses the error
	 * @param fatal true if the error was fatal
	 */
	public void reportError(String shortMessage,
							String longMessage,
							String title,
							OnErrorDismissListener l,
							boolean fatal);
	
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
		
		/**
		 * The same as {@link #swapFor(SimoriGui, int)}.
		 * Additionally calls {@link ExceptionManager#simoriReady}
		 * on the given ExceptionManager, passing it the GUI
		 * and the given AudioFeedbackSystem.
		 */
		public void swapFor(SimoriGui gui, int after,
				ExceptionManager errors, AudioFeedbackSystem afs);
	}
	
	/**
	 * Interface for receiving callbacks when the user dismisses
	 * an error reported through {@link SimoriGui#reportError}.
	 * @author Matt
	 * @version 1.0.0
	 */
	public interface OnErrorDismissListener {
		
		/** Called when the error is dismissed by whatever means */
		public void onErrorDismiss();
	}
	
	/**
	 * Provides a sequence of {@link Animation.Frame}s
	 * to be displayed one at a time.
	 * @author Matt
	 * @version 2.0.0
	 */
	public interface Animation {
		
		/** @return the number of frames in the animation */
		public int getFrameCount();
		
		/**
		 * Returns the next frame in the sequence,
		 * or null if there are no more frames.
		 * @return The next frame to display
		 */
		public Frame getNextFrame();
		
		/**
		 * Interface for receiving callbacks when an animation is complete.
		 * @author Matt
		 * @version 1.0.0
		 */
		public interface OnFinishListener {
			
			/** Called when the final frame has been displayed */
			public void onAnimationFinished();
		}
		
		/**
		 * Simple data structure which defines a state the GUI can be in:
		 * <br/>{@link #ledsGreyed}, the pattern of LEDs which are greyed out
		 * <br/>{@link #ledsIlluminated}, the pattern of LEDs illuminated
		 * <br/>{@link #btnsGreyed}, the greyed out state of each edge button
		 * Each may be null, indicating that the state of these components is
		 * not specified in this frame, and should be left as it was before.
		 * @author Matt
		 * @version 2.0.0
		 */
		public class Frame {
			
			/** true in locations corresponding to LEDs to grey out */
			public boolean[][] ledsGreyed;
			
			/** true in locations corresponding to LEDs to illuminate */
			public boolean[][] ledsIlluminated;
			
			/**
			 * Maps FunctionButtons to true if the corresponding button
			 * should be greyed out, false if it should be enabled,
			 * or null if unspecified
			 */
			public Map<FunctionButton, Boolean> btnsGreyed;
		}
	}
}
