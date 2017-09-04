package hanabi;

import java.util.List;

public class Player {
	private String playerName;
	private List<Card> cardList;

	public Player(int i) {
		this.playerName = "Player " + Integer.toString(i);
	}

}
