package simori.Modes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import simori.ModeController;

/**
 * Class to act as the master in the master slave mode.
 * It contains functions to search through an IP range
 * and set up a socket to transfer the model over.
 * @version 1.4.0
 * @author Adam
 * @author Matt
 */
public class NetworkMaster implements Runnable {
	
	private static final String OS_NAME =
			System.getProperty("os.name").toLowerCase();
	
	private int port;
	private String ip;
	private ModeController controller;
	private NetworkSlave slave;
	private boolean running;
	private ScanProgressListener listener;
	
	/**
	 * Constructor for the Network Master Class.
	 * @author Adam
	 * @param port   The port to search on.
	 * @param model  The model to export.
	 * @throws IOException 
	 */
	public NetworkMaster(int port, ModeController controller, NetworkSlave slave)
			throws IOException {
		this.port = port;
		this.controller = controller;
		this.slave = slave;
	}
	
	/**
	 * Find slave generates the IP ranges to search in for another Simori-ON.
	 * @author adam
	 */
	public void findSlave(){
		if(!running){
			running = true;
			/* generate y range (xxx.xxx.xxx.yyy) */
			String cloesestRangeIP =  ip.substring(0, ip.indexOf('.',
					ip.indexOf('.',ip.indexOf('.')+1)+1) + 1);
			/* generate y range (xxx.xxx.yyy.xxx) */
			String thisRangeIP = cloesestRangeIP.substring
					(0, cloesestRangeIP.indexOf('.',cloesestRangeIP.indexOf('.')+1) + 1);
			/* First check the same end IP range */
			boolean found = closestRangeIP(cloesestRangeIP);
			if(!found){
				iterateOverIPRange(thisRangeIP); //FIXME does this mean it repeats the range it just checked?
			}
			slave.switchOn();
		}
	}
	
	
	/**
	 * Method to check IPs in the last section of the address.
	 * @author Adam
	 * @param ip  The IP range to iterate over
	 * @return  boolean, true if an exception IP was found
	 */
	private boolean closestRangeIP(String ip) {
		if (listener != null)
			listener.onRangeChanged(ip.substring(0, ip.length() - 1));
		
		for(int i = 1; i < 256; i++){
			if(running) {
				if (listener != null) listener.onIpScan(i);
		        try {
		        	/* If it's not my IP */
		        	checkSocket(ip + i);
		        	return true;
		        } catch (IOException	 e){
		        	
		        }
			} else {
				break;
			}
	    }
		return false;
	}
	
	
	/**
	 * Method to attempt a socket connection on a given IP.
	 * @author Adam
	 * @author Matt
	 * @param ip  The IP to attempt a connection with.
	 * @throws IOException
	 */
	private void checkSocket(String ip) throws IOException{
		Socket socket = new Socket();
		
		/* attempt socket connection with 100ms timeout */
        socket.connect(new InetSocketAddress(ip, port), 200);
        if (listener != null) listener.onCompletion(true);
       
        OutputStream out = (OutputStream) socket.getOutputStream();
        ObjectOutputStream serializer = new ObjectOutputStream(out);
       
        /* Serialise and write the model to the output stream */
        serializer.writeObject(controller.getModel());
        serializer.close();
        out.close();
        socket.close();
	}
	
	/**
	 * Method to return the systems local IP address.
	 * @author Adam
	 * @return  A string containing the current systems local IP. 
	 * @throws UnknownHostException 
	 * @throws IOException 
	 */
	private String getIP() throws UnknownHostException, IOException {
		Socket s;
		String betterIP;
		try {
			/* Check if local address is on the 192.168.0 range */
			s = new Socket();
			s.connect(new InetSocketAddress("192.168.0.1", 80), 500);
			betterIP = s.getLocalAddress().getHostAddress();
			s.close();
		} catch (IOException e) {
			if (listener != null) listener.onLocalIpCheck();			
			/* Else try trace route for IP */
			if((betterIP = routeIP()).equals("0.0.0.0")){
				/* Else set address to unreliable local address */
				betterIP  = Inet4Address.getLocalHost().getHostAddress();
			}
		}
		return betterIP;
	}
	
	/**
	 * Method to exec traceroute and pass what it returns
	 * to the IPv4 function.
	 * @author Adam
	 * @return The IP address of the users router.
	 * @throws IOException
	 */
	private String routeIP() throws IOException {
		
		/* choose command based on windows or unix */
		String trace = (OS_NAME.contains("win") ? "tracert" : "traceroute");
		/* execute command */
		
		ProcessBuilder pb = new ProcessBuilder(trace, "www.google.com");
		Process p = pb.start();
		
        BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String content = output.readLine();
        String line;
        
        while((line = output.readLine()) != null){
        	content += line; //add each line content
        	
        }
        
        
        String gateway= ipv4(content);
        return gateway;
    }
	
	/**
	 * Method to find the 2nd IP address in a string.
	 * @author Adam.
	 * @param search  The string to search for an IP in.
	 * @return An IPv4 address.
	 */
	private static String ipv4(String search){
		/* Regex to match an IP Address */
		String IPADDRESS_PATTERN =  
				"(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
				"{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		
		/* Check input string against regex */
		Matcher matcher = pattern.matcher(search);
		if (matcher.find()) { //If one match
			if (matcher.find()) { //If two matches
				return matcher.group(); //return the 2nd IP.
			} 
		}
		return "0.0.0.0";
	}
	
	/**
	 * Method to iterate over an IP address.
	 * @author Adam
	 * @param ip  The first 2 sections of an IP to loop through.
	 */
	private void iterateOverIPRange(String ip) {
		boolean success = false;
		for(int j = 0; j < 256; j++){
			if(running){
				/* iterate through the end section. */
				success = closestRangeIP(ip + j + '.');
			} else {
				break;
			}
		}
		if (!success && listener != null) listener.onCompletion(false);
	}
	
	/**
	 * @author Adam
	 */
	public void stopRunning(){
		running = false;
	}
	
	/** Registers a listener for callbacks about the IP scan's progress */
	public void setIpScanListener(ScanProgressListener listener) {
		this.listener = listener;
		/* TODO If a tracert is in progress, call onLocalIpCheck
		        or if scanning a range, call onRangeChanged      */
	}

	@Override
	public void run() {
		try {
			slave.switchOff();
			this.ip = getIP();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		findSlave();
	}
	
	/**
	 * Callback interface for receiving updates on the
	 * progress of an IP-by-IP scan for a slave Simori-ON.
	 * @author Matt
	 * @version 1.1.0
	 */
	public interface ScanProgressListener {
		
		/** Called when determining the local IP is taking a long time */
		public void onLocalIpCheck();
		
		/**
		 * Called when the range of 256 IPs under consideration changes
		 * @param ipRange The most significant three segments of the IP range
		 */
		public void onRangeChanged(String ipRange);
		
		/**
		 * Called when a new IP is about to be considered
		 * @param lastOctet The least significant segment of the IP
		 */
		public void onIpScan(int lastOctet);
		
		/**
		 * Called at the completion of a scan (after success or timing out)
		 * @param success True if a listening slave Simori-ON was found
		 */
		public void onCompletion(boolean success);
	}
}