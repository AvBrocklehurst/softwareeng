package simori.Tests.GuiTests;

import static simori.FunctionButton.L1;
import static simori.FunctionButton.L2;
import static simori.FunctionButton.L3;
import static simori.FunctionButton.L4;
import static simori.FunctionButton.OK;
import static simori.FunctionButton.ON;
import static simori.FunctionButton.R1;
import static simori.FunctionButton.R2;
import static simori.FunctionButton.R3;
import static simori.FunctionButton.R4;

import simori.FunctionButton;
import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.GridPanel;
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
	
	public SimoriEdgeBar getLeftBar() {
		return ((MockSimoriPanel) simoriPanel).getLeftBar();
	}
	
	public SimoriEdgeBar getRightBar() {
		return ((MockSimoriPanel) simoriPanel).getRightBar();
	}
	
	public SimoriEdgeBar getBottomBar() {
		return ((MockSimoriPanel) simoriPanel).getBottomBar();
	}
	
	public class MockSimoriPanel extends SimoriPanel {

		public MockSimoriPanel(KeyboardMapping map,
				OnPressListenerMaker maker) {
			super(map, maker);
		}
		
		@Override
		protected void makeComponents(KeyboardMapping map,
				OnPressListenerMaker maker) {
			centrePanel = new MockGridPanel(map, maker);
			topBar = new MockSimoriEdgeBar(false, false, maker, ON);
			leftBar = new MockSimoriEdgeBar(true, false, maker, L1, L2, L3, L4);
			rightBar = new MockSimoriEdgeBar(true, false, maker, R1, R2, R3, R4);
			bottomBar = new MockSimoriEdgeBar(false, true, maker, OK);
		}
		
		public MockSimoriEdgeBar getTopBar() {
			return (MockSimoriEdgeBar) topBar;
		}
		
		public MockSimoriEdgeBar getLeftBar() {
			return (MockSimoriEdgeBar) leftBar;
		}
		
		public MockSimoriEdgeBar getRightBar() {
			return (MockSimoriEdgeBar) rightBar;
		}
		
		public MockSimoriEdgeBar getBottomBar() {
			return (MockSimoriEdgeBar) bottomBar;
		}
		
		public MockGridPanel getGridPanel() {
			return (MockGridPanel) centrePanel;
		}
	}
	
	public class MockSimoriEdgeBar extends SimoriEdgeBar {

		public MockSimoriEdgeBar(boolean vertical, boolean hasLcd,
				OnPressListenerMaker maker, FunctionButton... fbs) {
			super(vertical, hasLcd, maker, fbs);
		}
	}
	
	public class MockGridPanel extends GridPanel {

		public MockGridPanel(KeyboardMapping map, OnPressListenerMaker maker) {
			super(map, maker);
		}
	}
}
