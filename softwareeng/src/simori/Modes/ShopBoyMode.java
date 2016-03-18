package simori.Modes;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import simori.ModeController;
import simori.ResourceManager;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * ShopBoyMode is a demonstration mode where a serious of songs
 * will be played to show the power of the Simori.
 * 
 * @author James
 * @author Matt
 * @version 1.0.0
 * @see Mode, ModeController
 */
public class ShopBoyMode extends Mode implements Observer {

	public ShopBoyMode(ModeController controller) {
		super(controller);
		File shopboy = ResourceManager.getResource("ShopBoySongs");
		
	}
	
	@Override
	public void setInitialGrid() {
		getModel().addObserver(this);
		getGui().clearGrid();
		getGui().setText("Shop boy mode (in development!)");
		((simori.SwingGui.SimoriJFrame) getGui()).testAnimation();
	}

	@Override
	public void onGridButtonPress(GridButtonEvent e)
			throws InvalidCoordinatesException {} // No grid input in this mode
	
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e){
		switch(e.getFunctionButton()){
		case OK:
			super.onFunctionButtonPress(e);  //return to Performance Mode as normal
		
		default:
			break; //ignore all other function buttons
		}
	}
	
	private void iterateFile(File f){
		
		if(!f.isDirectory() || f == null){
			System.err.println("Shopboy is not a directory or is null!");
			return;
		}
		
		else{
			File[] files = f.listFiles();
			for(int i=0; i<files.length; i++){
				getGui().setText(files[i].getName());
				
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//get loop point current column and check if their equal, if so next song
		
	}
	
	
}
