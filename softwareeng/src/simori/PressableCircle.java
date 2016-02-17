package simori;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

import simori.OnPressListenerMaker.OnPressListener;

public class PressableCircle extends JComponent implements MouseListener {
	
	private static final Color OUT = new Color(0xFFFFFF);
	private static final Color IN = new Color(0xEEEEEE);
	protected static final Color BORDER = new Color(0x000000);
	
	protected boolean pushed, mouseOver;	
	private OnPressListenerMaker.OnPressListener listener;
	private Shape hitbox;
	
	public PressableCircle() {
		addMouseListener(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resized();
			}
		});
	}
	
	protected void resized() {
		int min = Math.min(getWidth(), getHeight());
		setSize(min, min);
	}
	
	public void setOnPressListener(OnPressListenerMaker.OnPressListener l) {
		listener = l;
	}
	
	protected Color getFillColour() {
		return pushed ? IN : OUT;
	}
	
	protected Color getBorderColour() {
		return BORDER;
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
		/*
		 * FIXME Very hard to notice, but buttons won't redraw as no
		 * 		 longer pressed if they're the last of a click and drag
		 */
	}
	
	/** Informs the registered {@link OnPressListener} of a press */
	protected void pressed() {
		if (listener != null) listener.onPress();
	}
}
