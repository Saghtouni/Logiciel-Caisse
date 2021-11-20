package Fenetre;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import objects.Vente;

	public class ProduitVenteControl extends AnchorPane implements Initializable{
	

	    @FXML
	    private TableView<Vente> TabProduitsV;

	    @FXML
	    private TableColumn<Vente, Integer> ColumnID;

	    @FXML
	    private TableColumn<Vente, String> ColumnProoduit;

	    @FXML
	    private TableColumn<Vente, Double> ColumnPrix;

	    @FXML
	    private TableColumn<Vente, Integer> ColumnQuantité;

	    @FXML
	    private TableColumn<Vente, Double> ColumnTva;
	    
	    @FXML
	    private TableColumn<Vente, Double> ColumnTvaC;

	    @FXML
	    private TableColumn<Vente, Integer> ColumnNumVente;
	    
		public  ProduitVenteControl() {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProduitsV.fxml"));
			fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        
	        try {
	            fxmlLoader.load();            
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
		}

	    
	    public void initTable() {
	    	ColumnID.setCellValueFactory(new PropertyValueFactory("id"));
	    	ColumnProoduit.setCellValueFactory(new PropertyValueFactory("nomProduits"));
	    	ColumnPrix.setCellValueFactory(new PropertyValueFactory("prixVente"));
	    	ColumnQuantité.setCellValueFactory(new PropertyValueFactory("qunatite"));
	    	ColumnTva.setCellValueFactory(new PropertyValueFactory("TVA"));
	    	ColumnTvaC.setCellValueFactory(new PropertyValueFactory("TVAC"));
	    	ColumnNumVente.setCellValueFactory(new PropertyValueFactory("numeroVente"));
	    	
	    }

	    public void setProduitsV(ObservableList<Vente> loadProduitsVS) {
	    	TabProduitsV.setItems(loadProduitsVS);
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		   initTable();
			
		}
 }
