package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import simori.AudioFeedbackSystem;
import simori.AudioFeedbackSystem.Sound;
import simori.MIDISoundSystem;
import simori.MatrixModel;

/**
 * 
 * @author Josh
 * @version 1.0.0
 * 
 * Class that tests AudioFeedback System.
 * Since this class extends (and implements) the abstract class MIDIMessengerSystem those methods will also be tested.
 * Since MIDIMessengerSystem is the class that holds a reference to MIDISoundSystem some of its methods will be inherently tested.
 */
public class TestAudioFeedbackSystem {
	AudioFeedbackSystem afs;
	
	@Before
	public void setUp() throws Exception {
		afs = new AudioFeedbackSystem(new MIDISoundSystem(), new MatrixModel(16, 16));
	}

	@After
	public void tearDown() throws Exception {
		afs = null;
	}
	
	//Test constructor.
	@Test
	public void testAudioFeedbackSystem() {
		assertNotNull(afs);
	}
	
	// Test all 4 sounds.
	@Test public void testPlayHappy() {afs.play(Sound.HAPPY);}
	@Test public void testPlaySad() {afs.play(Sound.SAD);}	
	@Test public void testPlayWelcome() {afs.play(Sound.WELCOME);}
	@Test public void testPlayGoodbye() {afs.play(Sound.GOODBYE);}

	//Test that we can play multiple tunes ok.
	@Test public void testPlayMultiple() {
		afs.play(Sound.WELCOME);
		afs.play(Sound.HAPPY);
		afs.play(Sound.SAD);
		afs.play(Sound.GOODBYE);
	}
	
}
