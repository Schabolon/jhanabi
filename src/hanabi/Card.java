package hanabi;

import java.io.Serializable;

import hanabi.message.ColorType;

public class Card implements Serializable {

	private static final long serialVersionUID = 1385655208456463264L;

	/**
	 * The position of the Card in a players hand.
	 */
	int position;

	/**
	 * The color of the card. Possible colors: yellow, red, green, blue, white
	 */
	ColorType color;
	
	/**
	 * The number "printed" onto the card
	 */
	int number;

	public Card(int position) {
		this.position = position;
	}

	public Card(int position, ColorType color) {
		this.position = position;
		this.color = color;
	}

	public Card(int position, ColorType color, int number) {
		this.position = position;
		this.color = color;
		this.number = number;
	}

	public Card(ColorType color, int number) {
		this.color = color;
		this.number = number;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public ColorType getColor() {
		return color;
	}

	public int getNumber() {
		return number;
	}

	public String toString() {
		return Integer.toString(this.getPosition());
	}

}
