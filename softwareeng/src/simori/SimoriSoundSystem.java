package simori;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

public class SimoriSoundSystem extends MIDISoundSystem{


/**
	 * @author Josh
	 * @version 5.0.1
	 * @param array
	 * @return void
	 * @throws InvalidMidiDataException
	 * 
	 * Method takes the input array array of bytes and turns those into a MIDI messages.
	 * These messages are stored in an ArrayList ready to be executed simultaneously.
	 * 
	 * for any given array of array of bytes:
	 * there is 1 command for the first 3 elements
	 * One command for each subsequent element.
	 * As a result the array will be of length (size of individual array -2)* number of arrays
	 * For example an array of arrays [[0,110,80,60]] would contain 2 commands
	 */
	private void readArray(byte[][] array) throws InvalidMidiDataException{
		int commandCounter = 0;	
		// work out the number of midi messages needed for the input array
		for(byte[] layer : array){
			commandCounter += (layer.length - 2);
		}

		noteOnArray = new ShortMessage[commandCounter]; // create an array of that length
		int currentPositionInArray = 0;

		for(int i = 0; i<array.length; i++){
			// for each byte array in the larger array we'll need a command change
			message  = new ShortMessage(); // for some reason constructor does not work in blue room, so setMessage has to be used instead
			message.setMessage(ShortMessage.PROGRAM_CHANGE, array[i][0], array[i][1], 0); // for the given layer set the channel and instrument, the zero is arbitrary (but is needed for correct number of bytes to be sent).
			noteOnArray[currentPositionInArray] = message; // add MIDI message to on array
			currentPositionInArray ++;
			
			// for every note in one of the inner byte arrays we'll need that many messages
			for(int j = 3; j<array[i].length; j++){
				message = new ShortMessage(); // for some reason constructor does not work in blue room, so setMessage has to be used instead
				message.setMessage(ShortMessage.NOTE_ON, array[i][0], array[i][j], array[i][2]); // set a play command for that note with the correct pitch and velocity.
				noteOnArray[currentPositionInArray] = message; // add MIDI message to on array
				currentPositionInArray ++;
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
		for (ShortMessage message : noteOnArray) { // for every message in the MIDI message arrayList:
			reciever.send(message, TIMESTAMP); // send that MIDI message to the synthesizer.
		}
	}
	
	
	/**
	 * @author Josh
	 * @version 1.0.5
	 * @throws InvalidMidiDataException 
	 * {@inheritDoc}
	 */
	//Override
	public void play(byte[][] array) throws InvalidMidiDataException {
		System.out.println(array[0][0] + " "+ array[0][1] + " "+ array[0][2] + " "+ array[0][3]);
		readArray(array); // take the array and turn it into MIDI messages.
		playArray(); //play all the MIDI messages.
	}
	
	

	
	

	
}

