package UserEdge;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Traffic.TrafficGenerator;

/**
 * 
 * @author ibbad hafeez
 * 
 * This is the Server that sits on User end and behaves as a User.
 * Any incoming message are simply displayed on Console. It receiver messages from the core module
 * 
 */


public class UserServer {
	
	/**
	 * Listen to incoming messages
	 * @param: PortNumber for listening messages
	 * @throws IOException 
	 * 
	 */
	
	
	public void listenIncomingMessages(int portNumber) {
		ServerSocket incomingSocket=null;
		try {
			incomingSocket= new ServerSocket (portNumber);
			System.out.println("Listening on "+incomingSocket.getLocalSocketAddress());
			while (true){
				//Open the socket
				Socket incomings=incomingSocket.accept();
				//Get input stream
				InputStream input=incomings.getInputStream();
				//Tie object stream to input stream
				ObjectInputStream objectInput= new ObjectInputStream(input);
				//catch the incoming object
				TrafficGenerator incomingPacket = (TrafficGenerator) objectInput.readObject();
				
				if (incomingPacket!=null){
					this.displayMessage(incomingPacket);
				} else {
					System.err.println("Corrupt Incoming Packet");
				}
			}
		}catch (Exception e){
			System.err.println("There has been some issue in User end");
			//Make sure the the socket is closed
			if (incomingSocket!=null){
				try{
					incomingSocket.close();
				} catch (Exception e1){
					System.err.println("There has been some issue when closing server socket");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Display the incoming packet
	 * @param TrafficGenerator:packet
	 */
	private void displayMessage(TrafficGenerator packet){
		System.out.println("Message Received from: "+packet.getIPAddress()
				+ " on Port Number: "+ packet.getPort()
				+ " with MAC:Address: "+packet.getMacAddress()
				+ " and VLAN ID: "+packet.getVlanID()
				);
	}
	
	
	public static void main (String[] args){
		UserServer user=new UserServer();
		user.listenIncomingMessages(2002);
	}
}
