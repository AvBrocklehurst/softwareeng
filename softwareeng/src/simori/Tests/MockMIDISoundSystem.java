package simori.Tests;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import simori.MIDISoundSystem;
import simori.Exceptions.SimoriNonFatalException;

/**
 * 
 * @author Josh
 * @version 2.0.1
 * 
 * Class that increases visibilites of MIDISoundSystem.
 * Used for testing.
 */
public class MockMIDISoundSystem extends MIDISoundSystem{
	
	// This constructor does not load in the external sound bank, saving time
	public MockMIDISoundSystem() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			reciever = synth.getReceiver();
			
		} catch (MidiUnavailableException e) {throw new SimoriNonFatalException("MidiSystem is unavailable. You will be unable to play sound.");}
		if (synth == null) System.exit(1);
		if (reciever == null) System.exit(1);
	}
	
	@Override
	public void sendCommand(ShortMessage message){
		super.sendCommand(message);
	}
	
	@Override
	public void sendCommands(ShortMessage[] toBePlayed) {
		super.sendCommands(toBePlayed);
	}
	
	@Override
	public void stopSound() {
		super.stopSound();
		
	}
	
	//checks to see if synth is on
	public boolean isSynthOn(){return synth.isOpen();}
	
	//checks to see if synth has any active recievers (ie not closed) attached to it. 
	public int hasActiveReceivers(){return synth.getReceivers().size();}
	
}
