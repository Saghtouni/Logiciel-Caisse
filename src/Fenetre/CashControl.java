package Fenetre;

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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
import objects.Produits;
import objects.Reglage;
import objects.Vente;


public class CashControl extends BorderPane implements Initializable {

	ObservableList<Vente> vente = FXCollections.observableArrayList();
	private Connection DB = null;
	VenteDB dbVente = null;
	TableControler table = null;
	Double tva = 0.0;
	CaisseControl caisse = null;
	com.itextpdf.kernel.geom.PageSize pagesizeDoc;
	int id;
	int numeroVente;
	int quantite;
	Double tvaP = 0.0;
	Double tvaC = 0.0;
	Double prix;
	Double somme = 0.0;
	ArrayList<String> tab = new ArrayList<>();
	int sizeText = 0;
	public static int nbr = 0;


	@FXML
	private Button butBillet10;

	@FXML
	private Button butBillet20;

	@FXML
	private Button butBillet50;

	@FXML
	private Button butBillet100;

	@FXML
	private Button butBillet200;

	@FXML
	private Button butdeux;

	@FXML
	private Button butSix;

	@FXML
	private Button buthuit;

	@FXML
	private Button butZero;

	@FXML
	private Button butcinq;

	@FXML
	private Button butPoint;

	@FXML
	private Button butCL;

	@FXML
	private Button butQuatre;

	@FXML
	private Button butNeuf;

	@FXML
	private Button butUn;

	@FXML
	private Button butTrois;

	@FXML
	private Button butSept;

	@FXML
	private Button butSUP;

	@FXML
	private Button butEnter;

	@FXML
	private TextField Afficheur;
	
	private Reglage myreglage =null;
	private LoginControl logincontrol = null;
	private ReglageDB dbreglage = null;

	private static boolean jobRunning = true;
	 
	public CashControl(CaisseControl caisseControl, Connection db, TableControler table ) {
		this.DB = db;
		this.table = table;
		caisse = caisseControl;
		dbVente = new VenteDB(DB);
		try {
			logincontrol = new LoginControl(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbreglage = new ReglageDB(db);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cash.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	@FXML
	void butBillet100Clicked(ActionEvent event) {
		Afficheur.clear();
		Afficheur.setText(Afficheur.getText() + "100");
	}

	@FXML
	void butBillet10Clicked(ActionEvent event) {
		Afficheur.clear();
		Afficheur.setText(Afficheur.getText() + "10");
	}

	@FXML
	void butBillet200Clicked(ActionEvent event) {
		Afficheur.clear();
		Afficheur.setText(Afficheur.getText() + "200");
	}

	@FXML
	void butBillet20Clicked(ActionEvent event) {
		Afficheur.clear();
		Afficheur.setText(Afficheur.getText() + "20");
	}

	@FXML
	void butBillet50Clicked(ActionEvent event) {
		Afficheur.clear();
		Afficheur.setText(Afficheur.getText() + "50");
	}

	@FXML
	void bntClClicked(ActionEvent event) {
		Afficheur.clear();
	}

	@FXML
	void bnt0Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "0");
	}

	@FXML
	void bnt1Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "1");
	}

	@FXML
	void bnt2Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "2");
	}

	@FXML
	void bnt3Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "3");
	}

	@FXML
	void bnt4Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "4");

	}

	@FXML
	void bnt5Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "5");
	}

	@FXML
	void bnt6Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "6");
	}

	@FXML
	void bnt7Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "7");
	}

	@FXML
	void bnt8Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "8");
	}

	@FXML
	void bnt9Clicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + "9");
	}

	@FXML
	void bntPointClicked(ActionEvent event) {
		Afficheur.setText(Afficheur.getText() + ".");
	}

	@FXML
	void butEnterClicked(ActionEvent event) {
		caisse.inserNamePrenom();
		Stage stageCh = new Stage();
		if (caisse.tableCaisse.getItems().size()==0)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Paiement");
			alert.setHeaderText("Séléction vide ");
			alert.setContentText("Aucun produiti n'a été selectionné ! ");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		    stage.getIcons().add(new Image("/img/its_icoTR.png"));
			alert.show();
			PauseTransition delay = new PauseTransition(Duration.seconds(3));
			delay.setOnFinished(eventt -> {
				alert.close();
		});
			delay.play();
		}
		else {

			Double montant = Double.parseDouble(Afficheur.getText());
			Double apayer = caisse.somme;
			System.out.println("apayer " + apayer + " montnant siais" + montant);
			if (montant < apayer) {
				System.out.println("infif");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Paiement");
				alert.setHeaderText("Montant saisi insuffisant !");
				BigDecimal mntCorr = new BigDecimal((apayer-montant)).setScale(2, RoundingMode.HALF_UP);
				alert.setContentText("Somme manquante = "+mntCorr);
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			    stage.getIcons().add(new Image("/img/its_icoTR.png"));
				alert.show();
				PauseTransition delay = new PauseTransition(Duration.seconds(3));
				delay.setOnFinished(eventt -> {
					alert.close();
				});
				delay.play();
			} else if (montant >= apayer) {
				    BigDecimal mntRend = new BigDecimal((montant-apayer)).setScale(2, RoundingMode.HALF_UP);
				    chargement( stageCh, mntRend);
					//openCashDrawer();
					Stage primaryStage = (Stage) butEnter.getScene().getWindow();
					primaryStage.close();
					try {
						manipulatePdf2();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					print2();
				
					/*Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Notification");
					alert.setHeaderText("Paiement effectué cash ! ");
					BigDecimal mntRend = new BigDecimal((montant-apayer)).setScale(2, RoundingMode.HALF_UP);
					alert.setContentText("Montant à rendre = "+ mntRend);
					
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				    stage.getIcons().add(new Image("/img/its_icoTR.png"));
					alert.show();
					PauseTransition delay = new PauseTransition(Duration.seconds(5));
					delay.setOnFinished(eventt -> {
						alert.close();
						
					});
					
					delay.play();*/
					
					if(table.nbrTabl != null) {
						dbVente.addVente(caisse.dateHeure, caisse.NameUser.getText(), caisse.FirstNameUser.getText(),
										String.valueOf(numeroVente), tva, caisse.somme, "Cash", table.nbrTabl);
						dbreglage.updateTale("Table "+table.nbrTabl, myreglage.getNameMachine(), 0, 0);
						dbVente.deletPrTable(table.nbrTabl);
					}
					else {
						dbVente.addVente(caisse.dateHeure,  caisse.NameUser.getText(), caisse.FirstNameUser.getText(),
								String.valueOf(numeroVente), tva, caisse.somme, "Cash", 0);
						dbVente.deletPrTable(TableControler.nbrTabl); 
					}
					
					
					if (montant >= apayer) {	
						for (int i = 0; i < caisse.item1.size(); i++) {
							if (caisse.item1.get(i).getPrixVente() != null) {
								quantite = caisse.item1.get(i).getQunatite();
								prix = caisse.item1.get(i).getPrixVente();
								tvaP = caisse.item1.get(i).getTVA();
								tvaC = (double) Math.round(((prix * tvaP) / 100) * 100) / 100;
								System.out.println("tvaP: " + tvaP);
								dbVente.addProduitsV( caisse.item1.get(i).getNomProduits(), quantite, prix, tvaP, tvaC,dbVente.getPk(), table.nbrTabl);
								tva += tvaC;
								id++;								
							}
							else {				
								dbVente.addProduitsV( "     Suite! :", 0, 0.0, 0.0, 0.0, dbVente.getPk(), table.nbrTabl);
							}
							
						} 
							tva = (double) Math.round(tva * 100) / 100;
							dbVente.UpdateTVA(dbVente.getPk(), tva,"", "CASH");
							dbVente.deletProduitsTabl(table.nbrTabl);
							caisse.tableCaisse.getItems().clear();
							caisse.AfficcheurSomme.clear();
							caisse.TableCouver.setText("");
							table.nbrTabl = 0;
							stageCh.close();
					}
			}
		}
	}

	public void chargement(Stage stage, BigDecimal mntRend) {
			ProgressIndicator progressIndicator = new ProgressIndicator();
	        Stage primaryStage = null;
	        FlowPane root = new FlowPane();
	        progressIndicator.setMinHeight(150);
	        progressIndicator.setMinWidth(150);
	        root.setPadding(new Insets(80));
	        root.setHgap(80);
	        Label label1 = new Label("\n \n"+"Paiement effectué cash !" +"\n"+ "Montant à rendre = "+ mntRend);
	        label1.setStyle("-fx-font-size: 18px;\r\n" + 
	        		"    -fx-font-weight: bold;");
	        root.getChildren().addAll(progressIndicator, label1);
	        final Scene scene = new Scene(root, 300, 350);
	        scene.getStylesheets().add("/Fenetre/Fenetre.css");
	        stage.setScene(scene);
	        stage.getIcons().add(new Image("/img/its_icoTR.png"));
	        stage.setResizable(false);
	        stage.setTitle("Chargement");
	        stage.show();
	}
	
	@FXML
	void butSUPlClicked(ActionEvent event) {
		Afficheur.deleteText(Afficheur.getLength() - 1, Afficheur.getLength());
	}

	public void butBillet10() {
		ImageView img = new ImageView("/img/Billet10.jpg");
		img.setFitWidth(125);
		img.setFitHeight(210);
		butBillet10.setGraphic(img);

	}

	public void butBillet20() {
		ImageView img = new ImageView("/img/Billet20.jpg");
		img.setFitWidth(125);
		img.setFitHeight(210);
		butBillet20.setGraphic(img);

	}

	public void butBillet50() {
		ImageView img = new ImageView("/img/Billet50.jpg");
		img.setFitWidth(125);
		img.setFitHeight(210);
		butBillet50.setGraphic(img);

	}

	public void butBillet100() {
		ImageView img = new ImageView("/img/billet100-.jpg");
		img.setFitWidth(125);
		img.setFitHeight(210);
		butBillet100.setGraphic(img);

	}

	public void butBillet200() {
		ImageView img = new ImageView("/img/Billet200-.jpg");
		img.setFitWidth(125);
		img.setFitHeight(210);
		butBillet200.setGraphic(img);

	}


   	   		protected void manipulatePdf2() throws Exception {
       	
       		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
   	        String dateF = LocalDateTime.now().format(formatter).toString();
   	        tab.clear();
   	        tab.add(" ");
   	        tab.add(" ");
   	        tab.add("Mont-Soleil 67");
   	        tab.add("2610 Saint-Imier");
   	        tab.add("032 941 23 77");
   	        tab.add(" ");
       		tab.add(dateF+"\n\n");
       	    tab.add(" ");
       		tab.add("Quittance"+"\n");
       		tab.add("---------------------------------------------------------------");
        	tab.add("Article                                         QNT     Prix");
        	tab.add(" ");
        	double somme = 0 ;
        	double somme2 = 0 ;
        
   	        for (int i = 0; i < caisse.item1.size(); i++) {
   	        	Vente f= caisse.item1.get(i);
   	        	if( f.getPrixVente() != null) {
   	        		somme+=f.getTVAC();
   	        		System.out.println("somme ="+ f.getTVA());
   	        		somme2+=f.getPrixVente();
   	        		tab.add(f.getNomProduits());
   	        		tab.add("                                                    "+f.getQunatite()+"          "+f.getPrixVente());	
   	        	}	
   	        }
 
   	        tab.add("---------------------------------------------------------------");
   	        tab.add("                                  TOTAL : "+String.format("%.2f",somme2)+"  CHF");
    		tab.add("TVA :  "+String.format("%.2f",somme)+" CHF");
    		tab.add("TVA N° : CHE-137.395.064\n");
    		tab.add("Paiement : Espéces");
    		tab.add("---------------------------------------------------------------");
    		tab.add("          Merci pour votre visite");
    		tab.add("                À bientôt !");
    		tab.add(" ");
    		tab.add("Vous êtes servis par : "+ caisse.NameUser.getText());
      		tab.add("Table N° "+ table.nbrTabl);
      		tab.add(" ");
      		tab.add("          www.lapattedours.ch");
      		
   	    }
   	    

   	 //************************************************** tout objet à imprimer doit etre declaré final
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
		
		public void openCashDrawer() {

	        byte[] open = {27,112,0,100,(byte) 250};
	        PrintService myPrintService = PrintUtility.findPrintService(myreglage.getImptickets().substring(8));
	        DocPrintJob job = myPrintService.createPrintJob();
	        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	        Doc doc = new SimpleDoc(open,flavor,null);
	        PrintRequestAttributeSet aset = new 
	        HashPrintRequestAttributeSet();
	        try {
	            job.print(doc, aset);
	        } catch (PrintException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
		
		public static int getNbr() {
			return nbr;
		}

		public static void setNbr(int nbr) {
			CashControl.nbr = nbr;
		}
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			int idd = logincontrol.getId_utilisateur();
			myreglage= dbreglage.getReglage(idd);
			vente = dbVente.loadProduitsV();
			butBillet50();
			butBillet20();
			butBillet10();
			butBillet100();
			butBillet200();
			setReglage();
		}

}


