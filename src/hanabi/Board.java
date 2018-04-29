package hanabi;

import java.io.Serializable;

import hanabi.message.ColorType;

public class Board implements Serializable {

	private static final long serialVersionUID = -9007243603738108878L;

	/**
	 * The card-number of the top card on the red pile. If the card-number reaches
	 * 5, the players have completed this pile.
	 */
	int cardNumberOnTopOfRedPile;

	/**
	 * The card-number of the top card on the yellow pile. If the card-number
	 * reaches 5, the players have completed this pile.
	 */
	int cardNumberOnTopOfYellowPile;

	/**
	 * The card-number of the top card on the green pile. If the card-number reaches
	 * 5, the players have completed this pile.
	 */
	int cardNumberOnTopOfGreenPile;

	/**
	 * The card-number of the top card on the blue pile. If the card-number reaches
	 * 5, the players have completed this pile.
	 */
	int cardNumberOnTopOfBluePile;

	/**
	 * The card-number of the top card on the white pile. If the card-number reaches
	 * 5, the players have completed this pile.
	 */
	int cardNumberOnTopOfWhitePile;

	public Board(int cardNumberOnTopOfRedPile, int cardNumberOnTopOfYellowPile, int cardNumberOnTopOfGreenPile,
			int cardNumberOnTopOfBluePile, int cardNumberOnTopOfWhitePile) {
		this.cardNumberOnTopOfRedPile = cardNumberOnTopOfRedPile;
		this.cardNumberOnTopOfYellowPile = cardNumberOnTopOfYellowPile;
		this.cardNumberOnTopOfGreenPile = cardNumberOnTopOfGreenPile;
		this.cardNumberOnTopOfBluePile = cardNumberOnTopOfBluePile;
		this.cardNumberOnTopOfWhitePile = cardNumberOnTopOfWhitePile;
	}

	public int getCardNumberOnTopOfRedPile() {
		return cardNumberOnTopOfRedPile;
	}

	public int getCardNumberOnTopOfYellowPile() {
		return cardNumberOnTopOfYellowPile;
	}

	public int getCardNumberOnTopOfGreenPile() {
		return cardNumberOnTopOfGreenPile;
	}

	public int getCardNumberOnTopOfBluePile() {
		return cardNumberOnTopOfBluePile;
	}

	public int getCardNumberOnTopOfWhitePile() {
		return cardNumberOnTopOfWhitePile;
	}

	public int getAllCardsCountAlreadyOnPile() {
		return cardNumberOnTopOfRedPile + cardNumberOnTopOfYellowPile + cardNumberOnTopOfGreenPile
				+ cardNumberOnTopOfBluePile + cardNumberOnTopOfWhitePile;
	}

	public boolean checkIfPlayedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(Card card, GameStats gameStats) {
		ColorType cardColor = card.getColor();
		int cardNumber = card.getNumberValue();
		switch (cardColor) {
		case BLUE:
			playedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(cardNumber, cardNumberOnTopOfBluePile,
					gameStats);
		case GREEN:
			playedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(cardNumber, cardNumberOnTopOfGreenPile,
					gameStats);
		case RED:
			playedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(cardNumber, cardNumberOnTopOfRedPile,
					gameStats);
		case WHITE:
			playedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(cardNumber, cardNumberOnTopOfWhitePile,
					gameStats);
		case YELLOW:
			playedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(cardNumber, cardNumberOnTopOfYellowPile,
					gameStats);
		default:
			return false;
		}
	}

	private boolean playedCardCorrectlyAndIncreaseHintCountIfCardPileIsComplete(int cardNumber,
			int cardNumberOnTopOfPile, GameStats gameStats) {
		if (isCardNumberOneSizeBiggerThanCardNumberOnPile(cardNumber, cardNumberOnTopOfPile)
				&& cardNumberOnTopOfBluePile != 5) {
			cardNumberOnTopOfBluePile++;
			increaseHintsCountIfCardPileIsComplete(cardNumberOnTopOfPile, gameStats);
			return true;
		}
		return false;
	}

	private boolean isCardNumberOneSizeBiggerThanCardNumberOnPile(int cardNumberValue, int cardNumberOnTopOfPile) {
		return ((cardNumberOnTopOfPile + 1) == cardNumberValue);
	}

	private void increaseHintsCountIfCardPileIsComplete(int cardNumberOnTopOfPile, GameStats gameStats) {
		if (cardNumberOnTopOfPile == 5) {
			gameStats.increaseHintsByOne();
		}
	}
}
