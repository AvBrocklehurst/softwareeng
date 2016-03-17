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
 * Please note: Whilst it may not appear like a lot of work has been done (making it an array instead of arrayList and the stopPlay method)
 * I assure you it look a lot of debugging, testing and implementing time!
 * 
 * @author Josh aka the music man
 * @version 6.2.4
 * {@link simori.MIDIPlayer}
 * {@link ShortMessage}
 * 
 * Class that implements the MIDIPlayer interface.
 * NOTE: This class is designed to have the lowest overhead as possible.
 * This is because it is played after the clock, i.e. if it takes too long to process it may become out of sync with the rest of the system.
 * The amount of time it takes to do .play(Array) is ideally zero.
 * As a result there is little to no error checking in this class. All error checking is done before this method is played (whilst it is still in sync with the clock)
 */
public class MIDISoundSystem implements PowerTogglable {

	final static int TIMESTAMP = -1; // Timestamp of -1 means MIDI messages will be executed immediately.
	Synthesizer synth;
	Receiver reciever;
	ShortMessage[] noteOnArray; // array that will hold all MIDI messages for a single tick.
	ShortMessage message;
	
	
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
	
	/**
	 * @author Josh
	 * @version 1.0.2
	 * @throws InvalidMidiDataException 
	 * {@inheritDoc}
	 */
	//Override
	public void stopPlay() throws InvalidMidiDataException {
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
			message = null; // just in case there is something stored in message
			noteOnArray = null; // just in case there is something stored in the array
		} catch (MidiUnavailableException e) {e.printStackTrace();System.exit(1);}
		
	}

	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * {@inheritDoc}
	 */
	@Override
	public void switchOff() {
		message = null; // just in case there is something stored in message
		noteOnArray = null; // just in case there is something stored in the array
		reciever.close();
		synth.close();	
	}
	
}