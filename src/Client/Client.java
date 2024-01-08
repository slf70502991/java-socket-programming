package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Date;

/**
 * The client to receive the message from the server
 * 
 * @author YENLING LIN
 * */
public class Client{
	private int serverPort;
	String hostServer;
	Socket connection;
	DataOutputStream output = null;
	DataInputStream input = null;
	Scanner scanner = new Scanner(System.in);
	
	Client(){
		this.serverPort = 1254;
		this.hostServer = "localhost";
	}
	Client(int serverPort, String hostServer){
		this.serverPort = serverPort;
		this.hostServer = hostServer;
	}
	//The method for client to communicate with server
	public void runClient() throws IOException {
//		System.out.println("Press any key to start connection.");
			try {
				connectToServer();
				getStreams();
				processConnection();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeConnection();
			}
		
	}
	//Start to connect with the server
	private void connectToServer() throws IOException {
		try {
			connection = new Socket(this.hostServer, this.serverPort);
			System.out.println("Try to connecting server...");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//THe data streams back and forth between server and client
	private void getStreams() throws IOException {
		input = new DataInputStream(connection.getInputStream());
		output = new DataOutputStream(connection.getOutputStream());
		System.out.println("Client's Data Steams are created.");
	}
	
	//Receive message
	private void processConnection() throws IOException {
		String finish = "finish";
		
		do {
			String receivedString = input.readUTF();
			System.out.println("From server: " + receivedString);
			
		}while(!input.readUTF().equalsIgnoreCase(finish));
		System.out.println("Client end");
	}
	
	//Close connection
	private void closeConnection() throws IOException {
		System.out.println("Connection closed.");
		input.close();
		output.close();
		connection.close();
	}
	
	//
	public static void main(String[] args) {
		Client client = new Client();
		
		try {
			client.runClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
