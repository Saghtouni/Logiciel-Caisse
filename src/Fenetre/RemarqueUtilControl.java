package Fenetre;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.ChecksDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RemarqueUtilControl extends BorderPane implements Initializable {
	private Connection DB = null;

	private ChecksDB checksdb = null;
	private String remarque;
	private UtilisateursControl utiliControl = null;
	
	@FXML
    private TextArea textRemarque;

    @FXML
    private Button bntSuprimer;

    @FXML
    private Button bntEnregistrer;

    public RemarqueUtilControl(Connection db) throws SQLException {
    	this.DB=db;
    	utiliControl = new UtilisateursControl(DB);
        checksdb = new ChecksDB(DB);
		  	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RemarqueUtilisateur.fxml"));
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
		
	  }
    
    @FXML
    void bntEnregistrerClicked(ActionEvent event) {
    	checksdb.setRemarque(utiliControl.getId_utilisateur(), textRemarque.getText());
    	Stage primaryStage = (Stage) bntEnregistrer.getScene().getWindow();
		primaryStage.close();
    }
    
    @FXML
    void bntSuprimerClicked(ActionEvent event) {
    	textRemarque.clear();
    	checksdb.setRemarque(utiliControl.getId_utilisateur(), textRemarque.getText());	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//textRemarque.setText(checksdb.getRemarque(utiliControl.getId_utilisateur()));	
	}

}
