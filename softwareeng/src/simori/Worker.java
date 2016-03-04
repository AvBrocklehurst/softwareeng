package simori;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker implements Runnable {
  private Socket s;
  private ServerSocket ss;

  Worker(ServerSocket ss, Socket s ) {
    this.s = s;
    this.ss = ss;
  }

  public void run() {
	  System.out.println("Starting");
	  ServerSocket serverSocket = ss;
	  Socket socket = s;
	  InputStream in = null;
	  OutputStream out = null;
	  

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
	  try {
		while ((count = in.read(bytes)) > 0) {
		    	System.out.println("writitng");
		        out.write(bytes, 0, count);
		  }
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  
	  try {
		out.close();
		in.close();
		  socket.close();
		  serverSocket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
}