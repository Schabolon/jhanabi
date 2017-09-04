package hanabi;

public interface IMessage {

	public Player getPlayer();
	
	public String getMessageType();
	
	public Card getCard();
	
	public Turn getTurn();
	
}
