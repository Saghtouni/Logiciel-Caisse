package Fenetre;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import DataBase.ReglageDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Banque;
import objects.Reglage;
import objects.Table;

public class BanqueControler extends BorderPane implements Initializable {
	

    @FXML
    private ScrollPane ScrolPane; 
	private ObservableList<Banque> banque = FXCollections.observableArrayList();
	private Connection DB = null;
	private CaisseControl caisse = null;
	private ReglageDB reglageDb = null;
	private LoginControl loginControl;
	private TableControler tabel;
	private double BUTTON_PADDING = 5;
	Reglage r;
	
	public BanqueControler(Connection DB, CaisseControl caisse) {
		this.DB = DB;
		this.caisse = caisse;
		reglageDb = new ReglageDB(DB);
		tabel = new TableControler(DB, caisse);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fenetre/Banque.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
	}
	
	  
		public void loadTable(ObservableList<Banque> banque ) {
			
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(BUTTON_PADDING));
			grid.setHgap(BUTTON_PADDING);
			grid.setVgap(BUTTON_PADDING);
			
			int nbrTable = banque.size();
			int nbTypeL;
			int div = 3;
		 
			if(nbrTable >= 3)
				 nbTypeL = nbrTable / 3;
			else 
				 nbTypeL = 1;
					
			if (nbrTable % 3 != 0)
				nbTypeL += 1;
			
			int nb = 0;
			
			for(int i  = 0; i < nbTypeL; i++) {
				for(int j = 0; j < 3; j++) {
					if( nb == nbrTable)  
						break;
					Button button = new Button(banque.get(nb).getName());
					button.setPrefWidth(191);
					button.setPrefHeight(150);
					button.setStyle(
							"-fx-background-color: #FF0000,  linear-gradient(#000000,#000000),linear-gradient(#FFFFFF 0%,#FFFFFF 10%, #FFFFFF 50%, #FFFFFF 51%, #FFFFFF 100%);-fx-font-weight: bold;-fx-font-size: 20;"+
							"-fx-font-size: 20pt;"+
						    "-fx-text-fill: black;"+
						    "-fx-alignment: center-left;"+
						    "-fx-opacity: 1; ");
					button.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							Stage primaryStage = (Stage) button.getScene().getWindow();
							primaryStage.close();
							caisse.carteclick(button.getText());
							
							caisse.TableCouver.setText("");
						}
						});
					nb++;
					grid.add(button, i, j);	
				}
			}
			
			ScrolPane.setContent(grid);
		}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		banque = reglageDb.getBanque();
		loadTable(banque);
		// TODO Auto-generated method stub
		
	}

}
