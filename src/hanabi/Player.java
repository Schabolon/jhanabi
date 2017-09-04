package hanabi;

import java.util.List;

public class Player {
	private String playerName;
	private List<Card> cardList;

	public Player(int i) {
		this.playerName = "Player " + Integer.toString(i);
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
