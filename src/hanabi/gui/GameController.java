package hanabi.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hanabi.Card;
import hanabi.Player;
import hanabi.client.v2.Client2;
import hanabi.message.ColorType;
import hanabi.message.Message;
import hanabi.message.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameController {

	@FXML
	HBox otherPlayerContainer;

	@FXML
	Pane personalPlayerContainer;

	List<OtherPlayerController> otherPlayersList = new ArrayList<>();
	List<String> playerNames;

	Client2 client;
	
	Stage stage;

	public void initialize(Client2 client, Stage stage) {
		this.client = client;
		this.stage = stage;
		this.client.setGameController(this);
		playerNames = client.getPlayerNames();

		System.out.println(client.getGameController().toString());

		this.client.listenToServer();
		
		//TODO start-Button hinzufügen
		this.client.sendMessage(new Message(MessageType.START));
	}

	public void updateCards(List<Card> cardList, Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				OtherPlayerController updateControllerForPlayerWithNewCards = otherPlayersList
						.get(player.getPlayerNumber());
				updateControllerForPlayerWithNewCards
						.setFirstCardNumber(Integer.toString(cardList.get(0).getNumberValue()));
				updateControllerForPlayerWithNewCards.getFirstHandCard()
						.setImage(new Image(ColorType.getColorToCardImage(cardList.get(0).getColor())));
				updateControllerForPlayerWithNewCards
						.setSecondCardNumber(Integer.toString(cardList.get(1).getNumberValue()));
				updateControllerForPlayerWithNewCards.getSecondHandCard()
						.setImage(new Image(ColorType.getColorToCardImage(cardList.get(1).getColor())));
				updateControllerForPlayerWithNewCards
						.setThirdCardNumber(Integer.toString(cardList.get(2).getNumberValue()));
				updateControllerForPlayerWithNewCards.getThirdHandCard()
						.setImage(new Image(ColorType.getColorToCardImage(cardList.get(2).getColor())));
				updateControllerForPlayerWithNewCards
						.setFourthCardNumber(Integer.toString(cardList.get(3).getNumberValue()));
				updateControllerForPlayerWithNewCards.getFourthHandCard()
						.setImage(new Image(ColorType.getColorToCardImage(cardList.get(3).getColor())));
			}
		});
	}

	public void createOtherPlayers(int count) {
		javafx.application.Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < count; i++) {
					Parent otherPlayers = null;
					OtherPlayerController otherPlayerController = null;
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("OtherPlayers.fxml"));
						otherPlayers = loader.load();
						otherPlayerController = loader.getController();
						otherPlayersList.add(otherPlayerController);
					} catch (IOException e) {
						e.printStackTrace();
					}
					otherPlayerContainer.getChildren().add(otherPlayers);
					otherPlayerController.setPlayerName("Player " + Integer.toString(i));
				}
				stage.sizeToScene();
				stage.centerOnScreen();
			}
		});
	}
}
