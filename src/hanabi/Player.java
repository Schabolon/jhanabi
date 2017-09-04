package hanabi;

import java.util.List;

public class Player {
	String playerName;
	List<Card> cardList;
	
	public Player(int i) {
		playerName = "Player " + Integer.toString(i);
	}

}
