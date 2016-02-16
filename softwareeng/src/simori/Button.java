package simori;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Button extends PressableCircle {
	
	private static final Color TEXT = new Color(0x000000);
	
	private String text;
	private int space;
	private int textX, textY;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawText(g);
	}
	
	private void drawText(Graphics g) {
		Rectangle b = getBounds();
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 1));
		g.setColor(TEXT);
		updateSize(g, b); //TODO Only if bounds has changed
		g.drawString(text, textX, textY);
		g.getFontMetrics().getStringBounds(text, g);
	}
	
	private void updateSize(Graphics g, Rectangle bounds) {
		calculateSpace();
		resizeFont(g);
		placeText(g.getFontMetrics(), bounds);
	}
	
	private void calculateSpace() {
		double squared = Math.pow((double) getWidth(), 2d);
		double rootHalfSquared = Math.sqrt(squared / 2d);
		space = (int) rootHalfSquared;
	}
	
	private void resizeFont(Graphics g) {
		final String name = g.getFont().getName();
		final int style = g.getFont().getStyle();
		int size = g.getFont().getSize();
		Rectangle2D b = g.getFontMetrics().getStringBounds(text, g);
		while (b.getWidth() < space && b.getHeight() < space) {
			g.setFont(new Font(name, style, size++));
			b = g.getFontMetrics().getStringBounds(text, g);
		}
		g.setFont(g.getFont().deriveFont(--size));
	}
	
	private void placeText(FontMetrics m, Rectangle bounds) {
		int spareX = space - m.stringWidth(text);
		int spareY = space - m.getAscent() + m.getDescent();
		textX = bounds.x + spareX / 2;
		textY = bounds.y - bounds.height + spareY / 2;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
