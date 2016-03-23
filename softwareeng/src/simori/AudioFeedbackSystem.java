package simori;
import simori.Exceptions.SimoriNonFatalException;

/**
 * @author Josh
 * @author Adam
 * @version 2.1.1
 * @see MIDIMessengerSystem
 * @see MIDISoundSystem
 * 
 * Class responsible for playing the audio feedback sounds.
 * Since these events are dynamic (can play at any time), the class needs to be in its own thread.
 * This allows it to play noise 'over the top' of the Simori.
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
	
	/**
	 * @author Josh
	 * @author Adam
	 * @version 1.0.2
	 * @param player - MIDISoundSystem (something that can actually play noise)
	 * @param model - Matrix model
	 * 
	 * Constructor for AudioFeedbackSystem.
	 */
	public AudioFeedbackSystem(MIDISoundSystem player, MatrixModel model) {
		super(player);
		this.model = model;
	}
	
	/**
	 * 
	 * @author Josh
	 * @version 1.0.0
	 * 
	 * Enum for different audio feedback
	 */
	public enum Sound {WELCOME, GOODBYE, HAPPY, SAD}
	
	/**
	 * @author Adam
	 * @param sound
	 * @version 1.0.0
	 * 
	 * Method that plays a sound (in its own thread).
	 */
	public void play(final Sound sound) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				playSynchronous(sound);
			}
		}).start();
	}
	
	/**
	 * @author Adam
	 * @author Josh 
	 * @param sound
	 * @version 1.1.0
	 * 
	 * Method that plays one of 4 audio sounds, depending on the enum.
	 * Method also prevents other sound system (the usual Simori) from playing noise whilst the audio is being played.
	 */
	private void playSynchronous(Sound sound) {
		model.setPlaying(); // tell the usual music player to shut up
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
			player.stopSound(); // stop the audio sound being played (in case it is still making noise).
		} catch (InterruptedException e) {
			throw new SimoriNonFatalException("Audio Thread was inturrupted.");
		}
		model.setPlaying(); // tell the usual music player that it can carry on making noise.
	}
	
	/**
	 * @author Adam
	 */
	public boolean isOpen(){
		return player.isOpen();
	}
	
	/**
	 * @author Josh - I am the music man ...
	 * @throws InvalidMidiDataException
	 * @throws InterruptedException
	 * @version 2.0.0
	 *  
	 * "a short, distinctive sequence of notes that fills the user with a sense of joyful anticipation at what is to come."
	 */
	private void playWelcomeSound() throws InterruptedException {
		playInstrument(1, C5, VELOCITY, 100, true);
		playInstrument(1, E5, VELOCITY, 150, true);
		playInstrument(1, G5, VELOCITY, 150, true);
		playInstrument(1, C6, VELOCITY, 150, true);
		Thread.sleep(150);
		playInstrument(1, C5, VELOCITY, 0, false);
		playInstrument(1, E5, VELOCITY, 0, false);
		playInstrument(1, G5, VELOCITY, 0, false);
		playInstrument(1, C6, VELOCITY, 0, false);
		Thread.sleep(2000); // keep that end chord to fill the user with excitement.
	}
	
	/**
	 * @author Josh - I come from far away...
	 * @throws InvalidMidiDataException
	 * @throws InterruptedException
	 * @version 1.1.0
	 * 
	 * " A short distinctive sequence of notes that fills the user with a carefree satisfaction at what has been done."
	 * Sounds suspiciously like a lullaby.
	 */
	private void playGoodbyeSound() throws InterruptedException {
		playInstrument(1, C6, VELOCITY, 350, false);
		playInstrument(1, G5, VELOCITY, 400, false);
		playInstrument(1, E5, VELOCITY, 400, false);
		playInstrument(1, C5, VELOCITY, 2500, true);
	}
	
	/**
	 * @author Josh - And i can play...
	 * @throws InvalidMidiDataException
	 * @throws InterruptedException
	 * @version 1.3.0
	 * 
	 * "a short, distinctive sequence of notes that gives the user the sense of a treat awarded."
	 * Congratulations! Here is a trumpet fanfare for using the Simori correctly.
	 */
	private void playHappySound() throws InterruptedException {
		playInstrument(62, C6, VELOCITYHIGH, 250, true);
		playInstrument(62, C6, VELOCITYHIGH, 150, true);
		playInstrument(62, C6, VELOCITYHIGH, 150, true);
		playInstrument(62, G6, VELOCITYHIGH, 500, true);
	}
	
	/**
	 * @author Josh - what can you play???
	 * @throws InvalidMidiDataException
	 * @throws InterruptedException
	 * @version 3.0.1
	 * 
	 * "A short, distinctive sequences of notes that gives the user the sense of a treat denied."
	 * DUN DUN DUNNNNNNNNNNNN.
	 */
	private void playSadSound() throws InterruptedException {
		playInstrument(56, D5, VELOCITYHIGH, 0, false);
		playInstrument(20, D5, VELOCITYHIGH, 400, true);
		
		playInstrument(56, B4, VELOCITYHIGH, 0, false);
		playInstrument(20, B4, VELOCITYHIGH, 600, true);
		
		playInstrument(56, F5, VELOCITYHIGH, 0, false);
		playInstrument(20, F5, VELOCITYHIGH, 600, true);
	}
	
	/**
	 * @author Josh
	 * @param instrument - midi number (1-128)
	 * @param pitch
	 * @param velocity
	 * @param duration - Length to play in millseconds
	 * @param stop - should the instrument stop making noise after the duration has ended?
	 * @throws InvalidMidiDataException
	 * @throws InterruptedException
	 * @version 2.0.1
	 * 
	 * Method that plays an instrument for some amount of time.
	 * If stop is true then this note is stopped after the duration is finished.
	 * Note: If the duration is zero and stop is false then this basically means 'play and immediately move on'.
	 * This allows for production of chords (multiple notes at the same time).
	 * 
	 */
	private void playInstrument(int instrument, int pitch, int velocity, int duration, boolean stop) throws InterruptedException{
		player.sendCommand(createMessage((byte)0, (byte)(instrument-1))); // send the program change message.
		player.sendCommand(createMessage((byte)0,(byte)pitch, (byte)velocity)); // send the note on message.
		Thread.sleep(duration);
		if(stop){player.stopSound();}
	}
}
