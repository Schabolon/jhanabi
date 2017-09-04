package hanabi.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanabi.GameStats;
import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.MessageType;
import hanabi.Player;

public class GameServer implements IServer {
	private List<Player> players;
	private Map<Player, ClientThread> playersByClientThread;
	private Map<Player, Boolean> playersReady;
	private GameStats gameStats;

	public GameServer() {
		players = new ArrayList<>();
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

	/**
	 * Handles the different message types that the server recieves from the clients
	 */
	private void handleMessage(ClientThread source, IMessage msg) {
		Message.MessageType messageType = msg.getMessageType();
		switch (messageType) {
		case START:
			System.out.println("Server recieved START message from " + source.getName());
			startGameHandler(source, (Message) msg);
			break;
		case NEWCARD:
			System.out.println("Server recieved NEWCARD message from " + source.getName());
			break;
		case QUIT:
			System.out.println("Server recieved QUIT message from " + source.getName());
			quitGameHandler(source, (Message) msg);
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

	/**
	 * sends a message to all clients in the game
	 */
	private void sendAll(Message msg) {
		for (ClientThread reciever : playersByClientThread.values()) {
			sendMessage(reciever, msg);
		}
	}

	/**
	 * checks when the game contains more then two players, if everyone already sent
	 * a
	 * 
	 * @return
	 */
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

	/**
	 * removes a certain player from the game
	 */
	private void removePlayer(Player player) {
		players.remove(player);
		playersByClientThread.remove(player);
		playersReady.remove(player);
	}

	/**
	 * Handler for messages of type START
	 */
	private void startGameHandler(ClientThread source, Message msg) {
		Player sender = source.getPlayer();
		if (!playersReady.containsKey(sender)) {
			playersReady.put(sender, true);
		}
		if (allPlayersReady()) {
			sendAll(new Message(MessageType.START));
			gameStats = new GameStats();
			gameStats.handOutCardsAtGamestart(players);
			Message turnStarMessage = new Message(MessageType.TURNSTART);
			sendMessage(playersByClientThread.get(players.get(0)), turnStarMessage);
		}
	}

	/**
	 * Handler for messages of type QUIT
	 */
	private void quitGameHandler(ClientThread source, Message msg) {
		Player quittingPlayer = source.getPlayer();
		sendMessage(source, new Message(MessageType.QUIT, quittingPlayer));
		removePlayer(quittingPlayer);
		source.closeSocket();
		for (Player remainingPlayer : players) {
			sendMessage(playersByClientThread.get(remainingPlayer), new Message(MessageType.QUIT, quittingPlayer));
		}

	}
}
