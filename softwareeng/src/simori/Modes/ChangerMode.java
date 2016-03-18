package simori.Modes;

import simori.ModeController;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;

/**
 * Mode which implements the common functionality of modes such as
 * Change Voice Mode and Change Velocity Mode. Specific functionality
 * is customised by providing a {@link #Changer} implementation.
 * @author Matt
 * @version 2.7.5
 */
public class ChangerMode extends Mode {
	
	private ModeController controller;
	private Changer changer;
	private boolean hLine, vLine;
	private int rows, columns;
	
	/**
	 * Creates a mode which changes some property of the Simori-ON,
	 * as specified by the {@link #Changer} provided.
	 * @param controller
	 * @param changer The specific desired behaviour
	 * @param verticalLine true if a vertical line is to be drawn
	 * @param horizontalLine true if a horizontal line is to be drawn
	 */
	public ChangerMode(ModeController controller, Changer changer,
			boolean verticalLine, boolean horizontalLine) {
		super(controller);
		this.controller = controller;
		this.changer = changer;
		this.hLine = horizontalLine;
		this.vLine = verticalLine;
		
		//Measure the number of rows and columns from the model
		boolean[][] grid = getModel().getGrid(getDisplayLayer());
		rows = grid.length;
		columns = grid[0].length;
	}
	
	/**
	 * Draws the horizontal and/or vertical lines applicable to this mode at
	 * the coordinates pressed, by illuminating the required pattern of LEDs.
	 * @param x The column in which to draw a vertical line, if applicable
	 * @param y The row in which to draw a horizontal line, if applicable
	 */
	private void drawSelector(Byte x, Byte y) {
		boolean[][] grid = new boolean[rows][columns];
		if (vLine && x != null) addVerticalLineTo(grid, x);
		if (hLine && y != null) addHorizontalLineTo(grid, y);
		getGui().setGrid(grid);
	}
	
	/**
	 * Queries the {@link Changer} for the text describing the setting
	 * which corresponds to the coordinates of the button pressed.
	 * If text exists, it is displayed on the LCD screen. If a setting
	 * exists at those coordinates, the selector line(s) are drawn.
	 */
	@Override
	public void onGridButtonPress(GridButtonEvent e) throws SimoriNonFatalException {
		Setting setting = new Setting((byte) e.getX(), (byte) e.getY());
		String text = changer.getText(setting);
		if (text == null) return;
		e.getSource().setText(text);
		drawSelector((byte) e.getX(), (byte) e.getY());
	}
	
	/**
	 * Intercepts press events for the L, R, ON and OK buttons.
	 * Standard behaviour is permitted for the power button.
	 * L and R buttons are ignored and do not switch mode.
	 * The OK button is ignored unless the selected setting is valid.
	 * If the setting is valid, {@link Changer#doThingTo} is called
	 * to apply the change, and the standard behaviour of returning
	 * to {@link PerformanceMode} is allowed to proceed.
	 */
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		switch (e.getFunctionButton()) {
		case ON:
			super.onFunctionButtonPress(e);
			break;
		case OK:
			if (!changer.doThingTo(controller)) break;
			getGui().setText(null); //Clear screen
			super.onFunctionButtonPress(e);
			break;
		default:
			//Ignore L and R buttons
			break;
		}
	}
	
	/**
	 * Draws the vertical and/or horizontal selection lines
	 * at a position specified by the {@link Changer}. This
	 * allows for the current setting to be shown to the user
	 * when the mode is entered. If the Changer does not
	 * provide an initial setting, the grid is simply cleared.
	 */
	@Override
	public void setInitialGrid() {
		Setting current = changer.getCurrentSetting();
		if (current == null) {
			getGui().clearGrid();
			getGui().setText(null);
		} else {
			getGui().setText(changer.getText(current));
			drawSelector(current.x, current.y);
		}
	}
	
	/**
	 * Sets a particular entire 'column' true.
	 * @param grid The 'grid' of booleans to modify
	 * @param x The column to set true
	 */
	private void addVerticalLineTo(boolean[][] grid, int x) {
		for (boolean[] row : grid) {
			row[x] = true;
		}
	}
	
	/**
	 * Sets a particular entire 'row' true.
	 * @param grid The 'grid' of booleans to modify
	 * @param y The row to set true
	 */
	private void addHorizontalLineTo(boolean[][] grid, int y) {
		for (int x = 0; x < grid[y].length; x++) {
			grid[y][x] = true;
		}
	}
	
	/**
	 * Used to specify the unique functionality of a {@link ChangerMode}.
	 * @author Matt
	 * @version 2.0.0
	 */
	public interface Changer {
		
		/**
		 * Called when a grid button has been pressed and the name of
		 * the value corresponding to its coordinates must be displayed.
		 * The generated text is displayed on the LCD screen. If the
		 * button does not correspond to a valid setting, the string may
		 * be null, which will result in no selector line(s) being drawn.
		 * @param setting Containing the coordinates of the button pressed
		 * @return A description of the setting, or null if none exists
		 */
		public String getText(Setting setting);
		
		/**
		 * Called when the OK button is pressed and the changes are to
		 * be applied. The proposed change is the setting corresponding
		 * to the coordinates most recently reported to {@link #getText}.
		 * These can be stored locally in the implementation.
		 * If the change is valid, the new setting should be applied.
		 * Returning true will cause {@link PerformanceMode} to resume.
		 * The given {@link ModeController} provides access to the modes
		 * and model, to allow changes to be made where applicable.
		 * @param controller upon which to make the relevant changes
		 * @return true if the changes were applied successfully
		 */
		public boolean doThingTo(ModeController controller);
		
		/**
		 * Allows the current setting of a property to be specified.
		 * If the {@link Setting} is not null, the selector line(s)
		 * will be drawn at its specified x and/or y positions.
		 * @return the initial location to display the selector(s)
		 */
		public Setting getCurrentSetting();
	}
	
	/**
	 * Data structure used with {@link Changer}.
	 * Encapsulates the x and y coordinates of a pressed grid button.
	 * Represents the proposed setting the user has entered.
	 * @author Matt
	 * @version 3.0.0
	 */
	public static class Setting {
		
		/** x coordinate corresponding to proposed setting */
		public Byte x;
		
		/** y coordinate corresponding to proposed setting */
		public Byte y;
		
		/** Leaves x and y null */
		public Setting() {}
		
		/** Convenience constructor for setting x and y */
		public Setting(Byte x, Byte y) {
			this.x = x;
			this.y = y;
		}
	}
}