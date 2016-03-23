package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.KeyboardMapping;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.ChangerMode.Changer;
import simori.Modes.ChangerMode.Setting;

/**
 * Allows {@link ChangerMode} to be used for text entry.
 * Takes care of displaying a {@link QwertyKeyboard} and
 * accepting a character at a time until the OK button is
 * pressed, and calls {@link #useText} with the result.
 * @author Matt
 * @version 2.0.0
 */
public abstract class TextEntry implements Changer {
	
	/** The maximum number of characters which may be entered */
	private static final int MAX_LENGTH = 20;
	
	private ModeController controller;
	private KeyboardMapping keyboard;
	
	protected StringBuilder builder; // For the characters being entered
	
	public TextEntry(ModeController controller) {
		this.controller = controller;
		builder = new StringBuilder("");
	}
	
	/**
	 * Called when the user has entered text and pressed OK to submit it.
	 * The text is to be validated and used for some purpose.
	 * @param text The text which has been entered before pressing OK
	 * @return true to close keyboard and exit mode, false to reject text
	 */
	protected abstract boolean useText(String text);

	/** {@inheritDoc} */
	@Override
	public String getText(Setting s) {
		addLetter(keyboard.getLetterOn(s.x, s.y));
		return builder.toString(); // Display modified text on LCD screen
	}

	/** {@inheritDoc} */
	@Override
	public boolean doThingTo(ModeController controller)  {
		if (useText(builder.toString())) {
			controller.getGui().setKeyboardShown(false);
			return true; // Re-enter PerformanceMode with keyboard hidden
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Setting getCurrentSetting() {
		controller.getGui().setKeyboardShown(true);
		keyboard = controller.getGui().getKeyboardMapping();
		return null; // Don't draw lines, as keyboard is shown instead
	}
	
	/**
	 * Appends the given Character to the sequence.
	 * If it is null, the String is not changed.
	 * If it is a backspace character, a character is removed.
	 */
	private void addLetter(Character letter) {
		if (letter == null) return;
		if (letter == '\b') {
			if (builder.length() == 0) {
				controller.sadSound();
				return;
			}
			builder.deleteCharAt(builder.length() - 1);
		} else {
			if (builder.length() == MAX_LENGTH) {
				controller.sadSound();
				return;
			}
			builder.append(letter);
		}
	}
}