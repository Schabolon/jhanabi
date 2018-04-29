package hanabi.gui;

import java.io.IOException;

import hanabi.client.v2.Client2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	TextField serverIp;

	@FXML
	TextField serverPort;

	Client2 client;

	@FXML
	public void connectToServer(ActionEvent event) {
		String serverPortString = serverPort.getText();
		int serverPort = Integer.parseInt(serverPortString);
		client = new Client2(serverIp.getText(), serverPort);
		client.connect();
		changeSceneToGameGui();
	}

	private void changeSceneToGameGui() {
		Stage stage;
		Parent root = null;
		stage = (Stage) serverIp.getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
			root = loader.load();
			GameController gameController = loader.getController();
			gameController.initialize(client, stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
