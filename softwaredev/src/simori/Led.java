package simori;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Led extends JComponent implements MouseListener {
	
	private static final Color ON = new Color(0xFF6600);
	private static final Color ON_IN = new Color(0xCC5200);
	private static final Color OFF = new Color(0xFFFFFF);
	private static final Color OFF_IN = new Color(0xEEEEEE);
	private static final Color BORDER = new Color(0x000000);
	
	private int x, y;
	private boolean pushed, lit;
	private boolean mouseDown, mouseOver;
	
	public Led(int x, int y) {
		this.x = x;
		this.y = y;
		addMouseListener(this);
	}
	
	public void setIlluminated(boolean on) {
		if (lit != on) {
			lit = on;
			repaint();
		}
	}
	
	private void pressed() {
		System.out.println(x + ", " + y);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Color colour = lit ? (pushed ? ON_IN : ON) : (pushed ? OFF_IN : OFF);
		g.setColor(colour);
		g.fillOval(0, 0, getSize().width-1, getSize().height-1);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		g.setColor(BORDER);
		g.drawOval(0, 0, getSize().width-1, getSize().height-1);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOver = true;
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
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		if (mouseOver) {
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
			repaint();
		}
	}
}
