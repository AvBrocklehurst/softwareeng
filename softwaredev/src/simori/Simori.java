package simori;

public class Simori {
	
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;
	private SimoriGui gui;
	private MatrixModel model;

	public static void main(String[] args) {
		Simori simori = new Simori();
		simori.model = new MatrixModel(); //Use GRID_WIDTH and GRID_HEIGHT?
		simori.gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		PerformanceMode mode = new PerformanceMode(simori,0,0);
		simori.gui.setMode(mode); //TODO Off mode by default
		MIDISoundPlayer midi = new MIDISoundPlayer();
		Clock clock = new Clock(simori.model, midi, mode, 88);
		Thread thread = new Thread(clock);
		thread.start();
	}
	
	public SimoriGui getGui(){
		return gui;
	}
	
	public MatrixModel getModel(){
		return model;
	}
}
