package simori;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import simori.Exceptions.InvalidCoordinatesException;

	/**
	 * Class implementing Runnable which keeps track of the current tempo and plays notes which are currently active
	 * @author Jurek
	 * @author Adam
	 * @version 1.2.3
	 * @see run()
	 */
	//TODO error checking for midi.play(layers)
	//TODO fine-tune the processing so that all the notes from the next column are played
	//TODO tempo testing, making sure its within bounds of 0<160, and in increments of 10

public class Clock implements Runnable {
		private boolean running = true;
		private MatrixModel model;
		private MIDIPlayer midi;
		private Object lock;
		private TimerTask timerTask;
		private long period;
		private ModeController modes;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.5
		 * @param model Holds the reference to the MatrixModel
		 * @param midi Holds the reference to the MIDIPlayer
		 * @param bbm Beats Per Minute; used to calculate the period
		 */
		public Clock(ModeController modes, MatrixModel model, MIDIPlayer midi){
			float bbm = 88;
			this.model = model;
			this.midi = midi;
			this.modes = modes;
			lock = new Object();
			//converts the beats per minute into the period, i.e.: how many seconds to play the notes
			period = (long)((1f/(bbm/60f))*1000f);
		}
	
		/**
		 * The thread method for running the clock
		 * First sets up a timer, before entering the proper thread loop, where it
		 * continuously takes data from the model, processes it and sends it across
		 * to the MIDIPlayer to be played. Highlights the current column on the GUI using
		 * the mode.
		 * @author Jurek
		 * @author Adam
		 * @version 1.3.0
		 */
		@Override
		public void run() {
			//starts a secondary thread to keep track of the tempo
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(timerTask = new TimerTask() {
				  @Override
				  public void run() {
					  synchronized(lock){
						  lock.notify();
					  }	
				  }
				}, period, period);
			
			while(running){
				List<Byte> activeLayers = model.getLayers();
				byte [][] toBePlayed = formatNotes(activeLayers);
						
				
				//wait until the beat hits...
				synchronized(lock){
					try {
						lock.wait();
					} catch (InterruptedException e) {}
				}
				//...and send a play request to the MIDIPlayer
				//if MIDIPlayer throws an error, print it out and stop the JVM
				try {midi.play(toBePlayed);} catch (InvalidMidiDataException e1) {e1.printStackTrace(); System.exit(1);}
				
				//turn the lights on the current column
				//modes.tickThrough(currentColumn) //TODO actually have a currentColumn again
				
				for(Byte activeLayer : activeLayers){
					model.incrementColumn(activeLayer);
				}
				//if(currentColumn == 15){currentColumn = 0;}
				//else{currentColumn++;}
			}
		}
				
		/**
		 * Stops the execution of the thread and the timer
		 * @author Jurek
		 * @version 1.0.2
		 */
		public void off() {
			running = false;
			timerTask.cancel();
		}
		
		/**
		 * Method to get and format the notes to be played in this tick from the model.
		 * @author Adam
		 * @version 1.0.0
		 * @param activeLayers  a List of Bytes containing all active layers
		 * @return a 2D byte array with the notes to be played.
		 */
		private byte[][] formatNotes(List<Byte> activeLayers){
			
			byte[][] layers = new byte[activeLayers.size()][];
			ArrayList<Byte> usedColumns = new ArrayList<Byte>();
			//using arrays with an array,
			//for each generally active layer...
			for(byte x=0; x<(byte)activeLayers.size(); x++){
				byte notZero = 0;
				byte[] thisLayer = new byte[19];
				//...get its current column...
				boolean[] layer = model.getCol(activeLayers.get(x));
				//...add any active notes to the current inner array...
				for(byte y=0; y<layer.length; y++){
					if(layer[y]){
						thisLayer[y + 3] = (byte) (67 - y);
						notZero++;
					} else {
						thisLayer[y + 3] = 0;
					}
				}
				//...discard unused columns and resize the inner arrays...
				if(notZero > 0){
					usedColumns.add(x);
					layers[x] = new byte[notZero + 3];
					//[Channel, Instrument, Velocity, Note, Note, Note...]
					short instrument = model.getInstrument(activeLayers.get(x));
					layers[x][1] = (byte) ((instrument < 128) ? instrument : instrument - 127);
					layers[x][2] = model.getVelocity(activeLayers.get(x));
					layers[x][0] = model.getChannel(activeLayers.get(x));
					byte count = 3;
					for(byte y = 0; y < thisLayer.length; y ++){
						if(thisLayer[y] != 0){
							layers[x][count] = thisLayer[y];
							count++;
						} 
					}
				}
			}
			byte[][] toBePlayed = new byte[usedColumns.size()][];
			for (byte i=0; i<usedColumns.size(); i++){
				toBePlayed[i] = layers[usedColumns.get(i)];
			}
			return toBePlayed;
		}
		
}
