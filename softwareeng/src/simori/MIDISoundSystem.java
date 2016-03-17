package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import simori.Simori.PowerTogglable;

/**
 * Please note: As discussed with Dave the Blue Room doesn't correctly play percussion instruments.
 * As a result this code works with multiple laptops,PC's etc but doesnt work properly in blue room.
 * (It just plays a clicking noise)
 * 
 * 
 * @author Josh aka the music man
 * @version 2.2.1
 *
 * Class that contains all access to playing sound.
 * This class is meant to act as the one place that all sound 'goes through' in order to be played,
 * regardless of whether it is a from a layer in the Simori or from a 'jingle' in the audio feedback.
 * {@link ShortMessage}
 */
public class MIDISoundSystem implements PowerTogglable {

	final static int TIMESTAMP = -1; // Timestamp of -1 means MIDI messages will be executed immediately.
	Synthesizer synth;
	Receiver reciever;
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * @throws MidiUnavailableException
	 * 
	 * Constructor that creates a MIDISynthesizer with a connected receiver that can send MIDI short messages to the synthesizer.
	 */
	public MIDISoundSystem(){
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			reciever = synth.getReceiver();
			} catch (MidiUnavailableException e) {e.printStackTrace(); System.exit(1);}
		if (synth == null){System.exit(1);}
		if (reciever == null){System.exit(1);}
		}
	
	public void sendCommand(ShortMessage message){
		reciever.send(message, TIMESTAMP);
	}
	
	/**
	 * Method takes arrayList of MIDI messages and executes them simultaneously (or near simultaneous).
	 * @param toBePlayed
	 */
	public void sendCommands(ShortMessage[] toBePlayed){
		for (ShortMessage message : toBePlayed) {
			sendCommand(message);
		}
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.2
	 * @throws InvalidMidiDataException 
	 * {@inheritDoc}
	 */
	public void stopSound() throws InvalidMidiDataException {
		synth.getChannels()[0].allNotesOff();
		synth.getChannels()[9].allNotesOff();
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * {@inheritDoc}
	 */
	@Override
	public void switchOn() {
		try {
			synth.open();
			reciever = synth.getReceiver();
		} catch (MidiUnavailableException e) {e.printStackTrace();System.exit(1);}
		
	}

	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * {@inheritDoc}
	 */
	@Override
	public void switchOff() {
		reciever.close();
		synth.close();	
	}
	
	
	
	/* FROM INTERFACE
	 * 	/**
	 * @author Josh
	 * @version 1.0.0
	 * @return void
	 * 
	 * Method that takes any playing notes and stops them playing.
	 * Is expected to be used after a play method.
	 *
	public void stopPlay() throws InvalidMidiDataException;
	 */
	
}