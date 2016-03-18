package simori;

public class Animation {
	
	private OnFinishListener listener;
	
	public Animation(OnFinishListener listener) {
		this.listener = listener;
	}
	
	public boolean[][] makeSquareGrid(int size, int phase) {
		boolean[][] grid = new boolean[size][size];
		int symmetry = --size % 2 == 0 ? 0 : 1;
		int bl = size / 2 - phase;
		int tr = size / 2 + phase + symmetry;
		for (int i = bl; i <= tr; i++) {
			grid[tr][i] = true;
			grid[bl][i] = true;
			grid[i][tr] = true;
			grid[i][bl] = true;
		}
		return grid;
	}
	
	public void finished() {
		if (listener != null) listener.onAnimationFinished();
	}
	
	public interface OnFinishListener {
		
		public void onAnimationFinished();
	}
}