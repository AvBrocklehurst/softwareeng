package simori;

import simori.Exceptions.InvalidCoordinatesException;

/**
 * Class to store the 16x16 grid of 1 layer.
 * <p> 
 *
 * @author  Adam
 * @version 1.0.4
 */
public class Layer {
	private boolean[][] grid; //Grid to store whether each button is on (True) or off (False).
	private int instrument; //int to store current instrument value.
	
	/**
	 * Constructor for an individual layer.
	 * Initialises the 2D array (grid) and sets a default instrument.
	 * @author  Adam
	 * @version 1.0.1
	 */
	public Layer() {
		this.grid = new boolean[16][16]; //Initialise the grid as a 16x16 2 dimensional array.
		this.instrument = 0; //Set a default instrument.
	}
	
	
	/**
	 * Method to collate the on and off values in a certain column.
	 * @author  Adam
	 * @version 1.0.1
	 * @param column  The integer value of the column to be returned.
	 * @return boolean array containing each value in the column as a True for on or False for off
	 */
	public boolean[] getCol(int column){
		boolean[] col = new boolean[16];
		for(int i = 0; i < 16; i++) { //For each row of the grid.
			col[i] = grid[i][column]; // Add the requested column value to the array col.
		}
		return col;
	}
	
	/**
	 * Method to return the layers grid. Only used for turning on/off lights when layer is changed.
	 * @author  Adam
	 * @version 1.0.1
	 * @return Two dimensional Int array containing the whole grid. 
	 */
	public boolean[][] getGrid(){
		return grid;
	}
	
	/**
	 * Method to return the layers instrument, used by the midi framework.
	 * @author  Adam
	 * @version 1.0.0
	 * @return int containing the value of the layers instrument 
	 */
	public int getInstrument(){
		return instrument;
	}
	
	
	/**
	 * Method to set the instrument of that layer
	 * @author  Adam
	 * @version 1.0.0
	 * @param  newInstrument  an int containing the value to change the layers instrument to
	 */
	public void setInstrument(int newInstrument){
		instrument = newInstrument;
	}
	
	
	/**
	 * Method to update a button in the grid when it is turned on / off.
	 * @author  Adam
	 * @version 1.1.0
	 * @param column  The column containing the button to change 
	 * @param row     The row containing the button to change
	 * @throws InvalidCoordinatesException 
	 */
	public void updateButton(int column, int row) throws InvalidCoordinatesException{
		if(column >= 0 && column < 16 && row >= 0 && row < 16){
			grid[row][column] = !grid[row][column]; //Inverse the current value to swap.
		} else {
			throw new InvalidCoordinatesException("Column or Row not between 0 and 16");
		}
	}
	
	
	
}
