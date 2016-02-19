package simori.SwingGui;

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
import static simori.SwingGui.GuiProperties.SCREEN_PROPORTION;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simori.Mode;
import simori.SimoriGui;

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
public class SimoriJFrame extends JFrame implements SimoriGui, MouseMotionListener {
	
	private static final int EXIT_CODE = 27; //ASCII for ESC
	
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	private Dimension simoriSize, sideBarSize, topBarSize;
	private int rows, columns;
	
	private LedPanel ledPanel;
	private SimoriEdgeBar leftBar, rightBar;
	private SimoriEdgeBar topBar, bottomBar;
	private Lcd lcd;
	
	private int startX, startY;
	private boolean couldDragBefore;
	private Cursor oldCursor;
	
	public SimoriJFrame(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		setUpWindow();
		addMouseMotionListener(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == EXIT_CODE) System.exit(0);
			}
		});
	}
	
	public void setGrid(boolean[][] grid) {
		ledPanel.setGrid(grid);
	}
	
	public void clearGrid() {
		setGrid(new boolean[rows][columns]);
	}
	
	public void setText(String text) {
		lcd.setText(text);
	}
	
	public void setGridButtonListener(GridButtonListener l) {
		gListener = l;
	}
	
	public void setFunctionButtonListener(FunctionButtonListener l) {
		fListener = l;
	}
	
	public GridButtonListener getGridButtonListener() {
		return gListener;
	}
	
	public FunctionButtonListener getFunctionButtonListener() {
		return fListener;
	}
	
	private void setUpWindow() {
		setIconImage(GuiProperties.getIcon());
		setTitle(GuiProperties.WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(GuiProperties.WINDOW_BACKGROUND);
		addComponents();
		sortSizes();
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
		s.width = s.height = Math.min(s.width, s.height) + 2;
		simoriSize = GuiProperties.ratioOf(SCREEN_PROPORTION, SCREEN_PROPORTION, s);
		sideBarSize = GuiProperties.ratioOf(0.1f, 0.8f, simoriSize);
		topBarSize = GuiProperties.ratioOf(1f, 0.1f, simoriSize);
	}
	
	private void makeComponents() {
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		topBar = new SimoriEdgeBar(false, false, maker, ON);
		leftBar = new SimoriEdgeBar(true, false, maker, L1, L2, L3, L4);
		ledPanel = new LedPanel(rows, columns, maker);
		rightBar = new SimoriEdgeBar(true, false, maker, R1, R2, R3, R4);
		bottomBar = new SimoriEdgeBar(false, true, maker, OK);
		lcd = bottomBar.getLcd();
	}
	
	private void addComponents() {
		makeComponents();
		JPanel panel = new SimoriPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(topBar, BorderLayout.PAGE_START);
		panel.add(leftBar, BorderLayout.LINE_START);
		panel.add(ledPanel, BorderLayout.CENTER);
		panel.add(rightBar, BorderLayout.LINE_END);
		panel.add(bottomBar, BorderLayout.PAGE_END);
		add(panel);
	}
	
	private boolean canDragFrom(Point point) {
		return topBar.contains(point)
				|| bottomBar.contains(point)
				|| leftBar.contains(point)
				|| rightBar.contains(point);
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
			setCursor(GuiProperties.MOVE_CURSOR);
		}
		if (couldDragBefore && !canDrag) {
			setCursor(oldCursor);
		}
		couldDragBefore = canDrag;
	}
}