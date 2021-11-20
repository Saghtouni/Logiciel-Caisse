package Fenetre;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import DataBase.ProduitsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import objects.Produits;


public class EnregisterProControl extends BorderPane implements Initializable{
	
	private  FileChooser fichier;
	private  File fichierSelectionner;
	private Connection DB = null;
	private ProduitsDB produitsDB =null;
	private ObservableList<Produits> produits = null;
    private ProduitsControl produitsCot = null;
	private String image ;
	private int sizeProduits;
	private int idProduit;

    @FXML
    private TextField testNom;
    
    @FXML
    private TextField testReference;
    
    @FXML
    private TextField textPrixA;

    @FXML
    private TextField textPrixV;

    @FXML
    private TextField tesxtType;

    @FXML
    private TextField textQuantite;

    @FXML
    private TextField textFornisseur;

    @FXML
    private TextField textTVA;
  
    @FXML
    private TextField code_bare;
    
    @FXML
    private TextField tesxtSousType;

    @FXML
    private ImageView ImgaeProduit;

    @FXML
    private Button bntJoindre;

    @FXML
    private Button bntEnregister;

    public EnregisterProControl(Connection db, ProduitsControl produitsCot) {
    	this.DB=db;
    	this.produitsCot = produitsCot;
    	produitsDB = new ProduitsDB(DB);
    	produits = produitsDB.load();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnregistrerProduits.fxml"));
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
    	sizeProduits = produits.size(); // id produit 
    }

    @FXML
    void bntJoindreClicked(ActionEvent event) {
    	fichier = new FileChooser();
    	fichier.setTitle("Sélectionner une image");
    	fichier.getExtensionFilters().addAll(new ExtensionFilter("Fichiers image", "*.bmp", "*.tiff" , "*.gif" , "*.png", "*.jpg"));
    	fichierSelectionner = fichier.showOpenDialog(bntJoindre.getScene().getWindow());
    	if(fichierSelectionner != null) {
    		Image image1 = new Image(fichierSelectionner.toURI().toString());
    		image = fichierSelectionner.toURI().toString();
    		ImgaeProduit.setImage(image1);
    	}
    	else {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Dialogue d'information");
    		alert.setHeaderText("Regardez, une boîte de dialogue d'information");
    		alert.setContentText("Il faut sélectionner une image");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/img/its_icoTR.png"));
    		alert.showAndWait();
    	}
    }
    		
    @FXML
    void bntEnregisterClicked(ActionEvent event) {
    	DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String formatResult = decimalFormat.format(Double.parseDouble(textPrixA.getText()));
        double prix = Double.parseDouble(formatResult);
    	produitsDB.add(testReference.getText(), testNom.getText() , prix, Double.parseDouble(textPrixV.getText()), tesxtType.getText(), Integer.parseInt(textQuantite.getText()),   textFornisseur.getText() ,Double.parseDouble(textTVA.getText()), image, code_bare.getText(), tesxtSousType.getText());
    	
    	Stage primaryStage = (Stage) bntEnregister.getScene().getWindow();
		primaryStage.close();
		produitsCot.intialTable();
    	
   }
    
    public void bntJoindre() {
    	ImageView img =new ImageView("/img/iconeJoindre.png");
    	img.setFitWidth(52);
    	img.setFitHeight(35);
    	bntJoindre.setGraphic(img);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bntJoindre();
	}
}
