package simori.SwingGui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import simori.FunctionButton;

public class SimoriEdgeBar extends JPanel {
	
	private Button[] buttons;
	private Lcd lcd;
	
	public SimoriEdgeBar(boolean vertical, boolean hasLcd,
			OnPressListenerMaker maker, FunctionButton... fbs) {
		int axis = vertical ? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS;
		setOpaque(false);
		if (fbs == null) return;
		BoxLayout layout = new BoxLayout(this, axis);
		setLayout(layout);
		addComponents(vertical, hasLcd, maker, fbs);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateSize();
			}
		});
	}
	
	private void addComponents(boolean vertical, boolean hasLcd,
			OnPressListenerMaker maker, FunctionButton[] fbs) {
		if (hasLcd) {
			add(Box.createGlue());
			lcd = new Lcd(vertical);
			add(lcd);
		}
		add(Box.createGlue());
		addButtons(fbs, maker);
	}
	
	private void addButtons(FunctionButton[] fbs,
			OnPressListenerMaker maker) {
		buttons = new Button[fbs.length];
		add(Box.createGlue());
		for (int i = 0; i < fbs.length; i++) {
			if (fbs[i] == null) continue;
			buttons[i] = makeButtonFor(fbs[i], maker);
			add(buttons[i]);
			add(Box.createGlue());
		}
		add(Box.createGlue());
	}
	
	private Button makeButtonFor(FunctionButton fb,
			OnPressListenerMaker maker) {
		Button b = new Button();
		b.setText(fb.buttonName());
		b.setToolTipText(fb.toolTip());
		b.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b.setOnPressListener(maker.getListener(fb));
		return b;
	}
	
	private void updateSize() {
		float min = Math.min(getWidth(), getHeight());
		int ratio = (int) (min * 5f / 6f);
		Dimension bSize = new Dimension(ratio, ratio);
		for (Button b : buttons) {
			b.setPreferredSize(bSize);
			b.setMaximumSize(bSize);
			b.setMinimumSize(bSize);
		}
		if (lcd != null) lcd.setShorterSize(ratio);
	}
	
	public Lcd getLcd() {
		return lcd;
	}
}
