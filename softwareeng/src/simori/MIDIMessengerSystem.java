package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import simori.Exceptions.SimoriNonFatalException;

/**
 * @author Josh
 * @version 3.2.0
 * @see ShortMessage
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
	 * Method that creates a PROGRAM_CHANGE message.
	 */
	protected ShortMessage createMessage(byte channel, byte instrument) {
		ShortMessage message = new ShortMessage();
		try {
			message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
		} catch (InvalidMidiDataException e) {
			throw new SimoriNonFatalException("Invalid data was sent to the Midi.");
		}
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
	 * Method that creates a NOTE_ON message.
	 */
	protected ShortMessage createMessage(byte channel, byte pitch, byte velocity) {
		ShortMessage message = new ShortMessage();
		try {
			message.setMessage(ShortMessage.NOTE_ON, channel, pitch, velocity);
		} catch (InvalidMidiDataException e) {
			throw new SimoriNonFatalException("Invalid data was sent to the Midi.");
		}
		return message;
	}
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * 
	 * Method that provides access for stopping sound (used by rest of the simori system).
	 */
	public void stopPlay() {
		player.stopSound();
	}
}