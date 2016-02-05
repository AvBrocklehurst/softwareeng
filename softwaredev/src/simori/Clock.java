package simori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import simori.Exceptions.InvalidCoordinatesException;

	/**
	 * 
	 * @author Jurek
	 * @version 1.1.1
	 *
	 */

public class Clock implements Runnable {
		private boolean running = true;
		private MatrixModel model;
		private MIDIPlayer midi;
		private PerformanceMode mode;
		private byte currentColumn;
		public Object lock;
		private TimerTask timerTask;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.3
		 * @param model Holds the reference to the MatrixModel
		 */
		Clock(MatrixModel model, MIDIPlayer midi, PerformanceMode mode){
			this.model = model;
			this.midi = midi;
			this.mode = mode;
			lock = new Object();
		}
	
		/**
		 * The thread method for running the clock
		 * @author Jurek
		 * @version 1.1.1
		 */
		@Override
		public void run() {
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(timerTask = new TimerTask() {
				  @Override
				  public void run() {
					  System.out.println("Notifying");
					  synchronized(lock){

						  lock.notify();
					  }
				    
				  }
				}, 1000, 1000);
			
			
			ArrayList<ArrayList<Short>> layers = new ArrayList<ArrayList<Short>>();
			boolean[] layer;
			List<Byte> activeLayers;
			byte currentLayer = 0;
			while(running){
				activeLayers = model.getLayers();
				for (Byte layerLoc : activeLayers){
					layer = model.getCol(layerLoc, currentColumn);
					if (Arrays.asList(layer).contains(true)){
						layers.get(layerLoc).addAll(Arrays.asList((short)model.getChannel(layerLoc), model.getInstrument(layerLoc), (short)model.getVelocity(layerLoc)));
						for(short row=0; row<17;row++){
							if(layer[row] == true){
								layers.get(currentLayer+3).add((short)(63-row));
								currentLayer++;
							}
						}
						currentLayer = 0;
					}
				}


				synchronized(lock){
					try {
						lock.wait();}
					catch (InterruptedException e) {}
				}
					
				try{mode.tickerLight(currentColumn);} catch (InvalidCoordinatesException e) {}
				midi.play(layers);
				
				//15 will need to be replaced later
				if(currentColumn == 15){currentColumn = 0;}
				else{currentColumn++;}
				
				//TODO GET A LIST OF LISTS FOR THE CURRENT COLUMN FROM MatrixModel-----------------------DONE
				//TODO SEND LIST OF LISTS TO MidiPlayer---------------------DONE
				//TODO SEND GUI A MESSAGE --------------------------------DONE
				//TODO WAIT A SET OF TIME == THE TEMPO ------------------------ DONE
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
			timerTask.cancel();
		}	
}
