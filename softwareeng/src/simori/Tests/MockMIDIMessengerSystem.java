package simori.Tests;

import javax.sound.midi.ShortMessage;

import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;

/**
 * 
 * @author Josh
 * @version 1.2.0
 * 
 * Class that increases visibility of MIDISoundSystem.
 * Used for testing.
 * Since MIDIMessengerSystem is abstract I shall be using AudioFeedbackSystem (a concrete class) to access it.
 */
public class MockMIDIMessengerSystem extends AudioFeedbackSystem {
	  
	public MockMIDIMessengerSystem(MIDISoundSystem player, MatrixModel model) {
		super(player, model);
	}
	
	// changing the parameters to ints means you have to cast it bytes every time you want to use it!
	 public ShortMessage createMessage(int channel, int instrument) {
		return super.createMessage((byte)channel, (byte)instrument);
	}
	
	 // changing the parameters to ints means you have to cast it bytes every time you want to use it!
	 public ShortMessage createMessage(int channel, int pitch, int velocity) {
		return super.createMessage((byte)channel, (byte)pitch, (byte)velocity);
	}

}
