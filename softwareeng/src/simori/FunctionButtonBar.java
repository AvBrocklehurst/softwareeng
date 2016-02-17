package simori;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class FunctionButtonBar extends JPanel {
	
	public FunctionButtonBar(boolean vertical,
			OnPressListenerMaker maker, FunctionButton... buttons) {
		int axis = vertical ? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS;
		setBackground(SimoriGui.BACKGROUND);;
		BoxLayout layout = new BoxLayout(this, axis);
		setLayout(layout);
		addButtons(buttons, maker);
	}
	
	private void addButtons(FunctionButton[] buttons,
			OnPressListenerMaker maker) {
		add(Box.createGlue());
		add(Box.createGlue());
		for (FunctionButton fb : buttons) {
			add(makeButtonFor(fb, maker));
			add(Box.createGlue());
		}
		add(Box.createGlue());
	}
	
	private Button makeButtonFor(FunctionButton fb,
			OnPressListenerMaker maker) {
		Button button = new Button();
		button.setText(fb.buttonName());
		button.setToolTipText(fb.toolTip());
		button.setPreferredSize(SimoriGui.SIDEBUTTON);
		button.setMaximumSize(SimoriGui.SIDEBUTTON);
		button.setMinimumSize(SimoriGui.SIDEBUTTON);
		button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		button.setOnPressListener(maker.getListener(fb));
		return button;
	}
}
