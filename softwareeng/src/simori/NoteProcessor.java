package simori;

import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import simori.Simori.PowerTogglable;
import simori.Exceptions.InvalidCoordinatesException;

	//TODO error checking for midi.play(layers)
	//TODO tempo testing, making sure its within bounds of 0<160, and in increments of 10
	//TODO JOSH implement in sprint 2: The percussion channel (9) doesn't have instruments,the pitch determines the instrument to be played.
	// TODO JOSH midi goes from 1-128, we go from 0-127, will need to change!
	/**
	 * Class implementing Runnable and PowerTogglable which keeps track
	 * of the current tempo and plays notes which are currently active
	 * @author Jurek
	 * @author Adam
	 * @version 1.5.0
	 * @see run()
	 */
public class NoteProcessor implements Runnable, PowerTogglable {
		private volatile boolean running;
		private ModeController mode;
		private MatrixModel model;
		private MIDIPlayer midi;
		private Object lock;
		public Object bpmLock;
		private Clock clock;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.6
		 * @param model Holds the reference to the MatrixModel
		 * @param midi Holds the reference to the MIDIPlayer
		 * @param bbm Beats Per Minute; used to calculate the period
		 */
		public NoteProcessor(ModeController modes, MatrixModel model, MIDIPlayer midi){
			running = true;
			this.mode = modes;
			this.model = model;
			this.midi = midi;
			lock = new Object();
			bpmLock = new Object();
			mode.setBpmLock(bpmLock);
			clock = new Clock(findMaxProcessingTime(), running, model, bpmLock, lock);
		}

		/**
		 * The thread method for running the note processor
		 * First sets up the clock, before entering the proper thread loop, where it
		 * continuously takes data from the model, processes it and sends it across
		 * to the MIDIPlayer to be played. Highlights the current column on the GUI using
		 * the mode.
		 * The thread waits through the majority of a tick before processing data.
		 * @author Jurek
		 * @version 1.5.0
		 */
		@Override
		public void run() {
			new Thread(clock).start();
			
			//start the thread loop
			while(running){
				
				//wait until the beat hits...
				synchronized(lock){try{   lock.wait();   }catch(InterruptedException e){}}
				
				//reach out for and process the notes...
				//...assuming that the simori has not been turned off
				if(!running) break;
				byte[][] toBePlayed = getNotes();
				
				//send a play request to the MIDIPlayer
				try{midi.play(toBePlayed);}
				//if MIDIPlayer throws an error, print it out and stop the JVM
				catch(InvalidMidiDataException e){e.printStackTrace();System.exit(1);}
				
				//turn the lights on the current column
				mode.tickThrough(model.getCurrentColumn());
				
				//advance to the next column
				model.incrementColumn();
			}
		}
		
		/**
		 * Finds the maximum note processing time. The method achieves this by
		 * creating a mock MatrixModel and population a single column from each
		 * layer before attempting to process them all.
		 * @author Jurek
		 * @version 1.0.0
		 * @return long returns the maximum processing time in milliseconds, rounded up
		 */
		private long findMaxProcessingTime() {
			
			//create a mock model
			MatrixModel actualModel = model;
			model = new MatrixModel(16, 16);
			model.switchOn();
			
			//populate a single column on each layer of the mock model
			for(byte z=0;z<16;z++){
				for(byte y=0;z<16;z++){
					try{model.updateButton(z, (byte)0, y);}catch(InvalidCoordinatesException e){}
				}
			}
			
			//play the mock object to determine max delay
			long time = System.currentTimeMillis();
			getNotes();
			long endTime = System.currentTimeMillis();
			long maxTime = endTime - time;
			maxTime = (long) (Math.ceil(maxTime/10)*10);
			
			//revert from the mock model to the actual one
			model = actualModel;
			return maxTime;
		}
		
		/**
		 * Method to product the 2D byte array of notes for the midi player.
		 * It takes the notes in the current column from the active layers
		 * in the model and converts them into the correctly sized byte 
		 * arrays that also house information such as the instrument, channel and velocity.
		 * This method also alters the note values to make them the right pitch.
		 * The method is particuallary long and complex because we decided that we want
		 * to send a correctly fixed size byte array rather than an array list.
		 * @author Adam
		 * @version 1.0.2
		 * @return 2D byte Array containing the notes to be played and layer information.
		 */
		private byte[][] getNotes(){
			List<Byte> activeLayers = model.getLayers();
			byte[][] layers = new byte[activeLayers.size()][]; //make the array the same size as the active layers.
			ArrayList<Byte> usedColumns = new ArrayList<Byte>();
			for(byte x=0; x<(byte)activeLayers.size(); x++){ //for each active layer.
				byte notZero = 0; //to keep the size to make this layers byte array.
				byte[] thisLayer = new byte[19];
				boolean[] layer = model.getCol(activeLayers.get(x));
				for(byte y=0; y<layer.length; y++){
					if(layer[y]){
						thisLayer[y + 3] = (byte) (y + 50); //alter the value to store the correct pitch.
						notZero++;
					} else {
						thisLayer[y + 3] = 0;
					}
				}
				if(notZero > 0){ // If this layer has notes in this column.
					usedColumns.add(x);
					layers[x] = new byte[notZero + 3];
					short instrument = model.getInstrument(activeLayers.get(x));
					if(instrument < 128){ // insturment isn't in normal set
						layers[x][0] = 0;
					} else {
						layers[x][0] = 9; //make the chanel 9 (percussion) 
						/* Subtract 94 from number to get percussion insturment value */
						instrument = (byte)(instrument - 94);
					}
					layers[x][1] = (byte) instrument;
					layers[x][2] = model.getVelocity(activeLayers.get(x));
					layers[x][0] = model.getChannel(activeLayers.get(x));
					byte count = 3; //start at 3 to store the other information before it.
					for(byte y = 0; y < thisLayer.length; y ++){
						if(thisLayer[y] != 0){
							layers[x][count] = thisLayer[y];
							count++;
						} 
					}
				}
			}
			/* resize the array to only store layers with notes in this column */
			byte[][] toBePlayed = new byte[usedColumns.size()][];
			for (byte i=0; i<usedColumns.size(); i++){
				toBePlayed[i] = layers[usedColumns.get(i)];
			}
					
			return toBePlayed;
		}
		
		/**
		 * Switches the note processor, and subsequently its clock, on
		 * @author Matt
		 * @version 1.0.0
		 */
		@Override
		public void switchOn() {
			running = true;
			new Thread(this).start();
		}
		
		
		/**
		 * Switches the note processor, and subsequently its clock, off
		 * @author Matt
		 * @author Adam
		 * @author Jurek
		 * @version 1.0.1
		 */
		@Override
		public void switchOff() {
			running = false;
			synchronized(lock){lock.notify();}
			synchronized(bpmLock){bpmLock.notify();}
		}
}