package simori;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import simori.Simori.PowerTogglable;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * Class to handle the storage of data for the 16x16 grid.
 * Contains each of the 16 layers {@link simori.Layer}
 * <p> 
 *
 * @author  Adam
 * @version 1.3.0
 */
public class MatrixModel extends Observable implements Serializable, PowerTogglable {
	private Layer[] layers;
	private volatile short BPM;
	private int height;
	private int width;
	private byte loopPoint;
	private byte currentColumn;
	
	
	/**
	 * Constctuctor that takes no arguments.
	 * It initalizes the Layer list and creates the first one.
	 * @author  Adam
	 * @version 1.0.1
	 */
	public MatrixModel(int width, int height){
		this.width = width;
		this.height = height;
		this.layers = new Layer[16]; //make layers 16 long to hold all 16 layers
		this.layers[0] = new Layer(width, height); //instatiate the first layer
		this.BPM = 88; // default BPM
		this.loopPoint = 15;
		this.currentColumn = 0;
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
			layers[laynum] = new Layer(width, height); //create a new layer
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
	 * @return boolean array represting the on and off state of a column
	 */
	public boolean[] getCol(byte laynum){
		layerExists(laynum);
		return layers[laynum].getCol(currentColumn);
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
	 * Method to update the BPM. It also updates the observable status
	 * and notifies observers to read it.
	 * @author Adam
	 * @version 1.1.0
	 * @param newBPM  byte with the value to set the bpm too.
	 */
	public void setBPM(short newBPM){
		BPM = newBPM; 
		setChanged(); //change the state of observable to changed.
	    notifyObservers(newBPM); //notify all observers.
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
	 * Method to get the loopPoint of the simori.
	 * @author Adam
	 * @version 1.0.0
	 * @return byte containing the loopPoint.
	 */
	public byte getLoopPoint(){
		return this.loopPoint;
	}
	
	/**
	 * Method to get the th current column from a given layer.
	 * @author Adam
	 * @version 1.0.0
	 * @param laynum  the number of the layer to get the column from
	 * @return byte containing the layers velocity
	 */
	public byte getCurrentColumn(){
		System.out.println("model:" + currentColumn);
		return currentColumn;
	}
	
	/**
	 * Method to increment the current column in a given layer.
	 * @param laynum      the layer to update
	 */
	public void incrementColumn(){
		if(currentColumn < loopPoint){
			currentColumn++;
		} else {
			currentColumn = 0;
		}
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
	 * Method to set the loop point for the simori.
	 * @author Adam.
	 * @version 1.0.0
	 * @param loopPoint  the byte containing the new looppoint.
	 */
	public void setLoopPoint(byte loopPoint){
		this.loopPoint = loopPoint;
	}
	
	/**
	 * Method to set the instrument on a certain layer.
	 * @author Adam.
	 * @version 1.0.0
	 * @param laynum      the layer to update
	 * @param velocity    the value for velocity to be set to.
	 */
	public void setVelocity(byte laynum, byte velocity){
		layerExists(laynum);
		layers[laynum].setVelocity(velocity);
	}
	
	/**
	 * Method to set the channel on a certain layer.
	 * @author Adam.
	 * @version 1.0.0
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
	
	/**
	 * Method to copy the values from the loaded in 
	 * model into the currently reference one.
	 * @version 1.0.0
	 * @author Adam
	 * @param temp  The temporary model to copy the contents from.
	 */
	public void convertModel(MatrixModel temp){
		this.layers = temp.layers;
		this.BPM = temp.BPM;
		this.loopPoint = temp.loopPoint;		
		this.currentColumn = 0;
	}

	@Override
	public void switchOn() {
		this.layers = new Layer[16]; //make layers 16 long to hold all 16 layers

		this.layers[0] = new Layer(width, height); //instatiate the first layer
	}

	@Override
	public void switchOff() {
		layers = null;
		BPM = 88;
		loopPoint = 15;
		currentColumn = 0;
	}

}
