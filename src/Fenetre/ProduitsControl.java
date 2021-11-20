package Fenetre;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import DataBase.ProduitsDB;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Produits;
import objects.Utilisateurs;

public class ProduitsControl extends BorderPane implements Initializable {
	private Connection DB = null;

		private ProduitsDB produitsdb = null;
		private Produits produit;
		private Image image;
	   	private static int idProduit;
	   	
	    @FXML
	    private ImageView imgChercher;
	    
		@FXML
	    private MenuBar MenuBar;

	    @FXML
	    private MenuItem MenuItemFermer;

	    @FXML
	    private Button bntAjouter;

	    @FXML
	    private Button bntModifier;

	    @FXML
	    private Button bntSuprimer;

	    @FXML
	    private Button bntActualiser;

	    @FXML
	    public TextField TextChercher;


	    @FXML
	    private TableView<Produits> TableProduits;

	    @FXML
	    private TableColumn<Produits, Integer> ColumnID;

	    @FXML
	    private TableColumn<Produits, String> ColumnReference;

	    @FXML
	    private TableColumn<Produits, String> ColumnNom;

	    @FXML
	    private TableColumn<Produits, Float> ColumPachat;

	    @FXML
	    private TableColumn<Produits, Float> ColumnPvente;

	    @FXML
	    private TableColumn<Produits, String> ColumnType;

	    @FXML
	    private TableColumn<Produits, String> ColumnQantite;

	    @FXML
	    private TableColumn<Produits, String> ColumnFornisseur;

	    @FXML
	    private TableColumn<Produits, Float> ColumnTVA;
	    
	    @FXML
	    private TableColumn<Produits, Float> ColumnSousType;
	    	
	    @FXML
	    private TextField TextNom;

	    @FXML
	    private TextField TextAchat;

	    @FXML
	    private TextField TextVente;

	    @FXML
	    private TextField TextType;

	    @FXML
	    private TextField TextQuantite;

	    @FXML
	    private TextField TextFornisseur;

	    @FXML
	    private TextField TextTVA;

	    @FXML
	    private TextField TextReference;
	    
	    @FXML
	    private Button btnImage;
	    
	    @FXML
	    private TextField TextCode;
	    
	    @FXML
	    private TextField TextSoutype;

	    public ProduitsControl(Connection db) {
	        this.DB=db;
	    	produitsdb = new ProduitsDB(DB);
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Produit.fxml"));
	        fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }		
	    

	     }
	    @FXML
	    void MouseClicked(MouseEvent event) {
	    	if(event.getButton().equals(MouseButton.PRIMARY)) {
	    		if (TableProduits.getSelectionModel().getSelectedItem().getImage() != null) {
	    		ImageView img =new ImageView(TableProduits.getSelectionModel().getSelectedItem().getImage());
		    	img.setFitWidth(241);
		    	img.setFitHeight(194);
		    	btnImage.setGraphic(img);
	    		}
	    		TextReference.setText(TableProduits.getSelectionModel().getSelectedItem().getReference());
	    		TextNom.setText(TableProduits.getSelectionModel().getSelectedItem().getNom());
	    		TextAchat.setText(TableProduits.getSelectionModel().getSelectedItem().getPrixAchat() +"");
	    		TextVente.setText(TableProduits.getSelectionModel().getSelectedItem().getPrixvente() +"");
	    		TextType.setText(TableProduits.getSelectionModel().getSelectedItem().getType());
	    		TextTVA.setText(TableProduits.getSelectionModel().getSelectedItem().getTVA() +"");
	    		TextFornisseur.setText(TableProduits.getSelectionModel().getSelectedItem().getFornisseur());
	    		TextSoutype.setText(TableProduits.getSelectionModel().getSelectedItem().getSousType());
	    		TextQuantite.setText(TableProduits.getSelectionModel().getSelectedItem().getQuantite()+"");
	    		idProduit = TableProduits.getSelectionModel().getSelectedItem().getId();
	    	}   	
	    }

	    @FXML
	    void bntActualiserClicked(ActionEvent event) {
	    	this.TableProduits.getItems().clear();
	    	this.TableProduits.setItems(produitsdb.load());
	    }
	    
	    

	    @FXML
	    void bntAjouterClicked(ActionEvent event) {
	    	EnregisterProControl enrgControl = new EnregisterProControl(DB, this);
			Stage primaryStage = new Stage();
			 primaryStage.setScene(new Scene(enrgControl));
		     primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		     primaryStage.setResizable(false);
		     primaryStage.setTitle("Ajouter produit");
		     primaryStage.show();
	    }

	    @FXML
	    void bntModifierClicked(ActionEvent event) {
	    	ModifierProdControl modifProduit = new ModifierProdControl(DB, this);
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(modifProduit));
		    primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		    primaryStage.setResizable(false);
		    primaryStage.setTitle("Modifier produit");
		    primaryStage.show();
	    }

	    @FXML
	    void bntSuprimerClicked(ActionEvent event) {  	
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Boîte de dialogue de confirmation");
	    	alert.setHeaderText("Regardez, une boîte de dialogue de confirmation");
	    	alert.setContentText("Voulez vous supprimer ce produit?");
	    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK) {
	    		produitsdb.delete(this.getIdProduit());
	    		intialTable();
	    	}
	    	else 
	    		alert.close();
	    }    
	    
	    @FXML
	    void InitialCherche(KeyEvent event) {
	    	/*FilteredList<Produits> filterData = new FilteredList<>(produitsdb.load(), p -> true);
	    	 TextChercher.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
	             filterData.setPredicate(pr -> {
	            	 String typedText = newvalue.toLowerCase();
	                 String objectvalues = pr.getType() + pr.getFornisseur() +pr.getNom() + pr.getReference();
	                 if (newvalue == null || newvalue.isEmpty()) 
	                	 return true;
	                 }
	                 
	                 if (objectvalues.toLowerCase().contains(typedText)) {
	                     return true;
	                 }
	               
	            
	                 return false;
	             });
	             SortedList<Produits> sortedList = new SortedList<>(filterData);
	             sortedList.comparatorProperty().bind(TableProduits.comparatorProperty());
	             //for(int i = 0, i < sortedList. )
	             //TableProduits.getItems().clear();
	             TableProduits.setItems(sortedList);
	         });*/
	    	
	     }
	    
	    public void bntAjouter() {
	    	ImageView img =new ImageView("/img/iconeAjouter.png");
	    	img.setFitWidth(95);
	    	img.setFitHeight(65);
	    	bntAjouter.setGraphic(img);
	    	
	    }
	    
	    public void bntSuprimer() {
	    	ImageView img =new ImageView("/img/iconeSuprimer.png");
	    	img.setFitWidth(95);
	    	img.setFitHeight(65);
	    	bntSuprimer.setGraphic(img);
	    	
	    }
	    public void bntModifier() {
	    	ImageView img =new ImageView("/img/iconeModifier.png");
	    	img.setFitWidth(95);
	    	img.setFitHeight(65);
	    	bntModifier.setGraphic(img);
	    	
	    }
	 
	    
	    public void intialTable() {
	    	this.TableProduits.getItems().clear();
	    	this.TableProduits.setItems( produitsdb.load());
	    }
	  
	    public int getIdProduit() {
			return idProduit;
		}

		public void setIdProduit(int idProduit) {
			this.idProduit = idProduit;
		}
		
	    public void initialize(URL arg0, ResourceBundle arg1) {
	    	TableProduits.setItems( produitsdb.load());
	    	ColumnID.setCellValueFactory(new PropertyValueFactory("id"));
	    	ColumnReference.setCellValueFactory(new PropertyValueFactory("Reference"));
	    	ColumnNom.setCellValueFactory(new PropertyValueFactory("Nom"));
	    	ColumPachat.setCellValueFactory(new PropertyValueFactory("prixAchat"));
	    	ColumnPvente.setCellValueFactory(new PropertyValueFactory("prixvente"));
	    	ColumnType.setCellValueFactory(new PropertyValueFactory("Type"));
	    	ColumnQantite.setCellValueFactory(new PropertyValueFactory("quantite"));
	    	ColumnFornisseur.setCellValueFactory(new PropertyValueFactory("Fornisseur"));
	    	ColumnTVA.setCellValueFactory(new PropertyValueFactory("TVA"));	
	    	ColumnSousType.setCellValueFactory(new PropertyValueFactory("sousType"));	
			bntAjouter(); 
			bntModifier();
			bntSuprimer();	
		}
	    
	    @FXML
		public void chercherProduit(KeyEvent event) throws SQLException
		{
			if(TextChercher.getText().toLowerCase().equals(""))
			{
				this.TableProduits.getItems().clear();
		    	this.TableProduits.setItems( produitsdb.load());
	    		
			}
			else
			{
				
				String s = TextChercher.getText().toLowerCase();
				this.TableProduits.getItems().clear();
		    	this.TableProduits.setItems( produitsdb.searchProduits(s));
		    	
			}
			
			
			
		}

}
