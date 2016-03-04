package simori;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
	  ObjectInputStream out = null;
	  

	  try {
	      in = socket.getInputStream();
	  } catch (IOException ex) {
	       System.out.println("Can't get socket input stream. ");
	  }
	  try {
		  out = new ObjectInputStream(in);
	      //out = new FileOutputStream("C:\\Users\\Adam\\Documents\\test.txt");
	  } catch (IOException ex) {
	      System.out.println("File not found. ");
	  }
	  System.out.println("get here");
	  byte[] bytes = new byte[16*1024];
	  int count;
	  try {
	//	while ((count = in.read(bytes)) > 0) {
		    	System.out.println("writitng");
		        MatrixModel temp = (MatrixModel) out.readObject();	
		        System.out.println(temp.getBPM());
	//	  }
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		System.out.println("here");
		e1.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  try {
		out.close();
		in.close();
		  socket.close();
	} catch (IOException e) {
		System.out.println("here");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
}