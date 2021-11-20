package Fenetre;

import java.awt.print.PageFormat

;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.print.PrintService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import DataBase.VenteDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import objects.Analyse;
import objects.Reglage;
import objects.Vente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.Desktop;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.BorderFactory;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.text.PDFTextStripper;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfViewerPreferences.PdfViewerPreferencesConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
//import com.qoppa.pdfViewerFX.PDFViewer;
import com.itextpdf.text.pdf.parser.TextMarginFinder;
import com.jfoenix.controls.JFXDatePicker;
import com.qoppa.pdfText.PDFText;

import DataBase.ReglageDB;
import DataBase.VenteDB;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import   javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import objects.Reglage;


public class JournalUtilisateurController  extends BorderPane implements Initializable  {
	
	    @FXML
	    private Label labeltotal;
	    
	    @FXML
	    private JFXDatePicker date;

	    @FXML
	    private Button btnImprimer;

	    @FXML
	    private Label user;

	    @FXML
	    private Label valeurDate;

	    @FXML
	    private Label valeurtotal;
	    
	    @FXML
	    private TableView<Analyse> tableticketsJournal;
	    
	    @FXML
	    private Button bntTicket;
	    
	    @FXML
		private TableColumn<Analyse, Double> sommecol;
		
		@FXML
		private TableColumn<Analyse, Button> detailscol;
		
		@FXML
		private TableColumn<Analyse, Double> statuscol;
		
		@FXML
		private TableColumn<Analyse, Integer> tableColumn;
		
		private String nameUser,prenomUser;
		ObservableList<objects.Analyse> ls = null;
		ObservableList<Vente> produitV = null;
		private ObservableList<Vente> produitVT = FXCollections.observableArrayList();
		private ObservableList<Vente> produiTri = FXCollections.observableArrayList();
		private Connection DB = null;
		private static int nbr = 0;
		private static int tmp = 0;
		VenteDB ventedb = null;
		CaisseControl caisse = null;
		private Reglage myreglage =null;
		private ReglageDB dbreglage = null;
		private LoginControl logincontrol = null;
		ArrayList<String> tab = new ArrayList<>();
		Stage stageCh = new Stage();
		
		public JournalUtilisateurController(String nameUser, String prenomUser,Connection db) throws SQLException {
			this.nameUser=nameUser;
			this.prenomUser=prenomUser;
			this.DB=db;
			ventedb = new VenteDB(DB);
			caisse = new CaisseControl(DB, null);
			try {
				logincontrol = new LoginControl(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbreglage = new ReglageDB(db);
		}


		public void btnImprimer() {
	    	ImageView img =new ImageView("/img/sigma.png");
	    	img.setFitWidth(75);
	    	img.setFitHeight(60);
	    	btnImprimer.setGraphic(img);
	    }
		
		public void bntTicket() {
	    	ImageView img =new ImageView("/img/icon.png");
	    	img.setFitWidth(75);
	    	img.setFitHeight(60);
	    	bntTicket.setGraphic(img);
	    }


		 @FXML
		 void btnImprimerCliked(ActionEvent event) {
			 Stage primaryStage = (Stage) btnImprimer.getScene().getWindow();
			 chargement( stageCh, "En cours d imprimer !");
			 try {
					manipulatePdf2();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			print2(); 
			stageCh.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Notification");
			alert.setHeaderText("Historique a été imprimé !");
		    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/img/its_icoTR.png"));
			alert.show();
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished(eventt -> alert.close());
			delay.play();	
		 }
		 
		 @FXML
		 void bntTicketCliked(ActionEvent event) {
			 Stage primaryStage = (Stage) btnImprimer.getScene().getWindow();
			 if(tableticketsJournal.getSelectionModel().getSelectedItem() != null) {	
				 chargement( stageCh, "En cours d imprimer !");
				 produitV = ventedb.loadProduitsVS(Integer.parseInt(tableticketsJournal.getSelectionModel().getSelectedItem().getProduit().toString().substring(34).replace("'", "").replace(" ", "")));
				 try {
					manipulatePdf3();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 print2();
				 stageCh.close();
				
				 Alert alert = new Alert(AlertType.INFORMATION);
				 alert.setTitle("Notification");
				 alert.setHeaderText("Ticket a été imprimé !");
			     Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				 stage.getIcons().add(new Image("/img/its_icoTR.png"));
				 alert.show();
				 PauseTransition delay = new PauseTransition(Duration.seconds(2));
				 delay.setOnFinished(eventt -> alert.close());
				 delay.play();
		    	}
		    	else {
		    		Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Imprimer Ticket");
					alert.setHeaderText("Résultat");
					alert.setContentText("il faut sélectionner un ticket!");
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image("/img/its_icoTR.png"));
					alert.showAndWait();
		    	}
			 
			 
		 }
		 
			public void chargement(Stage stage, String message) {
				ProgressIndicator progressIndicator = new ProgressIndicator();
		        Stage primaryStage = null;
		        FlowPane root = new FlowPane();
		        progressIndicator.setMinHeight(150);
		        progressIndicator.setMinWidth(150);
		        root.setPadding(new Insets(80));
		        root.setHgap(80);
		        Label label1 = new Label("\n \n"+ message);
		        label1.setStyle("-fx-font-size: 18px;\r\n" + 
		        		"    -fx-font-weight: bold;");
		        root.getChildren().addAll(progressIndicator, label1);
		        final Scene scene = new Scene(root, 350, 350);
		        scene.getStylesheets().add("/Fenetre/Fenetre.css");
		        stage.setScene(scene);
		        stage.getIcons().add(new Image("/img/its_icoTR.png"));
		        stage.setResizable(false);
		        stage.setTitle("Chargement");
		        stage.show();
		}
		 protected void manipulatePdf3() throws Exception {
		       	tab.clear();
		        int numeroVente = Integer.parseInt(tableticketsJournal.getSelectionModel().getSelectedItem().getProduit().toString().substring(34).replace("'", ""));
		       	String dateHeure = ventedb.getDateH(numeroVente);
	   	        int table = 0;
	   	        tab.add(" ");
	   	        tab.add(" ");
	   	        tab.add("Mont-Soleil 67");
	   	        tab.add("2610 Saint-Imier");
	   	        tab.add("032 941 23 77");
	   	        tab.add(" ");
	       		tab.add(valeurDate.getText()+"\n\n");
	       	    tab.add(" ");
	       		tab.add("Quittance"+"\n");
	       		tab.add("---------------------------------------------------------------");
	        	tab.add("Article                                         QNT     Prix");
	        	tab.add(" ");
	        	double somme = (double) 0.00;
	            double tva = 0.00;
		        
		        String modePaiement = ventedb.getPaiement(numeroVente);
		        produitVT = ventedb.loadProduitsVS(numeroVente);
	            
		        for (int i = 0; i < produitV.size(); i++) {
	 	        	Vente f= produitV.get(i);
	 	        	table = f.getTable();
	 	        	if(f.getPrixVente() != null) {
	 	        		tva+=(f.getTVA() * f.getPrixVente()) / 100;
	 	        		somme+=f.getPrixVente();
	 	        		tab.add(f.getNomProduits());
	 	        		tab.add("                                                    "+f.getQunatite()+"          "+f.getPrixVente());
	 	        		
	 	        	}   	
	 	        }
	 	
		        BigDecimal tvaTot = new BigDecimal(tva).setScale(2, RoundingMode.HALF_UP);
	   	        tab.add("---------------------------------------------------------------");
	   	        tab.add("                                  TOTAL : "+String.format("%.2f",somme)+"  CHF");
	    		tab.add("TVA :  "+String.format("%.2f",tvaTot.doubleValue())+" CHF");
	    		tab.add("TVA N° : CHE-137.395.064\n");
	    		tab.add("Paiement : "+modePaiement);
	    		tab.add("---------------------------------------------------------------");
	    		tab.add("          Merci pour votre visite");
	    		tab.add("                À bientôt !");
	    		tab.add(" ");
	    		tab.add("Vous êtes servis par : "+ventedb.getUser(numeroVente));
	      		tab.add("Table N° "+ table);
	      		tab.add(" ");
	      		tab.add("          www.lapattedours.ch");
	      		
	   	    }
		  
		  
		  
		  
		  
		  protected void manipulatePdf2() throws Exception {
		       	tab.clear();
	       		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	   	        String dateF = LocalDateTime.now().format(formatter).toString();
	   	  
	   	        tab.add(" ");
	   	        tab.add(" ");
	   	        tab.add("Mont-Soleil 67");
	   	        tab.add("2610 Saint-Imier");
	   	        tab.add("032 941 23 77");
	   	        tab.add(" ");
	       		tab.add(valeurDate.getText()+"\n\n");
	       	    tab.add(" ");
	       		tab.add("Fermeture de caisse"+"\n");
	       		tab.add("---------------------------------------------------------------");
	        	//tab.add("Article                                         QNT     Prix");
	        	tab.add(" ");
	        	double somme = (double) 0.00;
	            double tva = 0.00;
	            
	            for (int i = 0; i < ls.size(); i++) {
	   	        	somme+=ls.get(i).getSomme();
	  	        	tva+=ls.get(i).getTva();
	  	        	int numeroVente = Integer.parseInt(tableticketsJournal.getItems().get(i).getProduit().toString().substring(34).replace("'", ""));
	  	        	produitVT = ventedb.loadProduitsVS(numeroVente);
	  	        	
	  	        	if(ls.get(i).getTable() == 0) { 
	        			tab.add("           Vente directe : "+ls.get(i).getSomme()+" .-");
	  	        		tab.add(" ");
	  	        	}
	  	        	else { 
	  	        		tab.add("           Table N° "+ls.get(i).getTable()+" : "+ls.get(i).getSomme()+" .-");
	  	        		tab.add("");
	  	        	}
	  	        /*	for(Vente vente: produitVT) {  
	  	        		int nbrTable = vente.getTable();
	  	        		BigDecimal prixTot = new BigDecimal(vente.getQunatite() *vente.getPrixVente()).setScale(2, RoundingMode.HALF_UP);
	  	        		if(vente.getQunatite() != 0) {	
	  	        				tab.add(vente.getNomProduits());
	  	        				tab.add("                                                    "+vente.getQunatite()+"          "+prixTot.doubleValue());
	  	        						
	  	        		}
	  	        	}	*/
	  	        	tab.add(" "); 
	            }
	            
	   	        tab.add("---------------------------------------------------------------");
	   	        tab.add("                                  TOTAL : "+String.format("%.2f",somme)+"  CHF");
	    		tab.add("TVA :  "+String.format("%.2f",tva)+" CHF");
	    		tab.add("TVA N° : CHE-137.395.064\n");
	    		tab.add("---------------------------------------------------------------");
	      		tab.add(" ");
	      		tab.add("          www.lapattedours.ch");
	      		
	   	    }
		  
		  public void print2() 
		    {
				String image = myreglage.getImage();
				try {	
				PrintService myPrintService = PrintUtility.findPrintService(myreglage.getImptickets().substring(8));
		        PrinterJob job = PrinterJob.getPrinterJob();
		        PageFormat pf = job.defaultPage();
		        Paper paper = new Paper();
		        double margin = 0; 
		        paper.setImageableArea(5, margin, 0, 0);
		        pf.setPaper(paper);
		        job.setPrintable(new PaginationExampleOr(tab, nbr, image),pf);
		        job.setPrintService(myPrintService);
		        job.print();
					} catch (PrinterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 
		    }   
		 
			public void setReglage()
			{
		         Reglage r = dbreglage.getReglage(logincontrol.getId_utilisateur());
		         if (r != null)
		 			myreglage=r;
			}
			
		
		 private void buidlTicketsTable2() 
	     {
			 
			try {
				ls = ventedb.displayAllTicketsUtilisateur(nameUser,prenomUser);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ls.size()==0)
			{
				Alert a = new Alert(Alert.AlertType.INFORMATION) ; 
	            a.setContentText("Pas de tickets pour cette date !");
	            a.showAndWait();
			}
			else
			{
				 tableticketsJournal.setItems(ls);
			 System.out.println("ls = "+ls.toString());
			 Double total=0.0;
			 for (Analyse a : ls )
			 {
				 total+=a.getSomme();
			 }
			 BigDecimal bd4 = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
			 valeurtotal.setText(bd4+"");
			 user.setText(nameUser+" "+prenomUser);
			
			}
			
			 
		 
	    }
		 
		 @FXML
		 void dateClicked(ActionEvent event) {
			 
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			 valeurDate.setText(date.getValue().format(formatter).toString());
			 
			 try {
					ls = ventedb.displayAllTicketsUtilisateurDate(nameUser, prenomUser, date.getValue().format(formatter).toString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(ls.size()==0)
				{
					Alert a = new Alert(Alert.AlertType.INFORMATION) ; 
		            a.setContentText("Pas de tickets pour cette date !");
		            a.showAndWait();
				}
				else
				{
					 tableticketsJournal.setItems(ls);
					 System.out.println("ls = "+ls.toString());
					 Double total=0.0;
					 for (Analyse a : ls )
					 {
						 total+=a.getSomme();
					 }
					 BigDecimal bd4 = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
					 valeurtotal.setText(bd4+"");
					 user.setText(nameUser+" "+prenomUser);
				}
		 }


		 @Override
		 public void initialize(URL arg0, ResourceBundle arg1) {
			   tableticketsJournal.getColumns().clear();
			   sommecol.setCellValueFactory(new PropertyValueFactory("somme"));
			   detailscol.setCellValueFactory(new PropertyValueFactory("produit"));    	
			   statuscol.setCellValueFactory(new PropertyValueFactory("tva"));
			   tableColumn.setCellValueFactory(new PropertyValueFactory("table"));
			   tableticketsJournal.getColumns().addAll(sommecol,detailscol,statuscol,tableColumn); 
			   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy ");
			   valeurDate.setText(LocalDateTime.now().format(formatter).toString());
			   buidlTicketsTable2();
			   btnImprimer();
			   bntTicket();
			   setReglage();
				
	
		}	
}
