package simori.SwingGui;

import static simori.SwingGui.GuiProperties.SCREEN_PROPORTION;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;
import javax.swing.Timer;

import simori.Animation;
import simori.SimoriGui;
import simori.Exceptions.SimoriNonFatalException;

/**
 * Swing / AWT implementation of a Simori-ON GUI.
 * This class manages the window. Most of the implementation can be found in
 * its subcomponents, so many calls are forwarded to a {@link SimoriPanel}.
 * The {@link JFrame} rendered is undecorated, but can be moved around the
 * screen by clicking and dragging. It is initially positioned in the centre,
 * and scaled to take up a certain proportion of the screen size.
 * Due to Swing/AWT limitations, there is no way for the user to resize the
 * frame, as the aspect ratio would have to be maintained.
 * The window can be exited and the Simori-ON closed by pressing the key
 * corresponding to {@link GuiProperties#EXIT_KEY}.
 * @see GuiProperties
 * @author Matt
 * @version 2.5.0
 */
public class SimoriJFrame extends JFrame implements SimoriGui, MouseMotionListener {
	
	//Listeners for communication with Mode
	private GridButtonListener gListener;
	private FunctionButtonListener fListener;
	
	//Dragging state information
	private int startX, startY;
	protected boolean couldDragBefore;
	private Cursor oldCursor;
	
	//Size and layout information
	private Dimension simoriSize;
	private int rows, columns;
	protected KeyboardMapping mapping;
	
	//Components
	protected SimoriPanel simoriPanel;
	protected Lcd lcd;
	
	/**
	 * Creates a visual representation of a Simori-ON with an
	 * LED grid of the dimensions specified in the given mapping.
	 * @param mapping Layout of the keyboard to display for text entry
	 */
	public SimoriJFrame(KeyboardMapping mapping) {
		this.mapping = mapping;
		this.rows = mapping.getRows();
		this.columns = mapping.getColumns();
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
		simoriPanel.setGrid(grid);
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
	public String getText() {
		return lcd.getText();
	}
	
	/** {@inheritDoc} */
	public void play(final Animation toPlay) {
		final Timer timer = new Timer(300, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				boolean[][] which = toPlay.next();
					if (which == null) {
						timer.stop();
					} else {
						simoriPanel.setGreyedOut(which);
					} 
				} catch (SimoriNonFatalException ex){
					ex.printStackTrace();
				}
			}
		});
		timer.start();
	}
	
	/** {@inheritDoc} */
	@Override
	public int getGridSize() {
		return columns;
	}
	
	/** {@inheritDoc} */
	@Override
	public void ready() {
		simoriPanel.ready();
	}
	
	/** {@inheritDoc} */
	@Override
	public void switchOn() {
		simoriPanel.switchOn();
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop() {
		simoriPanel.stop();
	}
	
	/** {@inheritDoc} */
	@Override
	public void switchOff() {
		simoriPanel.switchOff();
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
	
	/** {@inheritDoc} */
	@Override
	public void setKeyboardShown(boolean shown) {
		simoriPanel.setKeyboardShown(shown);
	}
	
	/** {@inheritDoc} */
	@Override
	public KeyboardMapping getKeyboardMapping() {
		return mapping;
	}
	
	/** {@inheritDoc} */
	@Override
	public void reportError(String shortMessage, String longMessage,
								String title, OnErrorDismissListener l) {
		ErrorDialog d = new ErrorDialog(this);
		if (title != null )d.setTitle(title);
		d.setOnDismissListener(l);
		d.setShortMessage(shortMessage);
		d.setLongMessage(longMessage);
		d.setVisible(true);
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
		addSimoriPanel();
		sortSizes();
	}
	
	/**
	 * Constructs the {@link SimoriPanel}
	 * and keeps reference to its {@link Lcd}.
	 */
	protected void addSimoriPanel() {
		simoriPanel = new SimoriPanel(mapping, new OnPressListenerMaker(this));
		lcd = simoriPanel.getLcd();
		add(simoriPanel);
	}
	
	/**
	 * Sizes the various components of the Simori-ON GUI appropriately.
	 * The {@link JFrame} size is locked at a constant proportion of the
	 * screen size, and the window centred in the remaining screen space.
	 * Calculates the sizes of the top-level components based on
	 * the screen size and the proportions defined in {@link GuiProperties}.
	 */
	private void sortSizes() {
		Dimension s = getToolkit().getScreenSize();
		s.width = s.height = Math.min(s.width, s.height) + 2;
		simoriSize = GuiProperties.ratioOf(SCREEN_PROPORTION, SCREEN_PROPORTION, s);
		getContentPane().setPreferredSize(simoriSize);
		simoriPanel.setSizes(simoriSize);
		pack(); //Update window size to fit changes
		setResizable(false); //Disable manual resizing
		setLocationRelativeTo(null); //Move to middle of screen
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (simoriPanel.canDragFrom(e.getPoint())) {
			//Update window location on screen based on mouse drag displacement
			Point l = getLocation();
			setLocation(l.x + e.getX() - startX, l.y + e.getY() - startY);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(MouseEvent e) {
		//Updates the mouse cursor to indicate whether dragging is available
		boolean canDrag = simoriPanel.canDragFrom(e.getPoint());
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