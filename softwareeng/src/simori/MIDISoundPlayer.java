package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import simori.Simori.PowerTogglable;

/**
 * @author Josh aka the music man
 * @version 4.2.4
 * {@link simori.MIDIPlayer}
 * {@link ShortMessage}
 * 
 * Class that implements the MIDIPlayer interface.
 * NOTE: This class is designed to have the lowest overhead as possible.
 * This is because it is played after the clock, i.e. if it takes too long to process it may become out of sync with the rest of the system.
 * The amount of time it takes to do .play(Array) is ideally zero.
 * As a result there is little to no error checking in this class. All error checking is done before this method is played (whilst it is still in sync with the clock)
 */
public class MIDISoundPlayer implements MIDIPlayer, PowerTogglable {

	final static int TIMESTAMP = -1; // Timestamp of -1 means MIDI messages will be executed immediately.
	private Synthesizer synth;
	private Receiver reciever;
	private ShortMessage[] noteOnArray; // array that will hold all MIDI messages for a single tick.
	private ShortMessage[] noteOffArray;
	private ShortMessage message;
	
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * @throws MidiUnavailableException
	 * 
	 * Constructor that creates a MIDISynthesizer with a connected receiver that can send MIDI short messages to the synthesizer.
	 */
	public MIDISoundPlayer(){
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			reciever = synth.getReceiver();
			} catch (MidiUnavailableException e) {e.printStackTrace(); System.exit(1);}
		if (synth == null){System.exit(1);}
		if (reciever == null){System.exit(1);}
		}
		
	
	/**
	 * @author Josh
	 * @version 5.0.1
	 * @param array
	 * @return void
	 * @throws InvalidMidiDataException
	 * 
	 * Method takes the input array array of bytes and turns those into a MIDI messages.
	 * These messages are stored in an ArrayList ready to be executed simultaneously.
	 * 
	 * for any given array of array of bytes:
	 * there is 1 command for the first 3 elements
	 * One command for subsequent element.
	 * As a result the array will be of length (size of individual array -2)* number of arrays
	 * For example an array of arrays [[0,110,80,60]] would contain 2 commands
	 */
	private void readArray(byte[][] array) throws InvalidMidiDataException{
		int commandCounter = 0;	
		for(byte[] layer : array){
			commandCounter += (layer.length - 2);
		}

		
		//System.out.println("COMMAND COUNTER IS:"+commandCounter);
		
		noteOnArray = new ShortMessage[commandCounter];
		noteOffArray = new ShortMessage[commandCounter];
		int currentPositionInArray = 0;

		for(int i = 0; i<array.length; i++){
			
			message  = new ShortMessage(); // for some reason constructor does not work in blue room, so setMessage has to be used instead
			message.setMessage(ShortMessage.PROGRAM_CHANGE, array[i][0], array[i][1], 0); // for the given layer set the channel and instrument, the zero is arbitrary (but is needed for correct number of bytes to be sent).
			noteOnArray[currentPositionInArray] = message; // add MIDI message to array of all MIDI messages.
			noteOffArray[currentPositionInArray] = message;
			//System.out.println("command message at: "+currentPositionInArray + " with: "+ messageArray[currentPositionInArray]);
			currentPositionInArray ++;
			for(int j = 3; j<array[i].length; j++){
				message = new ShortMessage(); // for some reason constructor does not work in blue room, so setMessage has to be used instead
				message.setMessage(ShortMessage.NOTE_ON, array[i][0], array[i][j], array[i][2]); // set a play command for that note with the correct pitch and velocity.
				noteOnArray[currentPositionInArray] = message; // add MIDI message to array of all MIDI messages.
				
				message = new ShortMessage();
				message.setMessage(ShortMessage.NOTE_OFF, array[i][0], array[i][j], array[i][2]);
				noteOffArray[currentPositionInArray] = message;
				
				//System.out.println("note message at: "+currentPositionInArray + " with: "+ messageArray[currentPositionInArray]);
				currentPositionInArray ++;
			}
		}
	}

	
	/**
	 * @author Josh
	 * @version 4.0.0
	 * 
	 * Method takes arrayList of MIDI messages and executes them simultaneously (or near simultaneous).
	 */
	private void playArray(){
		for (ShortMessage message : noteOnArray) { // for every message in the MIDI message arrayList:
			reciever.send(message, TIMESTAMP); // send that MIDI message to the synthesizer.
		}
	}
	
	
	
	/**
	 * @author Josh
	 * @version 1.0.5;
	 * {@inheritDoc}
	 * @throws InvalidMidiDataException 
	 */
	@Override
	public void play(byte[][] array) throws InvalidMidiDataException {
		readArray(array); // take the array and turn it into MIDI messages.
		//System.out.println("size of array is: "+messageArray.length);
		playArray(); //play all the MIDI messages.
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * {@inheritDoc}
	 */
	@Override
	public void stopPlay() throws InvalidMidiDataException {
		for (ShortMessage message : noteOffArray) { // for every message in the MIDI message arrayList:
			reciever.send(message, TIMESTAMP); // send that MIDI message to the synthesizer.
		}
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.1;
	 * {@inheritDoc}
	 */
	@Override
	public void switchOn() {
		try {
			synth.open();
			reciever = synth.getReceiver();
			message = null; // just in case there is something stored in message
			noteOnArray = null; // just in case there is something stored in the array
		} catch (MidiUnavailableException e) {e.printStackTrace();System.exit(1);}
		
	}

	
	/**
	 * @author Josh
	 * @version 1.0.1;
	 * {@inheritDoc}
	 */
	@Override
	public void switchOff() {
		message = null; // just in case there is something stored in message
		noteOnArray = null; // just in case there is something stored in the array
		reciever.close();
		synth.close();	
	}

	public static void main(String[] args) throws InvalidMidiDataException, InterruptedException {
		MIDISoundPlayer josh = new MIDISoundPlayer();
		byte[][] test = {{0,7,80,60,64,67},{0,110,80,60,64,67}};
		josh.play(test);
		Thread.sleep(100);
		josh.stopPlay();
		Thread.sleep(100000);
	}
}

