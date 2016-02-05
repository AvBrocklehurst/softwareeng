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
		private int period;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.3
		 * @param model Holds the reference to the MatrixModel
		 */
		Clock(MatrixModel model, MIDIPlayer midi, PerformanceMode mode, int bbm){
			this.model = model;
			this.midi = midi;
			this.mode = mode;
			lock = new Object();
			period = (1/(bbm/60))*1000;
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
					  synchronized(lock){
						  System.out.println("Tick");
						  lock.notify();
					  }	
				  }
				}, period, period);
			
			ArrayList<ArrayList<Short>> layers;
			boolean[] layer;
			List<Byte> activeLayers;
			byte currentLayer;
			while(running){
				System.out.println("currentcolumn" + currentColumn);
				layers = new ArrayList<ArrayList<Short>>();
				currentLayer = 0;
				activeLayers = model.getLayers();
				for (Byte layerLoc : activeLayers){
					layer = model.getCol(layerLoc, currentColumn);
					if (contains(layer)){
						
						System.out.println("currentlayer" + currentLayer);
						
						layers.add(new ArrayList<Short>());
						layers.get(currentLayer).addAll(Arrays.asList((short)model.getChannel(layerLoc), model.getInstrument(layerLoc), (short)model.getVelocity(layerLoc)));
						for(short row=0; row<layer.length;row++){
							if(layer[row] == true){
								layers.get(currentLayer).add((short)(72-row));
							}
						}
						currentLayer++;
					}
				}


				synchronized(lock){
					try {
						lock.wait();}
					catch (InterruptedException e) {}
				}
				
				System.out.print("To be played: ");
				try{for(int i=0; i<layers.size();i++){
					for (Short thing : layers.get(i)) System.out.print(thing + " ");
					System.out.print("|");
				}}
				catch (Exception e){}
				System.out.println();
				
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
		
		public boolean contains(boolean[] layer){
			for (boolean bool : layer) if (bool == true) return true;
			return false;
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
		
//		public static void main(String[] args){
//			Clock clock = new Clock(new MIDISoundPlayer());
//			Thread thread = new Thread(clock);
//			thread.start();
//		}
}
