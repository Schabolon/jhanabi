package hanabi.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.MessageType;
import hanabi.Player;

public class GameServer implements IServer {
	private List<Player> players;
	private Map<Player, ClientThread> playersByClientThread;
	private Map<Player, Boolean> playersReady;

	public GameServer() {
		playersByClientThread = new HashMap<>();
		playersReady = new HashMap<>();
	}

	@Override
	public void addPlayer(Player player, ClientThread thread) {
		players.add(player);
		playersByClientThread.put(player, thread);
		playersReady.put(player, false);
	}

	@Override
	public void sendMessage(ClientThread reciever, IMessage msg) {
		try {
			reciever.getOutputStream().writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readMessage(ClientThread source, IMessage msg) {
		handleMessage(source, msg);

	}

	private void handleMessage(ClientThread source, IMessage msg) {
		Message.MessageType messageType = msg.getMessageType();
		switch (messageType) {
		case START:
			System.out.println("Server recieved START message from " + source.getName());
			startGame(source, (Message) msg);
			break;
		case NEWCARD:
			System.out.println("Server recieved NEWCARD message from " + source.getName());
			break;
		case QUIT:
			System.out.println("Server recieved QUIT message from " + source.getName());
			break;
		case STATUS:
			System.out.println("Server recieved STATUS message from " + source.getName());
			break;
		case TURNACTION:
			System.out.println("Server recieved TURNACTION message from " + source.getName());
			break;
		case TURNEND:
			System.out.println("Server recieved TURNEND message from " + source.getName());
			break;
		case TURNSTART:
			System.out.println("Server recieved TURNSTART message from " + source.getName());
			break;
		default:
			break;

		}
	}

	private void startGame(ClientThread source, Message msg) {
		Player sender = source.getPlayer();
		if (!playersReady.containsKey(sender)) {
			playersReady.put(sender, true);
		}
		if (allPlayersReady()) {
			sendAll(new Message(MessageType.START));
		}
	}

	private void sendAll(Message msg) {
		for (ClientThread reciever : playersByClientThread.values()) {
			try {
				reciever.getOutputStream().writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean allPlayersReady() {
		if (players.size() < 2) {
			return false;
		}
		for (boolean ready : playersReady.values()) {
			if (ready == false) {
				return false;
			}
		}
		return true;
	}
}
