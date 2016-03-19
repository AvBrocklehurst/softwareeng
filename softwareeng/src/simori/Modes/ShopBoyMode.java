package simori.Modes;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import simori.ModeController;
import simori.ResourceManager;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.GridButtonEvent;
import simori.Exceptions.SimoriNonFatalException;

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
	
	private File currentFile;
	private int counter = 0;
	private File shopboy;

	public ShopBoyMode(ModeController controller) {
		super(controller);
		shopboy = ResourceManager.getResource("ShopBoySongs");
		playShopBoy(shopboy);
		songPlay(currentFile);
	}
	
	@Override
	public void setInitialGrid() {
		getModel().addObserver(this);
		getGui().clearGrid();
		getGui().setText("Shop boy mode (in development!)");
	}

	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e){
		switch(e.getFunctionButton()){
		case OK:
			super.onFunctionButtonPress(e);  //return to Performance Mode as normal
		
		default:
			getController().sadSound();
			break; //ignore all other function buttons
		}
	}
	
	private void playShopBoy(File f){
		
		if(!f.isDirectory() || f == null){
			System.err.println("Shopboy is not a directory or is null!");
			return;
		}
		
		else{
			File[] files = f.listFiles();
			for(int i=0; i<files.length; i++){
				getGui().setText(files[i].getName());
				currentFile = files[i];
				if(counter == i) break;
			}
		}
	}
	
	private void songPlay(File f){
		File[] song = f.listFiles();
		for(int i=0; i<song.length; i++){
			SaveAndLoad.load(getModel(), song[i].getName());
			if(counter == i) break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		byte looppoint = getModel().getLoopPoint();
		byte currentcolumn = getModel().getCurrentColumn();
		
		if(looppoint == currentcolumn){
			counter++;
			playShopBoy(shopboy);
			songPlay(currentFile);
		}
		
	}
	
	
}
