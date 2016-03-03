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
import java.awt.GridLayout;
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
 * Uses a mixture of Swing components and AWT layouts.
 * This is a thin / dumb GUI in that it forwards all
 * inputs to the {@link Mode} to handle the logic.
 * Button presses are reported as events, and LEDs are
 * set using {@link #setPattern}. There is currently no
 * way to toggle a single {@link LED} on or off.
 * Also features a {@link Lcd} which can be used to
 * display text using {@link #setText}. The {@link JFrame}
 * rendered is undecorated, but can be moved around the
 * screen by clicking and dragging. It is initially
 * positioned in the centre, and scaled to take up a certain
 * proportion of the screen size. Due to Swing/AWT limitations,
 * there is no way for the user to resize the frame, as the
 * aspect ratio would have to be maintained. The window can
 * be exited and the Simori-ON closed by pressing the key
 * corresponding to {@link GuiProperties#EXIT_KEY}.
 * 
 * @see GuiProperties
 * @see simori.ModeController
 * @author Matt
 * @version 2.3.5
 */
public class SimoriJFrame extends JFrame implements SimoriGui, MouseMotionListener {
	
	//Listeners for communication with Mode
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	//Size and layout information
	private Dimension simoriSize, sideBarSize, topBarSize;
	private int rows, columns;
	
	//Components
	private SimoriPanel simoriPanel;
	private LedPanel ledPanel;
	private SimoriEdgeBar leftBar, rightBar;
	private SimoriEdgeBar topBar, bottomBar;
	private Lcd lcd;
	
	//Dragging state information
	private int startX, startY;
	private boolean couldDragBefore;
	private Cursor oldCursor;
	
	//For keyboard
	private JPanel keyboard;
	private Button[][] keys;
	
	/**
	 * Creates a visual representation of a Simori-ON
	 * with an LED grid of the specified dimensions.
	 * @param rows Number of LEDs the grid is wide
	 * @param columns Number of LEDs the grid is tall
	 */
	public SimoriJFrame(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		setUpWindow();
		addMouseMotionListener(this);  
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//Remember starting coordinates for mouse drags
				startX = e.getX();
				startY = e.getY();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//Terminate if the exit key was pressed
				if (e.getKeyCode() == GuiProperties.EXIT_KEY) System.exit(0);
			}
		});
	}
	
	/** {@inheritDoc} */
	@Override
	public void setGrid(boolean[][] grid) {
		ledPanel.setGrid(grid);
	}
	
	/** {@inheritDoc} */
	@Override
	public void clearGrid() {
		setGrid(new boolean[rows][columns]);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setText(String text) {
		lcd.setText(text);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setGridButtonListener(GridButtonListener l) {
		gListener = l;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setFunctionButtonListener(FunctionButtonListener l) {
		fListener = l;
	}
	
	/** @return The registered {@link GridButtonListener} */
	public GridButtonListener getGridButtonListener() {
		return gListener;
	}
	
	/** @return The registered {@link FunctionButtonListener} */
	public FunctionButtonListener getFunctionButtonListener() {
		return fListener;
	}
	
	//TODO javadoc
	public boolean setKeyboard(KeyboardMapping map) {
		if (map == null) {
			simoriPanel.remove(keyboard);
			simoriPanel.add(ledPanel, BorderLayout.CENTER);
		} else {
			if (!makeKeyboard(map)) return false;
			simoriPanel.remove(ledPanel);
			simoriPanel.add(keyboard, BorderLayout.CENTER);
		}
		return true;
	}
	
	//TODO javadoc
	private boolean makeKeyboard(KeyboardMapping map) {
		if (map.getRows() != rows || map.getColumns() != columns) return false;
		keyboard = new JPanel(new GridLayout(rows, columns, 0, 0));
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		keys = new Button[rows][columns];
		
		/* y is decremented each time, because Buttons are added from the
		   top left corner but should be numbered from the bottom left */
		for (byte y = (byte) (rows - 1); y >= 0; y--) {
			for (byte x = 0; x < columns; x++) {
				Button btn = new Button();
				keyboard.add(btn);
				btn.addOnPressListener(maker.getListener(x, y));
				btn.setText(String.valueOf(map.getLetterOn(x, y)));
				keys[x][y] = btn;
			}
		}
		return true;
	}
	
	/**
	 * Sets the desired properties of the {@link JFrame}.
	 * These include the window icon, title, close operation
	 * and background colour. Populates the frame with
	 * the required components.
	 */
	private void setUpWindow() {
		setIconImage(GuiProperties.getIcon());
		setTitle(GuiProperties.WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true); //Remove operating system's bar from the top
		setBackground(GuiProperties.WINDOW_BACKGROUND);
		addComponents();
		sortSizes();
	}
	
	/**
	 * Assembles the GUI, adding the hierarchy of {@link JPanel}s
	 * which make up the visual representation of the Simori-ON
	 * to the frame. Uses {@link BorderLayout} to manage positioning.
	 */
	private void addComponents() {
		makeComponents();
		simoriPanel = new SimoriPanel(); //TODO Maybe do the following in SimoriPanel?
		simoriPanel.setLayout(new BorderLayout(0, 0));
		simoriPanel.add(topBar, BorderLayout.PAGE_START);
		simoriPanel.add(leftBar, BorderLayout.LINE_START);
		simoriPanel.add(ledPanel, BorderLayout.CENTER);
		simoriPanel.add(rightBar, BorderLayout.LINE_END);
		simoriPanel.add(bottomBar, BorderLayout.PAGE_END);
		add(simoriPanel);
	}  
	
	/**
	 * Constructs the {@link JPanel}s and other components
	 * which make up the Simori-ON GUI.
	 */
	private void makeComponents() {
		OnPressListenerMaker maker = new OnPressListenerMaker(this);
		topBar = new SimoriEdgeBar(false, false, maker, ON);
		leftBar = new SimoriEdgeBar(true, false, maker, L1, L2, L3, L4);
		ledPanel = new LedPanel(rows, columns, maker);
		rightBar = new SimoriEdgeBar(true, false, maker, R1, R2, R3, R4);
		bottomBar = new SimoriEdgeBar(false, true, maker, OK);
		lcd = bottomBar.getLcd();
	}
	
	/**
	 * Sizes the various components of the Simori-ON GUI appropriately.
	 * The {@link JFrame} size is locked at a constant proportion of the
	 * screen size, and the window centred in the remaining screen space.
	 */
	private void sortSizes() {
		calculateDimensions();
		getContentPane().setPreferredSize(simoriSize);
		leftBar.setPreferredSize(sideBarSize);
		rightBar.setPreferredSize(sideBarSize);
		topBar.setPreferredSize(topBarSize);
		bottomBar.setPreferredSize(topBarSize);
		pack(); //Update window size to fit changes
		setResizable(false); //Disable manual resizing
		setLocationRelativeTo(null); //Move to middle of screen
	}
	
	/**
	 * Calculates the sizes of the top-level components based on
	 * the screen size and the proportions defined in {@link GuiProperties}.
	 */
	private void calculateDimensions() {
		Dimension s = getToolkit().getScreenSize();
		s.width = s.height = Math.min(s.width, s.height) + 2;
		simoriSize = GuiProperties.ratioOf(SCREEN_PROPORTION, SCREEN_PROPORTION, s);
		
		//BorderLayout means that side bars are smaller than top bars
		sideBarSize = GuiProperties.ratioOf(0.1f, 0.8f, simoriSize);
		topBarSize = GuiProperties.ratioOf(1f, 0.1f, simoriSize);
	}
	
	/**
	 * Determines whether the Simori-ON window can be moved by
	 * clicking and dragging from the specified point.
	 * @param point A location in the window
	 * @return true if the window can be dragged from this point
	 */
	private boolean canDragFrom(Point point) {
		return topBar.contains(point)
				|| bottomBar.contains(point)
				|| leftBar.contains(point)
				|| rightBar.contains(point);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (canDragFrom(e.getPoint())) {
			//Update window location on screen based on mouse drag displacement
			Point l = getLocation();
			setLocation(l.x + e.getX() - startX, l.y + e.getY() - startY);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(MouseEvent e) {
		//Updates the mouse cursor to indicate whether dragging is available
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