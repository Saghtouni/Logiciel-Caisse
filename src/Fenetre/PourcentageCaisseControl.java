package Fenetre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PourcentageCaisseControl {
	    CaisseControl caisseControl;
	 	@FXML
	    private TextField textPourcentage;

	    @FXML
	    private Button buttonEntrer;

	    @FXML
	    private Button buttonCancel;

	    @FXML
	    void buttonCancelClicked(ActionEvent event) {
	    	textPourcentage.clear();
	    }

	    @FXML
	    void buttonEntrerClicked(ActionEvent event) {
	  
	    }
}

