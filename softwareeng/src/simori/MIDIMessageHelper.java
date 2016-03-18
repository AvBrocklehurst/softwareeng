package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

abstract class MIDIMessageHelper {
	MIDISoundSystem player;
	
	public MIDIMessageHelper(MIDISoundSystem player) {
		this.player = player;
	}
	
	
	ShortMessage createMessage(byte channel, byte instrument) throws InvalidMidiDataException{
		ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0);
		return message;
	}
	ShortMessage createMessage(byte channel, byte pitch, byte velocity) throws InvalidMidiDataException{
		ShortMessage message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, channel, pitch, velocity);
		return message;
	}
	
	
	void stopPlay() throws InvalidMidiDataException{
		player.stopSound();
	}
	
	
}
