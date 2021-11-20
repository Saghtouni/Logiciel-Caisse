package Fenetre;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import org.omg.CORBA.NVList;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Vente;

public class ReductionController extends BorderPane implements Initializable  {
	@FXML
	private TextField valeurPour;
	@FXML
	private Button btnOK;
	@FXML
	private Button btnAnnuler;
	
    public TableView<Vente> tableCaisse;
	private Connection DB = null;

    private CaisseControl caisse = null;

	
	
	
	
    public ReductionController(CaisseControl caisseControl,Connection db) {
    	caisse=caisseControl;
		this.DB=db;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reduction.fxml"));
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

     }





	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		valeurPour.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String s = "";
				for (char c : newValue.toCharArray()) {
					if (((int) c >= 48 && (int) c <= 57 || (int) c == 46)) {
						s += c;
					}
				}
				valeurPour.setText(s);
			}
		});
		
	}
	
	@FXML
	void btnOKClicked(MouseEvent event)
	{
		int reduction =Integer.parseInt(valeurPour.getText());
		Vente v = caisse.tableCaisse.getSelectionModel().getSelectedItem();
		if(v != null)
		{
			//System.out.println("prix recupere "+tableCaisse.getSelectionModel().getSelectedItem().getPrixVente());
			double nvprix=v.getPrixVente()-v.getPrixVente()*reduction/100;
			caisse.tableCaisse.getSelectionModel().getSelectedItem().setPrixVente(nvprix);
			caisse.tableCaisse.refresh();
			caisse.updateTicketDetails();
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Réduction");
            alert.setContentText(reduction+"% de réduction effectué sur tout le produit : "+v.getNomProduits());
            alert.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished( eventt -> alert.close() );
            delay.play();
			
			//System.out.println("reduction = "+reduction + " nvprix = "+nvprix);

		}
		else
		{
			//System.out.println("somme"+caisse.somme+" prixfinal"+caisse.getPrixFinal());
			//caisse.somme = Double.parseDouble(caisse.AfficcheurSomme.getText());
			double nvprixfinal = Double.parseDouble(caisse.AfficcheurSomme.getText())-Double.parseDouble(caisse.AfficcheurSomme.getText())*reduction/100;
			
			caisse.updateTicketDetails();
			System.out.println("reduction = "+reduction + " nvprix2fina = "+nvprixfinal);
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Réduction");
            alert.setContentText(reduction+"% de réduction effectué sur tout le ticket ! ");
            alert.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished( eventt -> alert.close() );
            delay.play();

			
		}
		//System.out.println("done");
		Stage stage = (Stage) btnAnnuler.getScene().getWindow();
	    stage.close();

	}
	
	@FXML
	void btnAnnulerClicked(MouseEvent event)
	{
	    Stage stage = (Stage) btnAnnuler.getScene().getWindow();
	    stage.close();

		
	}


}
