package simori.SwingGui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Lcd extends JLabel {
	
	private final boolean vertical;
	
	public Lcd(boolean vertical) {
		this.vertical = vertical;
		setBorder(BorderFactory.createLineBorder(GuiProperties.LCD_BORDER));
		addComponentListener(makeResizeListener());
	}
	
	private ComponentAdapter makeResizeListener() {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (vertical) {
					float height = (float) getWidth() * GuiProperties.LCD_EDGE_RATIO;
					setSize(getWidth(), (int) height);
				} else {
					float width = (float) getHeight() * GuiProperties.LCD_EDGE_RATIO;
					setSize((int) width, getHeight());
				}
			}
		};
	}
	
	public void setShorterSize(float shorter) {
		float w = vertical ? shorter : shorter * GuiProperties.LCD_EDGE_RATIO;
		float h = vertical ? shorter * GuiProperties.LCD_EDGE_RATIO : shorter;
		Dimension size = new Dimension((int) w, (int) h);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		makeFontFit(size);
	}
	
	private void makeFontFit(Dimension size) {
		setFont(GuiProperties.getFont());
		Graphics g = getComponentGraphics(getGraphics());
		g.setFont(getFont());
		GuiProperties.sizeFontTo("gG", size.width, size.height, g);
		setFont(g.getFont());
	}
	
	@Override
	public void setText(String text) {
		if (text != null) text = " " + text;
		super.setText(text);
	}
}
