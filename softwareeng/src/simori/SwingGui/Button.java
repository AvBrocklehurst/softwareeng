package simori.SwingGui;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * A {@link simori.SwingGui.PressableCircle} with text.
 * The string is drawn on one line, scaled to fit.
 * @author Matt
 * @version 1.4.3
 */
public class Button extends PressableCircle {
	
	private String text;
	private int textX, textY; //Where to draw text
	private boolean resized;  //Whether to re-evaluate text size and position
	
	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawText(g);
	}
	
	/** {@inheritDoc} */
	@Override
	protected void resized() {
		super.resized();
		resized = true; //Resize and reposition text on next draw
	}
	
	/**
	 * Draws the {@link #text} on to the button.
	 * Uses the font and text colour defined in
	 * {@link simori.SwingGui.GuiProperties}, and the
	 * ({@link #textX}, {@link #textY}) coordinates
	 * calculated by 
	 * @param g The graphics context to use
	 */
	private void drawText(Graphics g) {
		g.setFont(GuiProperties.getFont());
		g.setColor(GuiProperties.BUTTON_TEXT);
		if (resized) updateSize(g);
		g.drawString(text, textX, textY);
	}
	
	private void updateSize(Graphics g) {
		int space = calculateSpace();
		GuiProperties.sizeFontTo(text, space, space, g);
		placeText(g);
	}
	
	private int calculateSpace() {
		double squared = Math.pow((double) getWidth(), 2d);
		double rootHalfSquared = Math.sqrt(squared / 2d);
		return (int) rootHalfSquared;
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