package simori;

import simori.Exceptions.SimoriNonFatalException;

public class Animation {
	
	private boolean isStartup;
	private int size, phase;
	private boolean[][] which;
	private OnFinishListener listener;
	
	public Animation(int size, OnFinishListener listener, boolean isStartup) {
		this.isStartup = isStartup;
		this.listener = listener;
		this.size = size;
		which = new boolean[size][size];
		phase = isStartup ? 0 : size / 2 - 1;
		if (!isStartup) return;
		for (boolean[] row : which) {
			for (int i = 0; i < row.length; i++) row[i] = true;
		}
	}
	
	public boolean[][] next() throws SimoriNonFatalException {
		boolean endA =  isStartup && phase >= size / 2;
		boolean endB = !isStartup && phase <  0;
		if (endA || endB) {
			listener.onAnimationFinished();
			return null;
		}
		drawSquare(which, phase, !isStartup);
		phase += isStartup ? 1 : -1;
		return which;
	}
	
	private void drawSquare(boolean[][] grid, int phase, boolean to) {
		int size = grid.length;
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - phase;
		int tr = size / 2 + phase + symmetry;
		for (int i = bl; i <= tr; i++) {
			grid[tr][i] = grid[bl][i] = to;
			grid[i][tr] = grid[i][bl] = to;
		}
	}
	
	public void finished() {
		if (listener != null) listener.onAnimationFinished();
	}
	
	public interface OnFinishListener {
		
		public void onAnimationFinished();
	}
}