package simori.Modes;

import simori.SimoriGui.KeyboardMapping;
import simori.SimoriGui;
import simori.Exceptions.KeyboardException;

public class QwertyKeyboard implements KeyboardMapping {
	
	private static final String[] ROWS =
		{"1234567890", null,
		 "QWERTYUIOP", "ASDFGHJKL\b", "ZXCVBNM_", null,
		 "qwertyuiop", "asdfghjkl\b", "zxcvbnm ", null,
		 "-+=!£$%^()", "{}[];@#~.,"};
	
	private Character[][] keys;
	private byte rows, columns;
	
	public QwertyKeyboard(byte rows, byte columns) throws KeyboardException {
		this.rows = rows;
		this.columns = columns;
		keys = new Character[columns][rows];
		if (ROWS.length > rows)
			throw new KeyboardException("Requested keyboard too narrow");
		int yOffset = (columns - ROWS.length) / 2;
		for (int i = 0; i < ROWS.length; i++) {
			Character[] fromRow = getCharactersForRow(i);
			if (fromRow == null) continue;
			if (fromRow.length > columns)
				throw new KeyboardException("Requested keyboard too short");
			int xOffset = (keys[yOffset + i].length - fromRow.length) / 2;
			System.arraycopy(fromRow, 0, keys[yOffset + i],
									xOffset, fromRow.length);
		}
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
