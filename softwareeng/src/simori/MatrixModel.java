package simori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import simori.Exceptions.InvalidCoordinatesException;

/**
 * Class to handle the storage of data for the 16x16 grid.
 * Contains each of the 16 layers {@link simori.Layer}
 * <p> 
 *
 * @author  Adam
 * @version 1.2.0
 */
public class MatrixModel  {
	private Layer[] layers;
	private short BPM;
	
	
	/**
	 * Constctuctor that takes no arguments.
	 * It initalizes the Layer list and creates the first one.
	 * @author  Adam
	 * @version 1.0.0
	 */
	public MatrixModel(){
		layers = new Layer[16]; //make layers 16 long to hold all 16 layers
		layers[0] = new Layer(); //instatiate the first layer
		BPM = 88; // default BPM
	}
	
	/**
	 * Method to shut down data structure in event of the simori-on being turned off.
	 * @author  Adam
	 * @version 1.0.0
	 */
	public void off(){
		layers = null;
		BPM = 88;
	}
	
	/**
	 * Method to return a list of all in use layers
	 * @author Adam
	 * @version 1.0.0
	 * @return arraylist containing all active layers.
	 */
	public List<Byte> getLayers(){
		List<Byte> lay = new ArrayList<Byte>();
		for(byte x = 0; x < layers.length; x ++){
			if(layers[x] != null){ // If layer exists
				lay.add(x);
			}
		}
		return lay;		
	}
	
	/**
	 * Priavte method that instantiates a layer if it doesn't exist.
	 * @author  Adam
	 * @version 1.0.0
	 * @param laynum   the index of the layer to check.
	 * 
	 */
	private void layerExists(byte laynum){
		if(layers[laynum] == null){ //if the layer isn't instatiated
			layers[laynum] = new Layer(); //create a new layer
		}
	}
	
	/**
	 * Method to return the 16 x 16 grid for a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the layer number to get the grid from
	 * @return 2D boolean array containing the whole grid for a layer
	 */
	public boolean[][] getGrid(byte laynum){
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
	public boolean[] getCol(byte laynum, byte col){
		layerExists(laynum);
		return layers[laynum].getCol(col);
	}
	
	/**
	 * Method to get the BPM.
	 * @author Adam
	 * @version 1.0.0
	 * @return short containing the current BPM
	 */
	public short getBPM(){
		return BPM;
	}
	
	/**
	 * Method to update the BPM.
	 * @author Adam
	 * @version 1.0.0
	 * @param newBPM  byte with the value to set the bpm too.
	 */
	public void setBPM(short newBPM){
		BPM = newBPM;
	}
	
	/**
	 * Method to get the instrument from a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the number of the layer to get the instrument from
	 * @return short containing the layers instrument
	 */
	public short getInstrument(byte laynum){
		layerExists(laynum);
		return layers[laynum].getInstrument();
	}
	
	/**
	 * Method to get the channel from a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the number of the layer to get the channel from
	 * @return byte containing the layers channel
	 */
	public byte getChannel(byte laynum){
		layerExists(laynum);
		return layers[laynum].getChannel();
	}
	
	/**
	 * Method to get the velocty from a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the number of the layer to get the velocity from
	 * @return byte containing the layers velocity
	 */
	public byte getVelocity(byte laynum){
		layerExists(laynum);
		return layers[laynum].getVelocity();
	}
	
	/**
	 * Method to set the instrument on a certain layer.
	 * @param laynum      the layer to update
	 * @param instrument  the value for instrument to be set to.
	 */
	public void setInstrument(byte laynum, short instrument){
		layerExists(laynum);
		layers[laynum].setInstrument(instrument);
	}
	
	/**
	 * Method to set the instrument on a certain layer.
	 * @param laynum      the layer to update
	 * @param velocity    the value for velocity to be set to.
	 */
	public void setVelocity(byte laynum, byte velocity){
		layerExists(laynum);
		layers[laynum].setVelocity(velocity);
	}
	
	/**
	 * Method to set the channel on a certain layer.
	 * @param laynum      the layer to update
	 * @param channel     the value for channel to be set to.
	 */
	public void setChannel(byte laynum, byte channel){
		layerExists(laynum);
		layers[laynum].setChannel(channel);
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
	public void updateButton(byte laynum, byte col, byte row) throws InvalidCoordinatesException{
		layerExists(laynum);
		layers[laynum].updateButton(col, row);
	}

}
