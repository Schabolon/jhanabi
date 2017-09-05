package hanabi;

import java.io.Serializable;
import java.util.List;

public class Message implements IMessage, Serializable {

	private static final long serialVersionUID = 4279125854700749725L;

	private MessageType messageType;
	private Player player;
	private Card card;
	private PlayerActions playerActions;
	private ColorType colorType;
	private int numberValue;
	private List<Card> cardList;
	private boolean cardPlayedCorrectly;
	private int score;
	private int stormCount;
	private int noteCount;
	private Board board;
	private int deckCount;
	private List<String> playerNames;

	public Message(MessageType messageType) {
		this.messageType = messageType;
	}

	public Message(MessageType messageType, Player player) {
		this.messageType = messageType;
		this.player = player;
	}

	public Message(MessageType messageType, Player player, Card card) {
		this.messageType = messageType;
		this.player = player;
		this.card = card;
	}

	public Message(MessageType messageType, Card card, PlayerActions playerActions) {
		this.messageType = messageType;
		this.card = card;
		this.playerActions = playerActions;
	}

	public Message(MessageType messageType, Player player, Card card, PlayerActions playerActions,
			ColorType colorType) {
		this.messageType = messageType;
		this.player = player;
		this.card = card;
		this.playerActions = playerActions;
		this.colorType = colorType;
	}

	public Message(MessageType messageType, Player player, PlayerActions playerActions, ColorType colorType) {
		this.messageType = messageType;
		this.player = player;
		this.playerActions = playerActions;
		this.colorType = colorType;
	}

	public Message(MessageType messageType, Player player, PlayerActions playerActions, int numberValue) {
		this.messageType = messageType;
		this.player = player;
		this.playerActions = playerActions;
		this.numberValue = numberValue;
	}

	public Message(MessageType messageType, ColorType colorType, List<Card> cardList, Player player) {
		this.messageType = messageType;
		this.colorType = colorType;
		this.cardList = cardList;
		this.player = player;
	}

	public Message(MessageType messageType, int numberValue, List<Card> cardList, Player player) {
		this.messageType = messageType;
		this.numberValue = numberValue;
		this.cardList = cardList;
		this.player = player;
	}

	public Message(MessageType messageType, Player player, Card card, boolean cardPlayedCorrectly) {
		this.messageType = messageType;
		this.player = player;
		this.card = card;
		this.cardPlayedCorrectly = cardPlayedCorrectly;
	}

	public Message(int score) {
		this.messageType = MessageType.STATUS_GAME_END;
		this.score = score;
	}

	public Message(MessageType messageType, List<Card> cardList) {
		this.messageType = messageType;
		this.cardList = cardList;
	}

	public Message(MessageType messageType, Player player, List<Card> cardList) {
		this.messageType = messageType;
		this.player = player;
		this.cardList = cardList;
	}

	public Message(MessageType messageType, int stormCount, int noteCount) {
		this.messageType = messageType;
		this.stormCount = stormCount;
		this.noteCount = noteCount;
	}

	public Message(MessageType messageType, Board board) {
		this.messageType = messageType;
		this.board = board;
	}

	public Message(MessageType messageType, int deckCount) {
		this.messageType = messageType;
		this.deckCount = deckCount;
	}

	public Message(List<String> playerNames) {
		this.messageType = MessageType.STATUS_PLAYER_NAMES;
		this.playerNames = playerNames;
	}

	@Override
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Card getCard() {
		return card;
	}

	@Override
	public Turn getTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageType getMessageType() {
		return messageType;

	}

	public PlayerActions getPlayerActions() {
		return playerActions;
	}

	@Override
	public ColorType getColorType() {
		return colorType;
	}

	@Override
	public int getNumberValue() {
		return numberValue;
	}

	@Override
	public List<Card> getCardList() {
		return cardList;
	}

	@Override
	public boolean isCardPlayedCorrectly() {
		return cardPlayedCorrectly;
	}

	@Override
	public int getHintCount() {
		return noteCount;
	}

	@Override
	public int getStormCount() {
		return stormCount;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public int getDeckCount() {
		return deckCount;
	}
	
	@Override
	public List<String> getPlayerNames() {
		return playerNames;
	}

	public enum MessageType {
		START, QUIT, TURNSTART, TURNACTION, NEWCARD, TURNEND, STATUS_COLOR_HINT, STATUS_NUMBER_HINT, STATUS_PLAYED_CARD, STATUS_GAME_END, STATUS_PLAYER_CARDS, STATUS_HINT_STORM_COUNT, STATUS_DISCARDED_CARD, STATUS_BOARD_INFORMATION, STATUS_CARDS_LEFT_IN_DECK, STATUS_PLAYER_NAMES, TURNACTION_NOT_POSSIBLE;
	}

	public enum PlayerActions {
		DISCARD, GIVE_COLOR_HINT, GIVE_NUMBER_HINT, PLAY_CARD;
	}

	public enum ColorType {
		RED, YELLOW, GREEN, BLUE, WHITE, UNKNOWN;

		public static ColorType getColorFromString(String color) {
			switch (color.toLowerCase()) {
			case "red":
				return RED;
			case "yellow":
				return YELLOW;
			case "green":
				return GREEN;
			case "blue":
				return BLUE;
			case "white":
				return WHITE;
			default:
				return UNKNOWN;
			}

		}
	}
}
