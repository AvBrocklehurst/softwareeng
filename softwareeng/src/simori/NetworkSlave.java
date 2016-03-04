package simori;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import simori.Simori.PowerTogglable;


public class NetworkSlave implements Runnable, PowerTogglable{
	 private int port = 20160;
	 private ServerSocket serverSocket;
	 
	 public NetworkSlave(int port){
		 this.port = port;
	 }

	@Override
	public void run() {
		try{
			serverSocket = new ServerSocket(port);
			while(true && !Thread.currentThread().isInterrupted()){
				Socket s = serverSocket.accept();
				Thread t = new Thread( new NetworkObjectReader(s) );
				t.start();
			}
			serverSocket.close();
		} catch(IOException e){
			System.out.println("Socket Error");
		}
	}

	@Override
	public void switchOn() {
		this.run();
	}

	@Override
	public void switchOff() {
		this.notify();	
	}
	
}
