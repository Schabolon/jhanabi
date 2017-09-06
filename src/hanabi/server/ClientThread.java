package hanabi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import hanabi.Player;
import hanabi.message.IMessage;

public class ClientThread extends Thread {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private IMessage input;
	private GameServer game;
	private Player player;
	boolean socketIsClosed = false;

	public ClientThread(GameServer game, Socket socket, int n) {
		super("ClientThread " + n);
		this.game = game;
		this.socket = socket;
		player = new Player(n);
	}

	@Override
	public void run() {
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
			game.addPlayer(player, this);
			while (!socketIsClosed) {
				input = (IMessage) inputStream.readObject();
				game.readMessage(this, input);
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	public Player getPlayer() {
		return player;
	}

	public void closeSocket() {
		socketIsClosed = true;
	}
}