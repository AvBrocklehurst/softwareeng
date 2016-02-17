package simori;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
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
			String fileName = Paths.get("../").toAbsolutePath().normalize().toString()+"\\instruments.csv";
			scanner = new Scanner(new File(fileName));
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
