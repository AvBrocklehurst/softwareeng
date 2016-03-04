package simori;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NetworkServer {
	 public final static int PORT = 20160;

	  public static void main( String[] argv ) throws IOException {
		  ServerSocket serverSocket = null;
		  System.out.println("Starting");
		  serverSocket = new ServerSocket(20160);
		  Socket socket = null;
		  InputStream in = null;
		  OutputStream out = null;
		  
		  try {
			  socket = serverSocket.accept();
		  } catch (IOException ex) {
		     System.out.println("Can't accept client connection. ");
		  }
		  try {
		      in = socket.getInputStream();
		  } catch (IOException ex) {
		       System.out.println("Can't get socket input stream. ");
		  }
		  try {
		      out = new FileOutputStream("C:\\Recovery2.txt");
		  } catch (FileNotFoundException ex) {
		      System.out.println("File not found. ");
		  }
		  byte[] bytes = new byte[16*1024];
		  int count;
		  while ((count = in.read(bytes)) > 0) {
		    	System.out.println("writitng");
		        out.write(bytes, 0, count);
		  }
		  
		  out.close();
		  in.close();
		  socket.close();
		  serverSocket.close();
	  }
}