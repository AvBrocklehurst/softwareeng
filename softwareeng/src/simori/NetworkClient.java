package simori;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient {
	final static int    PORT  =  20160;
	
	public static void main(String[] args) throws IOException {
		iterateOverIPRange(getIP().substring(0, getIP().indexOf('.',getIP().indexOf('.') + 1) + 1));
	}
	  
	private static String getIP(){
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	  }
	  
	  private static void iterateOverIPRange(String ip) {
		  MatrixModel model = new MatrixModel(16, 16); //TODO the actual model
		  model.setBPM((short) 55); 
		  outerloop:
		    for(int j = 0; j < 256; j++){
		    	for(int i = 0; i < 256; i++){
			    	System.out.println("scanning " + ip + j + "." + i);
			        try {
			        	Socket socket = new Socket();
			            socket.connect(new InetSocketAddress(ip + j + "." + i, PORT), 500);
			            OutputStream out = (OutputStream) socket.getOutputStream();
			            ObjectOutputStream serializer = new ObjectOutputStream(out);
			            serializer.writeObject(model);
			            serializer.close();
			            out.close(); //was probably closed automatically when serializer was closed anyway
			            socket.close();
				     	break outerloop;
			        } catch (Exception e){
			        	if (i == 7) {
			        		e.printStackTrace();
			        	}
			        	System.out.println("nope");
			        }
			    }
		    }
		}
}