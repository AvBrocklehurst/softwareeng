package simori;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable {
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