package hanabi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import hanabi.IMessage;
import hanabi.Message.MessageType;

public class Client implements IClient {

	private String hostName;
	private int port;

	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private Thread clientServerListener;

	public Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
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
			System.out.println("It's the next players turn");
			break;
		case TURNSTART:
			startTurn();
			break;
		default:
			System.out.println("Unknown Message Type");
			break;
		}
		System.out.println("Message recieved");
	}

	private void startTurn() {
		System.out.println("Its your turn!");
		System.out.println("Please choose one of the following options:");
		System.out.println("A: Give a hint");
		System.out.println("B: Discard a card");
		System.out.println("C: Play a card");

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		String userInput = null;
		try {
			userInput = stdIn.readLine();
		} catch (IOException e) {
			System.out.println("Couldn't read user input");
			e.printStackTrace();
		}
		processUserInput(userInput);
	}

	private void processUserInput(String input) {
		switch (input.toLowerCase()) {
		case "a":
			giveHint();
		case "b":
			discardCard();
		case "c":
			playCard();
		}
	}

	private void giveHint() {
		System.out.println("AAA");
	}

	private void discardCard() {
		System.out.println("BBB");
	}
	
	private void playCard() {
		System.out.println("CCC");
	}

	@Override
	public boolean connect() {
		Socket socket = null;
		try {
			socket = new Socket(hostName, port);
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host: " + hostName);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
			return false;
		}
		this.socket = socket;
		initializeObjectStreams();
		System.out.println("Connection established");
		return true;
	}

	private void initializeObjectStreams() {
		objectInputStream = getObjectInputStream();
		objectOutputStream = getObjectOutputStream();
	}

	private ObjectInputStream getObjectInputStream() {
		InputStream inputStream = null;
		try {
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			System.out.println("Couldn't get InputStream");
			e.printStackTrace();
		}

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(inputStream);
		} catch (IOException e) {
			System.out.println("Couldn't get ObjectInputStream");
			e.printStackTrace();
		}
		return objectInputStream;
	}

	private ObjectOutputStream getObjectOutputStream() {
		OutputStream outputStream = null;
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println("Couldn't get OutputStream");
			e.printStackTrace();
		}

		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(outputStream);
		} catch (IOException e) {
			System.out.println("Couldn't get ObjectOutputStream");
			e.printStackTrace();
		}
		return objectOutputStream;
	}

	@Override
	public void listenToServer() {
		clientServerListener = new ClientServerListener(objectInputStream, this);
		clientServerListener.start();
	}

	@Override
	public boolean disconnect() {
		clientServerListener.interrupt();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't disconnect");
			return false;
		}
		return true;
	}
}

class ClientServerListener extends Thread {

	ObjectInputStream objectInputStream;
	Client client;
	boolean listenToServer = true;

	public ClientServerListener(ObjectInputStream objectInputStream, Client client) {
		this.objectInputStream = objectInputStream;
		this.client = client;
	}

	@Override
	public void run() {
		while (listenToServer) {
			Object object = null;
			try {
				object = objectInputStream.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("The class recieved from the server was not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("An error occured while listening to the server");
				e.printStackTrace();
			}
			IMessage msg = (IMessage) object;
			client.readMessage(msg);
		}
	}

}
