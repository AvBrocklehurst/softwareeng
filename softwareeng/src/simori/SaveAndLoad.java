package simori;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Class for the saving and loading of the model.
 * @version 1.0.0
 * @author Adam
 *
 */
public class SaveAndLoad {
	
	/**
	 * Static method to save the contents of the model to a given file.
	 * @author Adam
	 * @version 1.0.0
	 * @param model     The model to serialize.
	 * @param filename  The file to save it to.
	 */
	public static void save(MatrixModel model, String filename){
		try {
	        FileOutputStream fos = new FileOutputStream(filename);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(model);
	        oos.close();
		} catch (Exception ex){
	        System.out.println(("Exception thrown during test: " + ex.toString()));
	    }
	}
	
	
	/**
	 * Static method to load a model into the simori-on.
	 * @author Adam
	 * @param model     Model to replce.
	 * @param filename  Filename of where to load the saved model from.
	 */
	public static void load(MatrixModel model, String filename){
		try {
	        FileInputStream fos = new FileInputStream(filename);
	        ObjectInputStream oos = new ObjectInputStream(fos);
	        MatrixModel tempModel = (MatrixModel)oos.readObject();
	        oos.close();
	        model.convertModel(tempModel);
		} catch (Exception ex){
	        System.out.println(("Exception thrown during test: " + ex.toString()));
	    }
	}
	
}
