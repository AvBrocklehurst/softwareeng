package simori;

import static simori.FunctionButton.L1;
import static simori.FunctionButton.L2;
import static simori.FunctionButton.L3;
import static simori.FunctionButton.L4;
import static simori.FunctionButton.OK;
import static simori.FunctionButton.ON;
import static simori.FunctionButton.R1;
import static simori.FunctionButton.R2;
import static simori.FunctionButton.R3;
import static simori.FunctionButton.R4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SimoriGuiEvents.GridButtonListener;

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
	
	public static final Color BACKGROUND = new Color(0xFFFFFF);
	public static final Color BORDER = new Color(0x000000);
	
	public static final Dimension SIDEBAR = new Dimension(EDGE_SIZE, SIZE - 2*EDGE_SIZE);
	public static final Dimension TOPBAR = new Dimension(SIZE, EDGE_SIZE);
	public static final Dimension DEFAULT = new Dimension(SIZE, SIZE);
	public static final Dimension SIDEBUTTON = new Dimension(50, 50);
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	private int rows, columns;
	
	protected JFrame frame;
	protected JPanel ledPanel;
	protected FunctionButtonBar leftBar, rightBar;
	protected FunctionButtonBar topBar, bottomBar;
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
		this.rows = rows;
		this.columns = columns;
		frame = new JFrame(WINDOW_TITLE);
		frame.getContentPane().setPreferredSize(DEFAULT);
		frame.pack();
		frame.setLayout(new BorderLayout(GAP, GAP));
		makeComponents();
		addComponents();
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
	
	public void clearGrid() {
		setGrid(new boolean[rows][columns]);
	}
	
	public void setText(String text) {
		lcd.setText(text);
	}
	
	public void setTitle(String text) {
		frame.setTitle(text);
	}
	
	private void makeComponents() {
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		topBar = new FunctionButtonBar(false, maker, ON);
		leftBar = new FunctionButtonBar(true, maker, L1, L2, L3, L4);
		makeLedPanel();
		rightBar = new FunctionButtonBar(true, maker, R1, R2, R3, R4);
		bottomBar = new FunctionButtonBar(false, maker, OK);
	}
	
	private void addComponents() {
		frame.add(topBar, BorderLayout.PAGE_START);
		frame.add(leftBar, BorderLayout.LINE_START);
		frame.add(ledPanel, BorderLayout.CENTER);
		frame.add(rightBar, BorderLayout.LINE_END);
		frame.add(bottomBar, BorderLayout.PAGE_END);
	}
	
	private void makeLedPanel() {
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		ledPanel = new JPanel(new GridLayout(rows, columns, GAP, GAP));
		ledPanel.setSize(SIZE - 2 * EDGE_SIZE, SIZE - 2 * EDGE_SIZE);
		leds = new Led[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				leds[x][y] = new Led();
				ledPanel.add(leds[x][y]);
				
				//Create event object for callback in advance, with coords
				final GridButtonEvent e = new GridButtonEvent(this, x, y);
				leds[x][y].setOnPressListener(maker.getListener(e));
			}
		}
		ledPanel.setBackground(BACKGROUND);
		ledPanel.setBorder(BorderFactory.createLineBorder(BORDER));
	}
	
	/** Sets the listener to receive events for Leds in the grid */
	public void setGridButtonListener(GridButtonListener l) {
		gListener = l;
	}
	
	/** Sets the listener to receive events for non-grid buttons */
	public void setFunctionButtonListener(FunctionButtonListener l) {
		fListener = l;
	}
	
	public GridButtonListener getGridButtonListener() {
		return gListener;
	}
	
	public FunctionButtonListener getFunctionButtonListener() {
		return fListener;
	}
}