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

import java.awt.Color;

import simori.FunctionButton;
import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;
import simori.SwingGui.Button;
import simori.SwingGui.SimoriCentrePanel;
import simori.SwingGui.Led;
import simori.SwingGui.LedPanel;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.PressableCircle;
import simori.SwingGui.SimoriEdgeBar;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SimoriPanel;

public class MockSimoriJFrame extends SimoriJFrame {
	
	@Override
	protected void addSimoriPanel() {
		simoriPanel = new MockSimoriPanel(mapping, new OnPressListenerMaker(this));
		add(simoriPanel);
		lcd = getBottomBar().getLcd();
	}
	
	public MockSimoriJFrame() throws KeyboardException {
		this(new QwertyKeyboard((byte) 16, (byte) 16));
	}

	public MockSimoriJFrame(KeyboardMapping mapping) {
		super(mapping);
	}
	
	public MockSimoriPanel getSimoriPanel() {
		return (MockSimoriPanel) simoriPanel;
	}
	
	public MockSimoriEdgeBar getTopBar() {
		return ((MockSimoriPanel) simoriPanel).getTopBar();
	}
	
	public MockSimoriEdgeBar getLeftBar() {
		return ((MockSimoriPanel) simoriPanel).getLeftBar();
	}
	
	public MockSimoriEdgeBar getRightBar() {
		return ((MockSimoriPanel) simoriPanel).getRightBar();
	}
	
	public MockSimoriEdgeBar getBottomBar() {
		return ((MockSimoriPanel) simoriPanel).getBottomBar();
	}
	
	public MockGridPanel getGridPanel() {
		return getSimoriPanel().getGridPanel();
	}
	
	public MockLedPanel getLedPanel() {
		return getSimoriPanel().getGridPanel().getLedPanel();
	}
	
	public MockLed getLed(byte row, byte column) {
		return getSimoriPanel().getGridPanel().getLedPanel().getLed(row, column);
	}
	
	public static class MockSimoriPanel extends SimoriPanel {

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
	
	public static class MockSimoriEdgeBar extends SimoriEdgeBar {

		public MockSimoriEdgeBar(boolean vertical, boolean hasLcd,
				OnPressListenerMaker maker, FunctionButton... fbs) {
			super(vertical, hasLcd, maker, fbs);
		}
		
		public Button[] getButtons() {
			return buttons;
		}
	}
	
	public static class MockGridPanel extends SimoriCentrePanel {

		public MockGridPanel(KeyboardMapping map, OnPressListenerMaker maker) {
			super(map, maker);
		}
		
		@Override
		protected LedPanel makeLedPanel(KeyboardMapping map, OnPressListenerMaker maker) {
			return new MockLedPanel(map.getRows(), map.getColumns(), maker);
		}
		
		public MockLedPanel getLedPanel() {
			return (MockLedPanel) ledPanel;
		}
	}
	
	public static class MockLedPanel extends LedPanel {
		
		@Override
		protected Led makeLed() {
			return new MockLed();
		}

		public MockLedPanel(int rows, int columns, OnPressListenerMaker maker) {
			super(rows, columns, maker);
		}
		
		public MockLed getLed(byte row, byte column) {
			return (MockLed) leds[column][row];
		}
		
		public PressableCircle getLastPressed(){
			return lastPressed;
		}
	}
	
	/**
	 * A MockObject to allow access to Led methods and
	 * attributes
	 * 
	 * @author James
	 * @version 1.0.0
	 * @see Led.java
	 *
	 */
	public static class MockLed extends Led{
		
		public boolean getIlluminated(){
			return lit;
		}
		
		public boolean getMouseDown(){
			return mouseDown;
		}
		
		public boolean getPushed(){
			return pushed;
		}
		
		public void setMouseDown(){
			mouseDown = true;
		}
		
		@Override
		public Color getFillColour(){
			return super.getFillColour();
		}
		
		@Override
		public Color getBorderColour(){
			return super.getBorderColour(); 
		}
	}
}
