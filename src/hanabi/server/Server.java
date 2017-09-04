package hanabi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static final int PORT = 1024;
	static int n = 0;
	
	public static void main (String[] args) {
		
		Socket socket = null;
		ServerSocket serverSocket = null;
		boolean listeningForClients = true;
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server Error");
		}
		
		while(listeningForClients) {
			
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Connection failed");
				}
				System.out.println("Connection established");
				ServerThread serverThread = new ServerThread(socket, n++);
				serverThread.start();
			
			
		}
		
}
}

class ServerThread extends Thread{
	Socket socket = null;
	String input = null;

	
	
	
	public ServerThread(Socket socket, int n) {
		super("serverThread " + n);
		this.socket = socket;
	}
	
	public void run() {
		try {
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
			
			while(true) {
				
					input = inputStream.readLine();
					if(input.equalsIgnoreCase("QUIT")) {
						socket.close();
					}
					else {
						// do something with input
						// give something to outputStream 
					}
				}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
}