package hanabi.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class OtherPlayerController {

	@FXML
	Text playerName;

	@FXML
	ImageView firstHandCard;
	@FXML
	ImageView secondHandCard;
	@FXML
	ImageView thirdHandCard;
	@FXML
	ImageView fourthHandCard;

	@FXML
	Text firstCardNumber;
	@FXML
	Text secondCardNumber;
	@FXML
	Text thirdCardNumber;
	@FXML
	Text fourthCardNumber;

	public void test(ActionEvent event) {
		// firstHandCard.setImage(new Image("Test.png"));
		System.out.println("Test");
	}

	public void setPlayerName(String playerName) {
		this.playerName.setText(playerName);
	}

	public void setFirstHandCard(ImageView firstHandCard) {
		this.firstHandCard = firstHandCard;
	}

	public void setSecondHandCard(ImageView secondHandCard) {
		this.secondHandCard = secondHandCard;
	}

	public void setThirdHandCard(ImageView thirdHandCard) {
		this.thirdHandCard = thirdHandCard;
	}

	public void setFourthHandCard(ImageView fourthHandCard) {
		this.fourthHandCard = fourthHandCard;
	}

	public void setFirstCardNumber(String firstCardNumber) {
		this.firstCardNumber.setText(firstCardNumber);
	}

	public void setSecondCardNumber(String secondCardNumber) {
		this.secondCardNumber.setText(secondCardNumber);
	}

	public void setThirdCardNumber(String thirdCardNumber) {
		this.thirdCardNumber.setText(thirdCardNumber);
	}

	public void setFourthCardNumber(String fourthCardNumber) {
		this.fourthCardNumber.setText(fourthCardNumber);
	}

	public ImageView getFirstHandCard() {
		return firstHandCard;
	}

	public ImageView getSecondHandCard() {
		return secondHandCard;
	}

	public ImageView getThirdHandCard() {
		return thirdHandCard;
	}

	public ImageView getFourthHandCard() {
		return fourthHandCard;
	}

}
