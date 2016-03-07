package simori.Tests.GuiTests;

import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.SimoriEdgeBar;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SimoriPanel;

public class MockSimoriJFrame extends SimoriJFrame {
	
	@Override
	protected void addSimoriPanel() {
		simoriPanel = new MockSimoriPanel(mapping, new OnPressListenerMaker(this));
		add(simoriPanel);
	}
	
	public MockSimoriJFrame() throws KeyboardException {
		this(new QwertyKeyboard((byte) 16, (byte) 16));
	}

	public MockSimoriJFrame(KeyboardMapping mapping) {
		super(mapping);
	}
	
	public SimoriPanel getSimoriPanel() {
		return simoriPanel;
	}
	
	public SimoriEdgeBar getTopBar() {
		return ((MockSimoriPanel) simoriPanel).getTopBar();
	}
	
	public class MockSimoriPanel extends SimoriPanel {

		public MockSimoriPanel(KeyboardMapping map, OnPressListenerMaker maker) {
			super(map, maker);
		}
		
		public SimoriEdgeBar getTopBar() {
			return topBar;
		}
		
		public SimoriEdgeBar getLeftBar() {
			return leftBar;
		}
		
		public SimoriEdgeBar getRightBar() {
			return rightBar;
		}
		
		public SimoriEdgeBar getBottomBar() {
			return bottomBar;
		}
	}
}
