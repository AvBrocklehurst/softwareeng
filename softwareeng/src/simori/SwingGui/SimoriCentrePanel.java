package simori.SwingGui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import simori.Simori.PowerTogglable;
import simori.SimoriGui.KeyboardMapping;

/**
 * {@link JPanel} displayed in the centre of {@link SimoriPanel}.
 * Constructs a {@link LedPanel}, a keyboard (panel of {@link Button}s)
 * and an entirely greyed-out panel of buttons. Uses {@link CardLayout}
 * to display one of these at a time.
 * @author Matt
 * @version 1.2.0
 */
public class SimoriCentrePanel extends JPanel implements PowerTogglable {
	
	// Keys to identify child JPanels
	private static final String LEDS = "LedPanel";
	private static final String GREYED = "Greyed";
	private static final String KEYBOARD = "Keyboard";
	
	// Layout and children
	private CardLayout layout;
	protected LedPanel ledPanel;
	private JPanel keyboard;
	private JPanel greyed;
	
	/**
	 * @param map Layout of the keyboard to construct
	 * @param maker Source of callbacks for LED / button presses
	 */
	public SimoriCentrePanel(KeyboardMapping map, OnPressListenerMaker maker) {
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
	
	/** @see SimoriJFrame#setKeyboardShown */
	public void setKeyboardShown(boolean shown) {
		layout.show(this, shown ? KEYBOARD : LEDS);
	}
	
	/** @see SimoriJFrame#setGrid */
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
		layout.show(this, GREYED); // Grey out grid when Simori-ON is off
	}
	
	/**
	 * Can be overridden to construct a different type of LedPanel.
	 * @param map Specifies the dimensions of the grid
	 * @param maker Source of callbacks for LED presses
	 * @return Newly constructed LedPanel
	 */
	protected LedPanel makeLedPanel(KeyboardMapping map,
			OnPressListenerMaker maker) {
		return new LedPanel(map.getRows(), map.getColumns(), maker);
	}
	
	/**
	 * Creates a panel of buttons representing the keyboard specified by the
	 * given mapping. Buttons which map to a character display that character,
	 * and buttons for which there is no character are greyed out. If a
	 * button's character is the backspace character, its text is set to "<-".
	 * @param map Layout of keyboard to create
	 * @param maker Source of callbacks for button presses
	 * @return Grid of buttons representing the given keyboard
	 */
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
	
	/**
	 * Creates a KeyboardMapping of the same grid dimensions as the given
	 * mapping, but which returns a null character for every coordinate,
	 * causing a keyboard created from it with {@link #makeKeyboard} to be
	 * an entire grid of greyed out buttons.
	 */
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
