package simori.SwingGui;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

import simori.SimoriGui;
import simori.SimoriGui.SplashScreen;

/**
 * Swing / AWT implementation of the splash screen for a Simori-ON.
 * Features a static image with an indeterminate progress bar.
 * The splash can optionally be moved by clicking and dragging.
 * @author Matt
 * @version 2.0.2
 */
public class SplashJWindow extends JWindow implements SplashScreen {
	
	private long appeared; // Records system time splash became visible
	
	/** Immediately displays a draggable splash screen */
	public SplashJWindow() {
		this(true); // Splash moveable by default
	}
	
	/**
	 * Creates and immediately displays a splash screen.
	 * Optional click-and-drag to move functionality.
	 * @param moveable true to enable click and drag
	 */
	public SplashJWindow(boolean moveable) {
		setUpWindow();
		if (moveable) {
			DragFunctionality drag = new DragFunctionality();
			addMouseMotionListener(drag);
			addMouseListener(drag);
			setCursor(GuiProperties.MOVE_CURSOR);
		}
		setVisible(true);
		appeared = System.currentTimeMillis();
	}
	
	/** {@inheritDoc} */
	@Override
	public void swapFor(SimoriGui gui, int after) {
		long elapsed = System.currentTimeMillis() - appeared;
		if (elapsed >= after) {
			swap(gui); // Swap immediately as GUI took a long time to be ready
			return;
		}
		swapLater(gui, (int) (after - elapsed)); // Otherwise wait a bit
	}
	
	/**
	 * Calls {@link #swap} with the given {@link SimoriGui},
	 * after the specified number of milliseconds.
	 */
	private void swapLater(final SimoriGui gui, int delay) {
		Timer timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SplashJWindow.this.swap(gui);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Disposes of splash screen and shows GUI instead.
	 * If the GUI is a Java Swing Window, its location
	 * is set relative to the splash screen, so that it
	 * opens roughly where the splash was dragged to.
	 * @param gui to display after this splash screen
	 */
	private void swap(SimoriGui gui) {
		if (gui instanceof Window) { // Is gui a Swing implementation?
			// If so, it can easily be made to open in the same location
			((Window) gui).setLocationRelativeTo(this);
		}
		gui.setVisible(true);
		setVisible(false);
		dispose();
	}
	
	/** Adds components and customises the look of the window. */
	private void setUpWindow() {
		SplashImage img = new SplashImage();
		JProgressBar bar = makeProgressBar(img);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		add(img);
		add(bar);
		pack();
		setBackground(GuiProperties.SPLASH_BACKGROUND);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Creates the progress bar to display under the image.
	 * @see GuiProperties#SPLASH_BAR_PROPORTION
	 * @param img to display a progress bar at the bottom of
	 * @return Indeterminate progress bar of the correct size
	 */
	private JProgressBar makeProgressBar(SplashImage img) {
		JProgressBar bar = new JProgressBar();
		float width = img.getWidth();
		float prop = GuiProperties.SPLASH_BAR_PROPORTION;
		float height = (float) img.getHeight() * prop;
		bar.setSize((int) width, (int) height);
		bar.setIndeterminate(true);
		return bar;
	}
	
	/**
	 * Listens for mouse press and drag events and updates the JWindow
	 * location accordingly, encapsulating the click and drag functionality.
	 */
	private class DragFunctionality extends MouseAdapter {
		
		private int startX, startY; // Record drag start position
		
		/** {@inheritDoc} */
		@Override
		public void mousePressed(MouseEvent e) {
			startX = e.getX();
			startY = e.getY();
		}
		
		/** {@inheritDoc} */
		@Override
		public void mouseDragged(MouseEvent e) {
			Point l = getLocation();
			int x = l.x + e.getX() - startX;
			int y = l.y + e.getY() - startY;
			SplashJWindow.this.setLocation(x, y); // Move window
		}
	}
}