package simori;

public class Simori {
	
	private static final int GRID_WIDTH = 16, GRID_HEIGHT = 16;
	private SimoriGui gui;
	private MatrixModel model;
	
	public Simori(){
		
	}

	public static void main(String[] args) {
		Simori simori = new Simori();
		simori.model = new MatrixModel(); //Use GRID_WIDTH and GRID_HEIGHT?
		simori.gui = new SimoriGui(GRID_WIDTH, GRID_HEIGHT);
		simori.gui.setMode(new PerformanceMode(simori,0,0)); //TODO Off mode by default
		//JoshThing joshthing = new JoshThing()
		//joshthing.setKerryThing(new KerryThing(model))

	}
	
	public SimoriGui getSimoriGui(){
		return gui;
	}
	
	public MatrixModel getModel(){
		return model;
	}
}
