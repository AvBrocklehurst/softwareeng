package simori;

import static simori.FunctionButton.*;

import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.GridButtonEvent;
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

	@Override
	public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException {
		String text = changer.getText(e.getX(), e.getY());
		e.getSource().setText(text);
		if (text == null) return;
		boolean[][] grid = new boolean[16][16]; //FIXME hardcoded 16s
		if (vLine) addVerticalLineTo(grid, e.getX());
		if (hLine) addHorizontalLineTo(grid, e.getY());
		e.getSource().setGrid(grid);
	}
	
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e) {
		if (e.getFunctionButton() == OK) {
			if (!changer.doThingTo(controller)) return;
		}
		super.onFunctionButtonPress(e);
	}
	
	public void addVerticalLineTo(boolean[][] grid, int x) {
		for (boolean[] row : grid) {
			row[x] = true;
		}
	}
	
	public void addHorizontalLineTo(boolean[][] grid, int y) {
		for (int x = 0; x < grid[y].length; x++) {
			grid[y][x] = true;
		}
	}
	
	public interface Changer {
		public String getText(int x, int y);
		public boolean doThingTo(ModeController controller);
	}
}
