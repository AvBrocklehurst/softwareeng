package simori;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 1));
		g.setColor(TEXT);
		updateSize(g); //TODO Only if bounds has changed
		g.drawString(text, textX, textY);
	}
	
	private void updateSize(Graphics g) {
		calculateSpace();
		resizeFont(g);
		placeText(g);
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
	
	private void placeText(Graphics g) {
		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(text, g);
		int textWidth = (int) textBounds.getWidth();
		int textHeight = (int) textBounds.getHeight();
		int ascent = g.getFontMetrics().getAscent();
		textX = (getWidth() - textWidth) / 2 + 1;
		textY = (getHeight() - textHeight) / 2 + ascent - 1;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
