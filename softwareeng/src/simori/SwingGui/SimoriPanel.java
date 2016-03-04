package simori.SwingGui;

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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import simori.SimoriGui.KeyboardMapping;

/**
 * A custom JPanel in the rounded rectangular shape of a Simori-ON.
 * @author Matt
 * @version 1.0.0 //TODO massive javadoc overhaul
 */
public class SimoriPanel extends JPanel {
	
	private GridPanel centrePanel;
	private SimoriEdgeBar leftBar, rightBar;
	private SimoriEdgeBar topBar, bottomBar;
	
	public SimoriPanel(KeyboardMapping map, OnPressListenerMaker maker) {
		makeComponents(map, maker);
		addComponents();
	}
	
	public void setKeyboardShown(boolean shown) {
		centrePanel.setKeyboardShown(shown);
	}
	
	public void setGrid(boolean[][] grid) {
		centrePanel.setGrid(grid);
	}
	
	public Lcd getLcd() {
		return bottomBar.getLcd();
	}
	
	public void setSizes(Dimension simoriSize) {
		Dimension topBarSize, sideBarSize;
		sideBarSize = GuiProperties.ratioOf(0.1f, 0.8f, simoriSize);
		topBarSize = GuiProperties.ratioOf(1f, 0.1f, simoriSize);
		leftBar.setPreferredSize(sideBarSize);
		rightBar.setPreferredSize(sideBarSize);
		topBar.setPreferredSize(topBarSize);
		bottomBar.setPreferredSize(topBarSize);
	}
	
	/**
	 * Determines whether the Simori-ON window can be moved by
	 * clicking and dragging from the specified point.
	 * @param point A location in the window
	 * @return true if the window can be dragged from this point
	 */
	public boolean canDragFrom(Point point) {
		return topBar.contains(point)
				|| bottomBar.contains(point)
				|| leftBar.contains(point)
				|| rightBar.contains(point);
	}
	
	private void makeComponents(KeyboardMapping map,
								OnPressListenerMaker maker) {
		centrePanel = new GridPanel(map, maker);
		topBar = new SimoriEdgeBar(false, false, maker, ON);
		leftBar = new SimoriEdgeBar(true, false, maker, L1, L2, L3, L4);
		rightBar = new SimoriEdgeBar(true, false, maker, R1, R2, R3, R4);
		bottomBar = new SimoriEdgeBar(false, true, maker, OK);
	}
	
	private void addComponents() {
		setLayout(new BorderLayout(0, 0));
		add(topBar, BorderLayout.PAGE_START);
		add(leftBar, BorderLayout.LINE_START);
		add(centrePanel, BorderLayout.CENTER);
		add(rightBar, BorderLayout.LINE_END);
		add(bottomBar, BorderLayout.PAGE_END);
	}
	
	/**
	 * Returns a copy of {@link #getBounds} with its
	 * width and height reduced by two pixels. This prevents
	 * the border from drawing outside the region of the window.
	 * @return The bounds to use for drawing
	 */
	private Rectangle getClippedBounds() {
		Rectangle b = getBounds();
		return new Rectangle(b.x+1, b.y+1, b.width-2, b.height-2);
	}
	
	/** @return The rounded corners' arc size (in either dimension) */
	private int getArc() {
		float min = Math.min(getBounds().width, getBounds().height);
		return (int) (min * GuiProperties.ARC_PROPORTION);
	}
	
	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(GuiProperties.SIMORI_BACKGROUND);
		Rectangle b = getClippedBounds();
		int arc = getArc();
		g.fillRoundRect(b.x, b.y, b.width, b.height, arc, arc);
	}
	
	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		g.setColor(GuiProperties.SIMORI_BORDER);
		Rectangle b = getClippedBounds();
		int arc = getArc();
		g.drawRoundRect(b.x, b.y, b.width, b.height, arc, arc);
	}
}
