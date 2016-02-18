package simori;

import simori.SimoriGui.GridButtonEvent;
import simori.SimoriGui.GridButtonListener;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * The class for Performance Mode, extending
 * the abstract class Mode.
 * 
 * @author James
 * @version 1.0.0
 * @see Mode
 */

public class PerformanceMode extends Mode implements GridButtonListener {
	
	private boolean[][] grid;

	/**
	 * Constructor for Performance Mode. In performance mode the ticker loops
	 * at a loopspeed to a certain looppoint. Notes are played depending on the
	 * layer. They are played with a certain voice and at a certain velocity.
	 * 
	 * @param loopspeed
	 * @param looppoint
	 * @author James
	 * @version 1.2.0
	 * @see makeGridCopy()
	 */
	public PerformanceMode(ModeController controller){
		super(controller);
		makeGridCopy((byte) controller.getDisplayLayer());
	}
	
	/**
	 * When a grid button is pressed in Performance mode, this method updates the current 
	 * layer by inverting the true/false value at the coordinates of press in the Grid.
	 * 
	 * @param e (A GridButtonEvent)
	 * @author James
	 * @see SimoriGuiEvents$GridButtonEvent, SimoriGuiEvents.GridButtonListener, Layer.updateButton, SimoriGui.setGrid
	 * @see GridButtonEvent.getX(), GridButtonEvent.getY(), GridButtonEvent.getSource()
	 * @version 1.1.3
	 */
	public void onGridButtonPress(GridButtonEvent e) throws InvalidCoordinatesException{
		
		int x = e.getX();            //grid position of button press
		int y = e.getY();  
		SimoriGui sc = e.getSource();
		
		grid[y][x] = !grid[y][x];    //invert grid button

		getModel().updateButton(getDisplayLayer(), (byte) x, (byte) y);   //update the data structure by inverting button at Gui position x,y
		sc.setGrid(grid);       //relay the change to the gui
	}
	
	/**
	 * Instructs the GUI what buttons to naturally light on passing of the
	 * clock. Modifies the layer with the buttons needed. Whole layer can be
	 * obtained from getCurrentLayer().
	 * 
	 * @param col
	 * @author James
	 * @throws InvalidCoordinatesException
	 * @see simori.Exceptions.InvalidCoordinatesException, makeGridCopy(), Simori.getGui(), SimoriGui.setGrid()
	 * @version 2.0.1
	 */
	@Override
	public void tickerLight(byte col) throws InvalidCoordinatesException {
		
		makeGridCopy(getDisplayLayer());   //copy the grid
		grid[0][col] = true;
		grid[5][col] = true;
		grid[10][col] = true;   
		grid[15][col] = true;	//positions of buttons due to the clock, forcing them to light on tick
		getGui().setGrid(grid); //FIXME: Mode may have changed! (Without this being deconstructed yet)
	}
	
	/**
	 * Gets the current grid for the current model, based on the current layer number
	 * (layno). Then a grid to copy to is created. The whole grid and then button by button
	 * are copied into the new grid.
	 * 
	 * @author James
	 * @param layno
	 * @version 1.1.2
	 * @see Simori.getModel(), MatrixModel.getGrid(), System.arraycopy()
	 */
	public void makeGridCopy(byte layno){
		
		boolean[][] grid1 = getModel().getGrid(layno);   //grid in use
		grid = new boolean[grid1.length][];       //grid to copy to
		
		System.arraycopy(grid1, 0, grid, 0, grid1.length);
		
		for(int i = 0 ; i<grid.length ; i++){
			grid[i] = new boolean[grid1[i].length];
			System.arraycopy(grid1[i], 0, grid[i], 0, grid1[i].length);   //deep copy element by element
		}
	}
	
	/**
	 * A method to get the current grid pattern when its been modified by
	 * tickerLight or onGridButtonPress. This grid pattern is passed to
	 * the gui to instruct the lights to turn on.
	 * 
	 * @author James
	 * @return boolean[][]
	 * @version 1.0.0
	 * @see tickerLight, onGridButtonPress
	 */
	public boolean[][] getModifiedGrid(){
		return grid;
	}
}
