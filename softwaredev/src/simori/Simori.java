package simori;

public class Simori {
	
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;

	public static void main(String[] args) {
		MatrixModel model = new MatrixModel(); //Use GRID_WIDTH and GRID_HEIGHT?
		SimoriGui gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		PerformanceMode mode;
		gui.setMode(mode = new PerformanceMode(model,0,0)); //TODO Off mode by default
		MIDISoundPlayer midi = new MIDISoundPlayer();
		Clock clock = new Clock(model, midi, mode, 88);
		Thread thread = new Thread(clock);
		thread.start();
	}
}
