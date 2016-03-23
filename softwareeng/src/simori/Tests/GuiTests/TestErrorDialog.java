package simori.Tests.GuiTests;

import org.junit.*;

import simori.Modes.QwertyKeyboard;
import simori.SwingGui.ErrorDialog;
import simori.SwingGui.SimoriJFrame;

public class TestErrorDialog {

	private SimoriJFrame jframe;
	
	@Test
	public void testErrorDialog() {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		new ErrorDialog(jframe, false);
	}
	
	@Test (expected=NullPointerException.class)
	public void testErrorDialogNull() {
		new ErrorDialog(null, false);
	}
}
