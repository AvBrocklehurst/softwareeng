package simori;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import simori.Simori.PowerTogglable;

	/**
	 * Class implementing Runnable which keeps track of the current tempo and plays notes which are currently active
	 * @author Jurek
	 * @author Adam
	 * @version 1.3.2
	 * @see run()
	 */
	//TODO error checking for midi.play(layers)
	//TODO fine-tune the processing so that all the notes from the next column are played
	//TODO tempo testing, making sure its within bounds of 0<160, and in increments of 10



//TODO JOSH implement in sprint 2: The percussion channel (9) doesn't have instruments,the pitch determines the instrument to be played.
// TODO JOSH midi goes from 1-128, we go from 0-127, will need to change!
//TODO KERRRY OMG SPLIT THIS UP INTO METHODS.





public class Clock implements Runnable, PowerTogglable {
		private boolean running = true; // TODO . Should be instantiated in constructor, not here
		private MatrixModel model;
		private MIDIPlayer midi;
		private Object lock;
		private TimerTask timerTask;
		private ModeController modes;
		private Timer timer;
		private short bpm;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.5
		 * @param model Holds the reference to the MatrixModel
		 * @param midi Holds the reference to the MIDIPlayer
		 * @param bbm Beats Per Minute; used to calculate the period
		 */
		public Clock(ModeController modes, MatrixModel model, MIDIPlayer midi){
			bpm = 88;
			this.model = model;
			this.midi = midi;
			this.modes = modes;
			lock = new Object();
		}
	
		/**
		 * The thread method for running the clock
		 * First sets up a timer, before entering the proper thread loop, where it
		 * continuously takes data from the model, processes it and sends it across
		 * to the MIDIPlayer to be played. Highlights the current column on the GUI using
		 * the mode.
		 * @author Jurek
		 * @author Adam
		 * @version 1.3.1
		 */
		@Override
		public void run() {
			//starts a secondary thread to keep track of the tempo
			startTimer();
			
			while(running){
				List<Byte> activeLayers = model.getLayers();
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
							thisLayer[y + 3] = (byte) (y + 50);
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
						
						// TODO josh. Move these to other methods
						//TODO josh. Sprinkle some error checking throughout this code
						//TODO josh. Error checking whilst creating or error checking before .play() is called
						//TODO josh. Close but no cigar .... so so close 
		//TODO josh. Error checking sprinkled throughout or all in one place???
						if(instrument < 128){
							layers[x][0] = 0;
						} else {
							layers[x][0] = 9;
							instrument = (short)(instrument - 9000);
						}
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
				//comment
				byte[][] toBePlayed = new byte[usedColumns.size()][];
				for (byte i=0; i<usedColumns.size(); i++){
					toBePlayed[i] = layers[usedColumns.get(i)];
				}
						
				
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
				modes.tickThrough(model.getCurrentColumn());
				
				//advance to the next column
				model.incrementColumn();
				//check if tempo changed, if so restart the timer thread with the new bpm
				if(model.getBPM()!=bpm){
					timer.cancel();
					bpm = model.getBPM();
					startTimer();
				}
			}
		}
		
		/**
		 * Starts the timer thread that keeps track of the tempo
		 * @version 1.0.0
		 * @author Jurek
		 */
		private void startTimer(){
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				  @Override
				  public void run() {
					  synchronized(lock){
						  lock.notify();
					  }	
				  }
				}, 0, (long)((1f/(bpm/60f))*1000f));
		}
		
		@Override
		public void switchOn() {
			running = true;
			new Thread(this).start();
		}

		@Override
		public void switchOff() {
			running = false;
			timerTask.cancel();
		}
}