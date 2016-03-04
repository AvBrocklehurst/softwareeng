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
		  ServerSocket serverSocket = new ServerSocket(PORT);
		  while(true){
			  System.out.println("opening socket");
	  	      Socket s = serverSocket.accept();
			  Thread t = new Thread( new Worker(serverSocket, s) );
			  t.start();
		  }
	 }
}
