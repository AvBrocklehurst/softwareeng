package simori.SwingGui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * A {@link PressableCircle} with text.
 * The string is drawn on one line, scaled to fit.
 * @author Matt
 * @version 1.4.5
 */
public class Button extends PressableCircle {
	
	private int textX, textY; //Where to draw text
	private String text = "";
	private Dimension size;
	private Font font;
	
	/**
	 * Sets the short string to be drawn inside the button on a single line.
	 * Causes the button to redraw itself with the new string.
	 */
	public void setText(String text) {
		this.text = text;
		repaint();
	}
	
	/** @return The text displayed on this button */
	public String getText() {
		return text;
	}
	
	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawText(g); //Draw text in addition to superclass behaviour
	}
	
	/**
	 * Sets the (minimum, maximum and preferred) size of this button.
	 * Sizes and positions the text as necessary.
	 * @param size The predetermined button size
	 */
	public void setDefiniteSize(Dimension size) {
		if (size != null && size.equals(this.size)) return;
		this.size = size;
		setSize(size);
		sizeAndPositionText();
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize() {
		return size;
	}
	
	/** {@inheritDoc} */
	@Override
	public Dimension getMinimumSize() {
		return size;
	}
	
	/**
	 * Draws the {@link #text} on to the button.
	 * @param g The graphics context to use
	 */
	private void drawText(Graphics g) {
		g.setFont(font);
		g.setColor(GuiProperties.BUTTON_TEXT);
		g.drawString(text, textX, textY);
	}
	
	/**
	 * Determines the {@link #font} size and coordinates at
	 * which to draw the {@link #text} so that it is centred
	 * and fills as much of the button's area as possible.
	 */
	private void sizeAndPositionText() {
		Graphics g = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB).createGraphics();
		g.setFont(GuiProperties.getFont());
		int space = calculateSpace();
		GuiProperties.sizeFontTo(text, space, space, g);
		font = g.getFont();
		placeText(g);
	}
	
	/**
	 * Calculates the size of the largest square contained
	 * within the button's circular area. This is the circle's
	 * diameter squared, halved and then square rooted.
	 * @return The dimension (width or height) of the square
	 */
	private int calculateSpace() {
		double squared = Math.pow((double) getWidth(), 2d);
		double rootHalfSquared = Math.sqrt(squared / 2d);
		return (int) rootHalfSquared;
	}
	
	/**
	 * Sets the values of {@link #textX} and {@link #textY}
	 * so that text drawn at those coordinates, in the font
	 * from the given graphics context will appear centred
	 * vertically and horizontally within the circle.
	 * @param g graphics context specifying the font and text size
	 */
	private void placeText(Graphics g) {
		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(text, g);
		int textWidth = (int) textBounds.getWidth();
		int textHeight = (int) textBounds.getHeight();
		int ascent = g.getFontMetrics().getAscent();
		textX = (getWidth() - textWidth) / 2 + 1;
		textY = (getHeight() - textHeight) / 2 + ascent - 1;
	}
}