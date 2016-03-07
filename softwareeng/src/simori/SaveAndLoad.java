package simori;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Class for the saving and loading of the model.
 * @version 1.0.0
 * @author Adam
 *
 */
public class SaveAndLoad {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String DOCUMENTS = "Documents";
	private static final String SAVES = "Simori-ON Songs";
	
	/**
	 * Static method to save the contents of the model to a given file.
	 * @author Adam
	 * @version 1.0.0
	 * @param model     The model to serialize.
	 * @param filename  The file to save it to.
	 */
	public static void save(MatrixModel model, String filename){
		try {
			File file = getLocationFor(filename);
	        FileOutputStream fos = new FileOutputStream(file);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(model);
	        oos.close();
		} catch (Exception ex){
	        System.out.println(("Exception thrown during test: " + ex.toString()));
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
	        oos.close();
	        model.convertModel(tempModel);
	        return true;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
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
}