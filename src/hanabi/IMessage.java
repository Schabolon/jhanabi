package hanabi;

import hanabi.Message.MessageType;

public interface IMessage {

	public Player getPlayer();
	
	public MessageType getMessageType();
	
	public Card getCard();
	
	public Turn getTurn();
	
}
