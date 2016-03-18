package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class AudioFeedbackSystem {
	MIDISoundSystem player;
	
	public AudioFeedbackSystem(MIDISoundSystem player) {
		this.player = player;
	}
	
	
	public static void happyNoise(){
		System.out.println("test");
	}
	
	
	public void stopPlay() throws InvalidMidiDataException{
		player.stopSound();
	}
}
