package simori.SwingGui;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import simori.FunctionButton;
import simori.Simori.PowerTogglable;

/**
 * {@link JPanel} representation of one of the Simori-ON's margins
 * containing {@link Button}s and optionally a {@link Lcd} screen.
 * Manages the sizing and resizing of the components it holds.
 * In horizontal bars, the LCD screen is left of all the buttons,
 * and in vertical bars it is above the buttons.
 * @author Matt
 * @version 3.0.0
 */
public class SimoriEdgeBar extends JPanel implements PowerTogglable {
	
	protected Button[] buttons;
	private Map<FunctionButton, Button> buttonMap;
	private Lcd lcd;
	
	/**
	 * Creates a bar, either vertical or horizontal, with an optional
	 * {@link Lcd} screen and any number of {@link simori.FunctionButton}s.
	 * Components are spaced evenly along a line and centred the other way.
	 * @param vertical true to create a vertical bar, false for horizontal
	 * @param hasLcd true to include an LCD screen in the bar
	 * @param maker A source of listeners to accept the buttons' callbacks
	 * @param fbs The buttons to add to the bar
	 */
	public SimoriEdgeBar(boolean vertical, boolean hasLcd,
			OnPressListenerMaker maker, FunctionButton... fbs) {
		int axis = vertical ? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS;
		setOpaque(false); //Simori colour beneath should be visible
		if (fbs == null) return;
		BoxLayout layout = new BoxLayout(this, axis);
		setLayout(layout);
		addComponents(vertical, hasLcd, maker, fbs);
	}
	
	/** {@inheritDoc} */
	@Override
	public void ready() {}
	
	/** {@inheritDoc} */
	@Override
	public void switchOn() {
		for (Button b : buttons) {
			b.setGreyedOut(false);
		}
		if (lcd != null) lcd.switchOn();
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop() {
		if (lcd != null) lcd.switchOff();
	}

	/** {@inheritDoc} */
	@Override
	public void switchOff() {
		for (Button b : buttons) {
			b.setGreyedOut(true);
		}
	}
	
	/**
	 * Switches an individual button on or off.
	 * Returns false if the requested button was not present in this bar.
	 * @param fb The identifier of the button to switch
	 * @param greyedOut True to grey out (switch off) the button
	 * @return true if the change was applied
	 */
	public boolean setGreyedOut(FunctionButton fb, boolean greyedOut) {
		if (!buttonMap.containsKey(fb)) return false;
		buttonMap.get(fb).setGreyedOut(greyedOut);
		return true;
	}
	
	/**
	 * If vertical, sets the width of the {@link Button}s
	 * to a proportion of the width of the bar.
	 * If horizontal, sets the height of the buttons
	 * to a proportion of the height of the bar.
	 * The {@link Lcd} is also sized to fit.
	 * @param The predetermined size of this bar
	 */
	public void setDefiniteSize(Dimension size) {
		setPreferredSize(size);
		float min = Math.min(size.width, size.height);
		int ratio = (int) (min * GuiProperties.MARGIN_PROPORTION);
		Dimension bSize = new Dimension(ratio, ratio);
		for (Button b : buttons) b.setDefiniteSize(bSize);
		if (lcd != null) lcd.setShorterSize(ratio);
	}
	
	/** @return The {@link Lcd} on this edge, or null */
	public Lcd getLcd() {
		return lcd;
	}
	
	/**
	 * Adds the list of {@link Button}s and optionally the {@link Lcd}.
	 * Components are spaced evenly.
	 * @param vertical true if the LCD should be drawn sideways
	 * @param hasLcd true if an LCD should be included
	 * @param maker to create listeners for the buttons' callbacks
	 * @param fbs the buttons to create
	 */
	private void addComponents(boolean vertical, boolean hasLcd,
			OnPressListenerMaker maker, FunctionButton[] fbs) {
		if (hasLcd) {
			add(Box.createGlue()); //Glue spaces the LCD away from the edge
			lcd = new Lcd(vertical);
			add(lcd);
		}
		add(Box.createGlue());
		addButtons(fbs, maker);
	}
	
	/**
	 * Creates and adds {@link Button}s to represent the specified
	 * {@link FunctionButtons}. Buttons are spaced evenly.
	 * @param fbs The list of buttons to represent
	 * @param maker Source of listeners for the buttons' callbacks
	 */
	private void addButtons(FunctionButton[] fbs,
			OnPressListenerMaker maker) {
		buttonMap = new HashMap<FunctionButton, Button>(fbs.length);
		buttons = new Button[fbs.length];
		add(Box.createGlue());
		for (int i = 0; i < fbs.length; i++) {
			if (fbs[i] == null) continue;
			buttons[i] = makeButtonFor(fbs[i], maker);
			add(buttons[i]);
			buttonMap.put(fbs[i], buttons[i]);
			add(Box.createGlue()); //Glue between each button spaces them out
		}
		add(Box.createGlue()); //Final glue for space away from right edge
	}
	
	/**
	 * Returns a {@link Button} representing the given {@link FunctionButton}.
	 * The text and tooltip are set according to the values in the enum.
	 * The given {@link OnPressListenerMaker} is used to set a listener to
	 * receive callbacks when the button is pressed.
	 */
	private Button makeButtonFor(FunctionButton fb,
			OnPressListenerMaker maker) {
		Button b = makeButton();
		b.setText(fb.buttonName());
		b.setToolTipText(fb.toolTip());
		b.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b.addOnPressListener(maker.getListener(fb));
		return b;
	}
	
	/** Protected so that subclasses can use different types of button */
	protected Button makeButton() {
		return new Button();
	}
}
