package simori;

import simori.SimoriGui.KeyboardMapping;

public class QwertyKeyboard implements KeyboardMapping {
	
	private byte rows, columns;
	
	public QwertyKeyboard(byte rows, byte columns) {
		this.rows = rows;
		this.columns = columns;
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
	public char getLetterOn(byte x, byte y) {
		//TODO work out how to map coords to char
		return '\0';
	}
}
