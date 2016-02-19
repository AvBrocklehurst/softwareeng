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

import javax.swing.JComponent;

import simori.SwingGui.OnPressListenerMaker.OnPressListener;

public class PressableCircle extends JComponent implements MouseListener {
	
	protected boolean pushed, mouseOver;	
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
		setCursor(GuiProperties.HAND_CURSOR);
		listeners = new ArrayList<OnPressListener>();
	}
	
	protected void resized() {
		int min = Math.min(getWidth(), getHeight());
		setSize(min, min);
	}
	
	public void addOnPressListener(OnPressListener l) {
		listeners.add(l);
	}
	
	public boolean removeOnPressListener(OnPressListener l) {
		return listeners.remove(l);
	}
	
	protected Color getFillColour() {
		return pushed ? GuiProperties.CIRCLE_PRESSED :
						GuiProperties.CIRCLE_NOT_PRESSED;
	}
	
	protected Color getBorderColour() {
		return GuiProperties.CIRCLE_BORDER;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getFillColour());
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
	}
	
	@Override
	public void paintBorder(Graphics g) {
		g.setColor(getBorderColour());
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
		if (mouseOver) { //Mouse button pressed inside this LED
			pushed = true;
			pressed();
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (pushed) {
			pushed = false;
			repaint(); //Redraw, no longer with 'IN' colour
		}
	}
	
	/** Informs the registered {@link OnPressListener} of a press */
	protected void pressed() {
		for (OnPressListener l : listeners) {
			l.onPress(this);
		}
	}
}
