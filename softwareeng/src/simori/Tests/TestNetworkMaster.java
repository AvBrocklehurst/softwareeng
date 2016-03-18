	/**
 * 
 */
package simori.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import simori.MatrixModel;
import simori.ModeController;
import simori.SimoriSoundSystem;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.SimoriJFrame;

/**
 * @author adam
 *
 */
public class TestNetworkMaster {

	@Test
	public void testRunning() throws SimoriNonFatalException{
		MatrixModel model = new MatrixModel(16, 16);
		QwertyKeyboard keyboard = new QwertyKeyboard((byte)16, (byte)16);
		SimoriJFrame gui = new SimoriJFrame(keyboard);
		SimoriSoundSystem player = new SimoriSoundSystem();
		ModeController modes = new ModeController(gui, model, 20160);
		modes.setComponentsToPowerToggle(model, player, gui);
		modes.setOn(false);
		modes.setOn(true);
		modes.startNetworkMaster();
	}
}
