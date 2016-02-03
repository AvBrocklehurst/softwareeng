package simori;
import java.awt.Color;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonListener;

public class SimoriGui {
	
	private static final String WINDOW_TITLE = "Simori-ON";
	private static final Color TRANSPARENT = new Color(0x00000000, true);
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	public static void main(String[] args) {
		new SimoriGui(1, 1);
	}
	
	public SimoriGui(int rows, int columns) {
		JFrame frame = new JFrame(WINDOW_TITLE);
		JPanel outer = new JPanel();
		JTable table = new JTable(rows, columns);
		frame.setSize(720, 720);
		frame.setBackground(TRANSPARENT);
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
