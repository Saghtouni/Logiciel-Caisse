package Fenetre;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.UtilisateursDB;
import application.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Utilisateurs;

public class LoginControl extends AnchorPane implements Initializable {
	private Connection DB = null;
	private UtilisateursDB utilisateursdb = null;
	private static ObservableList<Utilisateurs> data;
	private String emailLogin;
	private static int id_utilisateur;
	private static String name = null;
	private static String LastName = null;
	@FXML
    private Pane pane1;
    
    @FXML
    private Pane pan2;
    
    @FXML
    private Hyperlink buttonUrl;
    
    @FXML
    private Hyperlink bntPasse;

    @FXML
    private TextField textEmailPseudo;

    @FXML
    private PasswordField textPasse;

    @FXML
    private Button bntEnregistrer;

    @FXML
    private Button bntLogin;
    
    @FXML
    private Label LabelEmail;

    @FXML
    private Label LabelPasse;

    public LoginControl(Connection db) throws SQLException {
		this.DB=db;
    	utilisateursdb = new UtilisateursDB(DB);
    	//System.out.println(" user db "+ DB);
	  	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Loginn.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

  }
    @FXML
    void BntEnregistrerCliked(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
    	Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/Fenetre/Enregistrer.fxml"));
		Scene scene = new Scene(mainPane);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login ITS-CASH");
		primaryStage.show();
    }

    @FXML
    void BntLoginClicked(ActionEvent event) throws SQLException  { 	
    	LabelEmail.setText("");
   		LabelPasse.setText("");
   		for(int i = 0; i < data.size();i++) {
   			if ((textEmailPseudo.getText().equals(data.get(i).getEmail()) || textEmailPseudo.getText().equals(data.get(i).getPseudo())) && textPasse.getText().equals(data.get(i).getMot_passe())) {
   				id_utilisateur = data.get(i).getId();
   				Stage primaryStage = (Stage) bntLogin.getScene().getWindow();
   				primaryStage.close();
   				MenuControl menucontrol = new MenuControl(DB);
   				primaryStage.setScene(new Scene(menucontrol));
   				primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
   				primaryStage.setTitle("Menu ITS-CASH");
   				primaryStage.setResizable(false);
   				primaryStage.show();
   			}
   			else {
   				LabelEmail.setText("Votre email ou le pseudo");
   				LabelPasse.setText("ou le mot de passe n'est pas valide !");
   			}
   		}	
    }

    @FXML
    void BntPasseCliked(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
    	Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/Fenetre/MotPasse.fxml"));
		Scene scene = new Scene(mainPane);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		primaryStage.setResizable(false);;
		primaryStage.setTitle("Mot de passe oublier");
		primaryStage.show();
    }

    @FXML
    void ButtonUrlClicked(ActionEvent event) {
    	Stage primaryStage = new Stage();
    	AnchorPane mainPane = new AnchorPane();
		Scene scene = new Scene(mainPane);
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		engine.load("http://www.itsforward.ch");
		mainPane.getChildren().add(myWebView);
		myWebView.prefWidthProperty().bind(mainPane.widthProperty());
		myWebView.prefHeightProperty().bind(mainPane.heightProperty());
		//primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		primaryStage.setScene(scene);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX()-10);
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth()+20);
		primaryStage.setHeight(bounds.getHeight()+10);
		primaryStage.setTitle("Site ITSFORWARD");
		primaryStage.show();

    }
    
    @FXML
    void KeyPressedPan2(KeyEvent event) throws IOException, SQLException {
    	if (event.getCode().equals(KeyCode.ENTER)) {
        	LabelEmail.setText("a");
       		LabelPasse.setText("a");
       		for(int i = 0; i < data.size();i++) {
       			if ((textEmailPseudo.getText().equals(data.get(i).getEmail()) || textEmailPseudo.getText().equals(data.get(i).getPseudo())) && textPasse.getText().equals(data.get(i).getMot_passe())) {
       				id_utilisateur = data.get(i).getId();
       				id_utilisateur = data.get(i).getId();
       				Stage primaryStage = (Stage) bntLogin.getScene().getWindow();
       				primaryStage.close();
       				MenuControl menucontrol = new MenuControl(DB);
       				primaryStage.setScene(new Scene(menucontrol));
       				primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
       				primaryStage.setTitle("Menu ITS-CASH");
       				primaryStage.setResizable(false);
       				primaryStage.show();
       			}
       			else {
       				 LabelEmail.setText("Votre email ou le pseudo");
       				 LabelPasse.setText("ou le mot de passe n'est pas valide !");
       			}
       		}
    	}
   }
    
    public static int getId_utilisateur() {
		return id_utilisateur;
	}

    public void setName(String Name) {
    	name = Name;
    }
    public static String getName() {
    	if(name == null) {
    		for(int i = 0; i < data.size(); i++) {
    			if(data.get(i).getId() == id_utilisateur) {
    				name = data.get(i).getNom();
    				break;
    			}
    		}
    	} 
    	return name;
    }
    
    public void setLastName(String lastName) {
    	LastName = lastName;
    }
 
    public static  String getLastName() {
    	
    	if(LastName == null) {
    		for(int i = 0; i < data.size(); i++) {
    			if(data.get(i).getId() == id_utilisateur) {
    				LastName = data.get(i).getPrenom();
    				break;
    			}
    		}
    	}
    	return LastName;
    }
	public static void setId_utilisateur(int id_utilisateur) {
		LoginControl.id_utilisateur = id_utilisateur;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		data = utilisateursdb.load();	
		//System.out.println("after ");
	}
	
	
}
