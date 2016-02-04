package simori;
import java.awt.GridLayout;

import javax.swing.JButton;
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
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				frame.add(new JButton(x + "," + y));
			}
		}
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
