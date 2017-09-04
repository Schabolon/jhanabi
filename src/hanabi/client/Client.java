package hanabi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import hanabi.Card;
import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.ColorType;
import hanabi.Message.MessageType;
import hanabi.Message.PlayerActions;
import hanabi.Player;

public class Client implements IClient {

	private String hostName;
	private int port;

	private Socket socket;
	private ObjectOutputStream objectOutputStream;

	private ClientServerListener clientServerListener;

	public Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	public static void main(String[] args) {
		Client client = new Client("localhost", 1024);
		client.connect();
		client.listenToServer();
		client.sendMessage(new Message(MessageType.START));
		// client.sendMessage(new Message(MessageType.QUIT));
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
		System.out.println("Processing Message");
		MessageType messageType = msg.getMessageType();
		switch (messageType) {
		case START:
			System.out.println("The Game is starting.");
			break;
		case NEWCARD:
			break;
		case QUIT:
			System.out.println("Stopping the game");
			this.disconnect();
			break;
		case STATUS:
			System.out.println("Updating game information");
			break;
		case TURNACTION:
			System.out.println("Wrong MessageType");
			break;
		case TURNEND:
			System.out.println("It's the next players turn");
			break;
		case TURNSTART:
			startTurn();
			break;
		default:
			System.out.println("Unknown Message Type");
			break;
		}
	}

	private void startTurn() {
		System.out.println("Its your turn!");
		System.out.println("Please choose one of the following options:");
		System.out.println("A: Give a hint");
		System.out.println("B: Discard a card");
		System.out.println("C: Play a card");

		processUserFirstChoice(getUserInput());
	}

	private void processUserFirstChoice(String input) {
		switch (input.toLowerCase()) {
		case "a":
			giveHint();
			break;
		case "b":
			discardCard();
			break;
		case "c":
			playCard();
			break;
		default:
			System.out.println("Wrong Input");
			startTurn();
			break;
		}
	}

	private void giveHint() {
		System.out.println("Please choose which kind of hint you want to give:");
		System.out.println("A: Value Hint");
		System.out.println("B: Color Hint");
		processUserHintKind();
	}

	private void processUserHintKind() {
		String userInput = getUserInput();
		switch (userInput.toLowerCase()) {
		case "a":
			giveValueHint();
			break;
		case "b":
			giveColorHint();
			break;
		default:
			System.out.println("Wrong Input");
			startTurn();
			break;
		}
	}

	private void giveValueHint() {
		System.out.println("Please choose a number (1-5)");
		int numberValue = Integer.parseInt(getUserInput());
		System.out.println("Please choose the player (1-5) you want to give the hint");
		int playerNumber = Integer.parseInt(getUserInput());
		Message msg = new Message(MessageType.TURNACTION, new Player(playerNumber), PlayerActions.GIVE_NUMBER_HINT,
				numberValue);
		sendMessage(msg);
	}

	private void giveColorHint() {
		System.out.println("Please choose a color (red, green, blue, yellow)");
		String color = getUserInput();
		System.out.println("Please choose the player (1-4) you want to give the hint");
		int playerNumber = Integer.parseInt(getUserInput());
		Message msg = new Message(MessageType.TURNACTION, new Player(playerNumber), PlayerActions.GIVE_COLOR_HINT,
				ColorType.getColorFromString(color));
		sendMessage(msg);
	}

	private void discardCard() {
		System.out.println("Please choose the position (1-5) of the card you want to discard:");
		int cardPosition = getCardPositionUserInput();
		Message msg = new Message(MessageType.TURNACTION, new Card(cardPosition), PlayerActions.DISCARD);
		sendMessage(msg);
	}

	private void playCard() {
		System.out.println("Please choose the position (1-5) of the card you want to play:");
		int cardPosition = getCardPositionUserInput();
		Message msg = new Message(MessageType.TURNACTION, new Card(cardPosition), PlayerActions.PLAY_CARD);
		sendMessage(msg);
	}

	private int getCardPositionUserInput() {
		return Integer.parseInt(getUserInput());
	}

	private String getUserInput() {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		String userInput = null;
		try {
			userInput = stdIn.readLine();
		} catch (IOException e) {
			System.out.println("Couldn't read user input");
			e.printStackTrace();
		}
		return userInput;
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
		clientServerListener = new ClientServerListener(this);
		clientServerListener.start();
	}

	@Override
	public boolean disconnect() {
		System.out.println("Disconnecting ...");
		clientServerListener.terminate();
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