package simori;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import simori.Exceptions.SimoriNonFatalException;
/**
 * @author Josh
 * @version 1.2.0
 * @see ResourceManager
 * Class that uses a .csv file to map an instrument number to its name.
 * The class is a singleton class as only instance of it is required at any given time.
 * This class can be used to find the name of instrument (e.g. bagpipes) given its number (e.g. 110).
 */
public class InstrumentNamer {
	
	private static final String CSV_NAME = "instruments.csv";
	private static Map<Integer, String> map;
	private static InstrumentNamer instance;
	
	/**
	 * @author Josh 
	 * @version 1.0.3
	 * 
	 * Constructor for Instrument namer.
	 * Takes csv file and puts information stored it into a map you quicker access later on.
	 * @throws SimoriNonFatalException 
	 */
	private InstrumentNamer() {
		map = new HashMap<Integer, String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(ResourceManager.getResource(CSV_NAME)); //get the csv file.
		} catch (FileNotFoundException e) {
			throw new SimoriNonFatalException("Couldn't find instrument name file, instrument names will be unable to be shown.");
		}
		while(scanner.hasNext()){
			String[] instrument = scanner.nextLine().split(","); // take each line in csv file (each line consists of 2 elements).
        	map.put(Integer.parseInt(instrument[0]), instrument[1]); //add each element as key:value pair in the map.
        }
        scanner.close();
	}
	
	/**
	 * @author Josh
	 * @version 1.0.0
	 * @param num
	 * @return String  instrument name
	 * 
	 * Method that looks in the map for the instrument name.
	 */
	public String getName(int num){
		return map.get(num);
	}
	
	/**
	 * @author Josh
	 * @version 1.1.1
	 * @return InstrumentNamer  instance of class
	 * @throws SimoriNonFatalException 
	 */
	public static InstrumentNamer getInstance(){
		if(instance == null){
			instance = new InstrumentNamer(); // if we dont have an instance of instrument namer then get one.
		}
		return instance; // if we do have an instance then return that instance (to save us reconstructing it).
	}
}
