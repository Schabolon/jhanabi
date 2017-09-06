package hanabi.message;

public enum ColorType {
	RED, YELLOW, GREEN, BLUE, WHITE, UNKNOWN;

	public static ColorType getColorFromString(String color) {
		switch (color.toLowerCase()) {
		case "red":
			return RED;
		case "yellow":
			return YELLOW;
		case "green":
			return GREEN;
		case "blue":
			return BLUE;
		case "white":
			return WHITE;
		default:
			return UNKNOWN;
		}

	}
}