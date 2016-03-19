package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

/**
 * @author Josh
 * @version 3.2.0
 * 
 * Class that provides methods for constructing MIDIMessages
 */
abstract class MIDIMessengerSystem {
	MIDISoundSystem player;
	
	/**
	 * @author Josh
	 * @param player - MIDISoundSystem (something that can play noise).
	 * @version 1.0.0
	 * 
	 * Constructor for MIDIMessengerSystem
	 */
	public MIDIMessengerSystem(MIDISoundSystem player) {
		this.player = player;
	}
	
	/**
	 * @author Josh
	 * @param channel
	 * @param instrument
	 * @return
	 * @throws InvalidMidiDataException
	 * @version 1.0.1
	 * 
	 * Method that creates a program change message.
	 */
	ShortMessage createMessage(byte channel, byte instrument) throws InvalidMidiDataException{
		ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
		return message;
	}
	
	/**
	 * @author Josh
	 * @param channel
	 * @param instrument
	 * @return
	 * @throws InvalidMidiDataException
	 * @version 1.0.1
	 * 
	 * Method that creates a note on message.
	 */
	ShortMessage createMessage(byte channel, byte pitch, byte velocity) throws InvalidMidiDataException{
		ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, channel, pitch, velocity);
		return message;
	}
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * 
	 * Method that provides access for stopping sound (used by rest of the simori system).
	 */
	public void stopPlay() throws InvalidMidiDataException{
		player.stopSound();
	}
	
	
}