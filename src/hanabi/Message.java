package hanabi;

public class Message implements IMessage {

	private MessageType messageType;
	private Player player;
	private Card card;
	
	public enum MessageType {
		START, QUIT, TURNSTART, TURNACTION, NEWCARD, TURNEND, STATUS 
	}
	
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
	public String getMessageType() {
		return messageType.toString();
	}

}
