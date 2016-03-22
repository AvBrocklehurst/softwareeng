package simori.Tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;


/**
 * 
 * @author Josh
 * @version 1.0.0
 * Class that tests MIDIMessengerSystem
 * Since this class is abstract i will use AudioFeedbackSystem as the concrete implementation to test the methods.
 * Since MIDIMessengerSystem is the class that holds a reference to MIDISoundSystem I will use this class to test some of its methods. of its sted.
 */
public class TestMIDIMessengerSystem {
	AudioFeedbackSystem afs;
	MIDISoundSystem soundSystem;

	
	@Before
	public void setUp() throws Exception {
		soundSystem = new MIDISoundSystem();
		afs = new AudioFeedbackSystem(soundSystem, new MatrixModel(16, 16));
	}

	@After
	public void tearDown() throws Exception {
		soundSystem = null;
		afs = null;
	}
	
	//constructor
	@Test
	public void testMIDIMessengerSystem() {
		assertNotNull(afs); // constructor for MIDIMessengerSystem is used in AudioFeedback constructor.
	}

	@Test
	public void testCreateMessageByteByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMessageByteByteByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopPlay() {
		afs.stopPlay();
	}

}
