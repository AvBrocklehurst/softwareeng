package simori.Tests.GuiTests;

import org.junit.*;

import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.ErrorDialog;
import simori.SwingGui.SimoriJFrame;

public class TestErrorDialog {

	private SimoriJFrame jframe;
	private ErrorDialog error;
	
	@Test
	public void testErrorDialog() throws SimoriNonFatalException {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		error = new ErrorDialog(jframe);
	}
	
	@Test (expected=NullPointerException.class)
	public void testErrorDialogNull() throws SimoriNonFatalException {
		error = new ErrorDialog(null);
	}
}
