package simori;

import javax.sound.midi.InvalidMidiDataException;

public class AudioFeedbackSystem extends MIDIMessageHelper {
	
	public AudioFeedbackSystem(MIDISoundSystem player) {
		super(player);
	}
	
	/*public AudioFeedbackSystem(MIDISoundSystem player) {
		this.player = player;
	}*/
	
	
	public void happyNoise(){
		System.out.println("test");
	}
	
	


	// welcome sound
	// good bye sound
	// happy sound
	// sad sound
	
	void playInstrument(byte instrument, byte pitch, byte velocity) throws InvalidMidiDataException{
		player.sendCommand(createMessage((byte)0, instrument));
		player.sendCommand(createMessage((byte)0,pitch,velocity));
	}
	void playPercussion(byte percussion, byte velocity ) throws InvalidMidiDataException{
		player.sendCommand(createMessage((byte)9,percussion,velocity));
	}
	
	public static void main(String[] args) throws InvalidMidiDataException, InterruptedException {
		MIDISoundSystem player = new MIDISoundSystem();
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player);
		afs.playPercussion((byte)81, (byte)80);
		afs.playPercussion((byte)39,(byte) 80);
		Thread.sleep(2000);
		/*
		afs.playInstrument((byte)0, (byte)60, (byte)80);
		afs.playInstrument((byte)0, (byte)64, (byte)80);
		afs.playInstrument((byte)0, (byte)67, (byte)80);
		Thread.sleep(1000);
		afs.stopPlay();
		Thread.sleep(1000);
		afs.playInstrument((byte)110, (byte)60, (byte)80);
		Thread.sleep(500);
		afs.stopPlay();
		*/
		
		/*afs.playInstrument((byte)110, (byte)60, (byte)80);
		Thread.sleep(1000);
		afs.playPercussion((byte)72, (byte)80);
		Thread.sleep(200);
		afs.playInstrument((byte)57, (byte)60, (byte)80);
		afs.playPercussion((byte)39, (byte)80);
		Thread.sleep(200);
		afs.playPercussion((byte)39, (byte)80);
		Thread.sleep(200);
		afs.playPercussion((byte)39, (byte)80);
		Thread.sleep(1000);*/
		System.out.println("DONE");
	}
	
}
