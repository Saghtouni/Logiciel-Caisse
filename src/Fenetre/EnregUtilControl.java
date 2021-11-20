package Fenetre;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.text.TabableView;

import DataBase.ChecksDB;
import DataBase.ConnectionDB;
import DataBase.UtilisateursDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objects.Utilisateurs;

public class EnregUtilControl extends BorderPane implements Initializable{
	  private Connection DB = null;
	  UtilisateursDB Utilisateursdb = null;
	  ChecksDB checcksdb = null;
	  ObservableList<Utilisateurs> utilisateur = null;
	  UtilisateursControl utilControl = null;
	  long idUtilsateur;
	  int sizeUtilisateur ;
	  int sizeCheck;
	  int nbr;
	
	  @FXML
	  private TextField textNom;

	  @FXML
	  private TextField textPrenom;

	  @FXML
	  private TextField textPseudo;

	  @FXML
	  private TextField textMail;

	  @FXML
	  private PasswordField textPasse;

	  @FXML
	  private PasswordField textPasse2;

	  @FXML
	  private TextField textTelephone;
	  
	  @FXML
	  private DatePicker dateNaissance;

	  @FXML
	  private TextField textPays;

	  @FXML
	  private TextField textAdresse;

	  @FXML
	  private TextField textPostal;
	  
	  @FXML
	  private TextField textVille;

	  @FXML
	  private Label labelTitre;

	  @FXML
	  private Button bntEnregistrer;
	  
	  TableView<Utilisateurs> tableUt;
	  
	  public EnregUtilControl(Connection db, UtilisateursControl utilControl ) throws SQLException {
		  this.DB=db;
		  this.utilControl = utilControl;
	        checcksdb = new ChecksDB(DB);
		  Utilisateursdb = new UtilisateursDB(DB);
		  utilisateur = Utilisateursdb.load();
		  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnregistrerUtilisateur.fxml"));
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
	        
	  }
	  
	  @FXML
	    void MouseInitialSize(MouseEvent event) {
		  sizeUtilisateur =  utilisateur.size();
	    }
	
	  
	  @FXML
	  void bntEnregistrerClicked(ActionEvent event) { 
		
			  idUtilsateur = Utilisateursdb.getLAstID() +1 ;
		  
		 if(isValid(textMail.getText())) {
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			  String Date = dateNaissance.getValue().format(formatter);
			  Utilisateursdb.add( textNom.getText(), textPrenom.getText(), textPseudo.getText(), textMail.getText(),  textPasse.getText(), Integer.parseInt(textTelephone.getText()), Date,  textPays.getText(), textAdresse.getText(),  Integer.parseInt(textPostal.getText()), textVille.getText());
			  utilisateur.add(new Utilisateurs(textNom.getText(), textPrenom.getText(), textPseudo.getText(), textMail.getText(),  textPasse.getText(), Integer.parseInt(textTelephone.getText()), Date,  textPays.getText(), textAdresse.getText(),  Integer.parseInt(textPostal.getText()), textVille.getText()));
			  checcksdb.add(idUtilsateur);
			  utilControl.refrech();
			  Stage primaryStage = (Stage) bntEnregistrer.getScene().getWindow();
			 
			  primaryStage.close();	 
		  }else {
			  System.out.println("email not valid");
		  }
		  
		 
	  }

	  public static boolean isValid(String email) 
	    { 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    } 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 utilisateur = Utilisateursdb.load();
		
	}

}
