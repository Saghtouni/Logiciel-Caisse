package Fenetre;



import DataBase.ChecksDB;
import DataBase.ConnectionDB;




import DataBase.UtilisateursDB;
import DialogueAlerte.DialogueAlerteSql;
import application.Main;
import objects.CheckMenu;
import objects.Utilisateurs;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.security.auth.callback.Callback;
import javax.swing.Action;
import javax.swing.JTable;



import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TooltipBuilder;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class UtilisateursControl extends BorderPane implements Initializable  {
	private Connection DB = null;
	private UtilisateursDB Utilisateursdb = null;
	private ChecksDB Checkdb = null;
	private Utilisateurs utilisateurs;
	private DialogueAlerteSql dialogueAlerteSql;
	private ObservableList<Utilisateurs> utilisateur;
	private ObservableList<CheckMenu> checkMenu;
	private int sizeUtilisateur;
	private static int id_utilisateur;

    @FXML
    private ImageView imageChercher;
    
	@FXML
	private Menu MenuFichier;

	@FXML
	private MenuItem MenuItemFermer;

	@FXML
	private Menu MenuOptions;

	@FXML
	private MenuItem MenuItemAjouter;

	@FXML
	private MenuItem MenuItemModifier;
 
	@FXML
	private MenuItem MenuItemSuprimer;

	@FXML
	private MenuItem MenuItemActualiser;

	@FXML
	private MenuItem MenuItemChercher;
	    
	@FXML
	private MenuItem MenuItemConcernant;

    
	@FXML
	public TableView<Utilisateurs> TableUtilisateur; // Table Utilisateurs !!

	@FXML
	public TableColumn<Utilisateurs, Integer> ColumnId;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnNom;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnPrenom;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnPseudo;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnEmail;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnMotPasse;

	@FXML
	public TableColumn<Utilisateurs, Integer> ColumnTelephone;

	@FXML
	public TableColumn<Utilisateurs, Date> ColumnNaissance;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnPays;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnAdresse;

	@FXML
	public TableColumn<Utilisateurs, Integer> ColumnPostal;

	@FXML
	public TableColumn<Utilisateurs, String> ColumnVille;

    @FXML
    private CheckBox checkFactures;

    @FXML
    private CheckBox checkDevis;

    @FXML
    private CheckBox checkProduits;

    @FXML
    private CheckBox checkAnalyse;

    @FXML
    private CheckBox checkUtilisateurs;

    @FXML
    private CheckBox checkCaisse;

    @FXML
    private CheckBox checkCommandes;

    @FXML
    private CheckBox checkSalaire;

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

    @FXML
    private Label labelRemarque;

    @FXML
    private TextArea textRemarque;

    @FXML
    private Button bntAjouter;

    @FXML
    private Button bntSuprimer;

    @FXML
    private Button bntModifier;
    
    @FXML
    private Button bntRemarque;

    @FXML
    private Button bntActualiser;

    @FXML
    private TextField textcherche;

    @FXML
    private Button bntCherche;

    @FXML
    private Tooltip TooltipAjouter;

    @FXML
    private Tooltip TooltipModifier;

    @FXML
    private Tooltip TooltipSuprimer;

    @FXML
    private Tooltip TooltipRemarque;

    @FXML
    private Tooltip TooltipActualiser;
    
    @FXML
    private Tooltip TooltipTextRecherche;

    @FXML
    private Tooltip TooltipBntRecharche;
    

    @FXML
    private ImageView imageFacture;

    @FXML
    private ImageView imageSalaire;

    @FXML
    private ImageView imageProduit;

    @FXML
    private ImageView imageAnalyse;

    @FXML
    private ImageView imageUtilisateur;

    @FXML
    private ImageView imageCaisse;

    @FXML
    private ImageView imageCommande;

    @FXML
    private ImageView imageReglage;

    

    public UtilisateursControl(Connection db) throws SQLException {
    	this.DB=db;
    	//System.out.println("test "+DB);
    	//System.out.println("db from utilcontrol"+DB);
		Utilisateursdb = new UtilisateursDB(DB);
        Checkdb = new ChecksDB(DB);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Utilisateur.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
		
		
     }

	public UtilisateursDB getUtilisateursdb() {
		return Utilisateursdb;
	}

    
    @FXML
    void MouseClicked(MouseEvent event) {
    	
    	if(event.getButton().equals(MouseButton.PRIMARY) && (TableUtilisateur.getSelectionModel().getSelectedItem() != null)) {
    		
    	  id_utilisateur = TableUtilisateur.getSelectionModel().getSelectedItem().getId();
    	  checkMenu = Checkdb.load();
    	  for(int i = 0; i < utilisateur.size(); i++) {
    		  if( utilisateur.get(i).getId() == id_utilisateur) {
    			  textNom.setText(utilisateur.get(i).getNom());
    			  textPrenom.setText(utilisateur.get(i).getPrenom());
    			  textPseudo.setText(utilisateur.get(i).getPseudo());
    			  textEmail.setText(utilisateur.get(i).getEmail());
    			  textPasse.setText(utilisateur.get(i).getMot_passe());
    			  textTelephone.setText(utilisateur.get(i).getTelephone()+ "");
    			  textNaissance.setText(utilisateur.get(i).getDate_naissance()+ "");
    			  textPays.setText(utilisateur.get(i).getPays());
    			  textAdresse.setText(utilisateur.get(i).getAdresse());
    			  textPostal.setText(utilisateur.get(i).getCode_postal()+ "");
    			  textVille.setText(utilisateur.get(i).getVille()+ "");
    			  textRemarque.setText(Checkdb.getRemarque(id_utilisateur));
    			  break;
    		  }
    	  }
   		  	for (int i = 0; i < checkMenu.size(); i++) {
   		  		if(TableUtilisateur.getSelectionModel().getSelectedItem().getId() == checkMenu.get(i).getId() ) {
   		  			if(checkMenu.get(i).getChecFacture() == 1) {
   		  				checkFactures.setSelected(true);
   		  			}
   		  			else {
   		  				checkFactures.setSelected(false);
   		  			}
   		  			
   		  			if(checkMenu.get(i).getChecDevis() == 1) {
		  				checkDevis.setSelected(true);
		  			}
		  			else {
		  				checkDevis.setSelected(false);
		  			}
   		  			
   		  			if(checkMenu.get(i).getChecProduits() == 1) {
		  				checkProduits.setSelected(true);
		  			}
		  			else {
		  				checkProduits.setSelected(false);
		  			}
   		  			
   		  			if(checkMenu.get(i).getChecAnalyse() == 1) {
		  				checkAnalyse.setSelected(true);
		  			}
		  			else {
		  				checkAnalyse.setSelected(false);
		  			}
   		  			
   		  			if(checkMenu.get(i).getChecUtilisateur() == 1) {
		  				checkUtilisateurs.setSelected(true);
		  			}
		  			else {
		  				checkUtilisateurs.setSelected(false);
		  			}
   		  			
   		  			if(checkMenu.get(i).getChecCaisse() == 1) {
		  				checkCaisse.setSelected(true);
		  			}
		  			else {
		  				checkCaisse.setSelected(false);
		  			}
   		  			
   		  			if(checkMenu.get(i).getCommande() == 1) {
		  				checkCommandes.setSelected(true);
		  			}
		  			else {
		  				checkCommandes.setSelected(false);
		  			}
   		  			
   		  			if(checkMenu.get(i).getSalaire() == 1) {
		  				checkSalaire.setSelected(true);
		  			}
   		  			else {
		  				checkSalaire.setSelected(false);
		  			}
   		  		}
   		  	}
      	}
    }
   
    @FXML
    void bntActualiserClicked(ActionEvent event) {
    	this.TableUtilisateur.getItems().clear();
    	this.TableUtilisateur.setItems(utilisateur());
    	checkMenu = Checkdb.load();
    }

    @FXML
    void bntAjouterClicked(ActionEvent event) throws SQLException {
    	
    	EnregUtilControl enrUtilisateur = new EnregUtilControl(DB, this );
		Stage primaryStage = new Stage();
		primaryStage.setScene(new Scene(enrUtilisateur));
	    primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
	    primaryStage.setResizable(false);
	    primaryStage.setTitle("Ajouter utilisateur");
	    primaryStage.show();
    }

    @FXML
    void bntModifierClicked(ActionEvent event) throws SQLException {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {	
    		ModifierUtilControl modifUtilisateur = new ModifierUtilControl(DB, this);
    		Stage primaryStage = new Stage();
    		primaryStage.setScene(new Scene(modifUtilisateur));
    	    primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
    	    primaryStage.setTitle("Modifier Utilisateur");
    	    primaryStage.setResizable(false);
    	    primaryStage.show();
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Modifier utilisateur");
			alert.setHeaderText("Résultat");
			alert.setContentText("il faut sélectionner un utilisateur!");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/img/its_icoTR.png"));
			alert.showAndWait();
    	}

    }

    @FXML
    void bntSuprimerClicked(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	Alert alert1 = new Alert(AlertType.ERROR);
    	alert.setTitle("Boîte de dialogue de confirmation");
    	alert.setHeaderText("Regardez, une boîte de dialogue de confirmation");
    	alert.setContentText("Voulez vous supprimer ce utilisateur?");
    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
        		Utilisateursdb.delete(this.getId_utilisateur());
        		Checkdb.delete(this.getId_utilisateur());
        		refrech();
        	}
        	else {
    			alert1.setTitle("Suprimer utilisateur");
    			alert1.setHeaderText("Résultat");
    			alert1.setContentText("il faut sélectionner un utilisateur!");
    			Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
    			stage1.getIcons().add(new Image("/img/its_icoTR.png"));
    			alert1.showAndWait();
        	}
    	}
    	else {
    		alert1.close();
    	}
    	
    }
    
    @FXML
    void bntRemarqueClicked(ActionEvent event) throws SQLException {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		RemarqueUtilControl remarqueUtil = new RemarqueUtilControl(DB);
    		refrech();
    		Stage primaryStage = new Stage();
    		primaryStage.setScene(new Scene(remarqueUtil));
    	    primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
    	    primaryStage.setResizable(false);
    	    primaryStage.setTitle("Ajouter remarque utilisateur");
    	    primaryStage.show();
    	}
    	
    	else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Ajouter remarque");
    			alert.setHeaderText("Résultat");
    			alert.setContentText("il faut sélectionner un utilisateur!");
    			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    			stage.getIcons().add(new Image("/img/its_icoTR.png"));
    			alert.showAndWait();
    	}

    }
    
    
    @FXML
    void bntChercheClicked(ActionEvent event) {
    
    }
    
    
    @FXML
    void checkCaisseChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkCaisse.isSelected()  ) 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkcaisse");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checkcaisse");		
    	}
    	else {   		
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();  		
    	}
    }

    @FXML
    void checkCommandesChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkCommandes.isSelected()  ) 
     			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkcommande");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checkcommande");
    	}
    	else {	
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();	
    	}
    }

    @FXML
    void checkDevisChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkDevis.isSelected()  ) 
     			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkdevis");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checkdevis");
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();	
    	}
    }

    @FXML
    void checkFacturesChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkFactures.isSelected()  ) 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkfacture");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(), 0, "checkfacture");
    	}
    	else {		
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();
    		
    	}  	   	
    }

    @FXML
    void checkProduitsChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkProduits.isSelected()  ) 	
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkproduits");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checkproduits");
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();
    	}
    }

    @FXML
    void checkSalaireChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkSalaire.isSelected()  ) 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checksalaires");
    	
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checksalaires");
    	}
    	else {	
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();
    	}
    }

    @FXML
    void chekAnalyseChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkAnalyse.isSelected()  )
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkanalyse");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checkanalyse");
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();
    	}
    }

    @FXML
    void chekUtilisateursChecked(ActionEvent event) {
    	if(TableUtilisateur.getSelectionModel().getSelectedItem() != null) {
    		if(checkUtilisateurs.isSelected()  ) 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),1, "checkutilisateur");
    		else 
    			Checkdb.update(TableUtilisateur.getSelectionModel().getSelectedItem().getId(),0, "checkutilisateur");
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Acces Factures");
            alert.setHeaderText("Résultat");
            alert.setContentText("il faut sélectionner un utilisateur!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    	stage.getIcons().add(new Image("/img/its_icoTR.png"));
            alert.showAndWait();	
    	}
    }
    
    @FXML
    void itemActualiserClicked(ActionEvent event) {
    	this.TableUtilisateur.getItems().clear();
    	this.TableUtilisateur.setItems(utilisateur());
    }
    public void refrech() {
    	this.TableUtilisateur.getItems().clear();
    	this.TableUtilisateur.setItems(utilisateur());
    }

   
    ObservableList<Utilisateurs> utilisateur(){
    	utilisateur = Utilisateursdb.load();
   		ColumnId.setCellValueFactory(new PropertyValueFactory("Id"));
   		ColumnNom.setCellValueFactory(new PropertyValueFactory("Nom"));
   		ColumnPrenom.setCellValueFactory(new PropertyValueFactory("Prenom"));
   		ColumnPseudo.setCellValueFactory(new PropertyValueFactory("Pseudo"));
   		ColumnEmail.setCellValueFactory(new PropertyValueFactory("Email"));
   		ColumnMotPasse.setCellValueFactory(new PropertyValueFactory("mot_passe"));
   		ColumnTelephone.setCellValueFactory(new PropertyValueFactory("Telephone"));
   		//ColumnNaissance.setCellValueFactory(new PropertyValueFactory("date_naissance"));
   		//ColumnPays.setCellValueFactory(new PropertyValueFactory("Pays"));
   		//ColumnAdresse.setCellValueFactory(new PropertyValueFactory("Adresse"));
   		//ColumnPostal.setCellValueFactory(new PropertyValueFactory("Code_postal"));
   		//ColumnVille.setCellValueFactory(new PropertyValueFactory("Ville"));
   		//ColumnVille.getStyleClass().add("Times New Roman,100");
   		//sizeUtilisateur = utilisateur.size();
   		return utilisateur;   	   	
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
    public void bntCherche() {
    	ImageView img =new ImageView("/img/iconecherche.png");
    	img.setFitWidth(95);
    	img.setFitHeight(65);
    	//bntCherche.setGraphic(img);
    	
    }
 
    public void bntRemarque() {
    	ImageView img =new ImageView("/img/iconeRemarque.png");
    	img.setFitWidth(95);
    	img.setFitHeight(65);
    	bntRemarque.setGraphic(img); 	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//System.out.println("apple iniitializze "+DB);
		checkMenu = Checkdb.load();
		TableUtilisateur.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	
		TableUtilisateur.setItems(utilisateur());
		bntAjouter(); 
		bntCherche();
		bntModifier();
		bntRemarque();
		bntSuprimer();

	    imageFacture.setImage(new Image("/img/facture-icone.png"));
	    imageSalaire.setImage(new Image("/img/iconeSalaire.png"));
	    imageProduit.setImage(new Image("/img/IconeStock.png"));
	    imageAnalyse.setImage(new Image("/img/indexAnalyse2.png"));
	    imageUtilisateur.setImage(new Image("/img/iconeUtilisateur.png"));
	    imageCaisse.setImage(new Image("/img/iconeCaisse.png"));
	    imageCommande.setImage(new Image("/img/iconeCommandeTelephone.png"));
	    imageReglage.setImage(new Image("/img/Reglage.png"));
	    imageChercher.setImage(new Image("/img/iconecherche.png"));
	}
	
	
	
	
	@FXML
	public void chercherUtilisateur(KeyEvent event) throws SQLException
	{
		if(textcherche.getText().toLowerCase().equals(""))
		{
			this.TableUtilisateur.getItems().clear();
			this.TableUtilisateur.setItems(utilisateur());
    		checkMenu = Checkdb.load();
		}
		else
		{
			
			String s = textcherche.getText().toLowerCase();
			this.TableUtilisateur.getItems().clear();
	    	this.TableUtilisateur.setItems(Utilisateursdb.searchUser(s));
	    	checkMenu = Checkdb.load();
		}
		
		
		
	}

	public static int getId_utilisateur() {
		return id_utilisateur;
	}

	public static void setId_utilisateur(int id_utilisateur) {
		UtilisateursControl.id_utilisateur = id_utilisateur;
	}
	 
}
