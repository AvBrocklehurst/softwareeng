package simori;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simori.Led.OnPressListener;
import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonEvent;
import simori.SimoriGuiEvents.GridButtonListener;
import simori.Exceptions.InvalidCoordinatesException;

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
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(new BorderLayout());
		frame.add(makeLedPanel(rows, columns), BorderLayout.CENTER);
		frame.setBackground(GRID_BACKGROUND);
		frame.setVisible(true);
	}
	
	private JPanel makeLedPanel(int rows, int columns) {
		JPanel panel = new JPanel(new GridLayout(rows, columns, GAP, GAP));
		leds = new Led[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				leds[x][y] = new Led();
				panel.add(leds[x][y]);
				final GridButtonEvent e = new GridButtonEvent(this, x, y);
				leds[x][y].setOnPressListener(makeListenerWith(e));
			}
		}
		return panel;
	}
	
	private OnPressListener makeListenerWith(final GridButtonEvent e) {
		return new OnPressListener() {
			public void onPress(Led led) {
				try {
					gListener.onGridButtonPress(e);
				} catch (InvalidCoordinatesException e1) {
					// TODO Get rid of this try-catch
					e1.printStackTrace();
				}
			}
		};
	}
	
	public void setPattern(Layer pattern) {
		boolean[][] grid = pattern.getGrid();
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				leds[x][y].setIlluminated(grid[y][x]); //TODO Is grid the wrong way 'round or am I?
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