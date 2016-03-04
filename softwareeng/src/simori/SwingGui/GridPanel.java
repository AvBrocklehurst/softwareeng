package simori.SwingGui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import simori.SimoriGui.KeyboardMapping;

public class GridPanel extends JPanel {
	
	private static final String LEDS = "LedPanel";
	private static final String KEYBOARD = "Keyboard";
	
	private CardLayout layout;
	private LedPanel ledPanel;
	private JPanel keyboard;
	
	public GridPanel(KeyboardMapping map, OnPressListenerMaker maker) {
		makeKeyboard(map, maker);
		ledPanel = new LedPanel(map.getRows(), map.getColumns(), maker);
		layout = new CardLayout(0, 0);
		setLayout(layout);
		add(keyboard, KEYBOARD);
		add(ledPanel, LEDS);
		setKeyboardShown(false);
	}
	
	public void setKeyboardShown(boolean shown) {
		layout.show(this, shown ? KEYBOARD : LEDS);
	}
	
	public void setGrid(boolean[][] grid) {
		ledPanel.setGrid(grid);
	}
	
	private void makeKeyboard(KeyboardMapping map,
			OnPressListenerMaker maker) {
		int rows = map.getRows();
		int columns = map.getColumns();
		keyboard = new JPanel(new GridLayout(rows, columns, 0, 0));
		keyboard.setBackground(GuiProperties.LED_PANEL_BACKGROUND);
		Color border = GuiProperties.LED_PANEL_BORDER;
		keyboard.setBorder(BorderFactory.createLineBorder(border));
		
		/* y is decremented each time, because Buttons are added from the
		top left corner but should be numbered from the bottom left */
		for (byte y = (byte) (rows - 1); y >= 0; y--) {
			for (byte x = 0; x < columns; x++) {
				Button btn = new Button();
				keyboard.add(btn);
				btn.addOnPressListener(maker.getListener(x, y));
				Character letter = map.getLetterOn(x, y);
				if (letter == null) continue; //TODO grey out the button
				String text = letter.toString();
				if (letter == '\b') text = "<-";
				btn.setText(text);
			}
		}
	}
}
