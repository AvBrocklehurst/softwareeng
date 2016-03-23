package simori.Tests.GuiTests;

import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.ExceptionManager;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SplashJWindow;

/**
 * 
 * @author Jurek
 *
 */
public class TestSplashJWindow {

	private SplashJWindow jwindow;
	private SimoriJFrame jframe;
	
	private void setUp() {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		jwindow = new SplashJWindow();
	}
	
	@Test
	public void testSwapFor() throws InterruptedException {
		setUp();
		
		jwindow.swapFor(jframe, 2000);
		Thread.sleep(3000);
	}
	
	@Test
	public void testSwapForException() throws InterruptedException {
		setUp();
		ExceptionManager ex = new ExceptionManager();
		MatrixModel model = new MatrixModel(16, 16);
		MIDISoundSystem midi = new MIDISoundSystem(false);
		AudioFeedbackSystem audio = new AudioFeedbackSystem(midi, model);
		
		jwindow.swapFor(jframe, 2000, ex, audio);
		Thread.sleep(3000);
	}
}
