package simori.Modes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import simori.MatrixModel;
import simori.ModeController;
import simori.Modes.ChangerMode.Changer;
import simori.Modes.ChangerMode.Setting;

/**
 * Class to act as the master in the master slave mode.
 * It contains functions to search through an ip range
 * and set up a socket to transfer the model over.
 * @version 1.1.0
 * @author Adam
 * @author Matt
 */
public class NetworkMaster implements Runnable{
	private int port;
	private String ip;
	private MatrixModel model;

	
	
	/**
	 * Constructor for the Network Master Class.
	 * @param port   The port to search on.
	 * @param model  The model to export.
	 * @throws UnknownHostException
	 */
	public NetworkMaster(int port, MatrixModel model) throws UnknownHostException{
		this.port = port;
		this.ip = getIP();
		this.model = model;
	}
	
	/**
	 * Find slave generates the ip ranges to search in for another simori on.
	 */
	public void findSlave(){
		/* generate y range (xxx.xxx.xxx.yyy) */
		String cloesestRangeIP =  ip.substring(0, ip.indexOf('.',
				ip.indexOf('.',ip.indexOf('.')+1)+1) + 1);
		/* generate y range (xxx.xxx.yyy.xxx) */
		String thisRangeIP = cloesestRangeIP.substring
				(0, cloesestRangeIP.indexOf('.',cloesestRangeIP.indexOf('.')+1) + 1);
		/* First check the same end ip range */
		boolean found = closestRangeIP(cloesestRangeIP);
		if(!found){
			iterateOverIPRange(thisRangeIP);
		}
	}
	
	
	/**
	 * Method to check ips in the last section of the address.
	 * @author Adam
	 * @param ip  The ip range to itterate over
	 * @return  boolean, true if an exception ip was found
	 */
	private boolean closestRangeIP(String ip){
		for(int i = 0; i < 256; i++){
	        try {
	        	/* If it's not my ip */
	        	if(!this.ip.equals(ip + i)){
	        		checkSocket(ip + i);
	        	    return true;
	        	}
	        } catch (IOException e){
	        	
	        }
	    }
		return false;
	}
	
	
	/**
	 * Method to attempt a socket connection on a given ip.
	 * @author Adam
	 * @author Matt
	 * @param ip  The IP to attempt a connection with.
	 * @throws IOException
	 */
	private void checkSocket(String ip) throws IOException{
		Socket socket = new Socket();
		/* attempt socket connection with 100ms timeout */
        socket.connect(new InetSocketAddress(ip, port), 100);
        OutputStream out = (OutputStream) socket.getOutputStream();
        ObjectOutputStream serializer = new ObjectOutputStream(out);
        /* Serialize and write the model to the output stream */
        serializer.writeObject(model);
        serializer.close();
        out.close();
        socket.close();
	}
	
	/**
	 * Method to return the systems local IP address.
	 * @author Adam
	 * @return  A string containing the current systems local IP.
	 * @throws UnknownHostException
	 */
	private static String getIP() throws UnknownHostException{
		return Inet4Address.getLocalHost().getHostAddress();
	}
	 
	
	/**
	 * Method to itterate over an ip address.
	 * @author Adam
	 * @param ip  The first 2 sections of an ip to loop through.
	 */
	private void iterateOverIPRange(String ip) {
		for(int j = 0; j < 256; j++){
			/* itterate through the end section. */
			closestRangeIP(ip + j + '.'); 
		} 
	}

	@Override
	public void run() {
		findSlave();
	}
	
	/**
	 * This implementation of the Changer interface allows the simori
	 * to probe on a given port to find other Simori-ons over a network.
	 * The first to respond receives the masters configuration and the master
	 * continues to performance mode.
	 * 
	 * @author Adam
	 * @version 1.0.0
	 * @see Changer.getText(), Changer.doThingTo(), Changer.getCurrentSetting()
	 * @return Changer
	 */
	protected static Changer masterSlave(final ModeController controller){
		return new Changer(){

			@Override
			public String getText(Setting s) {
				return "Searching...";
			}

			@Override
			public boolean doThingTo(ModeController controller) {
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				System.out.println("why am I running?");
				try {
					new Thread(new NetworkMaster(controller.getPort(), controller.getModel())).start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}
}