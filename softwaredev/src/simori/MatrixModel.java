package simori;

import simori.Exceptions.InvalidCoordinatesException;

/**
 * Class to handle the storage of data for the 16x16 grid.
 * Contains each of the 16 layers {@link simori.Layer}
 * <p> 
 *
 * @author  Adam
 * @version 1.1.0
 */
public class MatrixModel  {
	private Layer[] layers;
	
	
	/**
	 * Constctuctor that takes no arguments.
	 * It initalizes the Layer list and creates the first one.
	 * @author  Adam
	 * @version 1.0.0
	 */
	public MatrixModel(){
		layers = new Layer[16];
		layers[0] = new Layer();
	}
	
	
	/**
	 * Priavte method that instantiates a layer if it doesn't exist.
	 * @author  Adam
	 * @version 1.0.0
	 * @param laynum   the index of the layer to check.
	 * 
	 */
	private void layerExists(int laynum){
		if(layers[laynum] == null){
			layers[laynum] = new Layer();
		}
	}
	
	/**
	 * Method to return the 16 x 16 grid for a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the layer number to get the grid from
	 * @return 2D boolean array containing the whole grid for a layer
	 */
	public boolean[][] getGrid(int laynum){
		layerExists(laynum);
		return layers[laynum].getGrid();
	}
	
	/**
	 * Method to return a 1x 16 column from a layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the layer number to get the grid from
	 * @param col     the column number to return
	 * @return boolean array represting the on and off state of a column
	 */
	public boolean[] getCol(int laynum, int col){
		layerExists(laynum);
		return layers[laynum].getCol(col);
	}
	
	/**
	 * Method to get the instrument from a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the number of the layer to get the instrument from
	 * @return int containing the layers instrument
	 */
	public int getInstrument(int laynum){
		layerExists(laynum);
		return layers[laynum].getInstrument();
	}
	
	/**
	 * Method to update a layers button.
	 * @author Adam
	 * @verion 1.0.0
	 * @param laynum  the layer to update
	 * @param col     the column the button is in.
	 * @param row     the row the layer is in.
	 * @throws InvalidCoordinatesException
	 */
	public void updateButton(int laynum, int col, int row) throws InvalidCoordinatesException{
		layerExists(laynum);
		layers[laynum].updateButton(col, row);
	}

}
