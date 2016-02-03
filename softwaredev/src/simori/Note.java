package simori;

import simori.Exceptions.InvalidMIDIChannelException;
import simori.Exceptions.InvalidMIDIInstrumentException;
import simori.Exceptions.InvalidMIDIPitchException;
import simori.Exceptions.InvalidMIDIVelocityException;

public class Note {
	private int channel;
	private int instrument;
	private int pitch;
	private int velocity;
	
	/*
	public static final int A3 = 57;
	public static final int A3SHARP = 58;
	public static final int B3 = 59;
	public static final int C4 = 60;
	public static final int C4SHARP = 61;
	public static final int D4 = 62;
	public static final int D4SHARP = 63;
	public static final int E4 = 64;
	public static final int F4 = 65;
	public static final int F4SHARP = 66;
	public static final int G4 = 67;
	public static final int G4SHARP = 68;
	public static final int A4 = 69;
	public static final int A4SHARP = 70;
	public static final int B4 = 71;
	public static final int C5 = 72;
	*/
	
	public Note(int channel, int instrument, int pitch, int velocity ) throws InvalidMIDIChannelException, InvalidMIDIInstrumentException, InvalidMIDIPitchException, InvalidMIDIVelocityException {
		if(!(0<=channel && channel<= 15)){throw new InvalidMIDIChannelException("channel has value: " + channel + "needs to be between 0 and 15");}
		if(!(0<=instrument && instrument<= 127)){throw new InvalidMIDIInstrumentException("instrument has value: " + instrument + "needs to be between 0 and 127");}
		if(!(0<=pitch && pitch<= 15)){throw new InvalidMIDIPitchException("pitch has value: " + pitch + "needs to be between 0 and 15");}
		if(!(0<=velocity && velocity<= 127)){throw new InvalidMIDIVelocityException("velocity has value: " + velocity + "needs to be between 0 and 127");}
		
		this.channel = channel;
		this.instrument = instrument;
		this.velocity = velocity;
		this.pitch = pitch + 57;
		}
	
	public int getChannel(){return channel;}
	
	public int getInstrument(){return instrument;}
	
	public int getPitch(){return pitch;}
	
	public int getVelocity(){return velocity;}
	
	
}
	

