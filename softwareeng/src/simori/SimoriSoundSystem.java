package simori;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

/**
 * @author Josh aka the music man
 * @version 8.1.0
 * {@link simori.SimoriSound}
 * {@link ShortMessage}
 * 
 * Class that converts information from the Simori layers into midi messages (which in turn make noise).
 * NOTE: This class is designed to have the lowest overhead as possible.
 * This is because it is played after the clock, i.e. if it takes too long to process it may become out of sync with the rest of the system.
 * The amount of time it takes to do .play(Array) is ideally zero.
 * As a result there is little to no error checking in this class. All error checking is done before this method is played (whilst it is still in sync with the clock)
 */
public class SimoriSoundSystem extends MIDIMessengerSystem {
	
	/**
	 * @author Josh 
	 * @param player
	 * 
	 * Constructor for SimoriSoundSystem
	 */
	public SimoriSoundSystem(MIDISoundSystem player) {
		super(player);
	}
	
	/**
	 * @author Josh
	 * @version 5.0.1
	 * @param simoriLayers - Array of array of bytes that comes from the simori.
	 * @return ShortMessage[] - Array of shortMessages that can be sent to the synth.
	 * @throws InvalidMidiDataException
	 * 
	 * Method takes an array of arrays which will contain (at most) 16 arrays of bytes.
	 * Each one of these arrays corresponds to a single layer in the Simori.
	 * Method takes the input array array of bytes and turns those into a MIDI messages.
	 * These messages are stored in an array ready to be executed simultaneously.
	 * 
	 * Each one of the sub-array of bytes will contain the following information:
	 * First element: MIDI channel 0-15 (1-16).
	 * Second element: MIDI instrument (e.g. 0 for piano, 127 for gunshot, 39 for clap if channel 9).
	 * Third element: MIDI velocity (for that layer 0-127).
	 * All subsequent elements: Notes to be played, the values correspond to the pitch that needs to be played (0-127).
	 * Since there are 16 rows, there can be a maximum of 16 notes, hence the array can have a maximum length of 19.
	 * 
	 * For any given array of array of bytes:
	 * There is 1 command for the first 3 elements.
	 * One command for each subsequent element.
	 * As a result the array will be of length (size of individual array -2)* number of arrays.
	 * For example an array of arrays [[0,110,80,60]] would contain 2 commands.
	 * As a result it possible to calculate the length of the ShortMessage array (so dont need arrayList).
	 */
	private ShortMessage[] convertToMIDIMessages(byte[][] simoriLayers) {
		ShortMessage message;
		ShortMessage[] toBePlayedArray = new ShortMessage[calculateMIDIArraySize(simoriLayers)]; // create an array of that length
		
		int currentPositionInArray = 0;
		for(int i = 0; i<simoriLayers.length; i++){
			// for each byte array in the larger array we'll need a command change
			message = createMessage(simoriLayers[i][0], simoriLayers[i][1]); // set the commandChange message
			toBePlayedArray[currentPositionInArray] = message; // add MIDI message to  array
			currentPositionInArray ++;
			
			// for every note in one of the inner byte arrays we'll need that many messages
			for(int j = 3; j<simoriLayers[i].length; j++){
				message = createMessage(simoriLayers[i][0], simoriLayers[i][j],  simoriLayers[i][2]); // set a play command for that note with the correct pitch and velocity.
				toBePlayedArray[currentPositionInArray] = message; // add MIDI message to on array
				currentPositionInArray ++;
			}
		}
		return toBePlayedArray;
	}
	
	/**
	 * @author Josh 
	 * @param array - simori layer array
	 * @return int - number of commands for the array
	 * @version 1.1.1
	 * 
	 * Method takes a simori layer array and works out how many MIDIMessages are needed for it.
	 */
	private int calculateMIDIArraySize(byte[][] array){
		int commandCounter = 0;	
		for(byte[] layer : array){
			commandCounter += (layer.length - 2);
		}
		return commandCounter;
	}
	
	/**
	 * @author Josh
	 * @version 2.0.5
	 * @throws InvalidMidiDataException 
	 * 
	 * Method that takes a simori layer array, converts it into MIDIMessages and then plays those notes.
	 */
	public void play(byte[][] array) {
		System.out.println(array[0][3]);
		player.sendCommands(convertToMIDIMessages(array)); // take the array and turn it into MIDI messages, then send it to the synth.
	}
	
}

