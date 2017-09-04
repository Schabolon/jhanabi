package hanabi.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.MessageType;
import hanabi.Player;

public class GameServer implements IServer{
	
	Map<Player, Socket> playersBySocket;
	
	public GameServer() {
		playersBySocket = new HashMap<>();
	}
	
	public void addPlayer(Player player, Socket socket) {
		playersBySocket.put(player, socket);
	}
	
	@Override
	public void sendMessage(IMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readMessage(IMessage msg) {
		handleMessage(msg);
		
	}

	private void handleMessage(IMessage msg) {
		Message.MessageType messageType = msg.getMessageType();
		switch(messageType) {
		case START:
			startGame();
			break;
		case NEWCARD:
			break;
		case QUIT:
			break;
		case STATUS:
			break;
		case TURNACTION:
			break;
		case TURNEND:
			break;
		case TURNSTART:
			break;
		default:
			break; 
		
		}
	}
	
	private void startGame() {
		for(Socket socket : playersBySocket.values()) {
			try {
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.writeObject(new Message(MessageType.START));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
