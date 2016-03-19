package simori.Modes;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import simori.MatrixModel;
import simori.ModeController;
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
	 private ModeController controller;
	 
	 
	 /**
	  * Constuctor for the NewworkSlave class.
	  * @author Adam
	  * @param port   The port to listen on.
	  * @param controller  ModeController with reference to GUI and model
	  */
	 public NetworkSlave(int port, ModeController controller){
		 this.port = port;
		 this.controller = controller;
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
			
				Thread t = new Thread( new NetworkObjectReader(s, controller) );
				t.start();
			}
			serverSocket.close();
		} catch(IOException e){
			
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void ready() {}
	
	/** {@inheritDoc} */
	@Override
	public void switchOn() {
		new Thread(this).start();
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop() {
		try {
			if (serverSocket != null) serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
	}

	/** {@inheritDoc} */
	@Override
	public void switchOff() {}
	

	/**
	 * Class to handle the reading in of an object 
	 * that is passed via a network socket.
	 * @author Adam
	 *
	 */
	public class NetworkObjectReader implements Runnable {
	  private Socket s;
	  private ModeController controller;
	  
	  
	  /**
	   * Constructor for the NewtworkObjectReader
	   * @param s      The accpeting socket.
	   * @param model  The model to override.
	   * @author Adam
	   */
	  public NetworkObjectReader( Socket s, ModeController controller ) {
	    this.s = s;
	    this.controller = controller;
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
				MatrixModel received = (MatrixModel) out.readObject();
				MatrixModel ours = controller.getModel();
				ours.convertModel(received);
				int num = ours.getInstrument(controller.getDisplayLayer());
				controller.showInstrumentName(num);
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
