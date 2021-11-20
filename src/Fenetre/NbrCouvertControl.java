package Fenetre;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import DataBase.ReglageDB;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Reglage;
import objects.Vente;

public class NbrCouvertControl extends BorderPane implements Initializable{

	@FXML
	private TextField valeurPour;
	@FXML
	private Button btnOK;
	@FXML
	private Button btnAnnuler;
	Reglage r;
    public TableView<Vente> tableCaisse;
	private Connection DB = null;
	private ReglageDB reglageDb = null;
    private CaisseControl caisse = null;
    private TableControler tableCont = null;
    private LoginControl loginControl;
	public static int  nbrCouvert = 0;
	
	
    public NbrCouvertControl(CaisseControl caisseControl, TableControler tableCont,Connection db) {
    	
		this.DB=db;
		this.caisse = caisseControl;
		this.tableCont = tableCont;
		this.reglageDb = new ReglageDB(db);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NbrCouvert.fxml"));
     
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

     }

	@FXML
	void btnOKClicked(MouseEvent event)
	{
		int x = 0;
    	try
    	{
    	 
    	Integer.parseInt(valeurPour.getText());
    	  x = 1;
    	}
    	catch(NumberFormatException e)
    	{
    		x = 0;
    		valeurPour.setText("0");
    		Alert alert1 = new Alert(AlertType.ERROR);
    		alert1.setTitle("Quantité Jours fériés");
			alert1.setHeaderText("Résultat");
			alert1.setContentText("il faut inseré un chifre pas de texte !");
			Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
			stage1.getIcons().add(new Image("/img/its_icoTR.png"));
			alert1.showAndWait();
    		
    	}
    	
    	if(x == 1) {
    		nbrCouvert = Integer.parseInt(valeurPour.getText());
    		caisse.TableCouver.setText("Table N° " + tableCont.nbrTabl + " avec "+ valeurPour.getText() +  " couverts");
    		//System.out.println(/*"Table "+ tableCont.nbrTabl +*/ "MAvhine" + r.getNameMachine() /*+"Couvert" +nbrCouvert */);
    		reglageDb.updateTaleC("Table "+ tableCont.nbrTabl, r.getNameMachine(), nbrCouvert);
    		Stage stage = (Stage) tableCont.bntReserved.getScene().getWindow();
    	    stage.close();
    	    
    	}
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
	    stage.close();

	}
	
	@FXML
	void btnAnnulerClicked(MouseEvent event)
	{
	    Stage stage = (Stage) btnAnnuler.getScene().getWindow();
	    stage.close();
	    reglageDb.updateTale("Table "+ tableCont.nbrTabl, r.getNameMachine(), 0, 0);
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int idd = loginControl.getId_utilisateur();
		r= reglageDb.getReglage(idd);
		System.out.print(r.getNameMachine());
	}
	
}
