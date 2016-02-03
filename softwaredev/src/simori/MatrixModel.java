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
	
	
	
	public MatrixModel(){
		layers = new Layer[16];
		layers[0] = new Layer();
	}
	
	private void layerExists(int laynum){
		if(layers[laynum] == null){
			layers[laynum] = new Layer();
		}
	}
	
	public boolean[][] getGrid(int laynum){
		return layers[laynum].getGrid();
	}
		

}
