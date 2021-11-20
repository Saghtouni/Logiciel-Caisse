package Fenetre;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import DataBase.ConnectionDB;
import DataBase.UtilisateursDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objects.Utilisateurs;

public class ModifierUtilControl extends BorderPane implements Initializable  {
	private Connection DB = null;

	private UtilisateursDB utilDB = null;
	private Utilisateurs utilisateur;
	private UtilisateursControl utilControl = null;
	
    @FXML
    private Button bntModifier;

    @FXML
    private Label labelNom;

    @FXML
    private TextField textNom;

    @FXML
    private Label labelPrenom;

    @FXML
    private TextField textPrenom;

    @FXML
    private Label labelPseudo;

    @FXML
    private TextField textPseudo;

    @FXML
    private Label labelEmail;

    @FXML
    private TextField textPasse;

    @FXML
    private Label labelPasse;

    @FXML
    private TextField textEmail;

    @FXML
    private Label labelTelephone;

    @FXML
    private TextField textTelephone;

    @FXML
    private Label labelNaissance;

    @FXML
    private TextField textNaissance;

    @FXML
    private Label labelPays;

    @FXML
    private TextField textPays;

    @FXML
    private Label labelAdresse;

    @FXML
    private TextField textAdresse;

    @FXML
    private Label labelPostal;

    @FXML
    private TextField textPostal;

    @FXML
    private Label labelVille;

    @FXML
    private TextField textVille;
    
    public ModifierUtilControl(Connection db,  UtilisateursControl utilControl) throws SQLException {
    	this.DB=db;
    	 utilDB = new UtilisateursDB(DB);
    	 this.utilControl = utilControl;
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierUtilisateur.fxml"));
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);     
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
			

	  }
    
    @FXML
    void bntModifierClicked(ActionEvent event) {
    	utilDB.update(utilControl.getId_utilisateur(), textNom.getText(), textPrenom.getText(), textPseudo.getText(), textEmail.getText(), textPasse.getText(), Integer.parseInt(textTelephone.getText()), textNaissance.getText(), textPays.getText(), textAdresse.getText(), Integer.parseInt(textPostal.getText()), textVille.getText());
    	utilControl.refrech();
    	Stage primaryStage = (Stage) bntModifier.getScene().getWindow();
		primaryStage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		utilisateur = utilDB.get(utilControl.getId_utilisateur());
    	textNom.setText(utilisateur.getNom());
    	textPrenom.setText(utilisateur.getPrenom());
    	textPseudo.setText(utilisateur.getPseudo());
    	textEmail.setText(utilisateur.getEmail());
    	textTelephone.setText( Integer.toString(utilisateur.getTelephone()));
    	textPays.setText(utilisateur.getPays());
    	textAdresse.setText(utilisateur.getAdresse());
    	textPostal.setText(Integer.toString(utilisateur.getCode_postal()));
    	textNaissance.setText(utilisateur.getDate_naissance());
    	textVille.setText(utilisateur.getVille());
    	textPasse.setText(utilisateur.getMot_passe());
		
	}

}
