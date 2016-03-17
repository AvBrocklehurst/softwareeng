package simori;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class AudioFeedbackSystem {
	
	final static int TIMESTAMP = -1; // Timestamp of -1 means MIDI messages will be executed immediately.
	private Synthesizer synth;
	private Receiver reciever;
	private ShortMessage[] noteOnArray; // array that will hold all MIDI messages for a single tick.
	private ShortMessage message;
	
	public AudioFeedbackSystem() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			reciever = synth.getReceiver();
			} catch (MidiUnavailableException e) {e.printStackTrace(); System.exit(1);}
		if (synth == null){System.exit(1);}
		if (reciever == null){System.exit(1);}
		}
	
	
	
	public static void happyNoise(){
		System.out.println("test");
	}
}
