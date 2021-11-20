package Fenetre;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.print.DocFlavor.STRING;

import DataBase.ClientsDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.Client;
import objects.ItemFacture;
import javafx.scene.control.TableView;

public class EnregistrerClientController extends AnchorPane {
	@FXML
	private TableView<Client> tableclients;
	@FXML
	public TableColumn<Client, String> columnNom;
	@FXML
	public TableColumn<Client, Long> columnTel;
	@FXML
	public TableColumn<Client, String> columnAdr;
	@FXML
	private TextField rechercherText;
	@FXML
	private Button btnSelectionner;
	@FXML
	private Label labelTitre;
	@FXML
	private Button bntAjouter;
	@FXML
	private TextField nomprenom;
	@FXML
	private TextField telephone;
	@FXML
	private TextArea adresse;
	
    @FXML
    private TextField rechercherPhone;

    @FXML
    private Button btnModifier;
    
    @FXML
    private ImageView imgText;

    @FXML
    private ImageView imagePhone;
    
	Label nomclientrecup, adresserecup, telephonerecup,idclient;
	

	Connection DB = null;
	ClientsDB clientDB = null;
	private ObservableList<Client> myclients = FXCollections.observableArrayList();

	public EnregistrerClientController(Connection db, Label nomclient, Label adresse2, Label telephone2, Label idclient2)
			throws SQLException {
		this.DB = db;
		clientDB = new ClientsDB(DB);

		nomclientrecup = nomclient;
		adresserecup = adresse2;
		telephonerecup = telephone2;
		idclient=idclient2;
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnregistrerClient.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		// set textfield to numeric entry
		telephone.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String s = "";
				for (char c : newValue.toCharArray()) {
					if (((int) c >= 48 && (int) c <= 57 || (int) c == 46)) {
						s += c;
					}
				}
				telephone.setText(s);
			}

			
		});
		columnNom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		columnTel.setCellValueFactory(new PropertyValueFactory<Client, Long>("telephone"));
		columnAdr.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		tableclients.setItems(clientDB.load());

	}

	@FXML
	public void ajouterClicked(ActionEvent event) {
		
		if (nomprenom.getText().equals("") || telephone.getText().equals("") ||  adresse.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING, "Veuiller remplir tous les champs !");
			alert.show();
		} else {
			
			String np = nomprenom.getText();
			long tel = Long.parseLong(telephone.getText());
			String adr = adresse.getText();
			Integer idrecup = clientDB.add(np, tel, adr);
			
			nomclientrecup.setText(nomprenom.getText());
			telephonerecup.setText(telephone.getText());
			adresserecup.setText(adresse.getText());
			idclient.setText(idrecup.toString());
			//System.out.println("idclient recup"+idclient.getText());
			Alert alert = new Alert(AlertType.INFORMATION, "Ajout avec succés !");
			alert.showAndWait();
			Stage primaryStage = (Stage) bntAjouter.getScene().getWindow();
			primaryStage.close();

		}

	}

	@FXML
	public void selectionnerClicked(ActionEvent event) {

		if (tableclients.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR, "Veuillez selectionner un client !");
			alert.showAndWait();
		} else {
			Client c =  tableclients.getSelectionModel().getSelectedItem();
			nomclientrecup.setText(c.getNom());
			telephonerecup.setText( c.getTelephone().toString());
			adresserecup.setText(c.getAdresse());
			//Integer idrecup = clientDB.add(c.getNom(), c.getTelephone(),c.getAdresse());
			String idr = Integer.toString(c.getId());
			idclient.setText(idr);
			//System.out.println("idclient recup"+idclient.getText()+" recap "+idrecup+" client"+c.getId());
			Stage primaryStage = (Stage) bntAjouter.getScene().getWindow();
			primaryStage.close();
		}
	}

	// Event Listener on TextField[#rechercherText].onKeyReleased
	@FXML
	public void rechercherClient(KeyEvent event) {
		String s = rechercherText.getText();
		if(s.equals(""))
			myclients = clientDB.load();
		else
			myclients = clientDB.SearchByNameORAdresse(s);
		tableclients.setItems(myclients);
	}
	

    @FXML
    void ModifierClicked(ActionEvent event) {

    }
    
    @FXML
    void rechercherPhone(KeyEvent event) {
    	Long s ;
		if(rechercherPhone.getText().equals("")) {
			myclients = clientDB.load();
		}
		else {
		
    	 s =  Long.parseLong(rechercherPhone.getText());
    	 System.out.println(s);
    	 myclients = clientDB.SearchPhone(s);
		 tableclients.setItems(myclients);
		}
    }


}
