package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.KeyboardMapping;
import simori.Modes.ChangerMode.Changer;
import simori.Modes.ChangerMode.Setting;

/**
 * Allows {@link ChangerMode} to be used for text entry.
 * Takes care of displaying a {@link QwertyKeyboard} and
 * accepting a character at a time until the OK button is
 * pressed, and calls {@link #useText} with the result.
 * @author Matt
 * @version 1.0.0
 */
public abstract class TextEntry implements Changer {
	
	/** The maximum number of characters which may be entered */
	private static final int MAX_LENGTH = 20;
	
	private ModeController controller;
	private KeyboardMapping keyboard;
	
	private StringBuilder builder; // For the characters being entered
	
	public TextEntry(ModeController controller) {
		this.controller = controller;
		builder = new StringBuilder("");
	}
	
	protected abstract boolean useText(String text);

	@Override
	public String getText(Setting s) {
		addLetter(keyboard.getLetterOn(s.x, s.y));
		return builder.toString();
	}

	@Override
	public boolean doThingTo(ModeController controller) {
		if (useText(builder.toString())) {
			controller.getGui().setKeyboardShown(false);
			return true;
		}
		return false;
	}

	@Override
	public Setting getCurrentSetting() {
		controller.getGui().setKeyboardShown(true);
		keyboard = controller.getGui().getKeyboardMapping();
		return null;
	}
	
	/**
	 * Appends the given Character to the given String.
	 * If it is null, the String is not changed.
	 * If it is a backspace character, a character is removed.
	 */
	private void addLetter(Character letter) {
		if (letter == null) return;
		if (letter == '\b') {
			if (builder.length() == 0) return;
			builder.deleteCharAt(builder.length() - 1);
		} else {
			if (builder.length() == MAX_LENGTH) return;
			builder.append(letter);
		}
	}
}