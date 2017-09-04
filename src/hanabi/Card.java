package hanabi;

import hanabi.Message.ColorType;

public class Card {

	int position;

	ColorType color;
	int numberValue;

	public Card(int position) {
		this.position = position;
	}

	public Card(int position, ColorType color) {
		this.position = position;
		this.color = color;
	}

	public Card(int position, ColorType color, int numberValue) {
		this.position = position;
		this.color = color;
		this.numberValue = numberValue;
	}

	public Card(ColorType color, int numberValue) {
		this.color = color;
		this.numberValue = numberValue;
	}

	public int getPosition() {
		return position;
	}

	public ColorType getColor() {
		return color;
	}

	public int getNumberValue() {
		return numberValue;
	}

}
