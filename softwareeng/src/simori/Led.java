package simori;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

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
	
	//TODO Control resizing to keep width and height the same
	
	/* 
	 * ON is the illuminated colour, OFF the default
	 * IN colours are darkened versions to represent pressed states
	 */
	private static final Color ON = new Color(255, 176, 0);
	private static final Color ON_IN = new Color(205, 126, 0);
	private static final Color OFF = new Color(0xFFFFFF);
	private static final Color OFF_IN = new Color(0xEEEEEE);
	
	private static final Dimension DEFAULT = new Dimension(30, 30);
	
	/*
	 * Having mouseDown static very conveniently produces the
	 * intentional click and drag to activate multiple LEDs behaviour.
	 */
	private static boolean mouseDown;
	private boolean lit;
	
	public Led() {
		super();
		setPreferredSize(DEFAULT);
		setMaximumSize(DEFAULT);
		setMinimumSize(DEFAULT);
	}
	
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
		return lit ? (pushed ? ON_IN : ON) : (pushed ? OFF_IN : OFF);
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
