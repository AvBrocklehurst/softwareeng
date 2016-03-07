package simori;

import simori.ChangerMode.Changer;
import simori.ChangerMode.Setting;
import simori.SimoriGui.KeyboardMapping;

public abstract class TextEntry implements Changer {
	
	private static final int MAX_LENGTH = 20;
	
	private ModeController controller;
	private KeyboardMapping keyboard;
	private String letters = "";
	
	public TextEntry(ModeController controller) {
		this.controller = controller;
	}
	
	protected abstract boolean useText(String text);

	@Override
	public String getText(Setting s) {
		letters = addLetter(keyboard.getLetterOn(s.x, s.y), letters);
		return letters;
	}

	@Override
	public boolean doThingTo(ModeController controller) {
		if (letters == null || letters.length() == 0) return false;
		if (useText(letters)) {
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
	 * @author Matt
	 * @author James
	 */
	private static String addLetter(Character letter, String letters) {
		if (letter == null) return letters;
		if (letter == '\b') {
			if (letters.length() == 0) return letters;
			return letters.substring(0, letters.length()-1);
		} else {
			return letters.length() == MAX_LENGTH ? letters : letters + letter;
		}
	}
}