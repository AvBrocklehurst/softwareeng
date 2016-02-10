package simori;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import simori.Led.OnPressListener;
import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SimoriGuiEvents.GridButtonListener;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * Creates the user interface for the Simori-ON.
 * Uses a mixture of Swing components and awt layouts.
 * This is a thin / dumb GUI in that it forwards all
 * inputs to the {@link Mode} to handle the thinking.
 * Button presses are reported as events, and LEDs are
 * set using {@link #setPattern}. There is currently no
 * way to toggle a single LED on or off.
 * @author Matt
 * @version 2.1.3
 */
public class SimoriGui {
	
	/*
	 * TODO Control resizing to ensure grid is always square
	 * TODO Buttons around the edge are currently JButtons
	 * 		but should be made to look and behave like Leds.
	 * 		They'll be properly implemented in a later sprint.
	 */
	
	private static final String WINDOW_TITLE = "Simori-ON";
	private static final int DEFAULT_WIDTH = 720;
	private static final int DEFAULT_HEIGHT = 720;
	private static final int GAP = 0; //Padding between components
	
	private static final Color BACKGROUND_COLOUR = new Color(0xFFFFFF);
	private static final Color BORDER_COLOUR = new Color(0x000000);
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	private Led[][] leds;
	
	/**
	 * Creates a new GUI which will be visible immediately.
	 * When the frame is closed, the entire program will exit.
	 * @param rows Number of LEDs in the vertical dimension
	 * @param columns Number of LEDs in the horizontal dimension
	 */
	public SimoriGui(int rows, int columns) {
		/*
		 * The window is a JFrame arranged with BorderLayout, containing:
		 * Led grid is a JPanel containing Leds arranged with GridLayout.
		 * Buttons around the edges are in JPanels arranged with BoxLayout.
		 */
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(new BorderLayout(GAP, GAP));
		//frame.add(makeTopButtons(), BorderLayout.PAGE_START);
		//frame.add(makeLeftButtons(), BorderLayout.LINE_START);
		frame.add(makeLedPanel(rows, columns), BorderLayout.CENTER);
		//frame.add(makeRightButtons(), BorderLayout.LINE_END);
		//frame.add(makeBottomButtons(), BorderLayout.PAGE_END);
		frame.setBackground(BACKGROUND_COLOUR);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Set the illumination of zero or more grid LEDs in a specified pattern.
	 * @param grid Matrix of values with true in the locations to illuminate
	 */
	public void setGrid(boolean[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				
				//FIXME Unexplained transpose neccessary
				leds[x][y].setIlluminated(grid[y][x]);
			}
		}
	}
	
	/**
	 * Change the mode of the Simori-ON.
	 * @param mode The mode to switch to
	 */
	public void setMode(Mode mode) {
		//TODO Check whether it's already in the requested mode
		setGridButtonListener(mode);
		setFunctionButtonListener(mode);
	}
	
	/**
	 * Helper method to create and populate the grid of Leds.
	 * @param rows Number of LEDs in the vertical dimension
	 * @param columns Number of LEDs in the horizontal dimension
	 * @return The completed panel containing a grid of Leds
	 */
	private JPanel makeLedPanel(int rows, int columns) {
		JPanel panel = new JPanel(new GridLayout(rows, columns, GAP, GAP));
		leds = new Led[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				leds[x][y] = new Led();
				panel.add(leds[x][y]);
				
				//Create event object for callback in advance, with coords
				final GridButtonEvent e = new GridButtonEvent(this, x, y);
				leds[x][y].setOnPressListener(makeListenerWith(e));
			}
		}
		panel.setBackground(BACKGROUND_COLOUR);
		panel.setBorder(BorderFactory.createLineBorder(BORDER_COLOUR));
		return panel;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of buttons along the top of the Simori.
	 * @return The completed panel
	 */
	private JPanel makeTopButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.LINE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("ON/OFF")); //Just for proof of concept
		return box;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of buttons along the left of the Simori
	 * @return The completed panel
	 */
	private JPanel makeLeftButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.PAGE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("L1")); //Mainly to test BoxLayout
		box.add(new JButton("L2"));
		box.add(new JButton("L3"));
		box.add(new JButton("L4"));
		return box;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of buttons along the right of the Simori
	 * @return The completed panel
	 */
	private JPanel makeRightButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.PAGE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("R1"));
		box.add(new JButton("R2"));
		box.add(new JButton("R3"));
		box.add(new JButton("R4"));
		return box;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of UI elements along the bottom of the Simori
	 * @return The completed panel
	 */
	private JPanel makeBottomButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.LINE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("OK"));
		//There will eventually be an LCD screen
		return box;
	}
	
	/**
	 * Helper method to create an anonymous {@link OnPressListener}.
	 * Its behaviour is to notify our registered listener
	 * (i.e. the current {@link Mode}) that an LED was pressed.
	 * @param e Event with coordinates and source set
	 * @return A listener, ready to use
	 */
	private OnPressListener makeListenerWith(final GridButtonEvent e) {
		return new OnPressListener() {
			public void onPress(Led led) {
				try {
					gListener.onGridButtonPress(e);
				} catch (InvalidCoordinatesException ex) {
					//TODO Add handling in case this is actually possible to trigger
				}
			}
		};
	}
	
	/** Sets the listener to receive events for Leds in the grid */
	private void setGridButtonListener(GridButtonListener l) {
		gListener = l;
	}
	
	/** Sets the listener to receive events for non-grid buttons */
	private void setFunctionButtonListener(FunctionButtonListener l) {
		fListener = l;
	}
}