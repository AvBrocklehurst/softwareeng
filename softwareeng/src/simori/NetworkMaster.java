package simori;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Adam
 * @author Matt
 */
public class NetworkMaster {
	private int port;
	private String ip;
	private MatrixModel model;
	
	public NetworkMaster(int port, MatrixModel model) throws UnknownHostException{
		this.port = port;
		this.ip = getIP();
		this.model = model;
	}
	
	public void findSlave(){
		System.out.println("here");
		String cloesestRangeIP =  ip.substring(0, ip.indexOf('.',
				ip.indexOf('.',ip.indexOf('.')+1)+1) + 1);
		String thisRangeIP = cloesestRangeIP.substring
				(0, cloesestRangeIP.indexOf('.',cloesestRangeIP.indexOf('.')+1) + 1);
		boolean found = closestRangeIP(cloesestRangeIP);
		if(!found){
			iterateOverIPRange(thisRangeIP);
		}
	}
	
	private boolean closestRangeIP(String ip){
		
		for(int i = 0; i < 256; i++){
	        try {
	        	if(!this.ip.equals(ip + i)){
	        		checkSocket(ip + i);
	        	    return true;
	        	}

	        } catch (IOException e){
	        	
	        }
	    }
		return false;
	}
	
	private void checkSocket(String ip) throws IOException{
		Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 200);
        OutputStream out = (OutputStream) socket.getOutputStream();
        ObjectOutputStream serializer = new ObjectOutputStream(out);
        serializer.writeObject(model);
        serializer.close();
        out.close();
        socket.close();
	}
	
	private static String getIP() throws UnknownHostException{
		return Inet4Address.getLocalHost().getHostAddress();
	}
	  
	private void iterateOverIPRange(String ip) {
		for(int j = 0; j < 256; j++){
			closestRangeIP(ip + j + '.');
		} 
	}
}