package simori;

import javax.sound.midi.InvalidMidiDataException;

/**
 * 
 * @author Josh
 * @version 1.1.4
 * 
 * Interface that describes the public methods available to accessing and playing MIDI sounds.
 * 
 */
public interface MIDIPlayer {
	/**
	 * @author Josh
	 * @version 1.0.4
	 * @param array
	 * @return void
	 * 
	 * Method will take an array of arrays which will contain (at most) 16 arrays of bytes.
	 * Each one of these arrays corresponds to a single layer in the Simori.
	 * 
	 * Each one of the sub-array of bytes will contain the following information:
	 * First element: MIDI channel 0-15 (1-16).
	 * Second element: MIDI instrument (e.g. 0 for piano, 127 for gunshot, 39 for clap if channel 9).
	 * Third element: MIDI velocity (for that layer 0-127).
	 * All subsequent elements: Notes to be played, the values correspond to the pitch that needs to be played (0-127).
	 * Since there are 16 rows, there can be a maximum of 16 notes, hence the array can have a maximum length of 19.
	 * 
	 * Method will play all notes simultaneously(or as close to simultaneous as possible).
	 */
	public void play(byte[][] array)  throws InvalidMidiDataException;
}


