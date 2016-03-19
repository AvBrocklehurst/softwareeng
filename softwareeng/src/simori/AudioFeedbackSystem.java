package simori;

import javax.sound.midi.InvalidMidiDataException;

public class AudioFeedbackSystem extends MIDIMessengerSystem {
	// see http://www.midimountain.com/midi/midi_note_numbers.html
	final static int B4 = 59;
	final static int C5 = 60;
	final static int CS5 = 61;
	final static int D5 = 62;
	final static int E5 = 64;
	final static int F5 = 65;
	final static int G5 = 67;
	final static int AS5 = 70;
	final static int C6 = 72;
	final static int CS6 = 73;
	final static int G6 = 79;
	
	
	final static int VELOCITY = 80;
	
	public AudioFeedbackSystem(MIDISoundSystem player) {
		super(player);
	}
	

	
	public void welcomeSound() throws InvalidMidiDataException, InterruptedException{
		playInstrument(1, C5, VELOCITY, 100, true);
		playInstrument(1, E5, VELOCITY, 150, true);
		playInstrument(1, G5, VELOCITY, 150, true);
		playInstrument(1, C6, VELOCITY, 150, true);
		Thread.sleep(150);
		playInstrument(1, C5, VELOCITY, 0, false);
		playInstrument(1, E5, VELOCITY, 0, false);
		playInstrument(1, G5, VELOCITY, 0, false);
		playInstrument(1, C6, VELOCITY, 0, false);
		Thread.sleep(2000);
		player.stopSound();
	}
	
	public void goodbyeSound() throws InvalidMidiDataException, InterruptedException{
		playInstrument(1, C6, VELOCITY, 350, false);
		playInstrument(1, G5, VELOCITY, 400, false);
		playInstrument(1, E5, VELOCITY, 400, false);
		playInstrument(1, C5, VELOCITY, 2500, true);
		player.stopSound();
	}
	
	public void happySound() throws InvalidMidiDataException, InterruptedException{
		playInstrument(62, C6, 127, 250, true);
		playInstrument(62, C6, 127, 150, true);
		playInstrument(62, C6, 127, 150, true);
		playInstrument(62, G6, 127, 1500, true);
		player.stopSound();
		//System.out.println("hello");
	}
	
	public void sadSound() throws InvalidMidiDataException, InterruptedException{
		playInstrument(56, D5, 127, 0, false);
		playInstrument(20, D5, 127, 400, true);
		
		playInstrument(56, B4, 127, 0, false);
		playInstrument(20, B4, 127, 600, true);
		
		playInstrument(56, F5, 127, 0, false);
		playInstrument(20, F5, 127, 2000, true);
		player.stopSound();
	}
	

	
	void playInstrument(int instrument, int pitch, int velocity, int duration, boolean stop) throws InvalidMidiDataException, InterruptedException{
		player.sendCommand(createMessage((byte)0, (byte)(instrument-1)));
		player.sendCommand(createMessage((byte)0,(byte)pitch, (byte)velocity));
		Thread.sleep(duration);
		if(stop){player.stopSound();}
	}
	void playPercussion(int percussion, int velocity, int duration, boolean stop ) throws InvalidMidiDataException, InterruptedException{
		player.sendCommand(createMessage((byte)9,(byte)percussion,(byte)velocity));
		Thread.sleep(duration);
		if(stop){player.stopSound();}
	}
	
	public static void main(String[] args) throws InvalidMidiDataException, InterruptedException {
		MIDISoundSystem player = new MIDISoundSystem();
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player);
		afs.welcomeSound();
		afs.goodbyeSound();
		
		afs.happySound();
		
		afs.sadSound();
		
		
		System.out.println("done");
	}
	
}
