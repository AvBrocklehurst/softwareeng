package simori;
import java.awt.Color;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class SimoriGui {
	
	private static final String WINDOW_TITLE = "Simori-ON";
	
	public static void main(String[] args) {
		new SimoriGui(1, 1);
	}
	
	public SimoriGui(int rows, int columns) {
		JFrame frame = new JFrame(WINDOW_TITLE);
		JPanel outer = new JPanel();
		JTable table = new JTable(rows, columns);
		frame.setSize(720, 720);
		frame.setBackground(new Color(0xFFFFFF00, true));
		frame.setVisible(true);
	}
	
	public void setPattern(Layer pattern) {
		
	}
}
