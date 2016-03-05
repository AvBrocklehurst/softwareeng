package simori;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import simori.Simori.PowerTogglable;


public class NetworkSlave implements Runnable, PowerTogglable{
	 private int port = 20160;
	 private ServerSocket serverSocket;
	 private MatrixModel model;
	 
	 public NetworkSlave(int port, MatrixModel model){
		 this.port = port;
		 this.model = model;
	 }

	@Override
	public void run() {
		try{
			
			serverSocket = new ServerSocket(port);
			System.out.println("Server Socket Made");
			while(true && !Thread.currentThread().isInterrupted()){
				Socket s = serverSocket.accept();
				System.out.println("Socket Accepted");
				Thread t = new Thread( new NetworkObjectReader(s, model) );
				t.start();
			}
			serverSocket.close();
		} catch(IOException e){
			System.out.println("Socket Error");
		}
	}

	@Override
	public void switchOn() {
		System.out.println("Starting");
		new Thread(this).start();
	}

	@Override
	public void switchOff() {
		Thread.currentThread().interrupt();
	}
	
}
