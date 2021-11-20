package Fenetre;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import DataBase.ProduitsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import objects.Produits;

public class ModifierProdControl extends BorderPane  implements Initializable{
	private Connection DB = null;

	private  FileChooser fichier;
	private  File fichierSelectionner;
	private Produits produit;
    private ProduitsDB produitsDB = null;
    private ObservableList<Produits> data = FXCollections.observableArrayList();
    private ProduitsControl produitsCot = null;
    
     
    @FXML
    private TextField textReference;

    @FXML
    private TextField textNom;

    @FXML
    private TextField textPrixA;

    @FXML
    private TextField textType;

    @FXML
    private TextField textPrixV;

    @FXML
    private TextField textQuantite;

    @FXML
    private TextField textFornisseur;

    @FXML
    private TextField textTVA;

    @FXML
    private TextField textCodeB;

    @FXML
    private TextField textImage;

    @FXML
    private TextField code_bare;
    
    @FXML
    private TextField textSousType;
    
    @FXML
    private ImageView imageProduits;

    @FXML
    private Button bntJoindre;

    @FXML
    private Button bntModifier;
    
    
    
    public ModifierProdControl(Connection db, ProduitsControl produitsCot ) {
    	this.DB=db;
    	this.produitsCot = produitsCot; 
    	produitsCot = new ProduitsControl(DB);
    	
    	 produitsDB = new ProduitsDB(DB);
		  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierProduits.fxml"));
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
			

	  }

    @FXML
    void bntJoindreClicked(ActionEvent event) {
    	fichier = new FileChooser();
    	fichier.setTitle("Sélectionner une image");
    	fichier.getExtensionFilters().addAll(new ExtensionFilter("Fichiers image", "*.bmp", "*.tiff" , "*.gif" , "*.png", "*.jpg"));
    	fichierSelectionner = fichier.showOpenDialog(bntJoindre.getScene().getWindow());
    	if(fichierSelectionner != null) {
    		imageProduits.setImage(new Image(fichierSelectionner.toURI().toString()));
    		textImage.setText(fichierSelectionner.toURI().toString());
    	}
    	else {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Boîte de dialogue d'information");
    		alert.setHeaderText("Regardez, une boîte de dialogue d'information");
    		alert.setContentText("Il faut sélectionner une image");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/img/its_icoTR.png"));
    		alert.showAndWait();
    	}
    }
    
    public void bntJoindre() {
        ImageView img =new ImageView("/img/iconeJoindre.png");
        img.setFitWidth(52);
        img.setFitHeight(35);
        bntJoindre.setGraphic(img);      
    }

    @FXML
    void bntModifierClicked(ActionEvent event) {
    	produitsDB.update(produitsCot.getIdProduit(), textReference.getText(), textNom.getText(), Double.parseDouble(textPrixA.getText()), Double.parseDouble(textPrixV.getText()), textType.getText(),Integer.parseInt(textQuantite.getText()), textFornisseur.getText(), Double.parseDouble(textTVA.getText()), textImage.getText(), code_bare.getText(), textSousType.getText());
    	Stage primaryStage = (Stage) bntModifier.getScene().getWindow();
		primaryStage.close();
		produitsCot.intialTable();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      produit = produitsDB.get(produitsCot.getIdProduit());
      
       	//textReference.setText(produit.getReference());
        textNom.setText(produit.getNom());
        textPrixA.setText(produit.getPrixAchat() +"");
        textPrixV.setText(produit.getPrixvente()+"");
        textType.setText(produit.getType());
        textQuantite.setText(produit.getQuantite() +"");
        textFornisseur.setText(produit.getFornisseur());
        textTVA.setText(produit.getTVA() +"");
        textImage.setText(produit.getImage());
        textSousType.setText(produit.getSousType());
        System.out.println("SOUTYPE:" +produit.getSousType());
        if(produit.getImage() != null)
        imageProduits.setImage(new Image(produit.getImage()));
        
        bntJoindre();
    }
}
