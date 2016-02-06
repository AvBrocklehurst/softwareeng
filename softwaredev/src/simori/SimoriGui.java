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

public class SimoriGui {
	
	//TODO Make LEDs always circular
	//TODO Make Grid always square
	
	private static final String WINDOW_TITLE = "Simori-ON";
	private static final int DEFAULT_WIDTH = 720;
	private static final int DEFAULT_HEIGHT = 720;
	private static final int GAP = 0;
	
	private static final Color BACKGROUND_COLOUR = new Color(0xFFFFFF);
	private static final Color BORDER_COLOUR = new Color(0x000000);
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	private Led[][] leds;
	
	public SimoriGui(int rows, int columns) {
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(new BorderLayout(GAP, GAP));
		frame.add(makeTopButtons(), BorderLayout.PAGE_START);
		frame.add(makeLeftButtons(), BorderLayout.LINE_START);
		frame.add(makeLedPanel(rows, columns), BorderLayout.CENTER);
		frame.add(makeRightButtons(), BorderLayout.LINE_END);
		frame.add(makeBottomButtons(), BorderLayout.PAGE_END);
		frame.setBackground(BACKGROUND_COLOUR);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
		panel.setBackground(BACKGROUND_COLOUR);
		panel.setBorder(BorderFactory.createLineBorder(BORDER_COLOUR));
		return panel;
	}
	
	private JPanel makeTopButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.LINE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("ON/OFF"));
		return box;
	}
	
	private JPanel makeLeftButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.PAGE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("L1"));
		box.add(new JButton("L2"));
		box.add(new JButton("L3"));
		box.add(new JButton("L4"));
		return box;
	}
	
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
	
	private JPanel makeBottomButtons() {
		JPanel box = new JPanel();
		BoxLayout layout = new BoxLayout(box, BoxLayout.LINE_AXIS);
		box.setLayout(layout);
		box.add(new JButton("OK"));
		return box;
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
	
	public void setGrid(boolean[][] grid) {
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