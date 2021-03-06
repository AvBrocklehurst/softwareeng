package simori.SwingGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToggleButton;

import simori.Exceptions.SimoriNonFatalException;
import simori.SwingGui.OnPressListenerMaker.OnPressListener;

/**
 * Custom JComponent implementing the common behaviour of LEDs
 * and circular buttons which can be pressed. Does not extend
 * {@link JButton} or {@link JToggleButton} because the desired
 * click behaviour is different. The {@link OnPressListener}s
 * are notified immediately on mouse down inside the circular
 * area, instead of on mouse button release. Features the hand
 * cursor to indicate that it can be clicked on. Can be enabled
 * and disabled using {@link #setGreyedOut}.
 * @author Matt
 * @version 1.6.4
 */
public abstract class PressableCircle
		extends JComponent implements MouseListener {
	
	protected boolean pushed, mouseOver;
	private boolean greyedOut = true;	
	private ArrayList<OnPressListener> listeners;
	private Shape hitbox;
	
	public PressableCircle() {
		addMouseListener(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resized();
			}
		});
		listeners = new ArrayList<OnPressListener>();
		setGreyedOut(false);
	}
	
	/** @param greyed true to draw greyed out and disable presses */
	public void setGreyedOut(boolean greyed) {
		if (greyedOut == greyed) return;
		this.greyedOut = greyed;
		setCursor(greyed ? GuiProperties.NORMAL_CURSOR :
								GuiProperties.HAND_CURSOR);
		repaint();
	}
	
	/** @return true if greyed out */
	public boolean isGreyedOut() {
		return greyedOut;
	}
	
	/**
	 * Called when the circle is resized.
	 * Enforces the circular shape by equating width and height.
	 * Protected so that subclasses can add further resizing behaviour.
	 */
	protected void resized() {
		int min = Math.min(getWidth(), getHeight());
		setSize(min, min);
		repaint();
	}
	
	/**
	 * {@link OnPressListener}s receive a callback when the circle is pressed.
	 * See {@link PressableCircle} for the definition of a press.
	 * @param l A listener to register
	 */
	public void addOnPressListener(OnPressListener l) {
		listeners.add(l);
	}  
	
	/**
	 * Unregisters an {@link OnPressListener}.
	 * @param l the listener to remove
	 * @return true if the listener was removed
	 */
	public boolean removeOnPressListener(OnPressListener l) {
		return listeners.remove(l);
	}
	
	/** @return Colour to fill the circular area */
	protected Color getFillColour() {
		if (greyedOut) return GuiProperties.CIRCLE_GREYED;
		return pushed ? GuiProperties.CIRCLE_PRESSED :
						GuiProperties.CIRCLE_NOT_PRESSED;
	}
	
	/** Fills the circular area using the {@link Graphics} provided */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getFillColour());
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
	}
	
	/** Draws the circular outline using the {@link Graphics} provided */
	@Override
	public void paintBorder(Graphics g) {
		g.setColor(GuiProperties.CIRCLE_BORDER);
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
	
	/** Not used, as click behaviour is different */
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	/** {@inheritDoc} */
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOver = true;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(MouseEvent e) {
		mouseOver = false;
		
		if (pushed) {
			pushed = false;
			repaint(); //Redraw, no longer with 'IN' colour
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(MouseEvent e){
		if (mouseOver) { //Mouse button pressed inside this LED
			pushed = true;
			pressed();
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (pushed) {
			pushed = false;
			repaint(); //Redraw, no longer with 'IN' colour
		}
	}
	
	/** Informs the registered {@link OnPressListener}s of a press */
	protected void pressed() {
		if (greyedOut) return;
		for (OnPressListener l : listeners) {
			l.onPress(this);
		}
	}
}
