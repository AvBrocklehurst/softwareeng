package simori;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

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
	
	
	public static void main(String[] args) throws InvalidMidiDataException, InterruptedException {
		MIDISoundSystem player = new MIDISoundSystem();
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player);
		afs.test();
		Thread.sleep(1000);
	}

	// welcome sound
	// good bye sound
	// happy sound
	// sad sound
	public void createMessage(int command, byte a , byte b, byte c){
		
	}
	
	
	
	public void test() throws InvalidMidiDataException, InterruptedException{
		ShortMessage message = new ShortMessage();
		ShortMessage[] messages = new ShortMessage[2];
		message.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 110, 0);
		messages[0] = message;
		message = new ShortMessage();
		message.setMessage(ShortMessage.NOTE_ON, 0, 60, 80);
		messages[1] = message;
		player.sendCommands(messages);
	}
	
}
