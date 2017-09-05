package hanabi;

import java.util.List;

import hanabi.Message.ColorType;
import hanabi.Message.MessageType;

public interface IMessage {

	public Player getPlayer();

	public MessageType getMessageType();

	public Card getCard();

	public Turn getTurn();

	public ColorType getColorType();

	public List<Card> getCardList();

	public int getNumberValue();

	public boolean isCardPlayedCorrectly();

	public int getScore();

	public int getNoteCount();

	public int getStormCount();

}
