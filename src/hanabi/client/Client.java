package hanabi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.MessageType;

public class Client implements IClient {

	private String hostName;
	private int port;

	private Socket socket;
	private ObjectOutputStream objectOutputStream;

	private Thread clientServerListener;

	public Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	public static void main(String[] args) {
		Client client = new Client("localhost", 1024);
		client.connect();
		client.listenToServer();
		client.sendMessage(new Message(MessageType.START));
	}

	@Override
	public void sendMessage(IMessage msg) {
		try {
			objectOutputStream.writeObject(msg);
		} catch (IOException e) {
			System.out.println("Couldn't write Message to ObjectOutputStream");
			e.printStackTrace();
		}
		try {
			objectOutputStream.flush();
		} catch (IOException e) {
			System.out.println("Couldn't flush ObjectOutputStream");
			e.printStackTrace();
		}
	}

	@Override
	public void readMessage(IMessage msg) {
		MessageType messageType = msg.getMessageType();
		switch (messageType) {
		case START:
			System.out.println("The Game is starting.");
			break;
		case NEWCARD:
			break;
		case QUIT:
			System.out.println("Stopping the game");
			break;
		case STATUS:
			System.out.println("Updating game information");
			break;
		case TURNACTION:
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
		System.out.println("Message received");
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
		case "a": giveValueHint();
			break;
		case "b": giveColorHint();
			break;
		}
	}

	private void giveValueHint() {
		System.out.println("Please choose which a number value (1-5)");
		int numberValue = Integer.parseInt(getUserInput());
		System.out.println("Please choose the player (1-4) you want to give the hint");
		int playerNumber = Integer.parseInt(getUserInput());
	}
	
	private void giveColorHint() {
		System.out.println("Please choose a color (red, green, blue, yellow)");
		String color = getUserInput();
		System.out.println("Please choose the player (1-4) you want to give the hint");
	}
	
	private void discardCard() {
		System.out.println("Please choose the position (1-5) of the card you want to discard:");
		int cardPosition = getCardPositionUserInput();
		// TODO send data to server
	}

	private void playCard() {
		System.out.println("Please choose the position (1-5) of the card you want to play:");
		int cardPosition = getCardPositionUserInput();
		// TODO send data to server
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
		clientServerListener.interrupt();
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

class ClientServerListener extends Thread {

	ObjectInputStream objectInputStream;
	Client client;
	boolean listenToServer = true;

	public ClientServerListener(Client client) {
		this.client = client;
		objectInputStream = getObjectInputStream();
	}

	@Override
	public void run() {
		System.out.println("Started listening ...");
		while (listenToServer) {
			Object object = null;
			try {
				object = objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("The class received from the server was not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("An error occured while listening to the server");
				e.printStackTrace();
			}
			IMessage msg = (IMessage) object;
			client.readMessage(msg);
		}
	}

	private ObjectInputStream getObjectInputStream() {
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
		} catch (IOException e) {
			System.out.println("Couldn't get ObjectInputStream");
			e.printStackTrace();
		}
		return objectInputStream;
	}

}
