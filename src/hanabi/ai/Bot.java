package hanabi.ai;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import hanabi.Card;
import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.ColorType;
import hanabi.Message.MessageType;
import hanabi.Message.PlayerActions;
import hanabi.Player;
import hanabi.client.IClient;

public class Bot implements IClient {

	private String hostName;
	private int port;
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private BotServerListener botServerListener;
	private List<String> playerNames;

	public Bot(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	public static void main(String[] args) {
		Bot bot = new Bot("localhost", 1024);
		bot.connect();
		bot.listenToServer();
		bot.sendMessage(new Message(MessageType.START));
	}

	@Override
	public void sendMessage(IMessage msg) {
		try {
			objectOutputStream.writeObject(msg);
			objectOutputStream.flush();
		} catch (IOException e) {
			System.out.println("Couldn't write Message to ObjectOutputStream");
			e.printStackTrace();
		}
	}

	@Override
	public void readMessage(IMessage msg) {
		MessageType messageType = msg.getMessageType();
		if (messageType.equals(MessageType.TURNSTART)) {
			performRandomAction();
		} else if (messageType.equals(MessageType.STATUS_PLAYER_NAMES)) {
			playerNames = msg.getPlayerNames();
		}
	}

	private void performRandomAction() {
		Random rand = new Random();
		int firstAction = rand.nextInt(2);
		if (firstAction == 0) {
			playRandomCard(rand.nextInt(4));
		} else if (firstAction == 1) {
			discardRandomCard(rand.nextInt(4));
		} else if (firstAction == 2) {
			giveRandomHint(rand.nextInt(1));
		}
	}

	private void playRandomCard(int randomInt) {
		sendMessage(new Message(MessageType.TURNACTION, new Card(randomInt), PlayerActions.PLAY_CARD));
	}

	private void giveRandomHint(int randomInt) {
		if (randomInt == 0) {
			giveColorHint();
		} else if (randomInt == 1) {
			giveNumberValueHint();
		}
	}

	private void giveColorHint() {
		ColorType[] colorTypes = ColorType.values();
		int maxBound = ColorType.values().length - 1;
		Random rand = new Random();
		sendMessage(new Message(MessageType.TURNACTION, new Player(getRandomPlayer()), PlayerActions.GIVE_COLOR_HINT,
				colorTypes[rand.nextInt(maxBound)]));
	}

	private void giveNumberValueHint() {
		Random rand = new Random();
		sendMessage(new Message(MessageType.TURNACTION, new Player(getRandomPlayer()), PlayerActions.GIVE_NUMBER_HINT,
				rand.nextInt(4)));
	}

	private int getRandomPlayer() {
		Random rand = new Random();
		return rand.nextInt(playerNames.size());
	}

	private void discardRandomCard(int randomInt) {
		sendMessage(new Message(MessageType.TURNACTION, new Card(randomInt), PlayerActions.DISCARD));
	}

	@Override
	public boolean connect() {
		System.out.println("Trying to connect ...");
		Socket socket = null;
		try {
			socket = new Socket(hostName, port);
			this.socket = socket;
			objectOutputStream = getObjectOutputStream();
			System.out.println("Connection established");
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host: " + hostName);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private ObjectOutputStream getObjectOutputStream() {
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Couldn't get ObjectOutputStream");
			e.printStackTrace();
		}
		return objectOutputStream;
	}

	@Override
	public void listenToServer() {
		botServerListener = new BotServerListener(this);
		botServerListener.start();
	}

	@Override
	public boolean disconnect() {
		System.out.println("Disconnecting ...");
		botServerListener.terminate();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't disconnect");
			return false;
		}
		System.out.println("Disconnected");
		return true;
	}

	@Override
	public Socket getSocket() {
		return socket;
	}

}
