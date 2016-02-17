package simori;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class SimoriPanel extends JPanel {
	
	public static final Color BACKGROUND = new Color(0xFFFFFF);
	public static final Color BORDER = new Color(0x000000);
	
	private Rectangle getRoundedBounds() {
		Rectangle b = getBounds();
		return new Rectangle(b.x+1, b.y+1, b.width-2, b.height-2);
	}
	
	private int getArc() {
		float min = Math.min(getBounds().width, getBounds().height);
		return (int) (min * 0.05f);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(BACKGROUND);
		Rectangle b = getRoundedBounds();
		int arc = getArc();
		g.fillRoundRect(b.x, b.y, b.width, b.height, arc, arc);
	}
	
	@Override
	public void paintBorder(Graphics g) {
		g.setColor(BORDER);
		Rectangle b = getRoundedBounds();
		int arc = getArc();
		g.drawRoundRect(b.x, b.y, b.width, b.height, arc, arc);
	}
}
