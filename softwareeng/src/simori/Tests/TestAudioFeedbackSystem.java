package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import simori.AudioFeedbackSystem;
import simori.AudioFeedbackSystem.Sound;
import simori.MatrixModel;

/**
 * 
 * @author Josh
 * @version 1.3.2
 * 
 * Class that tests AudioFeedback System.
 * Since this class extends (and implements) the abstract class MIDIMessengerSystem those methods will also be tested.
 * Since MIDIMessengerSystem is the class that holds a reference to MIDISoundSystem some of its methods will be inherently tested.
 */
public class TestAudioFeedbackSystem {
	AudioFeedbackSystem afs;
	
	@Before
	public void setUp() throws Exception {
		afs = new AudioFeedbackSystem(new MockMIDISoundSystem(false), new MatrixModel(16, 16));
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
	@Test public void testPlayHappy() throws InterruptedException {
		afs.play(Sound.HAPPY); 
		Thread.sleep(5000); // in order to get code coverage we need to stay in test until sound is complete.
	}
	@Test public void testPlaySad() throws InterruptedException {
		afs.play(Sound.SAD);
		Thread.sleep(5000); 
	}	
	@Test public void testPlayWelcome() throws InterruptedException {
		afs.play(Sound.WELCOME);
		Thread.sleep(5000); 
	}
	@Test public void testPlayGoodbye() throws InterruptedException {
		afs.play(Sound.GOODBYE);
		Thread.sleep(5000); 
	}
	
	//Test that we can play multiple tunes ok.
	@Test public void testPlayMultiple() throws InterruptedException {
		afs.play(Sound.WELCOME);
		afs.play(Sound.HAPPY);
		afs.play(Sound.SAD);
		afs.play(Sound.GOODBYE);
		Thread.sleep(5000);
	}
	
}
