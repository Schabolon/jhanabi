package hanabi;

import java.io.Serializable;

import hanabi.message.ColorType;

public class Board implements Serializable {

	private static final long serialVersionUID = -9007243603738108878L;

	int redCards;
	int yellowCards;
	int greenCards;
	int blueCards;
	int whiteCards;

	public Board(int redCards, int yellowCards, int greenCards, int blueCards, int whiteCards) {
		this.redCards = redCards;
		this.yellowCards = yellowCards;
		this.greenCards = greenCards;
		this.blueCards = blueCards;
		this.whiteCards = whiteCards;
	}

	public int getRedCards() {
		return redCards;
	}

	public int getYellowCards() {
		return yellowCards;
	}

	public int getGreenCards() {
		return greenCards;
	}

	public int getBlueCards() {
		return blueCards;
	}

	public int getWhiteCards() {
		return whiteCards;
	}

	public int getAllCardsCount() {
		return redCards + yellowCards + greenCards + blueCards + whiteCards;
	}

	//TODO besserer Name, da nicht nur geprüft wird, sondern auch Aktionen stattfinden
	public boolean playedCardCorrectly(Card card, GameStats gameStats) {
		ColorType cardColor = card.getColor();
		int cardNumberValue = card.getNumberValue();
		switch (cardColor) {
		case BLUE:
			if ((blueCards + 1) == cardNumberValue && blueCards != 5) {
				blueCards = blueCards + 1;
				if (blueCards == 5) {
					gameStats.increaseHintsByOne();
				}
				return true;
			}
			return false;
		case GREEN:
			if ((greenCards + 1) == cardNumberValue && greenCards != 5) {
				greenCards = greenCards + 1;
				if (greenCards == 5) {
					gameStats.increaseHintsByOne();
				}
				return true;
			}
			return false;
		case RED:
			if ((redCards + 1) == cardNumberValue && redCards != 5) {
				redCards = redCards + 1;
				if (redCards == 5) {
					gameStats.increaseHintsByOne();
				}
				return true;
			}
			return false;
		case WHITE:
			if ((whiteCards + 1) == cardNumberValue && whiteCards != 5) {
				whiteCards = whiteCards + 1;
				if (whiteCards == 5) {
					gameStats.increaseHintsByOne();
				}
				return true;
			}
			return false;
		case YELLOW:
			if ((yellowCards + 1) == cardNumberValue && yellowCards != 5) {
				yellowCards = yellowCards + 1;
				if (yellowCards == 5) {
					gameStats.increaseHintsByOne();
				}
				return true;
			}
			return false;
		default:
			return false;

		}
	}
}
