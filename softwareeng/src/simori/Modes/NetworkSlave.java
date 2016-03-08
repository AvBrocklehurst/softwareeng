package simori.Modes;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import simori.MatrixModel;
import simori.Simori;
import simori.Simori.PowerTogglable;

/**
 * Class that listens for incoming connections on a given port.
 * The network slave runs in the background whenever the simori-on is
 * turned on. When it accepts an incoming connection, it saves the
 * received object as the new model for this simori-on.
 * @author Adam
 * @version 2.0.0
 * 
 *
 */
public class NetworkSlave implements Runnable, PowerTogglable{
	 private int port; //The port to listen on.
	 private ServerSocket serverSocket;
	 private MatrixModel model;
	 
	 
	 /**
	  * Constuctor for the NewworkSlave class.
	  * @author Adam
	  * @param port   The port to listen on.
	  * @param model  Reference to the model to change.
	  */
	 public NetworkSlave(int port, MatrixModel model){
		 this.port = port;
		 this.model = model;
	 }

	@Override
	/**
	 * The run method continues listening to incoming connections
	 * on the classes port and spawning them new sockets and threads
	 * as they're needed.
	 * @author Adam
	 */
	public void run() {
		try{
			System.out.println("Socket Going");
			serverSocket = new ServerSocket(port); //open the server socket.
			while(true && !Thread.currentThread().isInterrupted()){
				System.out.println("Socket Going");
				Socket s = serverSocket.accept(); //accept connection and spawn new socket.
				System.out.println("Socket Accepted");
				Thread t = new Thread( new NetworkObjectReader(s, model) );
				t.start();
			}
			serverSocket.close();
		} catch(IOException e){
			
		}
	}

	@Override
	public void switchOn() {
		new Thread(this).start();
		System.out.println("Socket Open");
	}

	@Override
	public void switchOff() {
		try {
			if (serverSocket != null) serverSocket.close();
			System.out.println("Socket Closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
	}
	

	/**
	 * Class to handle the reading in of an object 
	 * that is passed via a network socket.
	 * @author Adam
	 *
	 */
	public class NetworkObjectReader implements Runnable {
	  private Socket s;
	  private MatrixModel model;
	  
	  
	  /**
	   * Constructor for the NewtworkObjectReader
	   * @param s      The accpeting socket.
	   * @param model  The model to override.
	   * @author Adam
	   */
	  public NetworkObjectReader( Socket s, MatrixModel model ) {
	    this.s = s;
	    this.model = model;
	  }
	
	  /**
	   * The run method reads in the object from the input stream
	   * and converts it into the MarixModel
	   * @author Adam
	   */
	  @Override 
	  public void run() {
		InputStream in = null;
		ObjectInputStream out = null;
		try {
			in = s.getInputStream();
			out = new ObjectInputStream(in);
		
			try {
				model.convertModel((MatrixModel) out.readObject());
				
			} catch (ClassNotFoundException e1) {
				System.err.println("Class sent wasn't a MatrixModel");
			}
	
			out.close();
			in.close();
			s.close();
		} catch (IOException e) {
			System.err.println("Input / output stream can't be found");
		}
		
	  }
	
	}
	
}
