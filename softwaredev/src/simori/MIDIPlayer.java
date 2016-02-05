package simori;

import javax.sound.midi.InvalidMidiDataException;
import java.util.ArrayList;

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
	 * 
	 * Constructor will create a 
	 */
	
	/**
	 * @author Josh
	 * @param array
	 * @return void
	 * 
	 * Method will take an arrayList which will contain (at most) 16 arrayList of Integers.
	 * Each one of these arrays corresponds to a single layer in the Simori.
	 * 
	 * Each one of the sub-ArrayLists will contain the following information:
	 * First element: MIDI channel 0-15 (1-16)
	 * Second element: MIDI instrument (e.g. 0 for piano, 127 for gunshot, 39 for clap if channel 9)
	 * Third element: MIDI velocity (for that layer)
	 * All subsequent elements: Notes to be played, the values correspond to the pitch that needs to be played
	 * 
	 * Method will play all notes simulatenously(or as close to simulatneous as possible)
	 */
	public void play(ArrayList<ArrayList<Byte>> array) throws InvalidMidiDataException, InterruptedException;

	
}
