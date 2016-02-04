package simori;
import java.awt.GridLayout;

import javax.swing.JFrame;

import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonListener;

public class SimoriGui {
	
	private static final String WINDOW_TITLE = "Simori-ON";
	private static final int DEFAULT_WIDTH = 720;
	private static final int DEFAULT_HEIGHT = 720;
	private static final int GAP = 0;
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	public SimoriGui(int rows, int columns) {
		JFrame frame = new JFrame(WINDOW_TITLE);
		GridLayout grid = new GridLayout(rows, columns, GAP, GAP);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(grid);
		Led five = null, seven = null;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				Led led = new Led(x, y);
				if (x == 5 && y == 5) five = led;
				frame.add(led);
				if (x == 7 && y == 7) seven = led;
			}
		}
		five.setSize(five.getWidth() / 2, five.getHeight() / 2);
		seven.setSize(seven.getWidth() / 2, seven .getHeight() / 2);
		frame.setVisible(true);
	}
	
	public void setPattern(Layer pattern) {
		
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
