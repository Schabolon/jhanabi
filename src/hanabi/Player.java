package hanabi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hanabi.Message.ColorType;

public class Player implements Serializable {

	private static final long serialVersionUID = -5801295088864066878L;

	private String playerName;
	private List<Card> cardList;
	private int playerNumber;

	public Player(int playerNumber) {
		this.playerName = "Player " + Integer.toString(playerNumber);
		cardList = new ArrayList<>();
		this.playerNumber = playerNumber;
	}

	public List<Card> getCardsByColor(ColorType color) {
		List<Card> cardsWithColor = new ArrayList<>();
		for (int i = 0; i < cardList.size(); i++) {
			Card card = cardList.get(i);
			if (card.getColor().equals(color)) {
				cardsWithColor.add(card);
			}
		}
		return cardsWithColor;
	}
	
	public List<Card> getCardsByNumberValue(int numberValue) {
		List<Card> cardsWithNumberValue = new ArrayList<>();
		for (int i = 0; i < cardList.size(); i++) {
			Card card = cardList.get(i);
			if (card.getNumberValue() == numberValue) {
				cardsWithNumberValue.add(card);
			}
		}
		return cardsWithNumberValue;
	}

	public List<Card> getCardList() {
		return cardList;
	}

	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

	public void handoutNewCard(Card card) {
		card.setPosition(cardList.size());
		cardList.add(card);
	}

	public void removeCard(int position) {
		cardList.remove(position);
		updatePlayerCardPositions();
	}

	private void updatePlayerCardPositions() {
		for (int i = 0; i < cardList.size(); i++) {
			cardList.get(i).setPosition(i);
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
}
