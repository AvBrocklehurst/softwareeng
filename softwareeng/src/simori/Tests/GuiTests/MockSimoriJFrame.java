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
import java.awt.Graphics;

import simori.FunctionButton;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.QwertyKeyboard;
import simori.Simori.PowerTogglable;
import simori.SimoriGui.KeyboardMapping;
import simori.SwingGui.Button;
import simori.SwingGui.Led;
import simori.SwingGui.LedPanel;
import simori.SwingGui.OnPressListenerMaker;
import simori.SwingGui.PressableCircle;
import simori.SwingGui.SimoriCentrePanel;
import simori.SwingGui.SimoriEdgeBar;
import simori.SwingGui.SimoriJFrame;
import simori.SwingGui.SimoriPanel;

/**
 * Uses mock versions of subcomponents to expose them for testing.
 * @author Matt
 * @author Jurek
 * @version 3.0.1
 */
public class MockSimoriJFrame extends SimoriJFrame {
	
	@Override
	protected void addSimoriPanel() {
		panel = new MockSimoriPanel(mapping, new OnPressListenerMaker(this));
		add(panel);
		lcd = getBottomBar().getLcd();
	}
	
	public MockSimoriJFrame() throws SimoriNonFatalException {
		this(new QwertyKeyboard((byte) 16, (byte) 16));
	}

	public MockSimoriJFrame(KeyboardMapping mapping) {
		super(mapping);
	}
	
	public MockSimoriPanel getSimoriPanel() {
		return (MockSimoriPanel) panel;
	}
	
	public MockSimoriEdgeBar getTopBar() {
		return ((MockSimoriPanel) panel).getTopBar();
	}
	
	public MockSimoriEdgeBar getLeftBar() {
		return ((MockSimoriPanel) panel).getLeftBar();
	}
	
	public MockSimoriEdgeBar getRightBar() {
		return ((MockSimoriPanel) panel).getRightBar();
	}
	
	public MockSimoriEdgeBar getBottomBar() {
		return ((MockSimoriPanel) panel).getBottomBar();
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
	
	public boolean getCouldDragBefore() {
		return couldDragBefore;
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
			components = new PowerTogglable[]
					{centrePanel, leftBar, rightBar, bottomBar}; // Exclude topBar
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
		
		/**
		 * @author Jurek
		 * @param index
		 * @return
		 */
		public MockButton getButton(int index) {
			return (MockButton) buttons[index];
		}

		public Button[] getButtons() {
			return buttons;
		}
		
		@Override
		protected Button makeButton() {
			return new MockButton();
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
	}
	
	/**
	 * A mock button to allow testing of the Button 
	 * class with getters and setters.
	 * 
	 * @author James
	 * @author Jurek
	 * @version 1.0.1
	 * @see Button
	 *
	 */
	public static class MockButton extends Button{
		
		@Override
		public Color getFillColour(){
			return super.getFillColour();
		}
		
		@Override
		public void resized(){
			super.resized();
		}
		
		
	}
	
	/**
	 * A Mock object for PressableCircle. Required as PressableCircle
	 * is an abstract class. Provides getters and setters needed in testing.
	 * 
	 * @author James
	 * @version 1.0.0
	 * @see PressableCircle.java, TestPressableCircle
	 *
	 */
	public static class MockPressableCircle extends PressableCircle{
		
		public MockPressableCircle(){
			super();
		}
		
		public boolean getPushed(){
			return pushed;
		}
		
		public void setPushed(){
			pushed = true;
		}
		
		public boolean getMouseOver(){
			return mouseOver;
		}
		
		public void setMouseOver(){
			mouseOver = true;
		}
		
		@Override
		public Color getFillColour(){
			return super.getFillColour();
		}

	}
	
	/**
	 * A Mock object for SimoriCentrePanel.Provides getters and setters needed in testing.
	 * 
	 * @author James
	 * @version 1.0.0
	 * @see SimoriCentrePanel.java, TestSimoriCentrePanel
	 *
	 */
	public static class MockCentrePanel extends SimoriCentrePanel{

		public MockCentrePanel(KeyboardMapping map, OnPressListenerMaker maker) {
			super(map, maker);
		}
		
		public LedPanel getLedPanel(){
			return ledPanel;
		}
		
		@Override
		public LedPanel makeLedPanel(KeyboardMapping map,
				OnPressListenerMaker maker){
			return super.makeLedPanel(map, maker);
		}
		
		
	}
}
