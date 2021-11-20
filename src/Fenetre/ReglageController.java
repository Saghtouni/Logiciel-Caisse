package Fenetre;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import objects.Banque;
import objects.Reglage;
import objects.Salle;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.ComboBoxModel;

import com.sun.glass.events.KeyEvent;

import DataBase.ReglageDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.CheckBox;

public class ReglageController extends AnchorPane implements Initializable {
	
	@FXML
	private Button imagebtn;
	@FXML
	private TextField pathimage;
	@FXML
	private TextField pathtickets;
	@FXML
	private Button ticketsbtn;
	@FXML
	private Button facturebtn;
	@FXML
	private TextField pathfactures;
	@FXML
	private TextField devise;
	@FXML
	private TextField pathhistoriques;
	@FXML
	private Button historiquebtn;
	@FXML
	private ComboBox<String>  imptickets;
	@FXML
	private ComboBox<String>  impfactures;
	@FXML
	private ComboBox<String> imphistoriques;
	@FXML
	private CheckBox supptckets;
	@FXML
	private Button enregistrerbtn;

    @FXML
    private TextField nbrTable;
	
    @FXML
    private TextField nameMachine;
    
    @FXML
    private TextField addSalle;

    @FXML
    private ComboBox<String> salleName;

    @FXML
    private Button bntAddSalle;

    @FXML
    private Button bntDeletSalle;
    
    @FXML
    private Button bntAddBanque;

    @FXML
    private Button bntDeletBanque;
    
    @FXML
    private TextField addbanque;

    @FXML
    private ComboBox<String> banqueName;
    
	private LoginControl loginControl;
	private Connection DB = null;
	private ReglageDB reglageDb = null;
	public String adresseMac;
	private static int nombreTable;
	private static String nameCaisse;
	private ObservableList<Salle> salle = FXCollections.observableArrayList();
	private ObservableList<Banque> banque = FXCollections.observableArrayList();

	public ReglageController(Connection dB) {
		DB=dB;
		reglageDb = new ReglageDB(dB);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fenetre/Reglage.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}



	@FXML
	public void imagebtnClicked(ActionEvent event) {
		FileChooser fichier = new FileChooser();
    	fichier.setTitle("Sélectionner une image");
    	fichier.getExtensionFilters().addAll(new ExtensionFilter("Fichiers image", "*.bmp", "*.tiff" , "*.gif" , "*.png", "*.jpg"));
    	  File  fichierSelectionner = fichier.showOpenDialog(imagebtn.getScene().getWindow());
    	if(fichierSelectionner != null) {
    		String image = fichierSelectionner.toURI().toString();
    		String m1=image;//=image.replace("/","\\" );
    		m1=m1.substring(6);
    		System.out.println("path image  =    "+m1);
    		pathimage.setText(image);
    	}
    	else {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Information");
    		alert.setHeaderText("Selection de l'image");
    		alert.setContentText("aucune image n'a été selectionné");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/img/its_icoTR.png"));
    		alert.showAndWait();
    	}

	}

	
    @FXML
    void bntAddClicked(ActionEvent event) {
    	if(addSalle.getText() != null && nameMachine.getText() != null) {
    		reglageDb.addSalle(addSalle.getText(), nameMachine.getText());
    		salleName.getItems().add(addSalle.getText());
    		salleName.getSelectionModel().select(0);
    		addSalle.clear();
    	}
    }
	
    @FXML
    void bntDeletClicked(ActionEvent event) {
    	if (salleName != null && nameMachine.getText() != null){
    		reglageDb.supSalle(salleName.getSelectionModel().getSelectedItem().toString(), nameMachine.getText());
    		salleName.getItems().remove(salleName.getSelectionModel().getSelectedItem().toString());
    		salleName.getSelectionModel().select(0);
    		
    	}
    	
    }
    
    @FXML
    void bntAddBanqueClicked(ActionEvent event) {
    	if(addbanque.getText() != null ) {
    		reglageDb.addBanque(addbanque.getText());
    		banqueName.getItems().add(addbanque.getText());
    		banqueName.getSelectionModel().select(0);
    		addbanque.clear();
    	}
    }
    
    @FXML
    void bntDeletBanqueClicked(ActionEvent event) {
    	if(addbanque.getText() != null ) {
    		reglageDb.supBanque(banqueName.getSelectionModel().getSelectedItem().toString());
    		banqueName.getItems().remove(banqueName.getSelectionModel().getSelectedItem().toString());
    		banqueName.getSelectionModel().select(0);
    		
    	}
    }
    
	@FXML
	public void ticketsbtnclicked(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(imagebtn.getScene().getWindow());
         
        if(selectedDirectory == null){
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Avertissement");
    		alert.setHeaderText("Attention ! ");
    		alert.setContentText("Aucun dossier n'a été selectionné ");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/img/its_icoTR.png"));
    		alert.showAndWait();
        }else{
        	System.out.println("pathfact  "+selectedDirectory.getAbsolutePath());
        	String f = selectedDirectory.getAbsolutePath();
        	f=f.replace("\\", "/");
        	System.out.println("pathfact  f  "+ f );
        	 pathtickets.setText(f);
        	 Alert alert = new Alert(AlertType.INFORMATION);
     		alert.setTitle("Information");
     		alert.setHeaderText("Succés");
     		alert.setContentText("Chemin modifié ! ");
     		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
     		stage.getIcons().add(new Image("/img/its_icoTR.png"));
     		alert.showAndWait();
        }
		
	}

	
	@FXML
	public void facturebtnClicked(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(imagebtn.getScene().getWindow());
         
        if(selectedDirectory == null){
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Avertissement");
    		alert.setHeaderText("Attention ! ");
    		alert.setContentText("Aucun dossier n'a été selectionné ");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/img/its_icoTR.png"));
    		alert.showAndWait();
        }else{
        	String f = selectedDirectory.getAbsolutePath();
        	f=f.replace("\\", "/");
        	 pathfactures.setText(f);
        	 Alert alert = new Alert(AlertType.INFORMATION);
     		alert.setTitle("Information");
     		alert.setHeaderText("Succés");
     		alert.setContentText("Chemin modifié ! ");
     		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
     		stage.getIcons().add(new Image("/img/its_icoTR.png"));
     		alert.showAndWait();
        }
	}

	@FXML
	public void historiquebtnClicked(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(imagebtn.getScene().getWindow());
         
        if(selectedDirectory == null){
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Avertissement");
    		alert.setHeaderText("Attention ! ");
    		alert.setContentText("Aucun dossier n'a été selectionné ");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image("/img/its_icoTR.png"));
    		alert.showAndWait();
        }else{
        	String f = selectedDirectory.getAbsolutePath();
        	f=f.replace("\\", "/");
        	 pathhistoriques.setText(f);
        	 Alert alert = new Alert(AlertType.INFORMATION);
     		alert.setTitle("Information");
     		alert.setHeaderText("Succés");
     		alert.setContentText("Chemin modifié ! ");
     		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
     		stage.getIcons().add(new Image("/img/its_icoTR.png"));
     		alert.showAndWait();
        }

		
	}

	public void initCurrentReglage()
	{
		System.out.println("inti curent reglage");
		int idd = loginControl.getId_utilisateur();
		Reglage r= reglageDb.getReglage(idd);
		System.out.println("idd   =   "+idd+" r = "+r);

		if(r!=null)
		{
			
			pathtickets.setText(r.getTickets());
			pathhistoriques.setText(r.getHistoriques());
			pathfactures.setText(r.getFactures());
			pathimage.setText(r.getImage());
			devise.setText(r.getDevise());
			nbrTable.setText(r.getNbrTable()+"");
			nombreTable = r.getNbrTable();
			nameMachine.setText(r.getNameMachine()+"");
			nameCaisse = r.getNameMachine();
			//System.out.println("impr recup = "+impfactures.getSelectionModel().getSelectedItem()+"  "+imphistoriques.getSelectionModel().getSelectedItem()+"  "+imptickets.getSelectionModel().getSelectedItem());
			//System.out.println("impr recup = "+r.getImpfactures()+"  "+r.getImphistoriques()+"  "+r.getImptickets());
			impfactures.getSelectionModel().select(r.getImpfactures());
			imphistoriques.getSelectionModel().select(r.getImphistoriques());
			imptickets.getSelectionModel().select(r.getImptickets());
			salle = reglageDb.getSalle(nameMachine.getText());
			banque = reglageDb.getBanque();
			if(banque != null) {
				for(Banque bnq : banque) {
					banqueName.getItems().add(bnq.getName());
				}
				banqueName.getSelectionModel().select(0);
			}
			if(salle != null) {
				for(Salle sal : salle) {
					salleName.getItems().add(sal.getName());
				}
				salleName.getSelectionModel().select(0);
			}
			if(r.getDroitsuppression()!=0)
				supptckets.setSelected(true);
			else
				supptckets.setSelected(false);
		}
		
			
		
	}
	/*public static void setSelectedValue(ComboBox comboBox, int value)
    {
        ComboBoxModel<String> item;
        for (int i = 0; i < comboBox.getItems().size(); i++)
        {
            item = (ComboBoxModel<String>)comboBox.getItemAt(i);
            if (item.getValue().equalsIgnoreCase(value))
            {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
	*/
	
	public  void enregistrerbtnClicked(ActionEvent event)
	{
		int idd = loginControl.getId_utilisateur(),sup=0;
		Reglage r= reglageDb.getReglage(idd);
		if(r!=null)
		{
			if ( supptckets.isSelected())
				sup = 1;
			if( isInteger(nbrTable.getText()) && (Integer.parseInt(nbrTable.getText()) == nombreTable) && nameMachine.getText() == nameCaisse) {
				reglageDb.update(r.getId(),pathtickets.getText(), imptickets.getSelectionModel().getSelectedItem().toString(),
				pathfactures.getText(), impfactures.getSelectionModel().getSelectedItem().toString(),
				pathhistoriques.getText(), imphistoriques.getSelectionModel().getSelectedItem().toString(),
				pathimage.getText(), devise.getText(), sup, Integer.parseInt(nbrTable.getText()), idd,nameMachine.getText(), adresseMac);
			
			    Alert alert = new Alert(AlertType.INFORMATION);
     		    alert.setTitle("Information");
     		    alert.setHeaderText("Modification des données");
     		    alert.setContentText("Les données ont été mis à jour ! ");
     		    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
     		    stage.getIcons().add(new Image("/img/its_icoTR.png"));
     		    alert.showAndWait();
			}
			else if  (isInteger(nbrTable.getText()) && (Integer.parseInt(nbrTable.getText()) != nombreTable) || nameMachine.getText() != nameCaisse ) {
						reglageDb.update(r.getId(),pathtickets.getText(), imptickets.getSelectionModel().getSelectedItem().toString(),
						pathfactures.getText(), impfactures.getSelectionModel().getSelectedItem().toString(),
						pathhistoriques.getText(), imphistoriques.getSelectionModel().getSelectedItem().toString(),
						pathimage.getText(), devise.getText(), sup, Integer.parseInt(nbrTable.getText()), idd,nameMachine.getText(), adresseMac);
						reglageDb.supTable(nameMachine.getText());
						
						for(int i = 1; i <= (Integer.parseInt(nbrTable.getText())); i ++) {
							reglageDb.addTable("Table "+i, 0, 0, nameMachine.getText());
						}
					
					    Alert alert = new Alert(AlertType.INFORMATION);
		     		    alert.setTitle("Information");
		     		    alert.setHeaderText("Modification des données");
		     		    alert.setContentText("Les données ont été mis à jour ! ");
		     		    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		     		    stage.getIcons().add(new Image("/img/its_icoTR.png"));
		     		    alert.showAndWait();
				
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
	     		alert.setTitle("Information");
	     		alert.setHeaderText("Nombre de table");
	     		alert.setContentText("Il faut inserer un nombre de table pas de texte ! ");
	     		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	     		stage.getIcons().add(new Image("/img/its_icoTR.png"));
	     		alert.showAndWait();
			}
			
			
			
		}
			
		else
		{
			
			reglageDb.addReglage(pathtickets.getText(), imptickets.getSelectionModel().getSelectedItem().toString(),
					pathfactures.getText(), impfactures.getSelectionModel().getSelectedItem().toString(),
					pathhistoriques.getText(), imphistoriques.getSelectionModel().getSelectedItem().toString(),
					pathimage.getText(), devise.getText(), sup,Integer.parseInt(nbrTable.getText()), idd, nameMachine.getText(), adresseMac);
			
			Alert alert = new Alert(AlertType.INFORMATION);
     		alert.setTitle("Information");
     		alert.setHeaderText("Réglage");
     		alert.setContentText("Les données de réglage ont été ajoutés ! ");
     		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
     		stage.getIcons().add(new Image("/img/its_icoTR.png"));
     		alert.showAndWait();
		}
			
			
		
	}
	
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public void initimps()
	{
		ObservableSet<Printer> printers = Printer.getAllPrinters();
		System.out.println("printers =    "+printers);
		for ( Printer p : printers)
		{
			impfactures.getItems().add(p.toString());
			imphistoriques.getItems().add(p.toString());
			imptickets.getItems().add(p.toString());
		}
		
		impfactures.getSelectionModel().select(0);
		imphistoriques.getSelectionModel().select(0);
		imptickets.getSelectionModel().select(0);
		
	
	}
	
	public String getMacAdresse() {
		String macAdresse = null;
		 InetAddress ip;
		    try {

		        ip = InetAddress.getLocalHost();
		        System.out.println("Current IP address : " + ip.getHostAddress());

		        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		        byte[] mac = network.getHardwareAddress();

		        System.out.print("Current MAC address : ");

		        StringBuilder sb = new StringBuilder();
		        for (int i = 0; i < mac.length; i++) {
		            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
		        }
		        
		        macAdresse = sb.toString();
		        System.out.println(sb.toString());

		    } catch (UnknownHostException e) {

		        e.printStackTrace();

		    } catch (SocketException e){

		        e.printStackTrace();

		    }
		    
		    return macAdresse;
	}
	
	
	
	
	 /* @FXML
	    void testNbrTable(KeyEvent event) {

	    }*/
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		initimps();
		initCurrentReglage();
		//adresseMac = getMacAdresse();
		
	
		
	}
	
	
}
