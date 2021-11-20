package Fenetre;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.print.PrintService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import DataBase.ReglageDB;
import DataBase.VenteDB;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Analyse;
import objects.Reglage;
import objects.Vente;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn;

public class TicketsFilterController  extends BorderPane implements Initializable{
	@FXML
	private TableView<Analyse> tableticketsFiltered;
	@FXML
	private TableColumn<Analyse, String> datecol;
	@FXML
	private TableColumn<Analyse, Double> sommecol;
	@FXML
	private TableColumn<Analyse, Button> detailscol;
	@FXML
	private TableColumn<Analyse, Button> actionscol;
    @FXML
    private TableColumn<Analyse, Integer> tableColum;
	@FXML
	private DatePicker datedebut;
	@FXML
	private DatePicker datefin;
	@FXML
	private Button btnRechercher;
	@FXML
	private Label dateD;
	@FXML
	private Label dateF;
    @FXML
    private TableColumn<?, ?> tableColumn;
    @FXML
    private Button bntTicket;

   
	private Connection DB = null;
	VenteDB ventedb = null;
	ObservableList<Vente> produitV = null;
	String nameUser, prenomUser;
	private LoginControl logincontrol = null;
	private ReglageDB dbreglage = null;
	private  static int nbr;
	private Reglage myreglage =null;
    DateTimeFormatter fOut = DateTimeFormatter.ofPattern( "dd-MM-yyyy" , Locale.UK );
    
	public  TicketsFilterController(String nameUser, String prenomUser,Connection db) {
		this.DB=db;
		this.nameUser = nameUser;
		this.prenomUser = prenomUser;
		ventedb = new VenteDB(DB);
		dbreglage = new ReglageDB(db);
		try {
			logincontrol = new LoginControl(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 private void buidlTicketsTable() 
	     {

		 tableticketsFiltered.setItems(ventedb.displayAllTickets());
		 
	    }
	 
	@FXML
	public void btnRechercherClicked(ActionEvent event) {
        LocalDate datedeb=datedebut.getValue();
        LocalDate datef=datefin.getValue();
        String output1 = datedeb.format( fOut );
        String output2 = datef.format( fOut );
        //System.out.println("Deb "+output1+" fin "+output2);
   	 tableticketsFiltered.setItems(ventedb.displayAllTicketsFiltered(output1,output2, nameUser, prenomUser));


	}

	 @FXML
	 void bntTicketCliked(ActionEvent event) {

		 
		 if(tableticketsFiltered.getSelectionModel().getSelectedItem() != null) {	
			 produitV = ventedb.loadProduitsVS(Integer.parseInt(tableticketsFiltered.getSelectionModel().getSelectedItem().getProduit().toString().substring(42).replace("'", "").replace(" ", "")));
			 SavePDF();
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
	 
	  public void SavePDF()
      {
      	try
          {
      		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
  	        String dateF = LocalDateTime.now().format(formatter).toString();
  	        System.out.println("gettickets"+myreglage.getTickets());
  			String t = myreglage.getTickets();
			t=t.replace("\\","//" );
			String nameFile = t+"//ticket"+ventedb.getPk()+".pdf";
  			File file = new File(nameFile );
  	        file.getParentFile().mkdirs();
  	        manipulatePdfTicket(nameFile);
  	        
  	        print(nameFile);
          }
          catch (Throwable t)
          {
              t.printStackTrace();
          }
      	
      }

	  protected void manipulatePdfTicket(String dest) throws Exception {
   		
   		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	        String dateF = LocalDateTime.now().format(formatter).toString();
	        
	    	PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
	        Document doc = new Document(pdfDoc);
	        int tabl = 0;
	        
	        doc.setMargins(0, 0, 0, 0);


   		//------------------- HEADER  ----------------------------

   	
   		Text t11 = new Text("Restaurant Le Manoir\n").setFontSize(18);
   		Text t12 = new Text("\n"+"Momt-Soleil 67" + "\n"+"1005 Lausanne").setFontSize(18);
   		Text t111 = new Text("\n+41 32 941 23 77\n\n").setFontSize(18);
   		Paragraph paraheader = new Paragraph().add(t11).add(t12).add(t111);
   		
   		paraheader.setMargins(0, 0, 2, 0).setTextAlignment(TextAlignment.CENTER).setWidth(260);
   		doc.add(paraheader); 
		//doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setMargins(0, 15, 2, 15).setTextAlignment(TextAlignment.CENTER).setWidth(260));
		
   		//Text detailstick = new Text("Vendeur  :\t\t\t\t"+caisse.NameUser.getText()+"\nTicket N° :\t\t\t\t"+dbVente.getPk()).setFontSize(16);
   		
   		//Paragraph dt = new Paragraph().add(detailstick);
   		//dt.setMargins(0, 2, 0, 0).setTextAlignment(TextAlignment.CENTER).setWidth(260);
   		//doc.add(dt); 
   		Text detailstick2 = new Text(dateF+"\n\n");
   		detailstick2.setTextRise((float) 50);
   		Text quittance = new Text("Quittance"+"\n");
   		
   		Paragraph dt2 = new Paragraph().add(detailstick2).add(quittance);
   		dt2.setMargins(1, 0, 0, 0).setTextAlignment(TextAlignment.LEFT).setWidth(200);
   		doc.add(dt2); 
   		doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setWidth(260));
	           
   		
    	double somme = (double) 0.00;
    	double somme2 = (double) 0.00;
    	
    	Text article = new Text("Article").setFontSize(16);
    	Paragraph articl = new Paragraph().add(article).setWidth(260);
    	doc.add(articl);
    	Text unite = new Text("QNT").setFontSize(16);
    	Paragraph unit = new Paragraph().add(unite).setWidth(260);
    	doc.add(unit);
    	Text prix = new Text("Prix").setFontSize(16);
    	Paragraph pri = new Paragraph().add(prix).setWidth(260);
    	doc.add(pri);
    	int supSuite = 0;
	        for (int i = 0; i < produitV.size(); i++) {
	        	Vente f= produitV.get(i);
	        	tabl = f.getTable();
	        	if(f.getPrixVente() != null) {
	        		somme+=(f.getTVA() * f.getPrixVente()) / 100;
	        		somme2+=f.getPrixVente();
	        		Text nomprod = new Text(f.getNomProduits());
	        		Paragraph nompro = new Paragraph().add(nomprod).setWidth(260);
	        		doc.add(nompro);
	        		Text qteprod = new Text(f.getQunatite()+"");
	        		Paragraph qtepro = new Paragraph().add(qteprod).setWidth(260);
	        		doc.add(qtepro);
	        	
	        		Text prixprod = new Text(String.format("%.2f",f.getPrixVente()));
	        		Paragraph prixpro = new Paragraph().add(prixprod).setWidth(260);
	        		doc.add(prixpro);	
	        	}
	        	else
	        		supSuite++;
	        	
	        }
	        nbr = (produitV.size() - supSuite) * 3;
	        doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setWidth(260));
	        Text footertick = new Text("TOTAL : "+String.format("%.2f",somme2)+"  CHF");
		Paragraph ft = new Paragraph().add(footertick).setWidth(260);
		doc.add(ft); 
		Text tvtick = new Text("TVA :\t\t"+String.format("%.2f",somme)+" CHF"+"\n").setFontSize(16);
		Paragraph tvt = new Paragraph().add(tvtick);
		tvt.setMargins(0, 2, 0, 2).setTextAlignment(TextAlignment.CENTER).setWidth(260);
		doc.add(tvt); 
		Text numTVA = new Text("TVA N° : CHE-245.559.463\n");
		Paragraph pt = new Paragraph().add(numTVA).setWidth(260);
		doc.add(pt); 
		
			Text paytick = new Text("Paiement : Historique");
			Paragraph typ = new Paragraph().add(paytick).setWidth(260);
			doc.add(typ); 
	
		/*Text tvaText = new Text("CHE-TVA").setFontSize(16);
		Paragraph ptv = new Paragraph().add(tvaText);
		pt.setMargins(2, 2, 2, 2).setTextAlignment(TextAlignment.CENTER).setWidth(260);
		doc.add(ptv);*/ 
		
		doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setWidth(260));
		Text mercitick = new Text("Merci pour votre visite\nÀ bientôt !\n\n www.restaurantlemanoir.ch\n\n");
		Paragraph mt = new Paragraph().add(mercitick).setWidth(260);
		doc.add(mt);
		
		Text vendeur = new Text("\nVous êtes servis par : "+ nameUser);
		Paragraph vd = new Paragraph().add(vendeur).setWidth(260);
		doc.add(vd);
		Text tablr = new Text("Table N° "+ tabl);
		Paragraph tb = new Paragraph().add(tablr).setWidth(260);
		doc.add(tb);
		doc.close();
		
	    }
	 
	  public void print(String path) 
	    {
			path = path.replace("//","/" );
			PDDocument document;
			
			try {	
			document = PDDocument.load(new File(path));
			String extractedText = PdfTextExtractor.getTextFromPage(new PdfReader(path), 1);
			
			PDDocument document1 = PDDocument.load(new File(path));
			
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document1);
			System.out.println("text ::    \n" + text.toString());
			PrintService myPrintService = PrintUtility.findPrintService(myreglage.getImptickets().substring(8));
	        PrinterJob job = PrinterJob.getPrinterJob();
	        PageFormat pf = job.defaultPage();
	        Paper paper = new Paper();
	        
	        double margin = 0; 

	        paper.setImageableArea(margin, margin, 0, 0);
	        paper.setSize(paper.getHeight(), extractedText.length());
	        pf.setPaper(paper);
	        //job.setPrintable(new OutputPrinter(text, nbr),pf);
	        job.setPrintService(myPrintService);
	        job.print();
				} catch (InvalidPasswordException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	  
	  public void bntTicket() {
	    	ImageView img =new ImageView("/img/icon.png");
	    	img.setFitWidth(75);
	    	img.setFitHeight(60);
	    	bntTicket.setGraphic(img);
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setReglage();
		tableticketsFiltered.getColumns().clear();
		datecol.setCellValueFactory(new PropertyValueFactory("date_heure"));
	   sommecol.setCellValueFactory(new PropertyValueFactory("somme"));
	   detailscol.setCellValueFactory(new PropertyValueFactory("produit"));    	
	   actionscol.setCellValueFactory(new PropertyValueFactory("status"));
	   tableColum.setCellValueFactory(new PropertyValueFactory("table"));
	   tableticketsFiltered.getColumns().addAll(datecol,sommecol,detailscol,actionscol, tableColum);      
		datedebut.setEditable(true); 
		datefin.setEditable(true); 
		LocalDate nowd =LocalDateTime.now().toLocalDate();
		Date date = java.sql.Date.valueOf(nowd);
		//System.out.println(date);
		datedebut.setValue(nowd);
		datefin.setValue(nowd);
       LocalDate datedeb=datedebut.getValue();
       LocalDate datef=datefin.getValue();
       String output1 = datedeb.format( fOut );
       String output2 = datef.format( fOut );
       //System.out.println("Deb "+output1+" fin "+output2);
       tableticketsFiltered.setItems(ventedb.displayAllTicketsFiltered(output1,output2,nameUser, prenomUser));
       System.out.println("namee: " + nameUser);		
       bntTicket();
	}
	

}

