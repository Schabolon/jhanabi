package hanabi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import hanabi.IMessage;
import hanabi.Player;

public class Server {
	static final int PORT = 1024;
	static int n = 0;
	
	public static void main (String[] args) {
		
		Socket socket = null;
		ServerSocket serverSocket = null;
		boolean listeningForClients = true;
		GameServer game = new GameServer();
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started");
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
				ClientThread clientThread = new ClientThread(game, socket, n++);
				clientThread.start();
				
			
		}
		
}
}

class ClientThread extends Thread{
	Socket socket = null;
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;
	IMessage input = null;
	GameServer game;
	Player player;
	
	
	
	public ClientThread(GameServer game, Socket socket, int n) {
		super("ClientThread " + n);
		this.game = game;
		this.socket = socket;
		player = new Player(n+1);
	}
	
	public void run() {
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			game.addPlayer(player, this);
			while(true) {
				
					input = (IMessage)inputStream.readObject();
					game.readMessage(input);
						
					
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
}