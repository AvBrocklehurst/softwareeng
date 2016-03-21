package simori.Tests.GuiTests;

import org.junit.*;

import simori.Exceptions.SimoriNonFatalException;
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
	
	@Before
	public void setUp() throws SimoriNonFatalException {

	}

	@Before
	public void tearDown() {
		jwindow = null;
		jframe = null;
	}
	
	@Test
	public void testSwapFor() throws SimoriNonFatalException, InterruptedException {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		jwindow = new SplashJWindow();
		// TODO untestable?
		// the splash screen disappears and nullifies before it can be tested :/
		System.out.println(jwindow.toString());
		jwindow.swapFor(jframe, 2000);
		Thread.sleep(3000);
		
	}
}
