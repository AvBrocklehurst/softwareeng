package simori;

import javax.sound.midi.InvalidMidiDataException;


/**
 * @author Josh
 * @author Adam
 */
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
	final static int VELOCITYHIGH = 127;
	
	private MatrixModel model;
	
	public AudioFeedbackSystem(MIDISoundSystem player, MatrixModel model) {
		super(player);
		this.model = model;
	}
	
	public enum Sound {
		WELCOME, GOODBYE, HAPPY, SAD
	}
	
	public void play(final Sound sound) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				playSynchronous(sound);
			}
		}).start();
	}
	
	public void playSynchronous(Sound sound) {
		model.setPlaying();
		try {
			switch(sound){
			case WELCOME:
				playWelcomeSound();
				break;
			case GOODBYE:
				playGoodbyeSound();
				break;
			case HAPPY:
				playHappySound();
				break;
			case SAD:
				playSadSound();
				break;
			}
			player.stopSound();
		} catch (InterruptedException e) {
			
		} catch (InvalidMidiDataException e) {
			
		}
		model.setPlaying();
	}
	
	private void playWelcomeSound() throws InvalidMidiDataException, InterruptedException {
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
	}
	
	private void playSadSound() throws InvalidMidiDataException, InterruptedException {
		playInstrument(56, D5, VELOCITYHIGH, 0, false);
		playInstrument(20, D5, VELOCITYHIGH, 400, true);
		
		playInstrument(56, B4, VELOCITYHIGH, 0, false);
		playInstrument(20, B4, VELOCITYHIGH, 600, true);
		
		playInstrument(56, F5, VELOCITYHIGH, 0, false);
		playInstrument(20, F5, VELOCITYHIGH, 2000, true);
	}
	
	private void playGoodbyeSound() throws InvalidMidiDataException, InterruptedException {
		playInstrument(1, C6, VELOCITY, 350, false);
		playInstrument(1, G5, VELOCITY, 400, false);
		playInstrument(1, E5, VELOCITY, 400, false);
		playInstrument(1, C5, VELOCITY, 2500, true);
	}
	
	private void playHappySound() throws InvalidMidiDataException, InterruptedException {
		playInstrument(62, C6, VELOCITYHIGH, 250, true);
		playInstrument(62, C6, VELOCITYHIGH, 150, true);
		playInstrument(62, C6, VELOCITYHIGH, 150, true);
		playInstrument(62, G6, VELOCITYHIGH, 1500, true);
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
		MatrixModel model = new MatrixModel(16,16);
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player, model);
		afs.play(Sound.WELCOME);
		for (int i = 0; i < 6; i ++) {
			System.out.println(i % 2 == 0 ? "Printing" : "whilst playing");
			Thread.sleep(200);
		}
	}
}
