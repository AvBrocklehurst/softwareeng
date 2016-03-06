package simori.SwingGui;

import static simori.SwingGui.GuiProperties.LED_COLOUR_OFF;
import static simori.SwingGui.GuiProperties.LED_COLOUR_OFF_IN;
import static simori.SwingGui.GuiProperties.LED_COLOUR_ON;
import static simori.SwingGui.GuiProperties.LED_COLOUR_ON_IN;

import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * A {@link PressableCircle} which can be illuminated.
 * It also differs in that if mouse down occurs inside another
 * LED and the mouse moves into the current LED, both fire
 * their events. This produces an enjoyable user experience
 * similar to running one's finger down the keys of a piano.
 * Illumination is toggled manually using {@link #setIlluminated}.
 * @author Matt
 * @version 3.0.3
 */
public class Led extends PressableCircle {
	
	/*
	 * Having mouseDown static very conveniently produces the
	 * intentional click and drag to activate multiple LEDs behaviour.
	 */
	protected static boolean mouseDown;
	protected boolean lit;
	
	/**
	 * @param on Whether LED should be illuminated
	 */
	public void setIlluminated(boolean on) {
		if (lit != on) { //Don't redraw unless new state is different
			lit = on;
			repaint(); //Redraw after any visual change
		}
	}
	
	/** {@inheritDoc} */
	@Override
	protected Color getFillColour() {
		//Four possible colours, depending on illumination and press state
		return lit ?
				(pushed ? LED_COLOUR_ON_IN : LED_COLOUR_ON) :
				(pushed ? LED_COLOUR_OFF_IN : LED_COLOUR_OFF);
	}
	
	/** {@inheritDoc} */
	@Override
	protected Color getBorderColour() {
		//No outline drawn if illuminated
		return lit ? null : GuiProperties.LED_BORDER;
	}	
	
	/** {@inheritDoc} */
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		
		//Mouse pressed in an LED and entered this LED
		if (mouseDown) {
			pushed = true;
			pressed();
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		super.mousePressed(e);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		super.mouseReleased(e);
	}
}
