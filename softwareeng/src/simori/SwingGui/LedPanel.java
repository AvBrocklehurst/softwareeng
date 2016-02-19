package simori.SwingGui;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import simori.SwingGui.OnPressListenerMaker.OnPressListener;

public class LedPanel extends JPanel implements OnPressListener {
	
	private Led[][] leds;
	private PressableCircle lastPressed;
	
	public LedPanel(int rows, int columns, OnPressListenerMaker maker) {
		addLeds(rows, columns, maker);
		setBackground(GuiProperties.LED_PANEL_BACKGROUND);
		setBorder(BorderFactory.createLineBorder(GuiProperties.LED_PANEL_BORDER));
		setCursor(GuiProperties.NORMAL_CURSOR);
		getToolkit().addAWTEventListener(makeGlobalMouseReleaseListener()
				, AWTEvent.MOUSE_EVENT_MASK);
	}
	
	public void setGrid(boolean[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				leds[x][y].setIlluminated(grid[y][x]);
			}
		}
	}
	
	private void addLeds(int rows, int columns, OnPressListenerMaker maker) {
		setLayout(new GridLayout(rows, columns, 0, 0));
		leds = new Led[rows][columns];
		for (int y = rows-1; y >= 0; y--) {
			for (int x = 0; x < columns; x++) {
				Led led = new Led();
				add(led);
				led.addOnPressListener(maker.getListener(x, y));
				led.addOnPressListener(this);
				leds[x][y] = led;
			}
		}
	}

	@Override
	public void onPress(PressableCircle circle) {
		lastPressed = circle;
	}

	private AWTEventListener makeGlobalMouseReleaseListener() {
		return new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event.getID() != MouseEvent.MOUSE_RELEASED) return;
				if (lastPressed == null) return;
				lastPressed.mouseReleased((MouseEvent) event);
			}
		};
	}
}