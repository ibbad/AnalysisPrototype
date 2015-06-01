package Core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Traffic.TrafficGenerator;

/**
 *
 * @author Ibbad Hafeez: (ibbad.hafeez@helsinki.fi)
 * March 1st, 2015
 * This is controller module. When a message arrives;
 * 1. It checks local Policy Database, checks if packet is clear to pass to user or not
 * 2. If there is no decision in local Policy Database, it adds temporary rule in local Policy Database to allow and sends a copy to Decision Edge
 * 3. Decision Edge makes the decision and sends back the reply, controller module updates the local Policy Database appropriately.
 */
public class ControllerRole {

	protected void recieveTraffic(){
		
	}
	
	/**
	 * This function transfers incoming traffic to the client, after deciding whether it should be transferred to client or not
	 * @param: incoming Packet
	 */
	
	protected void sendToClient(TrafficGenerator packet){
		Socket sendingToUser=null;
		try {
			sendingToUser= new Socket ("localhost",2002);
			OutputStream outputToClient= sendingToUser.getOutputStream();
			ObjectOutputStream objectStream= new ObjectOutputStream(outputToClient);
			//send incoming Packet to Client
			objectStream.writeObject(packet);
			objectStream.close();
			outputToClient.close();
			sendingToUser.close();
		} catch (IOException e){
			System.err.println("Problem sending data from controller to client");
			e.printStackTrace();
			if (sendingToUser!=null){
				try {
					sendingToUser.close();
				} catch (Exception e1){
					System.err.println("Problem in closing socket sending to client");
					e1.printStackTrace();
				}
			}
		}
	}
}
