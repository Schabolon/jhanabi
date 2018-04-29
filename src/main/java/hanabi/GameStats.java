package hanabi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hanabi.message.ColorType;

public class GameStats {

	/**
	 * Cards are drawn from the card deck.
	 */
	private List<Card> cardDeck = new ArrayList<>();

	/**
	 * Contains the cards all players have discarded this game.
	 */
	private List<Card> trayStack = new ArrayList<>();

	private int stormCount = 0;
	private int hintCount = 8;

	private Board board;

	/**
	 * The turns left after the card deck is empty.
	 */
	private int turnsLeft = -1;

	public GameStats() {
		createCarddeck();
		shuffle();
		board = Board.EMPTY_BOARD;
	}

	private void createCarddeck() {
		cardDeck.addAll(createTenCardsWithColor(ColorType.RED));
		cardDeck.addAll(createTenCardsWithColor(ColorType.YELLOW));
		cardDeck.addAll(createTenCardsWithColor(ColorType.GREEN));
		cardDeck.addAll(createTenCardsWithColor(ColorType.BLUE));
		cardDeck.addAll(createTenCardsWithColor(ColorType.WHITE));
	}

	private List<Card> createTenCardsWithColor(ColorType color) {
		List<Card> result = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			result.add(new Card(color, 1));
			result.add(new Card(color, 2));
			result.add(new Card(color, 3));
			result.add(new Card(color, 4));
		}
		result.add(new Card(color, 1));
		result.add(new Card(color, 5));
		return result;
	}

	public void handOutCardsAtGamestart(List<Player> playerList) {
		for (int i = 0; i < playerList.size(); i++) {
			handoutCardsFromCardDeckToPlayer(getCardCountAccordingToPlayerCount(playerList.size()), playerList.get(i));
		}
	}

	private void handoutCardsFromCardDeckToPlayer(int cardCount, Player player) {
		for (int i = 0; i < cardCount; i++) {
			player.handoutNewCard(drawCardFromDeck());
		}
	}

	private int getCardCountAccordingToPlayerCount(int playerCount) {
		if (playerCount == 2 || playerCount == 3) {
			return 5;
		} else {
			return 4;
		}
	}

	public Card drawCardFromDeck() {
		Card drawnCard = cardDeck.get(cardDeck.size() - 1);
		cardDeck.remove(cardDeck.size() - 1);
		return drawnCard;
	}

	/**
	 * Shuffles the card deck
	 */
	private void shuffle() {
		Collections.shuffle(cardDeck);
	}

	public void moveCardToTrayStack(Card card) {
		trayStack.add(card);
	}

	public List<Card> getCarddeck() {
		return cardDeck;
	}

	public List<Card> getTrayStack() {
		return trayStack;
	}

	public boolean canPlayerDiscard() {
		if (hintCount == 8) {
			return false;
		}
		return true;
	}

	public void increaseStormCountByOne() {
		stormCount++;
	}

	public int getStormCount() {
		return stormCount;
	}

	public void decreaseHintsByOne() {
		if (hintCount > 0) {
			hintCount--;
		}
	}

	public void increaseHintsByOne() {
		if (hintCount < 8) {
			hintCount++;
		}
	}

	public int getHintCount() {
		return hintCount;
	}

	public Board getBoard() {
		return board;
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}

	public void setTurnsLeft(int turnsLeft) {
		this.turnsLeft = turnsLeft;
	}

}
