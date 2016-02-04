package simori;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;

import simori.Led.OnPressListener;
import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SimoriGuiEvents.GridButtonListener;

public class SimoriGui {
	
	private static final String WINDOW_TITLE = "Simori-ON";
	private static final int DEFAULT_WIDTH = 720;
	private static final int DEFAULT_HEIGHT = 720;
	private static final int GAP = 0;
	
	private static final Color GRID_BACKGROUND = new Color(0xFFFFFF);
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	private Led[][] leds;
	
	public SimoriGui(int rows, int columns) {
		JFrame frame = new JFrame(WINDOW_TITLE);
		GridLayout grid = new GridLayout(rows, columns, GAP, GAP);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(grid);
		addLeds(frame, rows, columns);
		frame.setBackground(GRID_BACKGROUND);
		frame.setVisible(true);
	}
	
	private void addLeds(JFrame frame, int rows, int columns) {
		leds = new Led[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				leds[x][y] = new Led();
				frame.add(leds[x][y]);
				final GridButtonEvent e = new GridButtonEvent(this, x, y);
				leds[x][y].setOnPressListener(new OnPressListener() {
					public void onPress(Led led) {
						//TODO gListener.onGridButtonPress(e);
						System.out.println(e.getX() + ", " + e.getY());
					}
				});
			}
		}
	}
	
	public void setPattern(Layer pattern) {
		boolean[][] grid = pattern.getGrid();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				leds[x][y].setIlluminated(grid[x][y]);
			}
		}
	}
	
	public void setMode(Mode mode) {
		setGridButtonListener(mode);
		setFunctionButtonListener(mode);
	}
	
	private void setGridButtonListener(GridButtonListener l) {
		gListener = l;
	}
	
	private void setFunctionButtonListener(FunctionButtonListener l) {
		fListener = l;
	}
}