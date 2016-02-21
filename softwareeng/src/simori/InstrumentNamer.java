package simori;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InstrumentNamer {
	
	private static final String CSV_NAME = "instruments.csv";
	
	private static Map<Integer, String> map;
	private static InstrumentNamer instance;
	
	private InstrumentNamer() {
		
		map = new HashMap<Integer, String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(ResourceManager.getResource(CSV_NAME));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		while(scanner.hasNext()){
			String[] instrument = scanner.nextLine().split(",");
        	map.put(Integer.parseInt(instrument[0]), instrument[1]);
        }
        scanner.close();
	}
	
	
	public String getName(int num){
		return map.get(num);
	}
	
	public static InstrumentNamer getInstance(){
		if(instance == null){
			instance = new InstrumentNamer();
		}
		return instance;
	}
}
