package simori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InstrumentNamer {
	private static Map<Integer, String> map;
	private static InstrumentNamer instance;
	
	private InstrumentNamer() {
		
		map = new HashMap<Integer, String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("C:/Users/Josh/Documents/University/instruments.csv"));
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
	
	public static void main(String[] args) {
		System.out.println(InstrumentNamer.getInstance().getName(110));
	}
	
	public static InstrumentNamer getInstance(){
	if(instance == null){
	instance = new InstrumentNamer();
	}
	return instance;
	}
	


}
