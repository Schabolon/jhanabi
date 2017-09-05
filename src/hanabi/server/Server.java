package hanabi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int PORT = 1024;
	private static int n = -1;

	public static void main(String[] args) {

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

		while (listeningForClients) {
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
