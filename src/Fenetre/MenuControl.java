package Fenetre;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DataBase.ChecksDB;
import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.CheckMenu;
import objects.Utilisateurs;

public class MenuControl extends BorderPane implements Initializable{
	
	private Connection DB = null;
	private ChecksDB checksdb = null;
	private LoginControl logincontrol = null;
	private CheckMenu checkUtilisateur = null;
	
	@FXML
    private Button bntCommande;

	@FXML
	private Button bntSalaire;

	@FXML
    private Button bntFacture;

    @FXML
    private Button bntProduits;

    @FXML
    private Button bntUtilisateurs;

    @FXML
    private Button bntDevis;

    @FXML
    private Button bntAnalyse;

    @FXML
    private Button bntCaisee;
    
	public  MenuControl(Connection db) throws SQLException {
		
        this.DB=db;
        checksdb = new ChecksDB(DB);
        logincontrol = new LoginControl(DB);
        checkUtilisateur = checksdb.getCheckUtil(logincontrol.getId_utilisateur());
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu1.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

	}
    
    @FXML
    void bntAnalyseClicked(ActionEvent event) throws SQLException {
    	if(checkUtilisateur.getChecAnalyse() == 1) {
    		AnalyseControl analyse = new AnalyseControl(DB);
    		Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(analyse));
			primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX()-10);
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth()+20);
			primaryStage.setHeight(bounds.getHeight()+10);
			primaryStage.setTitle("Analyse ITS-CASH");
			primaryStage.show();
    	}
    	else {
    		ImageView img = new ImageView("/img/iconeCadena.png");
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		bntAnalyse.setGraphic(img);
    	}
    }

    @FXML
    void bntCaiseeClicked(ActionEvent event) throws SQLException {

    	if(checkUtilisateur.getChecCaisse() == 1) {
    		Stage primaryStage = new Stage();
			CaisseControl caisseControl = new CaisseControl(DB, null);
			primaryStage.setScene(new Scene(caisseControl));
			caisseControl.FirstNameUser.setText(logincontrol.getLastName());
			caisseControl.NameUser.setText(logincontrol.getName());
			primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
			//primaryStage.setFullScreen(true);
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX()-10);
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth()+20);
			primaryStage.setHeight(bounds.getHeight()+10);
			primaryStage.setTitle("Caisse ITS-CASH");
			primaryStage.show();
    	}
    	else {
    		ImageView img = new ImageView("/img/iconeCadena.png");
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		bntCaisee.setGraphic(img);
    	}

    }

    @FXML
    void bntDevisClicked(ActionEvent event) {
    
    		ImageView img = new ImageView("/img/Coming-Soon-Icon.png");
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		bntDevis.setGraphic(img);
    

    }

    @FXML
    void bntFactureClicked(ActionEvent event) {
    	if(checkUtilisateur.getChecFacture()== 1) {
    		FactureController factControl = new FactureController(DB);
    		Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(factControl));
			primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX()-10);
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth()+20);
			primaryStage.setHeight(bounds.getHeight()+10);
			primaryStage.setTitle("Facture ITS-CASH");
			primaryStage.showAndWait();
    	}
    	else {
    		ImageView img = new ImageView("/img/iconeCadena.png");
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		bntFacture.setGraphic(img);
    	}
    
    }
    
    @FXML
    void bntProduitsClicked(ActionEvent event) {
    	if(checkUtilisateur.getChecProduits() == 1) {
    		ProduitsControl produitsControl = new ProduitsControl(DB);
    		Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(produitsControl));
			primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX()-10);
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth()+20);
			primaryStage.setHeight(bounds.getHeight()+10);
			primaryStage.setTitle("Stock ITS-CASH");
			primaryStage.show();
    	}
    	else {
    		ImageView img = new ImageView("/img/iconeCadena.png");
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		bntProduits.setGraphic(img);
    		}	
    }

    @FXML
    void bntUtilisateursClicked(ActionEvent event) throws SQLException {
    	
    	if(checkUtilisateur.getChecUtilisateur() == 1) {
    		Stage primaryStage = new Stage();
			UtilisateursControl utiliControl = new UtilisateursControl(DB);
			primaryStage.setScene(new Scene(utiliControl));
			primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX()-10);
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth()+20);
			primaryStage.setHeight(bounds.getHeight()+10);
			primaryStage.setTitle("Utilisateur ITS-CASH");
			primaryStage.show();
    	}
    		else {
    			ImageView img = new ImageView("/img/iconeCadena.png");
    			img.setFitHeight(140);
    			img.setFitWidth(140);
    			bntUtilisateurs.setGraphic(img);
    		}
    }
    
    @FXML
    void bntSalaireClicked(ActionEvent event) {
    	if(checkUtilisateur.getSalaire() == 1) {	
			ReglageController regControl = new ReglageController(DB);
    		Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(regControl));
			primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Réglage ITS-CASH");
			primaryStage.show();
    	}
    	else {
    		ImageView img = new ImageView("/img/iconeCadena.png");
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		bntSalaire.setGraphic(img);
    		}
    }
    
    @FXML
    void bntCommandeClicked(ActionEvent event) {
    	
    	ImageView img = new ImageView("/img/Coming-Soon-Icon.png");
    	img.setFitHeight(140);
    	img.setFitWidth(140);
    	bntCommande.setGraphic(img);
    		
    
    }
    
    public void bntUtilisateur() {
    	ImageView img = new ImageView("/img/iconeUtilisateur.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntUtilisateurs.setGraphic(img);
		bntUtilisateurs.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntUtilisateurs.setText("Utilisateur");
    }
  
    public void bntFacture() {
    	ImageView img = new ImageView("/img/facture-icone.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntFacture.setGraphic(img);
		bntFacture.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntFacture.setText("Facture");
    }
    
    public void bntAnalyse() {
    	ImageView img = new ImageView("/img/indexAnalyse2.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntAnalyse.setGraphic(img);
		bntAnalyse.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntAnalyse.setText("Analyse");
    }
    public void bntDevis() {
    	ImageView img = new ImageView("/img/iconeSalaire.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntDevis.setGraphic(img);
		bntDevis.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntDevis.setText("Salaire");
    }
    
    public void bntProduits() {
    	ImageView img = new ImageView("/img/IconeStock.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntProduits.setGraphic(img);
		bntProduits.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntProduits.setText("Produit");
    }
    
    public void bntCaisse() {
    	ImageView img = new ImageView("/img/iconeCaisse.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntCaisee.setGraphic(img);
		bntCaisee.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntCaisee.setText("Caisse");
    }
    
    public void bntCommande() {
    	ImageView img = new ImageView("/img/iconeCommandeTelephone.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntCommande.setGraphic(img);
		bntCommande.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntCommande.setText("Commande");
		
    }
    
    public void bntSalaire(){
    	ImageView img = new ImageView("/img/Reglage.png");
    	img.setFitHeight(100);
		img.setFitWidth(100);
		bntSalaire.setGraphic(img);
		bntSalaire.setFont(Font.font("System", FontWeight.BOLD, 18));
		bntSalaire.setText("Réglage");
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bntFacture() ;
		bntUtilisateur();
		bntAnalyse();
		bntDevis();
		bntCaisse();
		bntCommande();
		bntSalaire();
		bntProduits();
		
	}

}
