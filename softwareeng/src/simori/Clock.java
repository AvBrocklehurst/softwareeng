package simori;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import simori.Simori.PowerTogglable;
import simori.Exceptions.InvalidCoordinatesException;

//TODO HEY KERRY, HERES SOME OF THE ERROR CHECKING THAT NEEDS DOING
//TODO HEY KERRY, SEE THE EXCEPTION PACKAGES,USE THOSE AS EXCEPTIONS
/*
 * 	if(layers.size <0){throw new NothingToplayException()};
	for (byte[] layer: layers) {
		//if(layers.size <4){throw new MidixxxToplayException()};
		 if(layer[0]!= 0 || layer[0] != 9){throw newxxxx};
		 if(layer[1] <0) {throw new xxx};
		 if(layer[2] <0) {throw new xxx};
		 for (int i = 3; i < layer.length; i++) {
		 if(layer[i] <0){throwxxx}; 
		}
		
 */
	/**
	 * Class implementing Runnable which keeps track of the current tempo and plays notes which are currently active
	 * @author Jurek
	 * @author Adam
	 * @version 1.4.0
	 * @see run()
	 */
	//TODO error checking for midi.play(layers)
	//TODO tempo testing, making sure its within bounds of 0<160, and in increments of 10



//TODO JOSH implement in sprint 2: The percussion channel (9) doesn't have instruments,the pitch determines the instrument to be played.
// TODO JOSH midi goes from 1-128, we go from 0-127, will need to change!
//TODO KERRRY OMG SPLIT THIS UP INTO METHODS.





public class Clock implements Runnable, PowerTogglable {
		private boolean running;
		private ModeController mode;
		private MatrixModel model;
		private MIDIPlayer midi;
		private Object lock;
		public Object bpmLock;
		private Timer timer;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.6
		 * @param model Holds the reference to the MatrixModel
		 * @param midi Holds the reference to the MIDIPlayer
		 * @param bbm Beats Per Minute; used to calculate the period
		 */
		public Clock(ModeController modes, MatrixModel model, MIDIPlayer midi){
			running = true;
			this.mode = modes;
			this.model = model;
			this.midi = midi;
			lock = new Object();
			bpmLock = new Object();
			mode.setBpmLock(bpmLock);
		}

		/**
		 * The thread method for running the clock
		 * First sets up a timer, before entering the proper thread loop, where it
		 * continuously takes data from the model, processes it and sends it across
		 * to the MIDIPlayer to be played. Highlights the current column on the GUI using
		 * the mode.
		 * @author Jurek
		 * @version 1.4.0
		 */
		@Override
		public void run() {
			//find the maximum processing time
			
			
			//starts a secondary thread to keep track of the tempo
			startTimer();
			
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
		 * 
		 * @author Jurek
		 * @version 1.0.0
		 * @category absolutely disgusting
		 */
		private void startTimer() {
			new Thread(new Runnable() {
				long startTime;
				long maxTime;
				
				@Override
				public void run() {
					maxTime = findMaxProcessingTime();
					
					//while running...
					while(running){
						//...update the tempo...
						short bpm = model.getBPM();
						changeTempo(bpm, maxTime);
						
						synchronized(bpmLock){
							//...everytime the BPM is changed
							while(bpm==model.getBPM() && running) {
								try{bpmLock.wait();}catch(InterruptedException e){}
							}
						}
						timer.cancel();
					}
				}

				/**
				 * @author Jurek
				 * @version 1.0.0
				 */
				private void changeTempo(short bpm, long maxTime) {
					long timePassed = System.currentTimeMillis() - startTime;
					long period = (long)((1f/(bpm/60f))*1000f)-maxTime;
					long timeLeft;
					if(period <= timePassed) timeLeft = 0;
					else timeLeft = period - timePassed;
					startTimer(timeLeft, period);
				}

				/**
				 * Starts the timer thread that keeps track of the tempo
				 * @version 1.0.1
				 * @author Jurek
				 */
				private void startTimer(long timeLeft, long period){
					timer = new Timer();
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							startTime = System.currentTimeMillis();
							synchronized(lock){
								lock.notify();
							}	
						}
					}, timeLeft, period);
				}

				/**
				 * 
				 * @author Jurek
				 * @version 1.0.0
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
				
			}).start();
		}

		/**
		 * 
		 * @author Jurek
		 * @author Adam
		 * @version 1.0.1
		 * @return
		 */
		private byte[][] getNotes(){
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
				//...add any active notes to the current inner array.. 
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
						instrument = (byte)(instrument - 94);
					}
					layers[x][1] = (byte) instrument;
					layers[x][2] = model.getVelocity(activeLayers.get(x));
					layers[x][0] = model.getChannel(activeLayers.get(x)); //TODO wrong!
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
					
			return toBePlayed;
		}
		
		/**
		 * @author Matt
		 * @version 1.0.0
		 */
		@Override
		public void switchOn() {
			running = true;
			new Thread(this).start();
		}
		
		
		/**
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