package hanabi;

import java.io.Serializable;

public class Message implements IMessage, Serializable {

	private static final long serialVersionUID = 4279125854700749725L;

	private MessageType messageType;
	private Player player;
	private Card card;
	private PlayerActions playerActions;
	private ColorType colorType;
	private int numberValue;

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

	public Message(MessageType messageType, Player player, Card card, PlayerActions playerActions) {
		this.messageType = messageType;
		this.player = player;
		this.card = card;
		this.playerActions = playerActions;
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

	public ColorType getColorType() {
		return colorType;
	}

	public int getNumberValue() {
		return numberValue;
	}

	public enum MessageType {
		START, QUIT, TURNSTART, TURNACTION, NEWCARD, TURNEND, STATUS;
	}

	public enum PlayerActions {
		DISCARD, GIVE_COLOR_HINT, GIVE_NUMBER_HINT, PLAY_CARD;
	}

	public enum ColorType {
		RED, YELLOW, GREEN, BLUE, WHITE;

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
				return null;
			}

		}
	}
	
}
