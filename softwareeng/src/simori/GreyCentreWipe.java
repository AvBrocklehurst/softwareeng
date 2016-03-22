package simori;

import static simori.FunctionButton.*;

import java.util.ArrayList;

public class GreyCentreWipe implements Animation {
	
	private boolean outwards;
	protected int size, phase;
	private int frameNum;
	protected Frame frame;
	private OnFinishListener listener;
	private ArrayList<FunctionButton> btnsGreyed;
	private FunctionButton[][] btns =
		{{L1, R1}, {L2, R2}, {L3, R3}, {L4, R4}, {OK}};
	
	public GreyCentreWipe(boolean ledInitGrey, boolean outwards,
							boolean btnInitGrey, boolean upwards,
								int size, OnFinishListener listener) {
		this.outwards = outwards;
		this.listener = listener;
		this.size = size;
		frame = new Frame();
		frame.ledsGreyed = new boolean[size][size];
		btnsGreyed = new ArrayList<FunctionButton>();
		phase = outwards ? 0 : size / 2 - 1;
		frameNum = 0;
		initialise(ledInitGrey, btnInitGrey);
	}
	
	private void initialise(boolean ledInitGrey, boolean btnInitGrey) {
		for (boolean[] row : frame.ledsGreyed) {
			for (int i = 0; i < row.length; i++)
				row[i] = ledInitGrey;
		}
		if (!btnInitGrey) return;
		for (FunctionButton[] row : btns) {
			for (FunctionButton fb : row) btnsGreyed.add(fb);
		}
	}
	
	public Frame next() {
		if (frameNum < size / 2 ) {
			int phase = outwards ? frameNum : size / 2 - frameNum - 1;
			invertSquare(frame.ledsGreyed, phase);
		} else if (frameNum < (size / 2) + btns.length) {
			//TODO take a function button off
		} else {
			if (listener != null) listener.onAnimationFinished();
			return null;
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
