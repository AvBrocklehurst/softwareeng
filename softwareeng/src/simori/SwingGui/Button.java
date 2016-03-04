package simori.SwingGui;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * A {@link PressableCircle} with text.
 * The string is drawn on one line, scaled to fit.
 * @author Matt
 * @version 1.4.5
 */
public class Button extends PressableCircle {
	
	private String text = "";
	private int textX, textY; //Where to draw text
	private boolean resized;  //Whether to re-evaluate text size and position
	
	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawText(g); //Draw text in addition to superclass behaviour
	}
	
	/** {@inheritDoc} */
	@Override
	protected void resized() {
		super.resized();
		resized = true; //Resize and reposition text on next draw
	}
	
	/**
	 * Draws the {@link #text} on to the button.
	 * @param g The graphics context to use
	 */
	private void drawText(Graphics g) {
		g.setFont(GuiProperties.getFont());
		g.setColor(GuiProperties.BUTTON_TEXT);
		if (resized) updateSize(g);
		g.drawString(text, textX, textY);
	}
	
	/**
	 * Calculates the size and position the {@link #text}
	 * should be drawn so that it all fits, centred within
	 * the circle, with as large a font size as possible. 
	 * @param g The graphics context with the font to use
	 */
	private void updateSize(Graphics g) {
		int space = calculateSpace();
		GuiProperties.sizeFontTo(text, space, space, g);
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
}