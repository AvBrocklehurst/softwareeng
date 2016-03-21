package simori.Tests;

import org.junit.*;

import simori.Animation;
import simori.Animation.OnFinishListener;
import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * 
 * @author Jurek
 *
 */
public class TestAnimation {

	private Animation anim;
	private MockModeController mode;
	private SimoriJFrame gui;
	private MatrixModel model;
	private MIDISoundSystem midi;
	private AudioFeedbackSystem audio;
	
	//@Before
	//public void setUp() throws SimoriNonFatalException {
		//gui = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		//model = new MatrixModel(16, 16);
		//midi = new MIDISoundSystem();
		//audio = new AudioFeedbackSystem(midi, model);
		//mode = new MockModeController(gui, model, audio, 0);
	//	OnFinishListener switchOn = new OnFinishListener() {
	//		@Override
	//		public void onAnimationFinished() throws SimoriNonFatalException {
	//			
	//		}
	//	};
	//	anim = new Animation(16, switchOn, false);
	//}

	public Animation constructAnimation(boolean isStarted) {
		OnFinishListener finishFunction = new OnFinishListener() {
			@Override
			public void onAnimationFinished() throws SimoriNonFatalException {
				
			}
		};
		return new Animation(16, finishFunction, isStarted);
	}
	
	@Test
	public void testAnimationFalse() {
		anim = constructAnimation(false);
	}
	
	@Test
	public void testAnimationTrue() {
		anim = constructAnimation(true);
	}
	
	@Test
	public void testNext() {
		
	}
}
