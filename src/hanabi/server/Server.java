package hanabi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static final int PORT = 1024;
	
	public static void main (String[] args) {
		
		Socket socket = null;
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server Error");
		}
		
		while(true) {
			
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Connection failed");
				}
				System.out.println("Connection established");
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
			
			
		}
		
}
}

class ServerThread extends Thread{
	String input = null;
	BufferedReader inputStream = null;
	PrintWriter outputStream = null;
	Socket socket = null;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new PrintWriter(socket.getOutputStream());
	}
	
}