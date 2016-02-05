package simori;

public class Simori {
	
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;

	public static void main(String[] args) {
		MatrixModel model = new MatrixModel();
		SimoriGui gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		gui.setMode(new PerformanceMode(0,0)); //TODO Off mode by default
	}
}
