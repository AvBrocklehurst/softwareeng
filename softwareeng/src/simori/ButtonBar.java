package simori;

import static simori.FunctionButton.L1;
import static simori.FunctionButton.L2;
import static simori.FunctionButton.L3;
import static simori.FunctionButton.L4;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ButtonBar {
	
	public ButtonBar(boolean vertical, FunctionButton... buttons) {
		Dimension initial = vertical ? SimoriGui.SIDEBAR : SimoriGui.TOPBAR;
		int axis = vertical ? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS;
		
		JPanel box = new JPanel();
		box.setBackground(SimoriGui.BACKGROUND);
		box.setPreferredSize(initial);
		BoxLayout layout = new BoxLayout(box, axis);
		box.setLayout(layout);
		glueButtonsTo(box, buttons);
	}
	
	private void glueButtonsTo(JPanel box, FunctionButton[] buttons) {
		box.add(Box.createGlue());
		box.add(Box.createGlue());
		for (FunctionButton fb : buttons) {
			box.add(makeButtonFor(fb));
			box.add(Box.createGlue());
		}
		box.add(Box.createGlue());
	}
	
	private Button makeButtonFor(FunctionButton fb) {
		Button button = new Button();
		button.setText(fb.buttonName());
		button.setToolTipText(fb.toolTip());
		return button;
	}
}
