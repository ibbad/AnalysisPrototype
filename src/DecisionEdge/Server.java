package DecisionEdge;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
	Random random=new Random();
	private int userPort;
	public Server(int portNumber) throws Exception{
		
		// TODO do something in constructor
		userPort=portNumber;
	}

	/**
	 * Server Listens and replies
	 * @param portNumber
	 * @throws Exception
	 */
	
	protected void listenAndReply () throws Exception{
		
		//Initialize the variables
		String clientRequest;
		String ServerReply;
		
		//Open the socket
		ServerSocket serverSocket=new ServerSocket(this.userPort);
		System.out.println("Server Listening on "+serverSocket.getInetAddress().toString()+":"+serverSocket.getLocalPort());
		//Continue listening
		while(true){
			//accept the connections
			Socket connectionSocket=serverSocket.accept();
			//Display that server is listening
			System.out.println("Connection from "+connectionSocket.getLocalAddress()+" on port: "+connectionSocket.getPort());
			//get the data from client
			BufferedReader msgFromClient= new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			//write data to client
			DataOutputStream msgToClient=new DataOutputStream(connectionSocket.getOutputStream());
			//read line from client line by line 
			clientRequest=msgFromClient.readLine();
			//show Client Message
			System.out.println(clientRequest);
			//make reply for client
			//ServerReply=clientRequest.toUpperCase()+'\n';
			ServerReply=generateReply(clientRequest);
			//send data to client
			msgToClient.writeBytes(ServerReply);			
		}
	}
	
	protected String generateReply(String incomingMsg){
		
		String incoming=incomingMsg;
		String reply;
		
		if (random.nextBoolean()){
			reply="Allow\n";
		} else {
			reply="Deny\n";
		}
		
		return reply;
	}
	
	//Server program to reply to client after analysis
	public  static void main (String[] args) throws Exception{
//		String clientRequest;
//		String ServerReply;
//		ServerSocket serverSocket=new ServerSocket(6789);
//		while(true){
//			Socket connectionSocket=serverSocket.accept();
//			System.out.println("Listening on localhost:6789");
//			BufferedReader msgFromClient= new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//			
//			DataOutputStream msgToClient=new DataOutputStream(connectionSocket.getOutputStream());
//			
//			clientRequest=msgFromClient.readLine();
//			ServerReply=clientRequest.toUpperCase()+'\n';
//			msgToClient.writeBytes(ServerReply);			
//		}
		//setup server
		Server server=new Server(6789);
		server.listenAndReply();
		
		
	}
}
