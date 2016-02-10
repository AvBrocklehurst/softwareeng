package simori;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

/**
 * Custom JComponent to represent illuminated LED buttons.
 * Does not extend JButton or JToggleButton because
 * the desired click behaviour is different.
 * The {@link OnPressListener} is notified immediately on mouse
 * down inside the LED area, instead of on mouse button release.
 * Furthermore, if mouse down occurs inside another LED and the
 * mouse moves into the current LED, both fire their events.
 * This produces an enjoyable user experience similar
 * to running one's finger down the keys of a piano.
 * Illumination is toggled manually using {@link #setIlluminated}.
 * @author Matt
 * @version 1.4.1
 */
public class Led extends JComponent implements MouseListener {
	
	//TODO Control resizing to keep width and height the same
	
	/* 
	 * ON is the illuminated colour, OFF the default
	 * IN colours are darkened versions to represent pressed states
	 */
	private static final Color ON = new Color(0xFF6600);
	private static final Color ON_IN = new Color(0xCC5200);
	private static final Color OFF = new Color(0xFFFFFF);
	private static final Color OFF_IN = new Color(0xEEEEEE);
	private static final Color BORDER = new Color(0x000000);
	
	/*
	 * Having this field static very conveniently produces the
	 * intentional click and drag to activate multiple LEDs behaviour.
	 */
	private static boolean mouseDown;
	
	private boolean pushed, lit, mouseOver;
	private OnPressListener listener;
	private Shape hitbox;
	
	public Led() {
		//Manual click handling requires subscribing to mouse events
		addMouseListener(this);
	}
	
	/**
	 * Allows a single {@link OnPressListener} to be registered.
	 * The listener will receive a callback when this LED is pressed.
	 * @param l
	 */
	public void setOnPressListener(OnPressListener l) {
		listener = l;
	}
	
	/**
	 * @param on Whether LED should be illuminated
	 */
	public void setIlluminated(boolean on) {
		if (lit != on) { //Don't redraw unless new state is different
			lit = on;
			repaint(); //Redraw after any visual change
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//Draw a filled circle in the colour corresponding to the state
		Color colour = lit ? (pushed ? ON_IN : ON) : (pushed ? OFF_IN : OFF);
		g.setColor(colour);
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		if (lit) return; //Diagram in spec has no outline on lit LEDs
		g.setColor(BORDER);
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}
	
	/** @return true if given coordinates lie inside the LED's circular area */
	@Override
	public boolean contains(int x, int y) {
		//Update hitbox shape if component is resized
		if (hitbox == null || !hitbox.getBounds().equals(getBounds())) {
			hitbox = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return hitbox.contains(x, y);
	}

	/** Not used, as LED click behaviour is different */
	@Override
	public void mouseClicked(MouseEvent e) {}	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOver = true;
		
		//Mouse pressed in an LED and entered this LED
		if (mouseDown) {
			pushed = true;
			pressed();
			repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseOver = false;
		
		if (pushed) {
			pushed = false;
			repaint(); //Redraw, no longer with 'IN' colour
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		
		if (mouseOver) { //Mouse button pressed inside this LED
			pushed = true;
			pressed();
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		
		if (pushed) {
			pushed = false;
			repaint(); //Redraw, no longer with 'IN' colour
		}
		/*
		 * FIXME Very hard to notice, but buttons won't redraw as no
		 * 		 longer pressed if they're the last of a click and drag
		 */
	}
	
	/** Informs the registered {@link OnPressListener} of a press */
	private void pressed() {
		if (listener != null) listener.onPress(this);
	}
	
	/** Callback interface for notification upon LED press */
	public interface OnPressListener {
		public void onPress(Led led);
	}
}
