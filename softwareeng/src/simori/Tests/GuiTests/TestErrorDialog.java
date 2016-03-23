package simori.Tests.GuiTests;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.junit.*;
	
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.ErrorDialog;
import simori.SwingGui.SimoriJFrame;

public class TestErrorDialog {

	class MockErrorDialog extends ErrorDialog {

		public MockErrorDialog(SimoriJFrame frame, boolean fatal) {
			super(frame, fatal);
		}
		
		public JLabel getLabel() {
			return label;
		}
		
		public JTextArea getTextArea() {
			return textArea;
		}
	}
	
	private SimoriJFrame jframe;
	private MockErrorDialog error;
	
	@Test
	public void testErrorDialog() {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		error = new MockErrorDialog(jframe, false);
	}
	
	@Test (expected=NullPointerException.class)
	public void testErrorDialogNull() {
		error = new MockErrorDialog(null, false);
	}
	
	@Test
	public void testSetShortMessage() {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		error = new MockErrorDialog(jframe, false);
		error.setShortMessage("test");
		JLabel label = error.getLabel();
		assertEquals("test", label.getText());
	}
	
	@Test
	public void testSetLongMessage() {
		jframe = new SimoriJFrame(new QwertyKeyboard((byte)16, (byte)16));
		error = new MockErrorDialog(jframe, false);
		error.setLongMessage("test");
		JTextArea textArea = error.getTextArea();
		assertEquals("test", textArea.getText());
	}
}