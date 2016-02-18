package simori;

import static simori.GuiProperties.LED_COLOUR_OFF;
import static simori.GuiProperties.LED_COLOUR_OFF_IN;
import static simori.GuiProperties.LED_COLOUR_ON;
import static simori.GuiProperties.LED_COLOUR_ON_IN;

import java.awt.Color;
import java.awt.event.MouseEvent;

import simori.OnPressListenerMaker.OnPressListener;

/**
 * Custom JComponent to represent illuminated LED buttons.
 * Does not extend JButton or JToggleButton because
 * the desired click behaviour is different.
 * The {@link OnPressListener} is notified immediately on mouse
 * down inside the LED area, instead of on mouse button release.
 * Furthermore, if mouse down occurs inside another LED and the
 * mouse moves into the current LED, both fire their events.
 * This produces an enjoyable user experience similar
 * to running one's finger down the keys of a piano.
 * Illumination is toggled manually using {@link #setIlluminated}.
 * @author Matt
 * @version 1.4.1
 */
public class Led extends PressableCircle {
	
	/*
	 * Having mouseDown static very conveniently produces the
	 * intentional click and drag to activate multiple LEDs behaviour.
	 */
	private static boolean mouseDown;
	private boolean lit;
	
	/**
	 * @param on Whether LED should be illuminated
	 */
	public void setIlluminated(boolean on) {
		if (lit != on) { //Don't redraw unless new state is different
			lit = on;
			repaint(); //Redraw after any visual change
		}
	}
	
	@Override
	protected Color getFillColour() {
		return lit ?
				(pushed ? LED_COLOUR_ON_IN : LED_COLOUR_ON) :
				(pushed ? LED_COLOUR_OFF_IN : LED_COLOUR_OFF);
	}
	
	@Override
	protected Color getBorderColour() {
		return lit ? null : BORDER;
	}	
	
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

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		super.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		super.mouseReleased(e);
		/*
		 * FIXME Very hard to notice, but buttons won't redraw as no
		 * 		 longer pressed if they're the last of a click and drag
		 */
	}
}
