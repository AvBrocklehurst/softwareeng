package simori;

public class Simori {
	
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;

	public static void main(String[] args) {
		SimoriGui gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		//TODO gui.setMode(new PerformanceMode());
	}
}
