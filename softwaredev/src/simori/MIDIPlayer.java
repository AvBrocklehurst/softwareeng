package simori;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import simori.Note;

/**
 * 
 * @author Josh
 * @version 1.0.0
 * 
 * Interface that describes the public methods available to accessing and playing MIDI sounds
 *
 */
public interface MIDIPlayer {
	
	/**
	 * @author Josh
	 * @param None 
	 * @return Synthesizer
	 * @throws MidiUnavailableException
	 * 
	 * Produces a MIDI synthesizer (for system to use) with an attached receiver that can send messages to the synthesizer
	 * 
	 */
	public Receiver getSynthesizer() throws MidiUnavailableException;
	
	
	/**
	 * @author Josh
	 * @param notes
	 * @return void
	 * 
	 * Method will take a list of all notes (where a note consists of a channel, instrument, pitch and velocity)
	 * Method will play all notes simulatenously(or as close to simulatneous as possible)
	 */
	public void playNotes(Note[] notes);
	
	

}
