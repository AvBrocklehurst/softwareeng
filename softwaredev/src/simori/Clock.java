package simori;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;

import simori.Exceptions.InvalidCoordinatesException;

/**
	 * 
	 * @author Jurek
	 * @version 1.1.0
	 *
	 */

public class Clock implements Runnable {
		private boolean running = true;
		private MatrixModel model;
		private MIDIPlayer midi;
		private PerformanceMode mode;
		private int currentColumn;
		public Object lock;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.1.0
		 * @param model Holds the reference to the MatrixModel
		 */
		Clock(MatrixModel model, MIDIPlayer midi, PerformanceMode mode){
			this.model = model;
			this.midi = midi;
			this.mode = mode;
		}
	
		/**
		 * The thread method for running the clock
		 * @author Jurek
		 * @version 1.1.0
		 */
		@Override
		public void run() {
			ArrayList<ArrayList<Integer>> layers = new ArrayList<ArrayList<Integer>>();
			boolean[] layer;
			while(running){
				for(int i=0; i<17; i++){
					layer = model.getCol(i, currentColumn);
					if (Arrays.asList(layer).contains(true)){
						layers.get(i).addAll(Arrays.asList(/*model.getChannel(i)*/0, /*model.getInstrument(i)*/0, /*model.getVelocity(i)*/80));
						for(int j=0; j<17;i++){
							if(layer[j] == true){
								layers.get(i).add(j);
							}
						}
					}
				}

				//fuckery of waiting for the clock to tell me to play shit
				lock = new Object();
				synchronized(lock){try {while(!Thread.interrupted()){lock.wait();}} catch (InterruptedException e) {}}
				
				//will have to get rid of the try catches later somehow (bleh)
				try {mode.tickerLight(currentColumn);} catch (InvalidCoordinatesException e1) {}
				try {midi.play(layers);} catch (InvalidMidiDataException | InterruptedException e) {}
				
				//15 will need to be replaced later
				if(currentColumn == 15){currentColumn = 0;}
				else{currentColumn++;}
				
				//TODO GET A LIST OF LISTS FOR THE CURRENT COLUMN FROM MatrixModel-----------------------DONE
				//TODO SEND LIST OF LISTS TO MidiPlayer---------------------DONE
				//TODO SEND GUI A MESSAGE
				//TODO WAIT A SET OF TIME == THE TEMPO ------- WILL DO NEXT SPRINT OR LATER AT SOME POINT >.>
				//TODO CHECK IF END OF COLUMN LOOP FOUND, IF SO currentColumn = 0, ELSE currentColumn += 1 ---------- SORT OF DONE, SINCE LOOP CHANGING IS NOT SET
				
			}
		}
		
		/**
		 * Stops the execution of the thread
		 * @author Jurek
		 * @version 1.0.1
		 */
		public void off() {
			running = false;
		}
	
	
	
}
