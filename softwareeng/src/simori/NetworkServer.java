package simori;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class NetworkServer {
	 public final static int PORT = 20160;

	  public void main( String[] argv )
	    throws IOException {
	    ServerSocket ss = new ServerSocket( PORT );

	    while ( true ) {
	      Socket s = ss.accept();
	      Thread t = new Thread( new Worker( s ) );
	      t.start();
	    }
	  }
	  
	  private class Worker implements Runnable {
		  private Socket s;

		  Worker( Socket s ) {
		    this.s = s;
		  }

		  public void run() {
		    try {
		      InputStream  in   =  s.getInputStream();
		      OutputStream out  =  s.getOutputStream();

		      BufferedReader reader =
			new BufferedReader( new InputStreamReader( in ) ); 
		      PrintWriter    writer =
		        new PrintWriter( out );

		      String content = reader.readLine() + "\n";

		      writer.print( "HTTP/1.1 200 OK\r\n" );
		      writer.print( "Content-Type: text/plain\r\n" );
		      writer.print( "Content-Length: " + content.length() + "\r\n" );
		      writer.print( "\r\n" );
		      writer.print( content );
		      writer.flush();
		      s.close();
		    } catch ( IOException e ) {
		      System.out.println( e );
		    }
		  }
		}
}
