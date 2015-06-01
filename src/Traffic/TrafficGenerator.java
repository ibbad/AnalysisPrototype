package Traffic;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 
 * @author Ibbad Hafeez
 * This class generates network traffic with random IP addresses and Port Numbers 
 */

public class TrafficGenerator {
	private Random random=new Random();
	private String macAddress;
	private String ipAddress;
	private int port;
	private int vlanID;
	
	
	// Constructor
	public TrafficGenerator (){
		this.ipAddress=ipGenerator();
		this.macAddress=macGenerator();
		this.port=portGenerator();
		this.vlanID=vlanGenerator();
	}
	
	//Copy Constructor
	public TrafficGenerator(TrafficGenerator argument){
		this.ipAddress=argument.getIPAddress();
		this.macAddress=argument.getMacAddress();
		this.port=argument.getPort();
		this.vlanID=argument.getVlanID();
	}
	
	
	/**
	 * Generate Random IP Address
	 * @return IP Address in String format
	 */
	
	private String ipGenerator (){
		//generate integer values
		int octet1= ((Math.abs(random.nextInt()))>>24)&0xff;
		int octet2= ((Math.abs(random.nextInt()))>>16)&0xff;
		int octet3= ((Math.abs(random.nextInt()))>>8)&0xff;
		int octet4= Math.abs(random.nextInt()%256);
		
		
		StringBuilder ip_addr= new StringBuilder();

		//convert to strings
		String octet_str1= Integer.toString(octet1)+".";
		String octet_str2= Integer.toString(octet2)+".";
		String octet_str3= Integer.toString(octet3)+".";
		String octet_str4= Integer.toString(octet4);
		
		ip_addr.append(octet_str1).append(octet_str2).append(octet_str3).append(octet_str4);
		
		return ip_addr.toString();
	}
	
	/**
	 * @param String: IP Address
	 * @return Integer array containing 4 integers
	 */
	private int[] decodeIPAddress(String ipAddr){
		int [] ip = new int [4];
		String [] ip_str=ipAddr.split("\\.");
		String [] ip_str1=ipAddr.split(Pattern.quote("."));
		for (int i=0;i<ip_str.length;i++){
			ip[i]=Integer.parseInt(ip_str1[i]);
		}
		return ip;		
	}
	
	/**
	 * @return Integer port number : 1-65535
	 */
	
	private int portGenerator(){
		return ((Math.abs(random.nextInt()))%65535)+1;
	}
	
	/**
	 * @return Vlan ID : 1-16
	 */
	
	private int vlanGenerator(){
		return ((Math.abs(random.nextInt()))%16)+1;
	}
	
	
	/**
	 * 
	 * @return String: MAC Address
	 */
	
	private String macGenerator(){
		//6 bytes long mac address
		byte[] macAddress= new byte[6];
		//generate MAC address
		random.nextBytes(macAddress);
		
		StringBuilder mac= new StringBuilder();
		
		for (byte b: macAddress){
			if (mac.length()>0){
				//append the bytes
				mac.append(":");
			} 
			//Append the byte
			mac.append(String.format("%02x", b));

		}
		return mac.toString();
	}

	
	@Override
	public String toString(){
		String NEW_LINE=System.getProperty("line.separator");
		return "MAC: "+this.macAddress+NEW_LINE
				+"IP-Address: "+this.ipAddress+NEW_LINE
				+"Port: "+this.port+NEW_LINE
				+"Vlan ID: "+this.vlanID;
	}
		
	/** 
	 * Getters
	 */
	
	public String getIPAddress (){
		return this.ipAddress;
	}
	
	public String getMacAddress (){
		return this.macAddress;
	}
	
	public int getPort (){
		return this.port;
	}
	
	public int getVlanID (){
		return this.vlanID;
	}
	
	/**
	 * Main Function for testing
	 * @param args
	 */
	public static void main (String[] args){
		TrafficGenerator packet= new TrafficGenerator();
		System.out.println(packet.toString());
		TrafficGenerator packet1=new TrafficGenerator(packet);
		System.out.println(packet1.toString());
		TrafficGenerator packet2=packet1;
		System.out.println(packet2.toString());
	}
	//End of class
}
