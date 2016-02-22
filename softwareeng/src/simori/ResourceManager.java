package simori;

import java.io.File;

/**
 * Manages locating the project's resource folder, res.
 * Provides a static helper method for returning a
 * file located within the folder, given its name.
 * @author Matt
 * @version 1.1.0
 */
public class ResourceManager {
	
	private static final String UP_ONE = "../";
	private static final String RES_FOLDER_NAME = "res";
	
	private static final String DOWN_ONE = "softwareeng/";
	private static final String DOWN_ALT = "softwareeng-master/";
	
	private static File workingDir, resFolder;
	
	/**
	 * Attempts to locate and return the specified
	 * file from the project's resources folder.
	 * Returns null if the res folder cannot be located.
	 * Even if the folder can be located, the requested file
	 * may not be present, so its existence should be checked.
	 * @param fileName The file name including extension
	 */
	public static File getResource(String fileName) {
		if (resFolder == null) findResFolder(); //May not succeed
		if (!resFolder.isDirectory()) return null;
		return new File(resFolder, fileName); //Leave exists() check to caller
	}
	
	/**
	 * Attempts to locate the resources folder from whatever location
	 * in the project's folder structure the program is run.
	 * The folder res is located in the innermost 'softwareeng' or
	 * 'softwareeng-master' folder. If the starting folder does not
	 * contain a folder with this name, a maximum of three directories
	 * above are checked. The res folder is then assumed to be some
	 * finite number of softwareeng folders below.
	 */
	private static void findResFolder() {
		workingDir = new File("."); //Start where the program is being run
		for (int tries = 0; tries < 3; tries++) {
			if (isInProject()) break; //To find softwareeng folder...
			cd(UP_ONE); //...traverse upward a maximum of three times
		}
		while (cd(DOWN_ONE) || cd(DOWN_ALT)); //Navigate to inner folder
		resFolder = new File(workingDir, RES_FOLDER_NAME); //Should be here
		workingDir = null; //Dereference temporary file
	}
	
	/**
	 * Appends the given extension to the path of {@link #workingDir}
	 * and sets workingDir to this new location if it is a directory.
	 * @param path The relative path of the folder to change to
	 * @return true if the working directory was changed
	 */
	private static boolean cd(String path) {
		File file = new File(workingDir, path);
		if (file.isDirectory()) {
			workingDir = file;
			return true;
		}
		return false;
	}
	
	/**
	 * Determines whether the {@link #workingDir} is inside the project's
	 * hierarchy of folders named 'softwareeng' or 'softwareeng-master'.
	 * @return true there is a softwareeng folder below
	 */
	private static boolean isInProject() {
		return new File(workingDir, DOWN_ONE).exists()
				|| new File(workingDir, DOWN_ALT).exists();
	}
}
