package simori;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkObjectReader implements Runnable {
  private Socket s;


  public NetworkObjectReader( Socket s ) {
    this.s = s;

  }

  public void run() {
	InputStream in = null;
	ObjectInputStream out = null;
	try {
		in = s.getInputStream();
		out = new ObjectInputStream(in);
	
		try {
			MatrixModel temp = (MatrixModel) out.readObject();
			
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
