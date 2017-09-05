package hanabi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hanabi.Message.ColorType;

public class GameStats {

	private List<Card> carddeck = new ArrayList<>();
	private List<Card> trayStack = new ArrayList<>();
	private int stormCount = 0;
	private int noteCount = 8;
	private Board board;
	private int turnsLeft = -1;

	public GameStats() {
		createCarddeck();
		shuffle();
		board = new Board(0, 0, 0, 0, 0);
	}

	private void createCarddeck() {
		carddeck.addAll(createTenCardsWithColor(ColorType.RED));
		carddeck.addAll(createTenCardsWithColor(ColorType.YELLOW));
		carddeck.addAll(createTenCardsWithColor(ColorType.GREEN));
		carddeck.addAll(createTenCardsWithColor(ColorType.BLUE));
		carddeck.addAll(createTenCardsWithColor(ColorType.WHITE));
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
			handoutCardsFromCarddeckToPlayer(5, playerList.get(i));
		}
	}

	private void handoutCardsFromCarddeckToPlayer(int cardCount, Player player) {
		for (int i = 0; i < cardCount; i++) {
			player.handoutNewCard(drawCardFromDeck());
		}
	}

	public Card drawCardFromDeck() {
		Card drawnCard = carddeck.get(carddeck.size() - 1);
		carddeck.remove(carddeck.size() - 1);
		return drawnCard;
	}

	private void shuffle() {
		Collections.shuffle(carddeck);
	}

	public void discardCard(Card card) {
		trayStack.add(card);
	}

	public List<Card> getCarddeck() {
		return carddeck;
	}

	public List<Card> getTrayStack() {
		return trayStack;
	}

	public void increaseStormCountByOne() {
		stormCount++;
	}

	public int getStormCount() {
		return stormCount;
	}

	public void decreaseNotesByOne() {
		noteCount--;
	}

	public void increaseNotesByOne() {
		noteCount++;
	}

	public int getNoteCount() {
		return noteCount;
	}

	public Board getBoard() {
		return board;
	}

	public boolean canPlayerDiscard() {
		if (noteCount == 8) {
			return false;
		}
		return true;
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}

	public void setTurnsLeft(int turnsLeft) {
		this.turnsLeft = turnsLeft;
	}

}
