package simori.SwingGui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
 * @version 2.8.5
 */
public class SimoriEdgeBar extends JPanel implements PowerTogglable {
	
	private Button[] buttons;
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
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateSize();
			}
		});
	}
	
	/** {@inheritDoc} */
	@Override
	public void switchOn() {
		for (Button b : buttons) {
			b.setEnabled(true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void switchOff() {
		for (Button b : buttons) {
			b.setEnabled(false);
		}
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
		buttons = new Button[fbs.length];
		add(Box.createGlue());
		for (int i = 0; i < fbs.length; i++) {
			if (fbs[i] == null) continue;
			buttons[i] = makeButtonFor(fbs[i], maker);
			add(buttons[i]);
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
		Button b = new Button();
		b.setText(fb.buttonName());
		b.setToolTipText(fb.toolTip());
		b.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b.addOnPressListener(maker.getListener(fb));
		return b;
	}
	
	/**
	 * If vertical, sets the width of the {@link Button}s
	 * to a proportion of the width of the bar.
	 * If horizontal, sets the height of the buttons
	 * to a proportion of the height of the bar.
	 * The {@link Lcd} is also sized to fit.
	 */
	private void updateSize() {
		float min = Math.min(getWidth(), getHeight());
		int ratio = (int) (min * GuiProperties.MARGIN_PROPORTION);
		Dimension bSize = new Dimension(ratio, ratio);
		for (Button b : buttons) {
			//BoxLayout respects size if all three are set
			b.setPreferredSize(bSize);
			b.setMaximumSize(bSize);
			b.setMinimumSize(bSize);
		}
		if (lcd != null) lcd.setShorterSize(ratio);
	}
	
	/** @return The {@link Lcd} on this edge, or null */
	public Lcd getLcd() {
		return lcd;
	}
}
