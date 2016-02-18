package simori;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LedPanel extends JPanel {
	
	private Led[][] leds;
	
	public LedPanel(int rows, int columns, OnPressListenerMaker maker) {
		setLayout(new GridLayout(rows, columns, 0, 0));
		leds = new Led[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				leds[x][y] = new Led();
				add(leds[x][y]);
				leds[x][y].setOnPressListener(maker.getListener(x, y)); //TODO start numbering from bottom left, not top
			}
		}
		setBackground(GuiProperties.LED_PANEL_BACKGROUND);
		setBorder(BorderFactory.createLineBorder(GuiProperties.LED_PANEL_BORDER));
	}
	
	public void setGrid(boolean[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				
				//FIXME Unexplained transpose necessary
				leds[x][y].setIlluminated(grid[y][x]);
			}
		}
	}
}