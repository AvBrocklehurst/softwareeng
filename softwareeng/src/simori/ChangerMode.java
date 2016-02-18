package simori;

import static simori.FunctionButton.*;

import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

public class ChangerMode extends Mode {
	
	private ModeController controller;
	private Changer changer;
	private boolean hLine, vLine;
	
	public ChangerMode(ModeController controller, Changer changer,
			boolean verticalLine, boolean horizontalLine) {
		super(controller);
		this.controller = controller;;
		this.changer = changer;
		this.hLine = horizontalLine;
		this.vLine = verticalLine;
	};
	
	private void drawSelector(int x, int y) {
		boolean[][] grid = new boolean[16][16]; //FIXME hardcoded 16s
		if (vLine) addVerticalLineTo(grid, x);
		if (hLine) addHorizontalLineTo(grid, y);
		getGui().setGrid(grid);
	}

	@Override
	public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException {
		String text = changer.getText(e.getX(), e.getY());
		e.getSource().setText(text);
		if (text == null) return;
		drawSelector(e.getX(), e.getY());
	}
	
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		if (e.getFunctionButton() == OK) {
			if (!changer.doThingTo(controller)) return;
		}
		getGui().setText(null);
		super.onFunctionButtonPress(e);
	}
	
	@Override
	public void setInitialGrid() {
		Setting current = changer.getCurrentSetting();
		if (current == null) {
			getGui().clearGrid();
		} else {
			drawSelector(current.x, current.y);
		}
	}
	
	private void addVerticalLineTo(boolean[][] grid, int x) {
		for (boolean[] row : grid) {
			row[x] = true;
		}
	}
	
	private void addHorizontalLineTo(boolean[][] grid, int y) {
		for (int x = 0; x < grid[y].length; x++) {
			grid[y][x] = true;
		}
	}
	
	public interface Changer {
		public String getText(int x, int y);
		public boolean doThingTo(ModeController controller);
		public Setting getCurrentSetting();
	}
	
	public class Setting {
		public Integer x, y;
		public Setting(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
