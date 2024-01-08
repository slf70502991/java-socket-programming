package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/**
 * The server sending message to the client
 * 
 * @author YENLING LIN
 * */
public class Server{
	
	private ServerSocket serverSocket;
	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	Scanner scanner = new Scanner(System.in);
	
	private int serverPort;
	
	//Default constructor
	public Server(){
		this.serverPort = 1254;
	};
	//User defined port
	public Server(int port) {
		this.serverPort = port;
	}
	
	//The method to deal with requests from clients
	public void runServer() throws IOException {
		//Listen the port
			try {
				serverSocket = new ServerSocket(serverPort);
				System.out.println("The server is listenng..."+ serverPort);
					waitForConnection();
					getStreams();
					processConnection();
				
			} catch (EOFException e) {
				e.printStackTrace();
				System.out.println(e);
			
			} finally {
				closeConnection();
			}
	}

	//Wait for connection from the client
	private void waitForConnection() {
		try {
			//Accept the connection
			connection = serverSocket.accept();
			System.out.println("Connection starts.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		} 
	}
	
	//Get streams to send and receive data
	private void getStreams() throws IOException {
		input = new DataInputStream(connection.getInputStream());
		output = new DataOutputStream(connection.getOutputStream());
		System.out.println("Server's Data Steams are created.");
	}
	//Process data
	private void processConnection() throws IOException {
		String finish = "finish";
		Boolean b = true;
		
			while(b) {
				//Enter the message for client
				System.out.println("\nEnter the message for client...");
				String stringServer = scanner.next();
				if(!stringServer.equalsIgnoreCase(finish)) {
					
					output.writeUTF(stringServer);
					System.out.println(stringServer);
				}else {
					b = false;
					break;
				}
			}
		
			System.out.println("End");
	}
	
	//Close connection
	private void closeConnection() throws IOException {
		System.out.println("Connection closed");
		input.close();
		output.close();
		connection.close();
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.runServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}