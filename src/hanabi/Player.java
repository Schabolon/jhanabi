package hanabi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

	private static final long serialVersionUID = -5801295088864066878L;

	private String playerName;
	private List<Card> cardList;

	public Player(int i) {
		this.playerName = "Player " + Integer.toString(i);
		cardList = new ArrayList<>();
	}

	public List<Card> getCardList() {
		return cardList;
	}

	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

	public void handoutNewCard(Card card) {
		cardList.add(card);
	}

	public void removeCard(int position) {
		cardList.remove(position);
	}

	public String getPlayerName() {
		return playerName;
	}
}
