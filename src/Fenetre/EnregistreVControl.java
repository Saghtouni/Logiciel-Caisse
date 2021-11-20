package Fenetre;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import DataBase.ProduitsDB;
import DataBase.UtilisateursDB;
import DataBase.VenteDB;
import application.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Produits;
import objects.Utilisateurs;
import objects.Vente;

public class EnregistreVControl  extends BorderPane implements Initializable  {
	
    	
		ObservableList<Vente> vente = FXCollections.observableArrayList();
		private Connection DB = null;
		VenteDB dbVente = null;
		Double tva1 = 0.0;
		private ProduitsDB produitsdb = null;
		private TableControler tableContr = null;
		private Produits produit;
		private Produits produitTable;
		private ObservableList<Produits> produits = FXCollections.observableArrayList();
		private ObservableList<String> type = FXCollections.observableArrayList();
		private ObservableList<Button> butType = FXCollections.observableArrayList();
		private ObservableList<String> nomProduit = FXCollections.observableArrayList();
		
		ObservableList<Vente> item = FXCollections.observableArrayList();
		
		static ObservableList<Vente> item1 = FXCollections.observableArrayList();
		static ObservableList<Double> tva = FXCollections.observableArrayList();
		
		private int nbTypeC;
		private int nbTypeL;
		private double BUTTONS_PER_LINE = 8;
		private double NUM_BUTTON_LINES = 8;
    	private double BUTTON_PADDING   = 5;
		private int nbProduitTypeC;
		private int nbProduitTypeL;
		private static float prixFinal;
		public static Double somme;
		private int minute;
		private int hour;
		private LoginControl loginControl;
		private int second;
		public ImageView ImageUser;
		public static String nameUser;
		public static String prenomUser;
		public static String dateHeure;

		@FXML
	    public GridPane gridPane4;
		
		@FXML
		public GridPane gridPane3;
		
	    @FXML
	    BorderPane borderPane;
	    
		@FXML
		public Label NameUser;

		@FXML
		public Label FirstNameUser;

	    @FXML
	    public Label timeNow;
	    
		@FXML
		public ImageView ImageLogoText;
		
	   

	    @FXML
	    public Button butdeux;
	
	    @FXML
	    public Button butSix;
	
	    @FXML
	    public Button buthuit;
	
	    @FXML
	    public Button butZero;
	
	    @FXML
	    public Button butcinq;
	
	    @FXML
	    public Button butPoint;
	
	    @FXML
	    public Button butCL;
	
	    @FXML
	    public Button butQuatre;
	
	    @FXML
	    public Button butNeuf;
	
	    @FXML
	    public Button butUn;
	
	    @FXML
	    public Button butTrois;
	
	    @FXML
	    private Button butSept;
	
	    @FXML
	    private Button bntSup;
	
	    @FXML
	    private Button butBillet10;
	
	    @FXML
	    private Button butBillet20;
	
	    @FXML
	    private Button butBillet50;
	
	    @FXML
	    private Button butBillet100;
	
	    @FXML
	    private Button butBillet200;
	
	    @FXML
	    public Button bntCarteCad;
	
	    @FXML
	    public Button bntPlus;
	
	    @FXML
	    public Button bntPour;
	
	    @FXML
	    public Button bntMoins;
	
	    @FXML
	    public Button bntCash;
	
	    @FXML
	    public Button bntCarte;
	 
	    @FXML
	    public AnchorPane paneBnt;

	    @FXML
	    public Button bntproduits;
	  
	    @FXML
	    public TableView<Vente> tableCaisse;
	    
	    @FXML
	    public TableColumn<Vente, String> columnProduit;
	    
	    @FXML
	    public TableColumn<Vente, Double> columnPrix;

	    @FXML
	    public TableColumn<Vente, Integer> columnQuantite;
	    
	    @FXML
	    public ScrollPane scrollPane;
	    
	    @FXML
	    public ScrollPane scrollPane1;
	    
	    @FXML
	    public TextField AfficcheurSomme;
	    
	    @FXML
	    private TextField textTest;
	    
		public  EnregistreVControl(Connection db) {
			
			 this.DB=db;
			 produitsdb = new ProduitsDB(DB);
			// tableContr = new TableControler(DB);
			dbVente = new VenteDB(DB);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnregisterVente.fxml"));
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }

		}
	   
	    
	    @FXML
	    void MousTableClicked(MouseEvent event) { 	
	    	if(tableCaisse.getSelectionModel().getSelectedItem() != null) 
    			produitTable = produitsdb.getName(tableCaisse.getSelectionModel().getSelectedItem().getNomProduits());	
	    }
	    
	 
	    @FXML
	    
	    void bntCashClicked(ActionEvent event) throws IOException {
	    	int id;
	    	int numeroVente;
	    	int quantite;
	    	Double tvaP = 0.0;
	    	Double tvaC = 0.0;
	    	Double prix;
	    	
	    	System.out.println("nom: " + NameUser.getText() +"\n" + "prenom :" +FirstNameUser.getText());
	    	if(item.isEmpty())
	    		return;
	    	else {
	    		if(vente.size() == 0) {
	    			id = 1;
	    			numeroVente = 1;
	    		}
	    		else {
	    			id = vente.get(vente.size() - 1).getId() + 1;
	    			numeroVente = vente.get(vente.size() - 1).getNumeroVente() + 1;
	    		}
	    	
	    		for(int i = 0; i <item.size(); i++ ) {
	    			quantite = item.get(i).getQunatite();
	    			prix = item.get(i).getPrixVente();
	    			tvaP =  item.get(i).getTVA();
	    			tvaC = (double) Math.round(((prix * tvaP) / 100 ) * 100) / 100;
	    			System.out.println("tvaP: " + tvaP);
	    			//dbVente.addProduitsV( item.get(i).getNomProduits(), quantite, prix, tvaP, tvaC, numeroVente);
	    			tva1 += tvaC;
	    			System.out.println("tva: " + tva1);
	    			id++;			
	    		}
	    		
	    		tva1 = (double) Math.round(tva1 * 100) / 100;
	    //		dbVente.addVente(numeroVente, timeNow.getText(), NameUser.getText(), FirstNameUser.getText(),"Tickets: "+ String.valueOf(numeroVente), tva1,Double.parseDouble(AfficcheurSomme.getText()),"Cash");
	    	}
	    
	    }
	    
	 
	 
	    public void bntCarte() {
	    	ImageView img =new ImageView("/img/IconeCarteBancaire.png");
	    	img.setFitWidth(136);
	    	img.setFitHeight(94);
	    	bntCarte.setGraphic(img);
	    	
	    }
	    
	    

	    public void bntCash() {
	    	ImageView img =new ImageView("/img/Iconecashfinal.png");
	    	img.setFitWidth(136);
	    	img.setFitHeight(94);
	    	bntCash.setGraphic(img);
	    	
	    }

	    public void loadType(int nbr) {
	    	GridPane grid = new GridPane();
	    	int nb = 0;
	    	int div = 5;
	    	int nbBut;
	    	if(nbr != 0 && nbr != 5)
	    		div = nbr +1;
	    	else if (nbr == 0)
	    		div = 1;
	    	
	    	nbTypeC = type.size();
	    	nbTypeL =  nbTypeC / div;
	    	
	    	if(nbTypeC % div != 0) 
	    		nbTypeL += 1;
	    	
	    	nbBut = nbTypeC -1;
	        grid.setPadding(new Insets(BUTTON_PADDING));
	        grid.setHgap(BUTTON_PADDING);
	        grid.setVgap(BUTTON_PADDING);
	        
	        for (int r = 0; r < nbTypeL; r++) {
	            for (int c = 0; c < div; c++) {
	                 Button button = new Button(type.get(nbBut--).toString());
	                 loadProduitsType(button ,div);
	                 button.setPrefWidth(150);
	                 button.setPrefHeight(70);
	                 button.setStyle("-fx-background-color: rgba(0,0,0,0),  linear-gradient(#000000,#000000),linear-gradient(#FFFFFF 0%,#FFFFFF 10%, #FFFFFF 50%, #FFFFFF 51%, #FFFFFF 100%);-fx-font-weight: bold;-fx-font-size: 24;");
	                 butType.add(button);
	                 grid.add(button, c, r);
	                 nb++;
	                 if(nb == nbTypeC)
	                	  break;
	             }
	         }   
	         scrollPane.setContent(grid);
	    }
	    
	    
	   public void loadProduitsType(Button button, int nbr) {
		   button.setOnAction(new EventHandler<ActionEvent>() {
    			public void handle(ActionEvent e) {
    				GridPane grid1 = new GridPane();
    				int div = 5;
    		    	if(nbr != 0)
    		    		div = nbr;
    		    	else if (nbr == 0)
    		    		div = 1;
    		    	
    				int nb1 = 0;
    				int nbBut1;
    				
    				for(int i = 0; i < produits.size(); i++) {
    					if(produits.get(i).getType().equals(e.getSource().toString().substring(34).replace("'", "")))
    						nomProduit.add(produits.get(i).getNom());
    				}
    				
    		    	nbProduitTypeC = nomProduit.size();
    		    	nbProduitTypeL =  nbProduitTypeC / div;
    		    	
    		    	if(nbProduitTypeC % div != 0) 
    		    		nbProduitTypeL += 1;
    		    	
    		    	nbBut1 = nbProduitTypeC -1;
    		    	
    		        grid1.setPadding(new Insets(BUTTON_PADDING));
    		    	grid1.setHgap(BUTTON_PADDING);
    		       	grid1.setVgap(BUTTON_PADDING);
    		       	
    		        for (int r = 0; r < nbProduitTypeL; r++) {
    		            for (int c = 0; c < div; c++) {
    		            	 int nbr = nbBut1--;
    		                 Button button = new Button(nomProduit.get(nbr).toString());
    		                 loadProduitTable(button, nomProduit.get(nbr) );
    		                 button.setPrefWidth(150);
    		                 button.setPrefHeight(70);
    		                 button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
    		                 grid1.add(button, c, r);
    		                 scrollPane1.setContent(grid1);
    		                 nb1++;
    		                 if(nb1 == nbProduitTypeC) 
    		                	  break;
    		             }
    		         }
    		       nomProduit.clear();   
    		    }		 
    		}); 
	   }
	   
	 
	   public void loadProduitTable(Button button, String name) {
		   button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					for(int i = 0; i < produits.size(); i++) {
    					if(produits.get(i).getNom().equals(name)){
    						produitTable = produits.get(i);
    						break;
    					}
					}
				//	item.add(new Vente(0, produitTable.getNom(), produitTable.getPrixvente(), 1, produitTable.getTVA(), (produitTable.getTVA()*produitTable.getPrixvente()) / 100, 0));
					prixFinal = (float) Math.round((prixFinal + produitTable.getPrixvente()) * 100) / 100; 
					tableCaisse.setItems(item);	
					AfficcheurSomme.setText(""+prixFinal+"");
					somme = Double.parseDouble(AfficcheurSomme.getText());
				} 
            });
	   }

	    @FXML
	    void bntPourClicked(MouseEvent event) {
	    	tableCaisse.getItems().clear();
	    }
	    
	    @FXML
	    void bntCarteCadClicked(ActionEvent even) {
	    	int id;
	    	int numeroVente;
	    	int quantite;
	    	Double tvaP = 0.0;
	    	Double tvaC = 0.0;
	    	Double prix;
	    	System.out.println("nom: " + NameUser.getText() +"\n" + "prenom :" +FirstNameUser.getText());
	    	if(item.isEmpty())
	    		return;
	    	else {
	    		if(vente.size() == 0) {
	    			id = 1;
	    			numeroVente = 1;
	    		}
	    		else {
	    			id = vente.get(vente.size() - 1).getId() + 1;
	    			numeroVente = vente.get(vente.size() - 1).getNumeroVente() + 1;
	    		}
	    	
	    		for(int i = 0; i <item.size(); i++ ) {
	    			quantite = item.get(i).getQunatite();
	    			prix = item.get(i).getPrixVente();
	    			tvaP =  item.get(i).getTVA();
	    			tvaC = (double) Math.round(((prix * tvaP) / 100 ) * 100) / 100;
	    			System.out.println("tvaP: " + tvaP);
	    			//dbVente.addProduitsV( item.get(i).getNomProduits(), quantite, prix, tvaP, tvaC, numeroVente);
	    			tva1 += tvaC;
	    			System.out.println("tva: " + tva1);
	    			id++;			
	    		}
	    		
	    		tva1 = (double) Math.round(tva1 * 100) / 100;
	    	//	dbVente.addVente(numeroVente, timeNow.getText(), NameUser.getText(), FirstNameUser.getText(),"Produits: "+ String.valueOf(numeroVente), tva1,Double.parseDouble(AfficcheurSomme.getText()),"Carte");
	    	}
	    
	    }
	    @FXML
	    void bntMoinsClicked(MouseEvent event) {
	    	if(event.getButton().equals(MouseButton.PRIMARY)) {
	    		if(tableCaisse.getSelectionModel().getSelectedItem() != null) {
	    			Double prix = (Double) tableCaisse.getSelectionModel().getSelectedItem().getPrixVente();	
	    			
	    			if(tableCaisse.getSelectionModel().getSelectedItem().getQunatite() == 0) 
	    				tableCaisse.getSelectionModel().getSelectedItem().setQunatite(tableCaisse.getSelectionModel().getSelectedItem().getQunatite());
	    			
	    			else {
	    				tableCaisse.getSelectionModel().getSelectedItem().setPrixVente(prix - produitTable.getPrixvente());
	    				tableCaisse.getSelectionModel().getSelectedItem().setQunatite(tableCaisse.getSelectionModel().getSelectedItem().getQunatite() - 1);
	    				tableCaisse.getColumns().get(1).setVisible(false);
	    				tableCaisse.getColumns().get(1).setVisible(true);
	    				tableCaisse.getColumns().get(2).setVisible(false);
	    				tableCaisse.getColumns().get(2).setVisible(true);
	    				prixFinal = (float) Math.round((prixFinal + produitTable.getPrixvente()) * 100) / 100; 
	    				AfficcheurSomme.setText(""+prixFinal+"");
	    				somme = Double.parseDouble(AfficcheurSomme.getText());
	    			}
	    			
	    		}
	    	}
	    }

	    @FXML
	    void bntPlusClicked(MouseEvent event) {
	    	if(event.getButton().equals(MouseButton.PRIMARY)) {
	    		if(tableCaisse.getSelectionModel().getSelectedItem() != null) {
	    			Double prix = (Double) tableCaisse.getSelectionModel().getSelectedItem().getPrixVente();
	    			tableCaisse.getSelectionModel().getSelectedItem().setPrixVente(prix + produitTable.getPrixvente());
	    			tableCaisse.getSelectionModel().getSelectedItem().setQunatite(tableCaisse.getSelectionModel().getSelectedItem().getQunatite() +1);
	    			tableCaisse.getColumns().get(1).setVisible(false);
	    			tableCaisse.getColumns().get(1).setVisible(true);
	    			tableCaisse.getColumns().get(2).setVisible(false);
	    			tableCaisse.getColumns().get(2).setVisible(true);
	    			prixFinal = (float) Math.round((prixFinal + produitTable.getPrixvente()) * 100) / 100; 
	    			AfficcheurSomme.setText(""+prixFinal+"");
	    			somme = Double.parseDouble(AfficcheurSomme.getText());
	    		}
	    	}
	    }
	   
	    public void InitialTable() {
	    	columnProduit.setCellValueFactory(new PropertyValueFactory("nomProduits"));
		    columnPrix.setCellValueFactory(new PropertyValueFactory("prixVente"));
		    columnQuantite.setCellValueFactory(new PropertyValueFactory("qunatite"));    	
	    }
	
	    
	    public void clearTable() {
	    	tableCaisse.getItems().clear(); 
	    	AfficcheurSomme.clear();
	    }
	 
	    public void inserNamePrenom() {
	    	nameUser = loginControl.getName();
			prenomUser = loginControl.getLastName();
	    }
	 
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		
			type = produitsdb.getType();
			produits = produitsdb.load();
			vente = dbVente.loadProduitsV();
			ImageLogoText.setImage(new Image("/img/logoText.png"));
			//ImageUser.setImage(new Image("/img/user2.png"));
			bntCarte();
			bntCash();
		    loadType(8);
			InitialTable();
			Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
				   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			        timeNow.setText(LocalDateTime.now().format(formatter).toString());
			        dateHeure = LocalDateTime.now().format(formatter).toString();
		    }), new KeyFrame(Duration.seconds(1)));
		    clock.setCycleCount(Animation.INDEFINITE);
		    clock.play();
		    textTest = new TextField("myEmail@example.com");
	    	textTest.getProperties().put("vkType", "email"); 
	    	  assert (borderPane != null);
	          assert (borderPane != null);
	         
		}
		
		
		 public ObservableList<Vente> getItem() {
				return item;
			}
			public void setItem(ObservableList<Vente> item) {
				this.item = item;
			}
}
