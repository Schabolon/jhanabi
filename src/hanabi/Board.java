package hanabi;

import hanabi.Message.ColorType;

public class Board {

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

	public boolean playedCardCorrectly(Card card) {
		ColorType cardColor = card.getColor();
		int cardNumberValue = card.getNumberValue();
		switch (cardColor) {
		case BLUE:
			if ((blueCards + 1) == cardNumberValue && blueCards != 5) {
				blueCards = blueCards + 1;
				return true;
			}
			return false;
		case GREEN:
			if ((greenCards + 1) == cardNumberValue && greenCards != 5) {
				greenCards = greenCards + 1;
				return true;
			}
			return false;
		case RED:
			if ((redCards + 1) == cardNumberValue && redCards != 5) {
				redCards = redCards + 1;
				return true;
			}
			return false;
		case WHITE:
			if ((whiteCards + 1) == cardNumberValue && whiteCards != 5) {
				whiteCards = whiteCards + 1;
				return true;
			}
			return false;
		case YELLOW:
			if ((yellowCards + 1) == cardNumberValue && yellowCards != 5) {
				yellowCards = yellowCards + 1;
				return true;
			}
			return false;
		default:
			return false;

		}
	}
}
