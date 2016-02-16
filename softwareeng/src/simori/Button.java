package simori;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Button extends JComponent implements MouseListener {
	
	private static final Color OUT = new Color(0xFFFFFF);
	private static final Color IN = new Color(0xEEEEEE);
	private static final Color TEXT = new Color(0x000000);
	protected static final Color BORDER = new Color(0x000000);
	
	private static final Dimension DEFAULT = new Dimension(50, 50);
	
	protected boolean pushed, mouseOver;
	protected String text;
	
	private OnPressListener listener;
	private Shape hitbox;
	
	public Button(OnPressListener l, String text) {
		this();
		this.text = text;
		listener = l; //TODO Just move this to a method in SimoriGui
		setPreferredSize(DEFAULT);
		setMaximumSize(DEFAULT);
		setMinimumSize(DEFAULT);
		setAlignmentX(CENTER_ALIGNMENT);
	}
	
	public Button() {
		addMouseListener(this);
	}
	
	public void setOnPressListener(OnPressListener l) {
		listener = l;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
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
		drawText(g);
	}
	
	@Override
	public void paintBorder(Graphics g) {
		g.setColor(getBorderColour());
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}
	
	protected void drawText(Graphics g) {
		Rectangle b = getBounds();
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		g.setColor(TEXT);
		g.drawString(getText(), b.x + b.height/2, b.y - b.height/2);
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
	
	/** Callback interface for notification upon LED press */
	public interface OnPressListener {
		public void onPress();
	}
}
