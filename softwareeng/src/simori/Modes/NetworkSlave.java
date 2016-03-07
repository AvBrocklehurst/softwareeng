package simori.Modes;
import java.io.IOException;
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
			serverSocket = new ServerSocket(port); //open the server socket.
			while(true && !Thread.currentThread().isInterrupted()){
				Socket s = serverSocket.accept(); //accept connection and spawn new socket.
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
	}

	@Override
	public void switchOff() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
	}
	
}
