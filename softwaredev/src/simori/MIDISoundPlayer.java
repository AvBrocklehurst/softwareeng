package simori;


import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import simori.Exceptions.InvalidMIDIChannelException;
import simori.Exceptions.InvalidMIDIInstrumentException;
import simori.Exceptions.InvalidMIDIPitchException;
import simori.Exceptions.InvalidMIDIVelocityException;

/**
 * 
 * @author Josh
 *
 */
public class MIDISoundPlayer{ //implements MIDIPlayer  {
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
	 * the percussion channel (9) doesnt have instruments,the pitch determines the instrument to be played
	 */
	private int[] channelsPreviousInstruments;
	Synthesizer synth;
	Receiver reciever;
	final static int TIMESTAMP = -1;
	ArrayList<ShortMessage>  messageArray = new ArrayList<ShortMessage>();
	ShortMessage message;
	
	public MIDISoundPlayer() throws MidiUnavailableException {
		channelsPreviousInstruments = new int[16];
		for (int i : channelsPreviousInstruments) {channelsPreviousInstruments[i] = 0;}
		
		synth  = MidiSystem.getSynthesizer();
		synth.open();
		reciever = synth.getReceiver();
	}
	
	public void readArray(int[][] array) throws InvalidMidiDataException{
		
		for (int[] layer : array) {
			message = new ShortMessage(ShortMessage.PROGRAM_CHANGE, layer[0], layer[1], 0);
			messageArray.add(message);
			for (int i = 3; i < layer.length; i++) {
				message = new ShortMessage(ShortMessage.NOTE_ON,layer[0], layer[i], layer[2]);
				messageArray.add(message);
			}
		}
	}

	public void play() throws InterruptedException{
		for (ShortMessage message : messageArray) {
			reciever.send(message, TIMESTAMP);
		}
	}
	
	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		MIDISoundPlayer josh = new MIDISoundPlayer();
		int[][] notes = new int[3][];
		notes[0] = new int[] {0,0,5,57,58,59,60,61,62,63,64,65,66,67,68,69,70};
		notes[1] = new int[] {0,126,80,57,58,59,60,61,62,63};
		notes[2] = new int[] {0,110,5,57,58,59,64,65,66,67,68,69,70};
		
		System.out.println("START");
		/*
		for (int[] is : notes) {
			System.out.println(is);
			for (int i : is) {
				System.out.println(i);
			}
			
		}
		*/
		josh.readArray(notes);
		//Thread.sleep(2000);
		josh.play();
		Thread.sleep(8000);
		System.out.println("DONE");
		
		
		
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