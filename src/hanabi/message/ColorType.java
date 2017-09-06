package hanabi.message;

import java.io.File;
import java.net.MalformedURLException;

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

	public static String getColorToCardImage(ColorType color) {
		String url = "Card_" + color.toString().toLowerCase() + ".png";
		File file = new File(url);
		try {
			return file.toURI().toURL().toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("FEHLER!");
		}
		return "";
	}
}