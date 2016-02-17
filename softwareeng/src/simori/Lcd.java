package simori;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Lcd extends JLabel {
	
	private static final float RATIO = 5f;
	
	public Lcd(final boolean vertical) {
		setFont(new Font(Font.SERIF, Font.PLAIN, 1));
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
	
	private void setShortDimension() {
		int min = Math.min(getWidth(), getHeight());
		setSize(min, min);
	}
}
