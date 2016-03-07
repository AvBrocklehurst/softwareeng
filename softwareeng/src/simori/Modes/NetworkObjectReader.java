package simori.Modes;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;

import simori.MatrixModel;


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
