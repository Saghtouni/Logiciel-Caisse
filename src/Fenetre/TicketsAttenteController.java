package Fenetre;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import DataBase.VenteDB;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import objects.Analyse;
import objects.Vente;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class TicketsAttenteController extends AnchorPane implements Initializable  {
	@FXML
	public TableView<Analyse> tabletickets;
	@FXML
	private TableColumn<Analyse, String> datecol;
	@FXML
	private TableColumn<Analyse, Double> sommecol;
	@FXML
	private TableColumn<Analyse, Button> detailscol;
	@FXML
	private TableColumn<Analyse, String> actionscol;
	@FXML
	private TableColumn<Analyse, String> statuscol;
    @FXML
    private TableColumn<Analyse, Integer> tableColumn;
    
	private Connection DB = null;

	VenteDB ventedb = null;
	CaisseControl caisse = null;
	ObservableList<Analyse> item = FXCollections.observableArrayList();
	public TableView<Vente> tableCaisseT = new TableView<Vente>();
	public TextField aff ;
	public TextField aff2 = new TextField() ;

@Override
public void initialize(URL arg0, ResourceBundle arg1) {

	tabletickets.getColumns().clear();
	datecol.setCellValueFactory(new PropertyValueFactory("date_heure"));
   sommecol.setCellValueFactory(new PropertyValueFactory("somme"));
   detailscol.setCellValueFactory(new PropertyValueFactory("produit"));    	
   statuscol.setCellValueFactory(new PropertyValueFactory("status"));
   actionscol.setCellValueFactory(new PropertyValueFactory("actions"));
   tableColumn.setCellValueFactory(new PropertyValueFactory("table"));
   tabletickets.getColumns().addAll(datecol,sommecol,detailscol,statuscol,actionscol,tableColumn);      
      
      
	buidlTicketsTable();
 
}

public  TicketsAttenteController( Connection db, CaisseControl caisseControl) {
	
	
	this.DB=db;
	this.caisse=caisseControl;
	ventedb = new VenteDB(DB);
	this.tableCaisseT=caisse.tableCaisse;
	this.aff=caisse.AfficcheurSomme;

}

 private void buidlTicketsTable() 
     {
	 /*ObservableList<Analyse> ls = ventedb.displayAllTickets();
	 System.out.println(ventedb.displayAllTickets().size());
     for (Analyse v : ls)
     {
 		item.add(new Analyse(v.getDate_heure(),v.getSomme(),v.getProduit(),v.getStatus()));
     }
     tabletickets.setItems(item);
     
	 System.out.println(tabletickets.getItems().size());*/
	 tabletickets.setItems(ventedb.displayAllTicketsAttente(caisse,tabletickets));
	 
	 //System.out.println("test");
	 
    }
 
 
 
}




