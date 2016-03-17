package simori.SwingGui;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import simori.SwingGui.OnPressListenerMaker.OnPressListener;

/**
 * JPanel which manages a grid of {@link Led}s.
 * @author Matt
 * @version 2.1.0
 */
public class LedPanel extends JPanel implements OnPressListener {
	
	protected Led[][] leds;
	protected PressableCircle lastPressed;
	
	/**
	 * Creates a panel of {@link #Led}s, in a grid
	 * of the specified size, with listeners generated
	 * by the given {@link OnPressListenerMaker}.
	 * @param rows Number of rows of LEDs
	 * @param columns Number of columns of LEDs
	 * @param maker To generate listeners for the LEDs
	 */
	public LedPanel(int rows, int columns, OnPressListenerMaker maker) {
		addLeds(rows, columns, maker);
		setBackground(GuiProperties.LED_PANEL_BACKGROUND);
		setBorder(BorderFactory.createLineBorder(
				GuiProperties.LED_PANEL_BORDER));
		setCursor(GuiProperties.NORMAL_CURSOR);
		getToolkit().addAWTEventListener(
				makeGlobalMouseReleaseListener(),
				AWTEvent.MOUSE_EVENT_MASK);
	}
	
	/**
	 * Sets the pattern of illuminated LEDs in the grid.
	 * The on/off state of the LEDs will correspond to the locations
	 * of true values in the given multidimensional boolean array.
	 * @param grid The pattern to display in the LED grid
	 */
	public void setGrid(boolean[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				leds[x][y].setIlluminated(grid[y][x]);
			}
		}
	}
	
	/**
	 * Greys out particular {@link Led}s in the grid,
	 * according to the specified pattern.
	 * LEDs in positions corresponding to true values in the
	 * given multidimensional boolean array will be greyed out.
	 * @param which The pattern of LEDs to grey out
	 */
	public void setGreyedOut(boolean[][] which) {
		for (int y = 0; y < which.length; y++) {
			for (int x = 0; x < which[y].length; x++) {
				leds[x][y].setGreyedOut(which[y][x]);
			}
		}
	}
	
	/**
	 * Adds the required {@link Led}s to the frame,
	 * using {@link GridLayout}, and sets their listeners.
	 * @param rows Number of LEDs in the horizontal dimension
	 * @param columns Number of LEDs in the vertical dimension
	 * @param maker Source of listeners to set
	 */
	private void addLeds(int rows, int columns, OnPressListenerMaker maker) {
		setLayout(new GridLayout(rows, columns, 0, 0));
		leds = new Led[rows][columns];
		
		/* y is decremented each time, because LEDs are added from the
		   top left corner but should be numbered from the bottom left */
		for (int y = rows-1; y >= 0; y--) {
			for (int x = 0; x < columns; x++) {
				Led led = makeLed();
				add(led);
				led.addOnPressListener(maker.getListener(x, y));
				led.addOnPressListener(this);
				leds[x][y] = led;
			}
		}
	}
	
	/**
	 * So that subclasses can change the type of {@link Led} to use.
	 * @return a newly constructed Led
	 */
	protected Led makeLed() {
		return new Led();
	}

	/** {@inheritDoc} */
	@Override
	public void onPress(PressableCircle circle) {
		lastPressed = circle; //Hold reference to last LED pressed
	}

	/**
	 * Creates an {@link AWTEventListener} which responds to mouse
	 * release events anywhere in the window by forwarding them to
	 * the most recently pressed {@link #Led}.
	 * This is a fix for a subtle bug in the click-and-drag multiple
	 * LED pressing feature. The final LED in a sequence would not
	 * redraw itself in its non-pressed colour when the mouse was
	 * released inside it, until the mouse left its area. This was
	 * because its MouseListener was not notified of mouse release
	 * events for which the corresponding press event did not occur
	 * inside the LED's area.
	 * To solve this problem, a single listener global to the window
	 * is used to capture all mouse released events and manually
	 * notify the relevant LED, which will be the last one pressed.
	 */
	private AWTEventListener makeGlobalMouseReleaseListener() {
		return new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event.getID() != MouseEvent.MOUSE_RELEASED) return;
				if (lastPressed == null) return;
				lastPressed.mouseReleased((MouseEvent) event);
			}
		};
	}
}