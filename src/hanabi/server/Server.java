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
				ServerThread serverThread = new ServerThread(game, socket, n++);
				serverThread.start();
				
			
		}
		
}
}

class ServerThread extends Thread{
	Socket socket = null;
	IMessage input = null;
	GameServer game;
	Player player;
	
	
	
	public ServerThread(GameServer game, Socket socket, int n) {
		super("serverThread " + n);
		this.game = game;
		this.socket = socket;
		player = new Player(n+1);
	}
	
	public void run() {
		try {
			
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			game.addPlayer(player, socket);
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
	
}