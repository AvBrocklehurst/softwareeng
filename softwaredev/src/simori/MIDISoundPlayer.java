package simori;


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
	private int[] channelsPreviousInstruments;
	Synthesizer synth;
	Receiver reciever;
	final static int TIMESTAMP = -1;
	
	
	public MIDISoundPlayer() throws MidiUnavailableException {
		channelsPreviousInstruments = new int[16];
		for (int i : channelsPreviousInstruments) {channelsPreviousInstruments[i] =0;}
		
		synth  = MidiSystem.getSynthesizer();
		synth.open();
		reciever = synth.getReceiver();
	}

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

}
