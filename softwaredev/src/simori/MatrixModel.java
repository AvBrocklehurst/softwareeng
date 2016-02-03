package simori;

/**
 * Class to handle the storage of data for the 16x16 grid.
 * Contains each of the 16 layers {@link simori.Layer}
 * <p> 
 *
 * @author  Adam
 * @version 1.0.0
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
		

}
