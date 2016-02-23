package simori.SwingGui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * A custom JPanel in the rounded rectangular shape of a Simori-ON.
 * @author Matt
 * @version 1.0.0
 */
public class SimoriPanel extends JPanel {
	
	/**
	 * Returns a copy of {@link #getBounds} with its
	 * width and height reduced by two pixels. This prevents
	 * the border from drawing outside the region of the window.
	 * @return The bounds to use for drawing
	 */
	private Rectangle getClippedBounds() {
		Rectangle b = getBounds();
		return new Rectangle(b.x+1, b.y+1, b.width-2, b.height-2);
	}
	
	/** @return The rounded corners' arc size (in either dimension) */
	private int getArc() {
		float min = Math.min(getBounds().width, getBounds().height);
		return (int) (min * GuiProperties.ARC_PROPORTION);
	}
	
	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(GuiProperties.SIMORI_BACKGROUND);
		Rectangle b = getClippedBounds();
		int arc = getArc();
		g.fillRoundRect(b.x, b.y, b.width, b.height, arc, arc);
	}
	
	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		g.setColor(GuiProperties.SIMORI_BORDER);
		Rectangle b = getClippedBounds();
		int arc = getArc();
		g.drawRoundRect(b.x, b.y, b.width, b.height, arc, arc);
	}
}
