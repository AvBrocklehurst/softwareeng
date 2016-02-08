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
		private long period;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.3
		 * @param model Holds the reference to the MatrixModel
		 */
		Clock(MatrixModel model, MIDIPlayer midi, PerformanceMode mode, float bbm){
			this.model = model;
			this.midi = midi;
			this.mode = mode;
			lock = new Object();
			period = (long)((1f/(bbm/60f))*1000f);
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
						  lock.notify();
					  }	
				  }
				}, period, period);
			currentColumn = 0;
			while(running){
				List<Byte> layers = model.getLayers();
				int size = layers.size();
				byte[][] lays =  new byte[size][];
				for(byte x = 0; x < (byte)size; x++){
					byte notZero = 0;
					byte[] thisLayer = new byte[19];
					boolean[] layer = model.getCol(layers.get(x), currentColumn);
					for(byte y = 0; y < 16; y ++){
						if(layer[y]){
							thisLayer[y + 3] = (byte) (67 - y);
							notZero++;
						} else {
							thisLayer[y + 3] = 0;
						}
					}
					if(notZero > 0){
						lays[x] = new byte[notZero + 3];
						short instru = model.getInstrument(layers.get(x));
						lays[x][0] = (byte) ((instru < 128) ? instru : instru - 127);
						lays[x][2] = model.getVelocity(layers.get(x));
						lays[x][0] = model.getChannel(layers.get(x));
						byte count = 3;
						for(byte y = 0; y < thisLayer.length; y ++){
							if(thisLayer[y] != 0){
								lays[x][count] = thisLayer[y];
								count++;
							} 
						}
					}
					
				}
				synchronized(lock){
					try {
						lock.wait();
					} catch (InterruptedException e) {}
				}
				if(lays[0] != null){
					System.out.println(Arrays.deepToString(lays));
					midi.play(lays);
				}
				try{mode.tickerLight(currentColumn);} catch (InvalidCoordinatesException e) {}
				
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
