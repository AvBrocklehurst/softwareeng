package simori;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import simori.Simori.PowerTogglable;

/**
 * Please note: As discussed with Dave the Blue Room doesn't correctly play percussion instruments.
 * As a result this code works with multiple laptops,PC's etc but doesnt work properly in blue room.
 * (It just plays a clicking noise)
 * 
 * 
 * @author Josh aka the music man
 * @version 2.2.1
 *
 * Class that contains all access to playing sound.
 * This class is meant to act as the one place that all sound 'goes through' in order to be played,
 * regardless of whether it is a from a layer in the Simori or from a 'jingle' in the audio feedback.
 */
public class MIDISoundSystem implements PowerTogglable {

	final static int TIMESTAMP = -1; // Timestamp of -1 means MIDI messages will be executed immediately.
	private Synthesizer synth;
	private Receiver reciever;
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * @throws MidiUnavailableException
	 * 
	 * Constructor that creates a MIDISynthesizer with a connected receiver that can send MIDI short messages to the synthesizer.
	 */
	public MIDISoundSystem(){
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			
			synth.unloadAllInstruments(synth.getDefaultSoundbank());
			setSoundbank();
			reciever = synth.getReceiver();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if (synth == null) System.exit(1);
		if (reciever == null) System.exit(1);
	}
	
	private void setSoundbank() {
		Soundbank sb = null;
		File file = ResourceManager.getResource("goodSoundbank.SF2");
		if (file == null) {
			System.err.println("Could not find res folder!");
			return;
		}
		if (!file.exists()) {
			System.err.println("Could not find soundbank file!");
			return;
		}
		try {
			sb = MidiSystem.getSoundbank(file);
		} catch (InvalidMidiDataException e) {
		} catch (IOException e) {
		}
		synth.loadAllInstruments(sb);
		System.out.println(sb.toString());
		System.out.println("Soundbank Loaded");
	}
	
	/**
	 * @author Josh
	 * @param message
	 * @version 1.0.0 
	 * 
	 * Method takes a MIDIMessage and sends it to the synth.
	 */
	void sendCommand(ShortMessage message){
		reciever.send(message, TIMESTAMP);
	}
	
	/**
	 * @author Josh
	 * @param toBePlayed
	 * @version 1.1.0
	 * 
	 * Method takes arrayList of MIDI messages and sends them to the synth simultaneously (or near simultaneously).
	 */
	void sendCommands(ShortMessage[] toBePlayed){
		for (ShortMessage message : toBePlayed) {
			sendCommand(message);
		}
	}
	
	/**
	 * @author Josh
	 * @version 1.0.2
	 * 
	 * Method that tells the synth to stop making noise ASAP.
	 */
	void stopSound(){
		synth.getChannels()[0].allNotesOff();
		synth.getChannels()[9].allNotesOff();
	}
	
	/** {@inheritDoc} */
	@Override
	public void ready() {
		try {
			synth.open();
			reciever = synth.getReceiver();
		} catch (MidiUnavailableException e) {e.printStackTrace();System.exit(1);}
	}
	
	/** {@inheritDoc} 
	 * @author Jurek
	 */
	@Override
	public void switchOn() {
		ready();
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop() {}
	
	/** {@inheritDoc} */
	@Override
	public void switchOff() {
		reciever.close();
		synth.close();
	}
	
	public static void main(String[] args) throws InvalidMidiDataException, InterruptedException {
		MIDISoundSystem josh = new MIDISoundSystem();
		ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON,0,60,80);
		josh.sendCommand(message);
		Thread.sleep(1000);
	}
}