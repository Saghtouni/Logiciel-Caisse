package Fenetre;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import DataBase.ReglageDB;
import DataBase.VenteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Produits;
import objects.Reglage;
import objects.Table;
import objects.Vente;
import objects.produitsTable;

public class TableControler extends BorderPane implements Initializable {
    @FXML
    private ScrollPane paneTable;
    
    @FXML
    public Button bntReserved;
    
    @FXML
    private Button lebereTable;
    
    private ObservableList<Table> table = FXCollections.observableArrayList();
    public  ObservableList<Button> buttonList = FXCollections.observableArrayList();
	private Connection DB = null;
	private ReglageDB reglageDb = null;
	private LoginControl loginControl;
	private double BUTTON_PADDING = 5;
	Reglage r;
	private boolean reserved = false ;
	private boolean lebere = false ;
	public static Integer nbrTabl = 0;
	private CaisseControl caisse = null;
	private VenteDB ventedb = null;
	
	public TableControler (Connection DB, CaisseControl caisse) {
		this.DB = DB;
		reglageDb = new ReglageDB(DB);
		this.ventedb = new VenteDB(DB);
		this.caisse = caisse;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fenetre/Table.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
	}
	
	 

	@FXML
	void bntReservedClicked(ActionEvent event) {
		reserved = true;
	}

    @FXML
    void bntLebereClicked(ActionEvent event) {
    	lebere = true;
    }
    
    public void setTableCaisse() {
    	ventedb.setProduiTable(caisse, this);
    }
    
    public void setTableCaisse1() {
    	ObservableList<Vente> item = FXCollections.observableArrayList();
    	caisse.tableCaisse.getItems().clear();
    	ObservableList<produitsTable> produitsTable = ventedb.loadProduitsT(nbrTabl);
    	
    	for(produitsTable produits :produitsTable) {
    		double tvaC = (double) Math.round((((produits.getQuantite() * produits.getPrix()*  produits.getTva()) / 100) * 100) / 100);
    		if(produits.getQuantite() != 0) {
    			item.add(new Vente(0,produits.getProduit(), produits.getPrix(), produits.getQuantite(),produits.getTva(),tvaC,0,nbrTabl));
    		}
    		else {
    			Vente vente = new Vente();
				vente.setNomProduits("     Suite! :");
				item.add(vente);
    		}
    	}
    	caisse.tableCaisse.setItems(item);
		caisse.tableCaisse.refresh();
		caisse.updateTicketDetails();
    	
    }
    
	public void loadTable(Reglage r) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(BUTTON_PADDING));
		grid.setHgap(BUTTON_PADDING);
		grid.setVgap(BUTTON_PADDING);
		int nbL = 6;
		int nbrTable = r.getNbrTable();
		int nbTypeL;

		if(nbrTable >= nbL)
			 nbTypeL = nbrTable / nbL;
		else 
			 nbTypeL = 1;		
		
		if (nbrTable % nbL != 0)
			nbTypeL += 1;
		
		int nbr = 1;
		int nb = 0;
		for(int i  = 0; i < nbTypeL; i++) {
			for(int j = 0; j < nbL; j++) {
				nb++;
				Button button = new Button();
				button.setStyle(
						"-fx-background-color: #FF0000,  linear-gradient(#000000,#000000),linear-gradient(#FFFFFF 0%,#FFFFFF 10%, #FFFFFF 50%, #FFFFFF 51%, #FFFFFF 100%);-fx-font-weight: bold;-fx-font-size: 20;"+
						"-fx-font-size: 20pt;"+
					    "-fx-text-fill: black;"+
					    "-fx-alignment: center-left;"+
					    "-fx-opacity: 1; ");
				NbrCouvertControl nbrCouvertCont = new NbrCouvertControl(caisse,this, DB);
				
				button.setOnAction(new EventHandler<ActionEvent>() {
					
					
					public void handle(ActionEvent e) {
						int etaTable = 0;
						nbrTabl = Integer.parseInt(e.getSource().toString().substring(40).replace("'", "").replace(" ", ""));
						System.out.print("   nbrTable  " + nbrTabl);
						etaTable = reglageDb.getEtat("Table " +nbrTabl , r.getNameMachine());
						
						if( etaTable == 0 ) {
							button.setStyle("-fx-background-color: #8b0000; -fx-font-weight: bold;-fx-font-size: 25; ");
							caisse.tableCaisse.getItems().clear();
							caisse.TableCouver.setText("");
							reglageDb.updateTale("Table "+nbrTabl, r.getNameMachine(), 1, nbrCouvertCont.nbrCouvert);
							Stage primaryStage3 = (Stage) button.getScene().getWindow();
							primaryStage3.close();
							Stage primaryStage = new Stage();
							primaryStage.setScene(new Scene(nbrCouvertCont));
							primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
							Screen screen = Screen.getPrimary();
							primaryStage.setResizable(false);
						    primaryStage.setTitle("Nombre de couverts");
						    primaryStage.show();
							
						}
						
						else if(etaTable == 1) {
							setTableCaisse1();
							caisse.TableCouver.setText("Table N° " + nbrTabl + " avec "+ reglageDb.getCouvert("Table "+nbrTabl, r.getNameMachine()) +  " couverts");
							Stage primaryStage = (Stage) button.getScene().getWindow();
							primaryStage.close();
						}
						
					}
					});
			
				reserved = false;
				
				if(table.size() != 0) {
					if(table.get(nbr-1).getEta() == 1) {	
						button.setStyle("-fx-background-color: #8b0000; -fx-font-weight: bold;-fx-font-size: 25; ");
					}
					else if(table.get(nbr-1).getEta() == 2) {
						button.setStyle("-fx-background-color: #006700; -fx-font-weight: bold;-fx-font-size: 25; ");		
					}						
					else {
						button.setStyle(
								"-fx-background-color: #FF0000,  linear-gradient(#000000,#000000),linear-gradient(#FFFFFF 0%,#FFFFFF 10%, #FFFFFF 50%, #FFFFFF 51%, #FFFFFF 100%);-fx-font-weight: bold;-fx-font-size: 20;"+
								"-fx-font-size: 20pt;"+
							    "-fx-text-fill: black;"+
							    "-fx-alignment: center-left;"+
							    "-fx-opacity: 1; ");
					
					}
				}
				button.setText("Table "+nbr++);
				button.setPrefWidth(191);
				button.setPrefHeight(146);
				
				//buttonList.add(button);
				grid.add(button, i, j);
				if( nb == nbrTable) 
					break;
			}
		}
		paneTable.setContent(grid);
	}
	
	public void bntReserved() {
		ImageView img = new ImageView("/img/reserved.png");
		img.setFitWidth(68);
		img.setFitHeight(53);
		bntReserved.setGraphic(img);

	}

	public void bntLebere() {
		ImageView img = new ImageView("/img/libere.png");
		img.setFitWidth(68);
		img.setFitHeight(53);
		lebereTable.setGraphic(img);

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int idd = loginControl.getId_utilisateur();
		Reglage r= reglageDb.getReglage(idd);
		System.out.print("machine: " +r.getNameMachine());
		table = reglageDb.getTable(r.getNameMachine());
		
		loadTable(r);
		bntReserved();
		bntLebere();
		buttonList = null;
		
	}

}
