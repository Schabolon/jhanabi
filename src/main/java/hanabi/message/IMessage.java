package hanabi.message;

import java.util.List;

import hanabi.Board;
import hanabi.Card;
import hanabi.Player;

public interface IMessage {

	public MessageType getMessageType();

	public Player getPlayer();

	public Card getCard();

	public ColorType getColorType();

	public int getNumberValue();

	public List<Card> getCardList();

	public boolean isCardPlayedCorrectly();

	public int getScore();

	public int getHintCount();

	public int getStormCount();

	public int getDeckCount();

	public Board getBoard();

	public List<String> getPlayerNames();

}
