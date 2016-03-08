package simori.Modes;

import simori.SimoriGui.KeyboardMapping;
import simori.Exceptions.KeyboardException;

/**
 * Keyboard mapping implementation which mimics
 * the layout of a traditional QWERTY keyboard.
 * @author Matt
 * @version 1.4.0
 */
public class QwertyKeyboard implements KeyboardMapping {
	
	/**
	 * Defines the keyboard layout in a readable way.
	 * Each String is a row in the keyboard.
	 * Each character in a string is a letter in the row, and
	 * null is a gap between rows.
	 */
	private static final String[] ROWS =
		{"1234567890", null,
		 "QWERTYUIOP", "ASDFGHJKL\b", "ZXCVBNM_", null,
		 "qwertyuiop", "asdfghjkl\b", "zxcvbnm ", null,
		 "-+=!£$%^()", "{}[];@#~.,"}; //TODO how does this look on Windows?
	
	private Character[][] keys;
	private byte rows, columns;
	
	/**
	 * Creates a QWERTY keyboard mapping from coordinates on a button grid of
	 * the specified dimensions to rows of letters centred within the grid.
	 * @param rows Width of grid of buttons to size keyboard to
	 * @param columns Height of grid of buttons to size keyboard to
	 * @throws KeyboardException If the requested dimensions are insufficient
	 */
	public QwertyKeyboard(byte rows, byte columns) throws KeyboardException {
		this.rows = rows;
		this.columns = columns;
		keys = new Character[columns][rows]; // Characters are initially null
		if (ROWS.length > rows) // Not tall enough for number of rows
			throw new KeyboardException("Requested keyboard too narrow");
		int yOffset = (columns - ROWS.length) / 2;
		for (int i = 0; i < ROWS.length; i++) {
			placeLetters(i, yOffset);
		}
	}
	
	private void placeLetters(int i, int yOffset) throws KeyboardException {
		Character[] fromRow = getCharactersForRow(i);
		if (fromRow == null) return;
		if (fromRow.length > columns)
			throw new KeyboardException("Requested keyboard too short");
		int xOffset = (keys[yOffset + i].length - fromRow.length) / 2;
		System.arraycopy(fromRow, 0, keys[yOffset + i],
								xOffset, fromRow.length);
	}
	
	private Character[] getCharactersForRow(int row) {
		String string = ROWS[ROWS.length - row - 1];
		if (string == null) return null;
		Character[] array = new Character[string.length()];
		for (int i = 0; i < array.length; i++) array[i] = string.charAt(i);
		return array;
	}

	/** {@inheritDoc} */
	@Override
	public byte getRows() {
		return rows;
	}

	/** {@inheritDoc} */
	@Override
	public byte getColumns() {
		return columns;
	}

	/** {@inheritDoc} */
	@Override
	public Character getLetterOn(byte x, byte y) {
		return keys[y][x];
	}
}
