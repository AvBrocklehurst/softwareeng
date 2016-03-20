package simori.SwingGui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

import simori.SimoriGui;

public class SplashScreen extends JWindow implements MouseMotionListener {
	
	private int startX, startY;
	private long appeared;
	
	public SplashScreen() {
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
		setCursor(GuiProperties.MOVE_CURSOR);
		appeared = System.currentTimeMillis();
	}
	
	public void swapFor(final SimoriGui gui, int after) {
		long now = System.currentTimeMillis();
		if (now - appeared >= after) {
			setVisible(false);
			gui.setVisible(true);
		} else {
			Timer timer = new Timer(after, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SplashScreen.this.setVisible(false);
					gui.setVisible(true);
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}
	
	private void setUpWindow() {
		SplashImage img = new SplashImage();
		JProgressBar bar = makeProgressBar(img);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		add(img);
		add(bar);
		pack();
		setBackground(GuiProperties.SPLASH_BACKGROUND);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JProgressBar makeProgressBar(SplashImage img) {
		JProgressBar bar = new JProgressBar();
		float width = img.getWidth();
		float height = (float) img.getHeight() / 15f;
		bar.setSize((int) width, (int) height);
		bar.setIndeterminate(true);
		return bar;
	}
	
	/** {@inheritDoc} */
	@Override
	public void mouseDragged(MouseEvent e) {
		Point l = getLocation();
		setLocation(l.x + e.getX() - startX, l.y + e.getY() - startY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
