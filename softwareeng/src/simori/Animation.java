package simori;

public class Animation {
	
	private OnFinishListener listener;
	
	public Animation(OnFinishListener listener) {
		this.listener = listener;
	}
	
	public void setSquareOfGrid(boolean[][] grid, int phase, boolean to) {
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