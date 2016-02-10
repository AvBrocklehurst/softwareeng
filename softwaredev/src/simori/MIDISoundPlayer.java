package simori;

import java.util.ArrayList;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;

/**
 * @author Josh
 * @version 3.2.4
 * {@link simori.MIDIPlayer}
 * {@link ShortMessage}
 * 
 * Class that implements the MIDIPlayer interface.
 * NOTE: This class is designed to have the lowest overhead as possible.
 * This is because it is played after the clock, i.e. if it takes too long to process it may become out of sync with the rest of the system.
 * The amount of time it takes to do .play(Array) is ideally zero.
 * As a result there is little to no error checking in this class. All error checking is done before this method is played (whilst it is still in sync with the clock)
 */
public class MIDISoundPlayer implements MIDIPlayer{
	//TODO implement in sprint 2: The percussion channel (9) doesn't have instruments,the pitch determines the instrument to be played.
	
	final static int TIMESTAMP = -1; // Timestamp of -1 means MIDI messages will be executed immediately.
	private Synthesizer synth;
	private Receiver reciever;
	private ArrayList<ShortMessage>  messageArray; // ArrayList that will hold all MIDI messages for a single tick.
	private ShortMessage message;
	
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * @throws MidiUnavailableException
	 * 
	 * Constructor that creates a MIDISynthesizer with a connected receiver that can send MIDI short messages to the synthesizer.
	 */
	public MIDISoundPlayer() throws MidiUnavailableException {
		//TODO Error checking sprint 2: getSynth (check for null etc).
		synth = MidiSystem.getSynthesizer();
		synth.open();
		reciever = synth.getReceiver();
	}
	
	
	/**
	 * @author Josh
	 * @version 4.0.1
	 * @param array
	 * @return void
	 * @throws InvalidMidiDataException
	 * 
	 * Method takes the input array array of bytes and turns those into a MIDI messages.
	 * These messages are stored in an ArrayList ready to be executed simultaneously.
	 */
	private void readArray(byte[][] array) throws InvalidMidiDataException{
		for(byte[] layer : array) { // for each 'layer' with a sound that needs playing:
			message = new ShortMessage(ShortMessage.PROGRAM_CHANGE, layer[0], layer[1], 0); // for the given layer set the channel and instrument, the zero is arbitrary (but is needed for correct number of bytes to be sent).
			messageArray.add(message); // add MIDI message to array of all MIDI messages.
		
			for (int i = 3; i < layer.length; i++) { // for all notes in a given layer:
				message = new ShortMessage(ShortMessage.NOTE_ON, layer[0], layer[i], layer[2]); // set a play command for that note with the correct pitch and velocity.
				messageArray.add(message); // add MIDI message to array of all MIDI messages.
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
		for (ShortMessage message : messageArray) { // for every message in the MIDI message arrayList:
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
		messageArray = new ArrayList<ShortMessage>(); 
		readArray(array); // take the array and turn it into MIDI messages.
		playArray(); //play all the MIDI messages.
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		/*  TODO implement in sprint 2 
		 * will probably be something along the lines of.....
		try {
			message = new ShortMessage(ShortMessage.STOP);
		} catch (InvalidMidiDataException e) {e.printStackTrace(); System.exit(1);}
		reciever.send(message, TIMESTAMP);
		*/
	}	
	
	
}