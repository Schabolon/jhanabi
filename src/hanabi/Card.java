package hanabi;

import java.io.Serializable;

import hanabi.message.ColorType;

public class Card implements Serializable {

	private static final long serialVersionUID = 1385655208456463264L;

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

	public void setPosition(int position) {
		this.position = position;
	}

	public ColorType getColor() {
		return color;
	}

	public int getNumberValue() {
		return numberValue;
	}

	public String toString() {
		return Integer.toString(this.getPosition());
	}

}
