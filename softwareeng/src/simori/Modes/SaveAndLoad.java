package simori.Modes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import simori.MatrixModel;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.ChangerMode.Changer;

/**
 * Class for the saving and loading of the model.
 * @version 1.0.0
 * @author Adam
 * @author Matt
 * @author James
 */
public class SaveAndLoad {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String DOCUMENTS = "Documents";
	private static final String SAVES = "Simori-ON Songs";
	private static final String SONG_EXTENSION = ".song";   //song file
	private static final String SONG_NOT_FOUND = "Couldn't find song!";
	
	/**
	 * Static method to save the contents of the model to a given file.
	 * @author Adam
	 * @version 1.0.0
	 * @param model     The model to serialise.
	 * @param filename  The file to save it to.
	 * @throws SimoriNonFatalException 
	 */
	public static void save(MatrixModel model, String filename) {
		try {
			File file = getLocationFor(filename);
	        FileOutputStream fos = new FileOutputStream(file);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(model);
	        fos.close();
	        oos.close();
		} catch (Exception ex){
	        throw new SimoriNonFatalException("Unable to save file.");
	    }
	}
		
	/**
	 * Static method to load a model into the Simori-ON.
	 * It searches for the ShopBoySongs folder for use
	 * in the Shop Boy mode.
	 * @author Adam
	 * @param model     Model to replace.
	 * @param filename  Filename of where to load the saved model from.
	 * @throws SimoriNonFatalException 
	 */
	public static boolean loadShop(MatrixModel model, String filename) {
		try {
			File file = new File(filename);
			if (!file.exists()){
				System.out.println("file doesn't exist");
				return false;
			}
	        FileInputStream fos = new FileInputStream(file);
	        ObjectInputStream oos = new ObjectInputStream(fos);
	        MatrixModel tempModel = (MatrixModel)oos.readObject();
	        fos.close();
	        oos.close();
	        model.convertModel(tempModel);
	        return true;
		} catch (Exception e){
			  throw new SimoriNonFatalException("Unable to save file.");
		}
	}
		
	/**
	 * Static method to load a model into the Simori-ON.
	 * @author Adam
	 * @author Matt
	 * @param model     Model to replace.
	 * @param filename  Filename of where to load the saved model from.
	 */
	public static boolean load(MatrixModel model, String filename){
		try {
			File file = getLocationFor(filename);
			if (!file.exists()) return false;
	        FileInputStream fos = new FileInputStream(file);
	        ObjectInputStream oos = new ObjectInputStream(fos);
	        MatrixModel tempModel = (MatrixModel)oos.readObject();
	        fos.close();
	        oos.close();
	        model.convertModel(tempModel);
	        return true;
		} catch (IOException e){
			 throw new SimoriNonFatalException("Unable to save file.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SimoriNonFatalException("Unable to save file.");
		}
	}
	
	/**
	 * Returns a file with the given name, located in a Simori-ON songs folder
	 * under their Documents folder. If they do not have a Documents folder in
	 * their home directory, the saves folder is created there instead.
	 * @author Matt
	 * @param fileName The name of the .song file, including extension
	 * @return The file, ready to be written to
	 */
	private static File getLocationFor(String fileName) {
		File home, documents, saves;
		home = new File(HOME); //User will definitely have a home directory
		documents = new File(home, DOCUMENTS); //May contain Documents
		saves = new File(documents.exists() ? documents : home, SAVES);
		if (!saves.exists()) saves.mkdir(); //Create folder for Simori-ON songs
		return new File(saves, fileName);
	}
	
	/**
	 * This implementation of the Changer interface allows a user to input
	 * Unix and Windows compatible symbols and letters in order to produce
	 * a filename to save the current simori configuration to.
	 * 
	 * @author James
	 * @version 1.0.0
	 * @see Changer.getText(), Changer.doThingTo(), Changer.getCurrentSetting(), coordsConverter(), 
	 * SaveAndLoad.save(), ModeController.getModel(), java.lang.String.substring()
	 * @return Changer
	 */
	protected static Changer makeSaveChanger(final ModeController controller){
		return new TextEntry(controller) {
			@Override
			protected boolean useText(String text) {
				if (text.length() == 0) return true;
				text += SONG_EXTENSION;   //add the .song extension
				SaveAndLoad.save(controller.getModel(), text);
				controller.happySound();
				return true;
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface allows a user to input
	 * Unix and Windows compatible symbols and letters in order to produce
	 * a filename to load a simori configuration from.
	 * 
	 * @author James
	 * @version 1.0.0
	 * @see Changer.getText(), Changer.doThingTo(), Changer.getCurrentSetting(), coordsConverter(), 
	 * SaveAndLoad.save(), ModeController.getModel(), java.lang.String.substring()
	 * @return Changer
	 */
	protected static Changer makeLoadChanger(final ModeController controller){
		return new TextEntry(controller) {
			@Override
			protected boolean useText(String text) {
				if (text.length() == 0) return true;
				text += SONG_EXTENSION;
				if (SaveAndLoad.load(controller.getModel(), text)) {
					controller.happySound();
					return true;
				} else {
					controller.sadSound();
					if (controller.getGui().getText().equals(SONG_NOT_FOUND)) {
						return true;
					} else {
						controller.getGui().setText(SONG_NOT_FOUND);
						return false;
					}
				}
			}
		};
	}
}