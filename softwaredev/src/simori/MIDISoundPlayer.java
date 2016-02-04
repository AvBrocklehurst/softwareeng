package simori;


import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;



/**
 * 
 * @author Josh
 * @version 3.0.0
 * 
 */
public class MIDISoundPlayer implements MIDIPlayer  {
	/*
	 * array of arrays
	 * Outer array will contain 16 arrays (at most, if a layer has not been used then there's no point sending it)
	 * inner arrays will contain:
	 * first 3 numbers will be channel, instrument and velocity
	 * all other numbers will be pitch 
	 * 
	 * [[0,10,80,1,2....]]
	 * [[9,0,80,45,46,47...]]
	 * 
	 * the percussion channel (9) doesn't have instruments,the pitch determines the instrument to be played
	 */
	
	//TODO CHANNEL 9 WILL BE A RIGHT PAIN! 
	
	final static int TIMESTAMP = -1;
	
	private Synthesizer synth;
	private Receiver reciever;
	private ArrayList<ShortMessage>  messageArray; 
	private ShortMessage message;
	
	
	/**
	 * @author Josh
	 * 
	 * @throws MidiUnavailableException
	 */
	public MIDISoundPlayer() throws MidiUnavailableException {
		//TODO getSynth (check for null etc)
		
		synth = MidiSystem.getSynthesizer();
		synth.open();
		reciever = synth.getReceiver();
		messageArray = new ArrayList<ShortMessage>(); // Initialise arrayList that will hold all MIDI messages for a single tick
	}
	
	
	
	/**
	 * 
	 * @param array
	 * @throws InvalidMidiDataException
	 */
	private void readArray(ArrayList<ArrayList<Integer>> array) throws InvalidMidiDataException{
		
		for (ArrayList<Integer> layer : array) { // for each 'layer' with a sound that needs playing: 
			message = new ShortMessage(ShortMessage.PROGRAM_CHANGE, layer.get(0), layer.get(1)); // for the given layer set the channel and instrument 
			messageArray.add(message); // add MIDI message to array of all MIDI messages
		
			for (int i = 3; i < layer.size(); i++) { // for all notes in a given layer:
				message = new ShortMessage(ShortMessage.NOTE_ON,layer.get(0), layer.get(i), layer.get(2)); // set a play command for that note with the correct pitch and velocity
				messageArray.add(message); // add MIDI message to array of all MIDI messages
			} 
		}
	}

	
	/**
	 * @author Josh
	 * @throws InterruptedException
	 */
	private void playArray() throws InterruptedException{
		for (ShortMessage message : messageArray) { // for every message in the MIDI message arrayList:
			reciever.send(message, TIMESTAMP); // send that MIDI message to the synthesizer.
		}
	}
	
	
	/**
	 * @author Josh
	 * {@inheritDoc}
	 */
	public void play(ArrayList<ArrayList<Integer>> array) throws InvalidMidiDataException, InterruptedException{
		readArray(array);
		playArray();
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		MIDISoundPlayer josh = new MIDISoundPlayer();
		ArrayList<ArrayList<Integer>> noteArray = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> layer1 = new ArrayList<Integer>();
		layer1.add(0);
		layer1.add(0);
		layer1.add(80);
		layer1.add(60);
		layer1.add(64);
		layer1.add(67);
		noteArray.add(layer1);
		
		josh.play(noteArray);
		Thread.sleep(10000);
		
		// ArrayList<ArrayList<Integer>> array
		
		
	}
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private void playNote(Note note) throws InvalidMidiDataException{
		int channel = note.getChannel();
		int instrument = note.getInstrument();
		int pitch = note.getPitch();
		int velocity = note.getVelocity();
		int channelsPreviousInstrument = channelsPreviousInstruments[channel];
		ShortMessage message = new ShortMessage();
		
		if(instrument == channelsPreviousInstrument ){
			message.setMessage(ShortMessage.NOTE_ON, channel, pitch, velocity);
			reciever.send(message, TIMESTAMP);
		}
		else{
			message.setMessage(ShortMessage.PROGRAM_CHANGE, instrument, 0);
			reciever.send(message, TIMESTAMP);
			message.setMessage(ShortMessage.NOTE_ON, channel, pitch, velocity);
			reciever.send(message, TIMESTAMP);
		}
	}


	public static void main(String[] args) throws MidiUnavailableException, InvalidMIDIChannelException, InvalidMIDIInstrumentException, InvalidMIDIPitchException, InvalidMIDIVelocityException, InvalidMidiDataException, InterruptedException {
		MIDISoundPlayer josh = new MIDISoundPlayer();
		Note note = new Note(0, 110, 3, 80);
		josh.playNote(note);
		Thread.sleep(1000);
		
	}

	
	/*
	 * TODO getSynthezier 
	 * TODO playNote
	 * TODO playNotes
	 * TODO createMessage
	 */