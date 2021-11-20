package Fenetre;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EnregisterControl {

    @FXML
    private TextField textPseudo;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textMotPasse1;

    @FXML
    private TextField textMotPasse2;

    @FXML
    private Button bntEnregistrer;

    @FXML
    private Label LabelMessage;

    @FXML
    void bntEnregistrerClicked(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
		Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/Fenetre/Login.fxml"));
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add("/Fenetre/Fenetre.css");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		primaryStage.setResizable(false);
		primaryStage.show();
		
    }

}
