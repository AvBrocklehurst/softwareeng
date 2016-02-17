package simori;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Lcd extends JLabel {
	
	private static final float RATIO = 5f;
	
	private final boolean vertical;
	
	public Lcd(boolean vertical) {
		this.vertical = vertical;
		setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		setBorder(BorderFactory.createLineBorder(new Color(0x000000))); //FIXME hardcoded
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (vertical) {
					float height = (float) getWidth() * RATIO;
					setSize(getWidth(), (int) height);
				} else {
					float width = (float) getHeight() * RATIO;
					setSize((int) width, getHeight());
				}
			}
		});
		setText("reeeeeeeeeeeeeeeeeeeeeeeeeeeealylongteststring");
		//TODO text size
	}
	
	public void setShorterSize(float shorter) {
		float w = vertical ? shorter : shorter * RATIO;
		float h = vertical ? shorter * RATIO : shorter;
		Dimension size = new Dimension((int) w, (int) h);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}
}
