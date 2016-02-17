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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class SimoriGui extends JFrame implements MouseMotionListener {
	
	private static final String WINDOW_TITLE = "Simori-ON";
	private static final int GAP = 0; //Padding between components
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	private Dimension simoriSize, sideBarSize, topBarSize;
	private int rows, columns;
	
	private JPanel ledPanel;
	private FunctionButtonBar leftBar, rightBar;
	private FunctionButtonBar topBar, bottomBar;
	private JLabel lcd;
	private Led[][] leds;
	
	private int startX, startY;
	private boolean couldDragBefore;
	private Cursor oldCursor;
	
	/**
	 * Creates a new GUI which will be visible immediately.
	 * When the frame is closed, the entire program will exit.
	 * @param rows Number of LEDs in the vertical dimension
	 * @param columns Number of LEDs in the horizontal dimension
	 */
	public SimoriGui(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setUndecorated(true);
		//setOpacity(0); //TODO illegalcomponentstateexception
		
		addComponents();
		sortSizes();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!canDragFrom(e.getPoint())) System.out.println("uh no draggi");
				startX = e.getX();
				startY = e.getY();
			}
		});
		addMouseMotionListener(this);
	}
	
	private void sortSizes() {
		calculateDimensions();
		getContentPane().setPreferredSize(simoriSize);
		leftBar.setPreferredSize(sideBarSize);
		rightBar.setPreferredSize(sideBarSize);
		topBar.setPreferredSize(topBarSize);
		bottomBar.setPreferredSize(topBarSize);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	private void calculateDimensions() {
		Dimension s = getToolkit().getScreenSize();
		s.width = s.height = Math.min(s.width, s.height);
		simoriSize = ratioOf(0.8f, 0.8f, s);
		sideBarSize = ratioOf(0.1f, 0.8f, simoriSize);
		topBarSize = ratioOf(1f, 0.1f, simoriSize);
	}
	
	private Dimension ratioOf(float x, float y, Dimension original) {
		float w = (float) original.width * x;
		float h = (float) original.height * y;
		return new Dimension((int) w, (int) h);
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
	
	private void makeComponents() {
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		topBar = new FunctionButtonBar(false, maker, ON);
		leftBar = new FunctionButtonBar(true, maker, L1, L2, L3, L4);
		makeLedPanel();
		rightBar = new FunctionButtonBar(true, maker, R1, R2, R3, R4);
		bottomBar = new FunctionButtonBar(false, maker, OK);
	}
	
	private void addComponents() {
		makeComponents();
		JPanel panel = new SimoriPanel();
		panel.setLayout(new BorderLayout(GAP, GAP));
		panel.add(topBar, BorderLayout.PAGE_START);
		panel.add(leftBar, BorderLayout.LINE_START);
		panel.add(ledPanel, BorderLayout.CENTER);
		panel.add(rightBar, BorderLayout.LINE_END);
		panel.add(bottomBar, BorderLayout.PAGE_END);
		add(panel);
	}
	
	private void makeLedPanel() {
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		ledPanel = new JPanel(new GridLayout(rows, columns, GAP, GAP));
		//TODO ledPanel.setSize(SIZE - 2 * EDGE_SIZE, SIZE - 2 * EDGE_SIZE);
		leds = new Led[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				leds[x][y] = new Led();
				ledPanel.add(leds[x][y]);
				
				//Create event object for callback in advance, with coords
				final GridButtonEvent e = new GridButtonEvent(this, x, y); //TODO (0,0) bottom left rather than top left
				leds[x][y].setOnPressListener(maker.getListener(e));
			}
		}
		ledPanel.setBackground(new Color(0xFFFFFF)); //FIXME hardcoding
		ledPanel.setBorder(BorderFactory.createLineBorder(new Color(0x000000)));
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

	@Override
	public void mouseDragged(MouseEvent e) {
		if (canDragFrom(e.getPoint())) {
			Point l = getLocation();
			setLocation(l.x + e.getX() - startX, l.y + e.getY() - startY);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		boolean canDrag = canDragFrom(e.getPoint());
		if (canDrag && !couldDragBefore) {
			oldCursor = getCursor();
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}
		if (couldDragBefore && !canDrag) {
			setCursor(oldCursor);
		}
		couldDragBefore = canDrag;
	}
	
	private boolean canDragFrom(Point point) {
		return topBar.contains(point)
				|| bottomBar.contains(point)
				|| leftBar.contains(point)
				|| rightBar.contains(point);
	}
}