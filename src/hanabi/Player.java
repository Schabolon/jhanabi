package hanabi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hanabi.message.ColorType;

public class Player implements Serializable {

	private static final long serialVersionUID = -5801295088864066878L;

	private String playerName;
	private List<Card> handCards;
	private int playerNumber;

	public Player(int playerNumber) {
		this.playerName = "Player " + Integer.toString(playerNumber);
		handCards = new ArrayList<>();
		this.playerNumber = playerNumber;
	}

	public List<Card> getCardsByColor(ColorType color) {
		List<Card> cardsWithColor = new ArrayList<>();
		for (int i = 0; i < handCards.size(); i++) {
			Card card = handCards.get(i);
			if (card.getColor().equals(color)) {
				cardsWithColor.add(card);
			}
		}
		return cardsWithColor;
	}

	public List<Card> getCardsByNumber(int number) {
		List<Card> cardsWithSpecifiedNumber = new ArrayList<>();
		for (int i = 0; i < handCards.size(); i++) {
			Card card = handCards.get(i);
			if (card.getNumber() == number) {
				cardsWithSpecifiedNumber.add(card);
			}
		}
		return cardsWithSpecifiedNumber;
	}

	public List<Card> getHandCards() {
		return handCards;
	}

	public void handoutNewCard(Card card) {
		card.setPosition(handCards.size());
		handCards.add(card);
	}

	public void removeCard(int position) {
		handCards.remove(position);
		updatePlayerCardPositions();
	}

	private void updatePlayerCardPositions() {
		for (int i = 0; i < handCards.size(); i++) {
			handCards.get(i).setPosition(i);
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
}
