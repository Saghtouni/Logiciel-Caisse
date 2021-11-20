package Fenetre;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.ReglageDB;
import DataBase.VenteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class paiementControl extends AnchorPane implements Initializable {
	private String message;
	private String somme;

    @FXML
    private Label labelMessage;

    @FXML
    private Label labelSomme;

    @FXML
    private Button bntok;

	public paiementControl()  {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Paiement.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		//this.message = labelMessage;
		//this.somme = labelSomme;

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}
	
    @FXML
    void bntokClicked(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//labelMessage.setText(message);
		//labelSomme.setText(somme);
		
	}

}
