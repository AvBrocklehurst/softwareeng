package simori;

import static simori.FunctionButton.L1;
import static simori.FunctionButton.L2;
import static simori.FunctionButton.L3;
import static simori.FunctionButton.L4;
import static simori.FunctionButton.OK;
import static simori.FunctionButton.R1;
import static simori.FunctionButton.R2;
import static simori.FunctionButton.R3;
import static simori.FunctionButton.R4;

import java.util.HashMap;

import simori.SimoriGui.Animation;

public class GreyCentreWipe implements Animation {
	
	private boolean outwards, upwards;
	protected int size, phase;
	private int frameNum;
	protected Frame frame;
	private FunctionButton[][] btns =
		{{L1, R1}, {L2, R2}, {L3, R3}, {L4, R4}, {OK}};
	
	public GreyCentreWipe(boolean ledInitGrey, boolean outwards,
							boolean btnInitGrey, boolean upwards,
								int size) {
		this.outwards = outwards;
		this.size = size;
		frame = new Frame();
		frame.ledsGreyed = new boolean[size][size];
		frame.btnsGreyed = new HashMap<FunctionButton, Boolean>();
		phase = outwards ? 0 : size / 2 - 1;
		frameNum = 0;
		initialise(ledInitGrey, btnInitGrey);
	}
	
	private void initialise(boolean ledInitGrey, boolean btnInitGrey) {
		for (boolean[] row : frame.ledsGreyed) {
			for (int i = 0; i < row.length; i++)
				row[i] = ledInitGrey;
		}
		for (FunctionButton[] row : btns) {
			for (FunctionButton fb : row) {
				frame.btnsGreyed.put(fb, btnInitGrey);
			}
		}
	}
	
	public int getFrameCount() {
		return size / 2 + btns.length;
	}
	
	public Frame getNextFrame() {
		int excess = frameNum - size / 2;
		if (excess < 0) {
			int phase = outwards ? frameNum : size / 2 - frameNum - 1;
			invertSquare(frame.ledsGreyed, phase);
		} else if (excess < btns.length) {
			int i = upwards ? excess : btns.length - 1 - excess;
			for (FunctionButton fb : btns[i]) {
				boolean greyed = frame.btnsGreyed.get(fb);
				frame.btnsGreyed.put(fb, !greyed);
			}
		} else {
			frame = null;
		}
		frameNum++;
		return frame;
	}
	
	private void invertSquare(boolean[][] grid, int phase) {
		int size = grid.length;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - phase;
		int tr = size / 2 + phase + symmetry;
		for (int i = bl + 1; i < tr; i++) {
			grid[tr][i] = !grid[tr][i];
			grid[bl][i] = !grid[bl][i];
			grid[i][tr] = !grid[i][tr];
			grid[i][bl] = !grid[i][bl];
		}
		grid[tr][tr] = !grid[tr][tr];
		grid[tr][bl] = !grid[tr][bl];
		grid[bl][tr] = !grid[bl][tr];
		grid[bl][bl] = !grid[bl][bl];
	}
}
