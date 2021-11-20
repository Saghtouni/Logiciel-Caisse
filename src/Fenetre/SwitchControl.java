package Fenetre;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import DataBase.UtilisateursDB;
import application.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Utilisateurs;

public class SwitchControl extends BorderPane implements Initializable {
	private Connection DB = null;

	private UtilisateursDB utilisateursdb = null;
	private static ObservableList<Utilisateurs> data;


	private int id_utilisateur;
	@FXML private CaisseControl caisse;
    @FXML
    private Label LabelEmail;

    @FXML
    private Label LabelPasse;
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Pane Pane;

    @FXML
    public TextField textEmailPseudo;

    @FXML
    public PasswordField textPasse;

    @FXML
    private Button bntLogin;

    @FXML
    private Hyperlink bntPasse;

    private LoginControl logincontrol = null;
    
   public SwitchControl(Connection db) throws SQLException {
	   this.DB=db;

       utilisateursdb = new UtilisateursDB(DB);
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("switchs.fxml"));
       fxmlLoader.setRoot(this);
       fxmlLoader.setController(this);

		try {
			logincontrol = new LoginControl(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       try {
           fxmlLoader.load();            
       } catch (IOException exception) {
           throw new RuntimeException(exception);
       }
		
    }
    
    @FXML
    void BntLoginClicked(ActionEvent event) throws SQLException  {
    	for(int i = 0; i < data.size();i++) {
    			if ((textEmailPseudo.getText().equals(data.get(i).getEmail()) || textEmailPseudo.getText().equals(data.get(i).getPseudo())) && textPasse.getText().equals(data.get(i).getMot_passe())) {
    				String name;
    				String lastName;
    				id_utilisateur = data.get(i).getId();
    				CaisseControl caisseControl = new CaisseControl(DB, null);
    				caisseControl.NameUser.setText(data.get(i).getNom());
    				caisseControl.nameUser = data.get(i).getNom();
    				System.out.println("NameUser:                                                                      " +caisseControl.nameUser );
    				caisseControl.prenomUser = data.get(i).getPrenom();
    				caisseControl.FirstNameUser.setText(data.get(i).getPrenom());
    				caisseControl.clearTable();
    				Stage primaryStage1 = (Stage) bntLogin.getScene().getWindow();
    				primaryStage1.close();	
    				Stage primaryStage = new Stage();
    				primaryStage.setScene(new Scene(caisseControl));
    				primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
    				Screen screen = Screen.getPrimary();
    				Rectangle2D bounds = screen.getVisualBounds();
    				primaryStage.setX(bounds.getMinX()-10);
    				primaryStage.setY(bounds.getMinY());
    				primaryStage.setWidth(bounds.getWidth()+20);
    				primaryStage.setHeight(bounds.getHeight()+10);
    				primaryStage.show();
    				logincontrol.setName(data.get(i).getNom());
    				logincontrol.setLastName(data.get(i).getPrenom());
    			}
    			else {
    				LabelEmail.setFont(Font.font("System", FontWeight.NORMAL, 18));
    				LabelEmail.setText("Votre email ou le pseudo ou le mot de passe n'est pas valide !");
    			}
   			}	
    	}

    @FXML
    void BntPasseCliked(ActionEvent event) {

    }

    @FXML
    void KeyPressedPan2(KeyEvent event) throws IOException, SQLException {
    	if (event.getCode().equals(KeyCode.ENTER)) {
    		for(int i = 0; i < data.size();i++) {
    			if ((textEmailPseudo.getText().equals(data.get(i).getEmail()) || textEmailPseudo.getText().equals(data.get(i).getPseudo())) && textPasse.getText().equals(data.get(i).getMot_passe())) {
    				id_utilisateur = data.get(i).getId();
    				CaisseControl caisseControl = new CaisseControl(DB, null);
    				caisseControl.NameUser.setText(data.get(i).getNom());
    				caisseControl.FirstNameUser.setText(data.get(i).getPrenom());
    				caisseControl.NameUser.setText(data.get(i).getNom());
    				caisseControl.nameUser = data.get(i).getNom();
    				caisseControl.clearTable();
    				Stage primaryStage1 = (Stage) bntLogin.getScene().getWindow();
    				primaryStage1.close();	
    				Stage primaryStage = new Stage();
    				primaryStage.setScene(new Scene(caisseControl));
    				primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
    				Screen screen = Screen.getPrimary();
    				Rectangle2D bounds = screen.getVisualBounds();
    				primaryStage.setX(bounds.getMinX()-10);
    				primaryStage.setY(bounds.getMinY());
    				primaryStage.setWidth(bounds.getWidth()+20);
    				primaryStage.setHeight(bounds.getHeight()+10);
    				primaryStage.show();
    			}
    			else {
    				LabelEmail.setFont(Font.font("System", FontWeight.NORMAL, 18));
    				LabelEmail.setText("Votre email ou le pseudo ou le mot de passe n'est pas valide !");
    			}
   			}
    	}
    }
    
 
    @Override
  	public void initialize(URL arg0, ResourceBundle arg1) {
    	LabelEmail.setText("");
   		LabelPasse.setText("");
  		data = utilisateursdb.load();	
  	}
  		
  		

}
