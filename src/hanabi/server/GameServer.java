package hanabi.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanabi.Card;
import hanabi.GameStats;
import hanabi.IMessage;
import hanabi.Message;
import hanabi.Message.ColorType;
import hanabi.Message.MessageType;
import hanabi.Message.PlayerActions;
import hanabi.Player;

public class GameServer implements IServer {
	private List<Player> players;
	private Map<Player, ClientThread> playersByClientThread;
	private Map<Player, Boolean> playersReady;
	private GameStats gameStats;
	private int currentPlayerNumber = 0;

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
	public void sendMessage(ClientThread receiver, IMessage msg) {
		try {
			receiver.getOutputStream().writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readMessage(ClientThread source, IMessage msg) {
		handleMessage(source, msg);

	}

	/**
	 * Handles the different message types that the server receives from the
	 * clients
	 */
	private void handleMessage(ClientThread source, IMessage msg) {
		Message.MessageType messageType = msg.getMessageType();
		switch (messageType) {
		case START:
			System.out.println("Server received START message from " + source.getName());
			startGameHandler(source, (Message) msg);
			break;
		case NEWCARD:
			System.out.println("Server received NEWCARD message from " + source.getName());
			break;
		case QUIT:
			System.out.println("Server received QUIT message from " + source.getName());
			quitGameHandler(source, (Message) msg);
			break;
		case STATUS_COLOR_HINT:
			System.out.println("Server received STATUS_COLOR_HINT message from " + source.getName());
			break;
		case STATUS_NUMBER_HINT:
			System.out.println("Server received STATUS_COLOR_HINT message from " + source.getName());
			break;
		case TURNACTION:
			System.out.println("Server received TURNACTION message from " + source.getName());
			gameActionHandler(source, (Message) msg);
			break;
		case TURNEND:
			System.out.println("Server received TURNEND message from " + source.getName());
			break;
		case TURNSTART:
			System.out.println("Server received TURNSTART message from " + source.getName());
			break;
		default:
			break;

		}
	}

	/**
	 * sends a message to all clients in the game
	 */
	private void sendAll(Message msg) {
		for (ClientThread receiver : playersByClientThread.values()) {
			sendMessage(receiver, msg);
		}
	}

	private void sendAllExcept(Message msg, Player excluded) {
		for (ClientThread receiver : playersByClientThread.values()) {
			if (!playersByClientThread.get(excluded).equals(receiver)) {
				sendMessage(receiver, msg);
			}
		}
	}

	/**
	 * checks when the game contains more then two players, if everyone already
	 * sent a
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
		if (!playersReady.get(sender)) {
			playersReady.put(sender, true);
		}
		if (allPlayersReady()) {
			sendAll(new Message(MessageType.START));
			gameStats = new GameStats();
			gameStats.handOutCardsAtGamestart(players);
			sendCardInformation();
			Message turnStarMessage = new Message(MessageType.TURNSTART);
			currentPlayerNumber = 0;
			sendMessage(playersByClientThread.get(players.get(currentPlayerNumber)), turnStarMessage);
		}
	}

	private void sendCardInformation() {
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			Message msg = new Message(MessageType.STATUS_PLAYER_CARDS, player, player.getCardList());
			sendAllExcept(msg, player);
		}
	}

	private void gameActionHandler(ClientThread source, Message msg) {
		PlayerActions playerActions = msg.getPlayerActions();
		switch (playerActions) {
		case DISCARD:
			discardHandler(source.getPlayer(), msg.getCard());
			break;
		case GIVE_COLOR_HINT:
			colorHintHandler(msg.getPlayer().getPlayerNumber(), msg.getColorType());
			break;
		case GIVE_NUMBER_HINT:
			numberHintHandler(msg.getPlayer().getPlayerNumber(), msg.getNumberValue());
			break;
		case PLAY_CARD:
			playCardHandler(source.getPlayer(), msg.getCard().getPosition());
			break;
		default:
			break;
		}
	}

	private void discardHandler(Player player, Card card) {
		if (gameStats.canPlayerDiscard()) {
			player.removeCard(card.getPosition());
			player.handoutNewCard(gameStats.drawCardFromDeck());
			gameStats.increaseNotesByOne();
			Message msg = new Message(MessageType.NEWCARD, player, card);
			sendAllExcept(msg, player);
		}
		sendCardInformation();
		sendNoteAndStormTokenCount();
		nextPlayersTurn();
	}

	private boolean canPlayerGiveHint() {
		if (gameStats.getNoteCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	private void colorHintHandler(int playerNumber, ColorType color) {
		if (canPlayerGiveHint()) {
			Player playerTheHintIsGivenTo = players.get(playerNumber);
			List<Card> cardsWithGivenColor = playerTheHintIsGivenTo.getCardsByColor(color);
			gameStats.increaseNotesByOne();
			Message msg = new Message(MessageType.STATUS_COLOR_HINT, color, cardsWithGivenColor,
					playerTheHintIsGivenTo);
			sendAll(msg);
			sendNoteAndStormTokenCount();
			nextPlayersTurn();
		}
	}

	private void numberHintHandler(int playerNumber, int numberValue) {
		if (canPlayerGiveHint()) {
			Player playerTheHintIsGivenTo = players.get(playerNumber);
			List<Card> cardsWithGivenNumberValue = playerTheHintIsGivenTo.getCardsByNumberValue(numberValue);
			gameStats.increaseNotesByOne();
			Message msg = new Message(MessageType.STATUS_NUMBER_HINT, numberValue, cardsWithGivenNumberValue,
					playerTheHintIsGivenTo);
			sendAll(msg);
			sendNoteAndStormTokenCount();
			nextPlayersTurn();
		}
	}

	private void playCardHandler(Player player, int cardPosition) {
		Card card = player.getCardList().get(cardPosition);
		if (gameStats.getBoard().playedCardCorrectly(card)) {
			Message msg = new Message(MessageType.STATUS_PLAYED_CARD, player, card, true);
			sendAll(msg);
		} else {
			gameStats.increaseStormCountByOne();
			Message msg = new Message(MessageType.STATUS_PLAYED_CARD, player, card, false);
			sendAll(msg);
		}
		player.handoutNewCard(gameStats.drawCardFromDeck());
		sendCardInformation();
		sendNoteAndStormTokenCount();
		nextPlayersTurn();
	}

	private void sendNoteAndStormTokenCount() {
		Message msg = new Message(MessageType.STATUS_NOTE_STORM_COUNT, gameStats.getStormCount(),
				gameStats.getNoteCount());
		sendAll(msg);
	}

	private void nextPlayersTurn() {
		if (isGameEnd()) {
			int score = getGameScore();
			Message msg = new Message(MessageType.STATUS_GAME_END, score);
			sendAll(msg);
		} else {
			endCurrentPlayersTurn();
			currentPlayerNumber = getNextPlayersNumber();
			startCurrentPlayersTurn();
		}
	}

	private boolean isGameEnd() {
		boolean isGameOver = false;
		if (gameStats.getStormCount() >= 3) {
			return true;
		} else if (gameStats.getCarddeck().isEmpty() && gameStats.getTurnsLeft() == -1) {
			gameStats.setTurnsLeft(players.size());
			return false;
		}
		if (gameStats.getTurnsLeft() > 0) {
			gameStats.setTurnsLeft(gameStats.getTurnsLeft() - 1);
		} else if (gameStats.getTurnsLeft() == 0) {
			return true;
		}

		if (gameStats.getBoard().getAllCardsCount() == 25) {
			return true;
		}

		return isGameOver;
	}

	private int getGameScore() {
		if (gameStats.getStormCount() >= 3) {
			return 0;
		} else {
			return gameStats.getBoard().getAllCardsCount();
		}
	}

	private void endCurrentPlayersTurn() {
		Player currentPlayer = players.get(currentPlayerNumber);
		Message msg = new Message(MessageType.TURNEND);
		ClientThread clientThread = playersByClientThread.get(currentPlayer);
		sendMessage(clientThread, msg);
	}

	private void startCurrentPlayersTurn() {
		Player currentPlayer = players.get(currentPlayerNumber);
		Message msg = new Message(MessageType.TURNSTART);
		ClientThread clientThread = playersByClientThread.get(currentPlayer);
		sendMessage(clientThread, msg);
	}

	private int getNextPlayersNumber() {
		if ((players.size() - 1) > currentPlayerNumber) {
			return currentPlayerNumber + 1;
		} else {
			return 0;
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
