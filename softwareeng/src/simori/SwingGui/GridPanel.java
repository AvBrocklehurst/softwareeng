package simori.SwingGui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import simori.Simori.PowerTogglable;
import simori.SimoriGui.KeyboardMapping;

public class GridPanel extends JPanel implements PowerTogglable {
	
	private static final String LEDS = "LedPanel";
	private static final String GREYED = "Greyed";
	private static final String KEYBOARD = "Keyboard";
	
	private CardLayout layout;
	protected LedPanel ledPanel;
	private JPanel keyboard;
	private JPanel greyed;
	
	public GridPanel(KeyboardMapping map, OnPressListenerMaker maker) {
		ledPanel = makeLedPanel(map, maker);
		keyboard = makeKeyboard(map, maker);
		greyed = makeKeyboard(getGreyMap(map), maker);
		layout = new CardLayout(0, 0);
		setLayout(layout);
		add(keyboard, KEYBOARD);
		add(greyed, GREYED);
		add(ledPanel, LEDS);
		setKeyboardShown(false);
	}
	
	protected LedPanel makeLedPanel(KeyboardMapping map, OnPressListenerMaker maker) {
		return new LedPanel(map.getRows(), map.getColumns(), maker);
	}
	
	public void setKeyboardShown(boolean shown) {
		layout.show(this, shown ? KEYBOARD : LEDS);
	}
	
	public void setGrid(boolean[][] grid) {
		ledPanel.setGrid(grid);
	}
	
	/** {@inheritDoc} */
	@Override
	public void switchOn() {
		layout.show(this, LEDS);
	}

	/** {@inheritDoc} */
	@Override
	public void switchOff() {
		layout.show(this, GREYED);
	}
	
	private JPanel makeKeyboard(KeyboardMapping map,
			OnPressListenerMaker maker) {
		int rows = map.getRows();
		int columns = map.getColumns();
		JPanel keyboard = new JPanel(new GridLayout(rows, columns, 0, 0));
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
				btn.setGreyedOut(letter == null);
				if (letter == null) continue;
				String text = letter.toString();
				if (letter == '\b') text = "<-";
				btn.setText(text);
			}
		}
		return keyboard;
	}
	
	private KeyboardMapping getGreyMap(final KeyboardMapping map) {
		return new KeyboardMapping() {
			@Override
			public byte getRows() {
				return map.getRows();
			}
			@Override
			public byte getColumns() {
				return map.getColumns();
			}
			@Override
			public Character getLetterOn(byte x, byte y) {
				return null;
			}
		};
	}
}
