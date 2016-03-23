	/**
 * 
 */
package simori.Tests.ModeTests;

import org.junit.Test;

import simori.AudioFeedbackSystem;
import simori.MIDISoundSystem;
import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * @author adam
 * @author Jurek
 *
 */
public class TestNetworkMaster {

	@Test
	public void testRunning() {
		MatrixModel model = new MatrixModel(16, 16);
		QwertyKeyboard keyboard = new QwertyKeyboard((byte)16, (byte)16);
		SimoriJFrame gui = new SimoriJFrame(keyboard);
		MIDISoundSystem midi = new MIDISoundSystem(false);
		AudioFeedbackSystem audio = new AudioFeedbackSystem(midi, model);
		ModeController modes = new ModeController(gui, model, audio, 20160);
		modes.setComponentsToPowerToggle(model, midi, gui);
		modes.setOn(false, false);
		modes.setOn(true, false);
		modes.startNetworkMaster();
		midi.switchOff();
	}
}
