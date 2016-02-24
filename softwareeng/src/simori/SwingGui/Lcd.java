package simori.SwingGui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * A JLabel with its appearance modified to represent
 * the Simori-ON's LCD screen. It sets its own text
 * size and manages its aspect ratio when resized.
 * It is flexible enough to use vertically, creating a
 * 'sideways' vertical LCD screen taller than it is high.
 * @author Matt
 * @version 1.2.0
 */
public class Lcd extends JLabel {
	
	private final boolean vertical;
	
	/**
	 * Creates a JLabel resembling a horizontal or vertical LCD screen.
	 * @param vertical false to create a horizontal LCD screen
	 */
	public Lcd(boolean vertical) {
		this.vertical = vertical;
		setBorder(BorderFactory.createLineBorder(GuiProperties.LCD_BORDER));
		addComponentListener(makeResizeListener());
	}
	
	/**
	 * Creates a ComponentListener which listens
	 * for resizing events and responds by enforcing
	 * that the LCD is {@link GuiProperties#LCD_EDGE_RATIO}
	 * times longer in one dimension.
	 * @return A ComponentListener ready to be set
	 */
	private ComponentAdapter makeResizeListener() {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (vertical) {
					//Update height to match new width
					float height = (float) getWidth() * GuiProperties.LCD_EDGE_RATIO;
					setSize(getWidth(), (int) height);
				} else {
					//Update width to match new height
					float width = (float) getHeight() * GuiProperties.LCD_EDGE_RATIO;
					setSize((int) width, getHeight());
				}
			}
		};
	}
	
	/**
	 * A way of setting the LCD screen's size by specifying
	 * only the length in the screen's shorter dimension.
	 * The length in the other dimension is set automatically.
	 * If the LCD is horizontal, shorter will be its required height.
	 * @param shorter The desired size in the shortest dimension
	 */
	public void setShorterSize(float shorter) {
		float w = vertical ? shorter : shorter * GuiProperties.LCD_EDGE_RATIO;
		float h = vertical ? shorter * GuiProperties.LCD_EDGE_RATIO : shorter;
		Dimension size = new Dimension((int) w, (int) h);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		makeFontFit(size);
	}
	
	/**
	 * Retrieves the desired font from {@link #GuiProperties}
	 * and resizes it to fit a test string.
	 * @param size The width and height of the LCD screen
	 */
	private void makeFontFit(Dimension size) {
		final String test = "gG"; //With tall letter and hanging letter
		setFont(GuiProperties.getFont());
		Graphics g = getComponentGraphics(getGraphics());
		g.setFont(getFont());
		GuiProperties.sizeFontTo(test, size.width, size.height, g);
		setFont(g.getFont());
	}
	
	/** {@inheritDoc} */
	@Override
	public void setText(String text) {
		//Add a leading space to pad text away from left edge
		if (text != null) text = " " + text;
		super.setText(text);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getText() {
		//Remove the leading space added by setText
		String text = super.getText();
		if (text != null) text = text.substring(1);
		return text;
	}
}
