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
 * @version 1.0.0
 * @see Mode, ModeController
 */
public class ShopBoyMode extends PerformanceMode implements Observer {
	
	private File currentFile;     //a handle on the current file to be played
	private int counter = 0;         //keep track of the track directory
	private int partCounter = 0;     //keep track of the song part
	private File shopboy;            //main res directory
	private File[] song;

	public ShopBoyMode(ModeController controller) {
		super(controller);
		shopboy = ResourceManager.getResource("ShopBoySongs");    //get the folder
	}
	
	/**{@inheritDoc}*/
	/**
	 * @author James
	 * @version 1.0.0
	 */
	@Override
	public void setInitialGrid() {
		getModel().addObserver(this);
		getGui().clearGrid();
		playShopBoy(shopboy);      //begin the first song
		songPlay(currentFile);
	}
	
	/**{@inheritDoc}*/
	@Override
	public void onFunctionButtonPress(FunctionButtonEvent e){
		switch(e.getFunctionButton()){
		case ON:
			getModeController().setOn(!getModeController().isOn(), true);
			break;
		case OK:
			getModeController().setMode(new PerformanceMode(getModeController()));
			break;  //return to Performance Mode as normal
		
		default:
			getController().sadSound();
			break; //ignore all other function buttons
		}
	}
	
	/**
	 * This method sets the current LCD text to a found subdirectory or song
	 * name and makes that song the current song to play.
	 * 
	 * @author James
	 * @version 1.3.0
	 * @see java.io.File, Mode.getGui()
	 * @param f  A file to search for songs in
	 */
	private void playShopBoy(File f){
		if(!f.isDirectory() || f == null){        //if shopboy for some reason was not retrieved correctly
			System.err.println("Shopboy is not a directory or is null!");
			return;
		}
		
		else{
			File[] files = f.listFiles();        //a list of subdirectories to ShopBoySongs
			if(counter == files.length) counter = 0;       //loops if the end of the songs is reached
			getGui().setText(files[counter].getName());      //song name on LCD
			currentFile = files[counter];
		}
	}
	
	/**
	 * This method finds all the part files for a given song and then 
	 * loads one in depending on what has been played so far (partCounter).
	 * 
	 * @author James
	 * @version 2.0.0
	 * @see java.io.File, SaveAndLoad.load()
	 * @param f   A single song to process
	 */
	private void songPlay(File f){
		song = f.listFiles();      //list all files in a single song subdirectory
		SaveAndLoad.load(getModel(), song[partCounter].getName());       //load in a part
	}

	/**{@inheritDoc}*/
	/**
	 * @author James
	 * @version 2.0.0
	 */
	@Override
	public void update(Observable o, Object arg) {
		byte looppoint = getModel().getLoopPoint();
		byte currentcolumn = getModel().getCurrentColumn();
		
		if(currentcolumn == 0){                 //if the end of the simori's columns is reached
			partCounter++;       //next part

			if(partCounter == song.length){       //if all parts have been played
				partCounter = 0;
				counter++;                //next song subdirectory
				playShopBoy(shopboy);
				songPlay(currentFile);
			}
			
			else{
				playShopBoy(shopboy);       //continue to play
				songPlay(currentFile);
			}
		}
		
	}
	
	
}
