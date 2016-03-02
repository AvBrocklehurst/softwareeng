package simori;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.omg.CORBA.portable.OutputStream;

public class NetworkClient {
	final static String HOST  =  "127.0.0.1"; /* localhost */
	  final static int    PORT  =  20160;

	  public static void main( String[] argv ) 
	    throws IOException {

	    Socket s = new Socket( HOST, PORT );
	    
	    InputStream  in   =  s.getInputStream();
	    java.io.OutputStream out  =  s.getOutputStream();

	    BufferedReader reader =
	      new BufferedReader( new InputStreamReader( in ) );
	    PrintWriter writer =
	      new PrintWriter( out );

	    writer.print( "GET / HTTP/1.1\r\n" );
	    writer.print( "Host : " + HOST + "\r\n" );
	    writer.print( "Content-Length : 0\r\n" );
	    writer.print( "\r\n" );
	    writer.flush();

	    int c;
	    while ( ( c = reader.read() ) != -1 ) {
	      System.out.write( c );
	    }

	    s.close();
	  }
}
