package simori;

/**
 * Class to store the 16x16 grid of 1 layer.
 * <p> 
 *
 * @author  Adam
 * @version 1.0.0
 */
public class Layer {
	private int[][] grid; //Grid to store whether each button is on (1) or off (0).
	private int instrument; //int to store current instrument value.
	
	/**
	 * Constructor for an individual layer.
	 * Initialises the 2D array (grid) and sets a default instrument.
	 * @author  Adam
	 * @version 1.0.0
	 */
	public Layer() {
		this.grid = new int[16][16]; //Initialise the grid as a 16x16 2 dimensional array.
		this.instrument = 0; //Set a default instrument.
	}
	
	
	/**
	 * Method to collate the on and off values in a certain column.
	 * @author  Adam
	 * @version 1.0.0
	 * @param column  The integer value of the column to be returned.
	 * @return int array containing each value in the column as a 1 for on or 0 for off
	 */
	public int[] getCol(int column){
		int[] col = new int[16];
		for(int i = 0; i < 16; i++) { //For each row of the grid.
			col[i] = grid[i][column]; // Add the requested column value to the array col.
		}
		return col;
	}
	
	/**
	 * Method to return the layers grid. Only used for turning on/off lights when layer is changed.
	 * @author  Adam
	 * @version 1.0.0
	 * @return Two dimensional Int array containing the whole grid. 
	 */
	public int[][] getGrid(){
		return grid;
	}
	
	
}
