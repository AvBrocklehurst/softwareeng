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

import simori.Exceptions.SimoriNonFatalException;
import simori.Simori.PowerTogglable;

/**
 * TODO CHANGE THIS IF IT DOES WORK IN BLUE ROOM DUE TO NEW SOUNDBANK!
 * Please note: As discussed with Dave the Blue Room doesn't correctly play percussion instruments.
 * As a result this code works with multiple laptops,PC's etc but doesnt work properly in blue room.
 * (It just plays a clicking noise)
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
	protected Synthesizer synth;
	protected Receiver reciever;
	
	/**
	 * @author Josh
	 * @version 1.0.1
	 * @param should I use the better soundbank? (Takes longer due to loading)
	 * @throws MidiUnavailableException
	 * 
	 * Constructor that creates a MIDISynthesizer with a connected receiver that can send MIDI short messages to the synthesizer.
	 */
	public MIDISoundSystem(boolean useBetterSound){
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			if(useBetterSound){
			synth.unloadAllInstruments(synth.getDefaultSoundbank()); 
			setSoundbank(); // change to new sound bank.
			}
			reciever = synth.getReceiver();
			
		} catch (MidiUnavailableException e) {throw new SimoriNonFatalException("MidiSystem is unavailable. You will be unable to play sound.");}
		if (synth == null) throw new SimoriNonFatalException("MidiSystem is unavailable. You will be unable to play sound.");
		if (reciever == null) throw new SimoriNonFatalException("MidiSystem is unavailable. You will be unable to play sound.");
	}
	
	/**
	 * @author Josh
	 * @version 1.0.2
	 * 
	 * Method that loads in a soundbank (This means music will play ok in the blue room).
	 */
	private void setSoundbank() {
		Soundbank sb = null;
		File file = ResourceManager.getResource("bestSoundbank.SF2");
		if (file == null) {
			System.err.println("Could not find Res Folder.");
			return;
		}
		if (!file.exists()) {
			System.err.println("Could not find Sound bank.");
			return;
		}
		try {
			sb = MidiSystem.getSoundbank(file); // Make the MidiSystem use this loaded in soundbank
		} catch (InvalidMidiDataException e) {
			throw new SimoriNonFatalException("Invalid data was sent to the Midi.");
		} catch (IOException e) {
			throw new SimoriNonFatalException("Could not open an I/O Stream.");
		}
		synth.loadAllInstruments(sb);
	}
	
	/**
	 * @author Josh
	 * @param message
	 * @version 1.0.0 
	 * 
	 * Method takes a MIDIMessage and sends it to the synth.
	 */
	protected void sendCommand(ShortMessage message){
		reciever.send(message, TIMESTAMP);
	}
	
	/**
	 * @author Josh
	 * @param toBePlayed
	 * @version 1.1.0
	 * 
	 * Method takes arrayList of MIDI messages and sends them to the synth simultaneously (or near simultaneously).
	 */
	protected void sendCommands(ShortMessage[] toBePlayed){
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
	protected void stopSound(){
		synth.getChannels()[0].allNotesOff(); // these are the only channels that are ever used.
		synth.getChannels()[9].allNotesOff();
	}
	
	
	/**
	 * Check if a synth is open.
	 * @return boolean, true if synth is open.
	 */
	public boolean isOpen(){
		if(synth == null) return false;
		if(reciever == null) return false;
		return synth.isOpen();
	}
	
	/** {@inheritDoc} */
	@Override
	public void ready() {
		try {
			synth.open();
			reciever = synth.getReceiver();
		} catch (MidiUnavailableException e) {
			throw new SimoriNonFatalException("Midi Player was unable to be opened. You will be unable to play sound.");
		}
	}
	
	/** {@inheritDoc}  */
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
}