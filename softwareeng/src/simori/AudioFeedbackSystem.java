package simori;

import javax.sound.midi.InvalidMidiDataException;


/**
 * @author Josh
 * @author Adam
 *
 */
public class AudioFeedbackSystem extends MIDIMessengerSystem implements Runnable{
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
	private MatrixModel model;
	private int sound;
	
	
	final static int VELOCITY = 80;
	final static int VELOCITYHIGH = 127;
	
	public AudioFeedbackSystem(MIDISoundSystem player, MatrixModel model, int sound) {
		super(player);
		this.model = model;
		this.sound = sound;
	}
	
	/**
	 * @author Adam
	 * @author Josh
	 * @param type (1 - welcome, 2 - goodbye, 3- happy, 4-sad)
	 * @throws InterruptedException 
	 * @throws InvalidMidiDataException 
	 */
	public void sound(int type) throws InvalidMidiDataException, InterruptedException{
		model.setPlaying();
		switch(type){
			case 1:
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
				break;
			case 2:
				playInstrument(1, C6, VELOCITY, 350, false);
				playInstrument(1, G5, VELOCITY, 400, false);
				playInstrument(1, E5, VELOCITY, 400, false);
				playInstrument(1, C5, VELOCITY, 2500, true);
				player.stopSound();
				break;
			case 3:
				playInstrument(62, C6, VELOCITYHIGH, 250, true);
				playInstrument(62, C6, VELOCITYHIGH, 150, true);
				playInstrument(62, C6, VELOCITYHIGH, 150, true);
				playInstrument(62, G6, VELOCITYHIGH, 1500, true);
				player.stopSound();
				break;
			case 4:
				playInstrument(56, D5, VELOCITYHIGH, 0, false);
				playInstrument(20, D5, VELOCITYHIGH, 400, true);
				
				playInstrument(56, B4, VELOCITYHIGH, 0, false);
				playInstrument(20, B4, VELOCITYHIGH, 600, true);
				
				playInstrument(56, F5, VELOCITYHIGH, 0, false);
				playInstrument(20, F5, VELOCITYHIGH, 2000, true);
				player.stopSound();
				break;	
		}
		model.setPlaying();
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
		AudioFeedbackSystem afs = new AudioFeedbackSystem(player,model,2);
		new Thread(afs).start();
	}

	@Override
	/**
	 * @author Adam
	 */
	public void run() {
		try {
			sound(sound);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
