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
		 "QWERTYUIOP", "ASDFGHJKL\b", "ZXCVBNM ", null,
		 "qwertyuiop", "asdfghjkl\b", "zxcvbnm ", null,
		 "-+=!$%^()_", "{}[];@#~.,"};
	
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
		placeRows();
	}
	
	/**
	 * Calculates how many buttons away from the edge to begin drawing the
	 * rows so that the keyboard appears centred vertically, and iterates
	 * over {@link #ROWS} to place the letters into {@link #keys}.
	 * @throws KeyboardException If there are too few rows or columns
	 */
	private void placeRows() throws KeyboardException {
		if (ROWS.length > rows) // Not tall enough for number of rows
			throw new KeyboardException("Requested keyboard too narrow");
		int yOffset = (rows - ROWS.length) / 2;
		for (int i = 0; i < ROWS.length; i++) {
			placeLetters(i, yOffset); 
		}
	}
	
	/**
	 * Enters the specified row of letters into {@link #keys}, starting
	 * at such a number of buttons from the edge that it appears centred.
	 * @param row The index of {@link #ROWS} to place the letters of
	 * @param yOffset The distance from the bottom edge to draw the bottom row
	 * @throws KeyboardException If columns does not fit "QWERTYUIOP"
	 */
	private void placeLetters(int row, int yOffset) throws KeyboardException {
		Character[] fromRow = getCharactersForRow(row);
		if (fromRow == null) return; // Empty row between character rows
		if (fromRow.length > columns) // Not enough width to fit all letters
			throw new KeyboardException("Requested keyboard too short");
		int xOffset = (columns - fromRow.length) / 2; // Centre horizontally
		System.arraycopy(fromRow, 0,
							keys[yOffset + row], xOffset, fromRow.length);
	}
	
	/**
	 * Converts a row of the readable keyboard layout {@link #ROWS}
	 * to an array of Characters ready to be copied in to {@link #keys}.
	 * @param row An index of {@link #ROWS} with the String to be converted
	 * @return The String as an array of Characters, or null
	 */
	private Character[] getCharactersForRow(int row) {
		/*
		 * Loop goes bottom-to-top. Index is inverted so that ROWS
		 * is read backwards and the first appears at the top.
		 */
		String string = ROWS[ROWS.length - row - 1];
		if (string == null) return null; // This was an intentionally empty row
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
