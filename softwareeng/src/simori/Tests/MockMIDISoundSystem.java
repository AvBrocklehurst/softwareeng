package simori.Tests;

import javax.sound.midi.ShortMessage;
import simori.MIDISoundSystem;

/**
 * 
 * @author Josh
 * @version 2.0.1
 * 
 * Class that increases visibility of MIDISoundSystem.
 * Used for testing.
 */
public class MockMIDISoundSystem extends MIDISoundSystem{
	
	// This constructor does not load in the external sound bank, saving time
	public MockMIDISoundSystem(boolean soundbank) {
		super(soundbank);
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
	public boolean hasActiveReceivers(){return !synth.getReceivers().isEmpty();}
	
}
