package simori;

import static simori.SimoriGuiEvents.FunctionButton.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import simori.Button.OnPressListener;
import simori.SimoriGuiEvents.FunctionButton;
import simori.SimoriGuiEvents.FunctionButtonEvent;
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
	private static final int SIZE = 600;
	private static final int EDGE_SIZE = 60;
	private static final int GAP = 0; //Padding between components
	
	private static final Color BACKGROUND = new Color(0xFFFFFF);
	private static final Color BORDER_COLOUR = new Color(0x000000);
	
	private static final Dimension SIDEBAR = new Dimension(EDGE_SIZE, SIZE - 2*EDGE_SIZE);
	private static final Dimension TOPBAR = new Dimension(SIZE, EDGE_SIZE);
	private static final Dimension DEFAULT = new Dimension(SIZE, SIZE);
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	protected JFrame frame;
	protected JLabel lcd;
	protected Led[][] leds;
	
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
		frame = new JFrame(WINDOW_TITLE);
		frame.getContentPane().setPreferredSize(DEFAULT);
		frame.pack();
		frame.setLayout(new BorderLayout(GAP, GAP));
		frame.add(makeTopButtons(), BorderLayout.PAGE_START);
		frame.add(makeLeftButtons(), BorderLayout.LINE_START);
		frame.add(makeLedPanel(rows, columns), BorderLayout.CENTER);
		frame.add(makeRightButtons(), BorderLayout.LINE_END);
		frame.add(makeBottomButtons(), BorderLayout.PAGE_END);
		frame.setBackground(BACKGROUND);
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
	
	public void setText(String text) {
		lcd.setText(text);
	}
	
	public void setTitle(String text) {
		frame.setTitle(text);
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
	protected JPanel makeLedPanel(int rows, int columns) {
		JPanel panel = new JPanel(new GridLayout(rows, columns, GAP, GAP));
		panel.setSize(SIZE - 2 * EDGE_SIZE, SIZE - 2 * EDGE_SIZE);
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
		panel.setBackground(BACKGROUND);
		panel.setBorder(BorderFactory.createLineBorder(BORDER_COLOUR));
		return panel;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of buttons along the top of the Simori.
	 * @return The completed panel
	 */
	protected JPanel makeTopButtons() {
		JPanel box = new JPanel();
		box.setPreferredSize(TOPBAR);
		BoxLayout layout = new BoxLayout(box, BoxLayout.LINE_AXIS);
		box.setLayout(layout);
		box.add(Box.createHorizontalGlue());
		box.add(new Button(makeListenerWith(POWER)));
		box.add(Box.createHorizontalGlue());
		box.setBackground(BACKGROUND);
		return box;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of buttons along the left of the Simori
	 * @return The completed panel
	 */
	protected JPanel makeLeftButtons() {
		JPanel box = new JPanel();
		box.setPreferredSize(SIDEBAR);
		BoxLayout layout = new BoxLayout(box, BoxLayout.PAGE_AXIS);
		box.setLayout(layout);
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(L1)));
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(L2)));
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(L3)));
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(L4)));
		box.add(Box.createVerticalGlue());
		box.setBackground(BACKGROUND);
		return box;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of buttons along the right of the Simori
	 * @return The completed panel
	 */
	protected JPanel makeRightButtons() {
		JPanel box = new JPanel();
		box.setPreferredSize(SIDEBAR);
		BoxLayout layout = new BoxLayout(box, BoxLayout.PAGE_AXIS);
		box.setLayout(layout);
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(R1)));
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(R2)));
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(R3)));
		box.add(Box.createVerticalGlue());
		box.add(new Button(makeListenerWith(R4)));
		box.add(Box.createVerticalGlue());
		box.setBackground(BACKGROUND);
		return box;
	}
	
	/**
	 * Helper method to create and populate the
	 * row of UI elements along the bottom of the Simori
	 * @return The completed panel
	 */
	protected JPanel makeBottomButtons() {
		JPanel box = new JPanel();
		box.setPreferredSize(TOPBAR);
		BoxLayout layout = new BoxLayout(box, BoxLayout.LINE_AXIS);
		box.setLayout(layout);
		lcd = new JLabel();
		box.add(Box.createHorizontalGlue());
		box.add(lcd);
		box.add(Box.createHorizontalGlue());
		box.add(new Button(makeListenerWith(OK)));
		box.add(Box.createHorizontalGlue());
		box.setBackground(BACKGROUND);
		return box;
	}
	
	/**
	 * Helper method to create an anonymous {@link OnPressListener}.
	 * Its behaviour is to notify our registered listener
	 * (i.e. the current {@link Mode}) that an LED was pressed.
	 * @param e Event with coordinates and source set
	 * @return A listener, ready to use
	 */
	protected OnPressListener makeListenerWith(final GridButtonEvent e) {
		return new OnPressListener() {
			public void onPress() {
				try {
					gListener.onGridButtonPress(e);
				} catch (InvalidCoordinatesException ex) {
					//TODO Add handling in case this is actually possible to trigger
				}
			}
		};
	}
	
	protected OnPressListener makeListenerWith(final FunctionButton btn) {
		return new OnPressListener() {
			@Override
			public void onPress() {
				fListener.onFunctionButtonPress(
						new FunctionButtonEvent(SimoriGui.this, btn));
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