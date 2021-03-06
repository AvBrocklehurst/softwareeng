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
	MockMIDISoundSystem mock;
	
	@Before
	public void setUp() {
		mock = new MockMIDISoundSystem(false);
		afs = new AudioFeedbackSystem(mock, new MatrixModel(16, 16));
	}

	@After
	public void tearDown() {
		mock.switchOff();
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
		Thread.sleep(2100); // in order to get code coverage we need to stay in test until sound is complete.
	}
	@Test public void testPlaySad() throws InterruptedException {
		afs.play(Sound.SAD);
		Thread.sleep(3100); 
	}	
	@Test public void testPlayWelcome() throws InterruptedException {
		afs.play(Sound.WELCOME);
		Thread.sleep(2800); 
	}
	@Test public void testPlayGoodbye() throws InterruptedException {
		afs.play(Sound.GOODBYE);
		Thread.sleep(3700); 
	}
	
	//Test that we can play multiple tunes ok (if the user spammed a button or something similar)
	@Test public void testPlayMultiple() throws InterruptedException {
		afs.play(Sound.WELCOME);
		afs.play(Sound.HAPPY);
		afs.play(Sound.SAD);
		afs.play(Sound.GOODBYE);
		Thread.sleep(5000);
	}
	
}
