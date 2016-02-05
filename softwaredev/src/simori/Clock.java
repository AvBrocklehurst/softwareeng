package simori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		}
	
		/**
		 * The thread method for running the clock
		 * @author Jurek
		 * @version 1.1.1
		 */
		@Override
		public void run() {
			ArrayList<ArrayList<Byte>> layers = new ArrayList<ArrayList<Byte>>();
			boolean[] layer;
			List<Byte> activeLayers;
			byte currentLayer = 0;
			while(running){
				activeLayers = model.getLayers();
				for (Byte layerLoc : activeLayers){
					layer = model.getCol(layerLoc, currentColumn);
					if (Arrays.asList(layer).contains(true)){
						layers.get(layerLoc).addAll(Arrays.asList(/*model.getChannel(layerLoc)*/(byte)0, /*model.getInstrument(layerLoc)*/(byte)0, /*model.getVelocity(layerLoc)*/(byte)80));
						for(byte row=0; row<17;row++){
							if(layer[row] == true){
								layers.get(currentLayer+3).add(row);
							}
						}
						currentLayer = 0;
					}
				}
//				for(byte i=0; i<model.getLayers().size(); i++){
//					layer = model.getCol(i, currentColumn);
//					if (Arrays.asList(layer).contains(true)){
//						layers.get(i).addAll(Arrays.asList(/*model.getChannel(i)*/(byte)0, /*model.getInstrument(i)*/(byte)0, /*model.getVelocity(i)*/(byte)80));
//						for(byte j=0; j<17;i++){
//							if(layer[j] == true){
//								layers.get(i).add(j);
//							}
//						}
//					}
//				}

				//fuckery of waiting for the clock to tell me to play shit
				//kerry no -josh
				//KERRY YES!!! >:D
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
	
		/**
		 * Testing because fuck you
		 * @author Jurek
		 * @version 1.0.0
		 * @throws MidiUnavailableException 
		 * @category godihopeourgithistoryisntassessed
		 */
		public static void main(String[] args) throws MidiUnavailableException{
			Clock clock = new Clock(new MatrixModel(), new MIDISoundPlayer(), new PerformanceMode(69, 0));
			Thread thread = new Thread(clock);
			ClockTimer timer = new ClockTimer(thread, 88);
			Thread threadTimer = new Thread(timer);
			thread.start();
			threadTimer.start();
		}
	
}
