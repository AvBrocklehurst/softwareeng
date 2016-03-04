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
		    for(int j = 92; j < 256; j++){
		    	for(int i = 0; i < 256; i++){
			    	System.out.println("scanning " + ip + j + "." + i);
			        try {
			        	Socket socket = new Socket();
			            socket.connect(new InetSocketAddress(ip + j + "." + i, PORT), 50);
			            socket.close();
			            
			        	Socket s = new Socket( ip + j + "." + i, PORT );
			            
			        	 InputStream  in   =  s.getInputStream();
				     	    java.io.OutputStream out  =  s.getOutputStream();
		
				     	    BufferedReader reader =
				     	      new BufferedReader( new InputStreamReader( in ) );
				     	    PrintWriter writer =
				     	      new PrintWriter( out );
		
				     	    writer.print( "GET / HTTP/1.1\r\n" );
				     	    writer.print( "Host : " + getIP() + "\r\n" );
				     	    writer.print( "Content-Length : 0\r\n" );
				     	    writer.print( "\r\n" );
				     	    writer.flush();
		
				     	    int c;
				     	    while ( ( c = reader.read() ) != -1 ) {
				     	      System.out.write( c );
				     	    }
		
				     	    s.close(); 
				     	    break outerloop;
			        } catch (Exception e){
			        	System.out.println("nope");
			        }
			    }
		    }
		}
}
