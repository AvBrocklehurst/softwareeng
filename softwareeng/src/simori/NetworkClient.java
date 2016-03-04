package simori;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.OutputStream;

public class NetworkClient {
	  final static int    PORT  =  20160;
	  
	  public static void main( String[] argv ) 
	    throws IOException {
		  
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
		  outerloop:
		    for(int j = 93; j < 256; j++){
		    	for(int i = 0; i < 256; i++){
			    	System.out.println("scanning " + ip + j + "." + i);
			        try {
			        	Socket socket = new Socket();
			            socket.connect(new InetSocketAddress(ip + j + "." + i, PORT), 50);
			            File file = new File("C:\\Recovery.txt");
			            // Get the size of the file
			            long length = file.length();
			            byte[] bytes = new byte[16 * 1024];
			            InputStream in = new FileInputStream(file);
			            OutputStream out = (OutputStream) socket.getOutputStream();
			            int count;
			            while ((count = in.read(bytes)) > 0) {
			            	out.write(bytes, 0, count);
			            }
			            
			            out.close();
			            in.close();
			            socket.close();
				     	break outerloop;
			        } catch (Exception e){
			        	System.out.println("nope");
			        }
			    }
		    }
		}
}
