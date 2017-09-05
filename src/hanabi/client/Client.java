package hanabi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import hanabi.Board;
import hanabi.Card;
import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.ColorType;
import hanabi.Message.MessageType;
import hanabi.Message.PlayerActions;
import hanabi.Player;

public class Client implements IClient {

	private String hostName;
	private int port = 1024;

	private Socket socket;
	private ObjectOutputStream objectOutputStream;

	private ClientServerListener clientServerListener;

	private List<String> playerNames;

	public Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	public Client() {
		this.hostName = "localhost";
		this.port = 1024;
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.instructions();
		client.connect();
		client.listenToServer();
		client.connectedToServerWaitingForUserinput();
	}

	private void instructions() {
		System.out.println("Welcome to Hanabi a coop card game");
		System.out.println("Please enter the server ip adress you want to connect to:");
		hostName = getUserInput();
		System.out.println("Please enter the server port: (default: 1024)");
		String userPortInput = getUserInput();
		if (!userPortInput.isEmpty()) {
			port = Integer.parseInt(userPortInput);
		}
		System.out.println("Trying to connect to the server " + hostName + ":" + port);
	}

	private void connectedToServerWaitingForUserinput() {
		System.out.println("You connected succesfully to the Hanabi server.");
		System.out.println(
				"As soon as all players have typed 'start' and submited it to the server the game is going to start");
		clientCommands();
	}

	private void clientCommands() {
		String userInput = getUserInput().toLowerCase();
		switch (userInput) {
		case "start":
			sendMessage(new Message(MessageType.START));
			break;
		case "quit":
			sendMessage(new Message(MessageType.QUIT));
			break;
		default:
			System.out.println("Unknown command.");
			clientCommands();
			break;
		}
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
		case STATUS_COLOR_HINT:
			System.out.println("The " + msg.getPlayer().getPlayerName() + " has " + msg.getColorType()
					+ " cards at the positions " + msg.getCardList().toString());
			break;
		case STATUS_NUMBER_HINT:
			System.out.println("The " + msg.getPlayer().getPlayerName() + " has cards with the value '"
					+ msg.getNumberValue() + "' at the positions " + msg.getCardList().toString());
			break;
		case STATUS_PLAYED_CARD:
			System.out.println("The " + msg.getPlayer().getPlayerName() + " played the card with the value '"
					+ msg.getCard().getNumberValue() + "' and the color " + msg.getCard().getColor() + " and it was "
					+ msg.isCardPlayedCorrectly());
			break;
		case STATUS_PLAYER_CARDS:
			System.out.println("The " + msg.getPlayer().getPlayerName() + " has the following cards:");
			List<Card> playersCards = msg.getCardList();
			for (int i = 0; i < playersCards.size(); i++) {
				Card card = playersCards.get(i);
				System.out.println(card.getNumberValue() + " " + card.getColor());
			}
			System.out.println(" ");
			break;
		case STATUS_GAME_END:
			System.out.println("The game ended!");
			System.out.println("You a achieved a score of " + msg.getScore());
			break;
		case STATUS_HINT_STORM_COUNT:
			System.out.println("Storm count: " + msg.getStormCount() + ", " + " Hint count: " + msg.getHintCount());
			System.out.println("------------------------------------------------------------");
			break;
		case STATUS_DISCARDED_CARD:
			System.out.println("The " + msg.getPlayer().getPlayerName() + " discarded the card with the value '"
					+ msg.getCard().getNumberValue() + "' and the color " + msg.getCard().getColor());
			break;
		case STATUS_BOARD_INFORMATION:
			Board board = msg.getBoard();
			System.out.println("RED:" + board.getRedCards() + " YELLOW:" + board.getYellowCards() + " GREEN:"
					+ board.getGreenCards() + " BLUE:" + board.getBlueCards() + " WHITE:" + board.getWhiteCards());
			break;
		case STATUS_CARDS_LEFT_IN_DECK:
			System.out.println("Cards left in deck: " + msg.getDeckCount());
			break;
		case STATUS_PLAYER_NAMES:
			playerNames = msg.getPlayerNames();
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
		case TURNACTION_NOT_POSSIBLE:
			System.err.println("Your last action couldn't be performed. Please try again.");
			startTurn();
			break;
		default:
			System.out.println("Unknown Message Type");
			break;
		}
	}

	private void startTurn() {
		System.out.println("It's your turn!");
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
		if (isNumberInRange(1, 5, numberValue)) {
			System.out.println("Please choose the player (0-" + playerNames.size() + ") you want to give the hint");
			int playerNumber = Integer.parseInt(getUserInput());
			if (isNumberInRange(0, playerNames.size(), playerNumber)) {
				Message msg = new Message(MessageType.TURNACTION, new Player(playerNumber),
						PlayerActions.GIVE_NUMBER_HINT, numberValue);
				sendMessage(msg);
			} else {
				startTurn();
			}
		} else {
			startTurn();
		}
	}

	private void giveColorHint() {
		System.out.println("Please choose a color (red, green, blue, yellow, white)");
		String colorUserInput = getUserInput();
		ColorType color = ColorType.getColorFromString(colorUserInput);
		if (isColor(color)) {
			System.out.println("Please choose the player (0-" + playerNames.size() + ") you want to give the hint");
			int playerNumber = Integer.parseInt(getUserInput());
			if (isNumberInRange(0, playerNames.size(), playerNumber)) {
				Message msg = new Message(MessageType.TURNACTION, new Player(playerNumber),
						PlayerActions.GIVE_COLOR_HINT, color);
				sendMessage(msg);
			} else {
				startTurn();
			}
		} else {
			startTurn();
		}
	}

	private boolean isNumberInRange(int minimum, int maximum, int number) {
		if (number >= minimum && number <= maximum) {
			return true;
		}
		return false;
	}

	private boolean isColor(ColorType color) {
		return !color.equals(ColorType.UNKNOWN);
	}

	private void discardCard() {
		System.out.println("Please choose the position (0-4) of the card you want to discard:");
		int cardPosition = getCardPositionUserInput();
		if (isNumberInRange(0, 4, cardPosition)) {
			Message msg = new Message(MessageType.TURNACTION, new Card(cardPosition), PlayerActions.DISCARD);
			sendMessage(msg);
		} else {
			startTurn();
		}
	}

	private void playCard() {
		System.out.println("Please choose the position (0-4) of the card you want to play:");
		int cardPosition = getCardPositionUserInput();
		if (isNumberInRange(0, 4, cardPosition)) {
			Message msg = new Message(MessageType.TURNACTION, new Card(cardPosition), PlayerActions.PLAY_CARD);
			sendMessage(msg);
		} else {
			startTurn();
		}
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