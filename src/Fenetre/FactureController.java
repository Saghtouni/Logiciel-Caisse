package Fenetre;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
 
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.lang.model.element.ElementKind;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.qoppa.pdfWriter.PDFPage;

import DataBase.FactureDB;
import DataBase.ProduitsDB;
import DataBase.ReglageDB;
import DataBase.VenteDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.print.Printer;
import java.awt.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.ItemFacture;
import objects.Produits;
import objects.Reglage;
import objects.Vente;

import com.qoppa.pdf.settings.ImageCompression;

public class FactureController extends AnchorPane implements Initializable {

	@FXML
	private TextArea remarque;
	@FXML
	private HBox vboxtotal;
	@FXML
	private Label totalnet;
	@FXML
	private Label tva;
	@FXML
	private Label totalbrut;
	@FXML
	private ImageView ImageUser;
	@FXML
	private Label NameUser;
	@FXML
	private Label FirstNameUser;
	@FXML
	private Button btnImprimer;
	@FXML
	private VBox vboxclient;
	@FXML
	private Label nomclient;
	@FXML
	private Label adresse;
	@FXML
	private Label telephone;
	@FXML
	private Button btnclient;
	@FXML
	public TableView<ItemFacture> tableproduits;
	@FXML
	public TableColumn<ItemFacture, String> columnNom;
	@FXML
	public TableColumn<ItemFacture, Integer> columnQte;
	@FXML
	public TableColumn<ItemFacture, Double> columnPrixunitaire;
	@FXML
	public TableColumn<ItemFacture, Double> columnPrixtotal;
	@FXML
	private ComboBox<String> combocategorie;
	@FXML
	private ComboBox<String> comboproduit;
	@FXML
	private TextField qte;
	@FXML
	private Button btnajouter;
	@FXML
	private Button btnsupprimer;
	@FXML
	private Button btnenregistrer;
	@FXML
	private Button btnannuler;
	@FXML
	private Label numFact;
	@FXML
	private ListView<String> listeproduits;
	@FXML
	private TextField produitrecherche;

	private Label idclient = new Label();

	private Connection DB = null;

	private ProduitsDB produitsdb = null;

	private FactureDB facturedb = null;
	
	private LoginControl logincontrol = null;
	private ReglageDB dbreglage = null;
	public static int nbr = 0;

	private ObservableList<String> categories = FXCollections.observableArrayList();
	private ObservableList<String> nomProduits = FXCollections.observableArrayList();
	private ObservableList<String> LSN = FXCollections.observableArrayList();
	private ObservableList<Produits> LSP = FXCollections.observableArrayList();
	ObservableList<ItemFacture> items = FXCollections.observableArrayList();
	private Reglage myreglage =null;
	int numeroVente;
	Double tva1 = 0.0;
	VenteDB dbVente = null;
	private static boolean jobRunning = true;

	public FactureController(Connection dB) {
		this.DB = dB;
		produitsdb = new ProduitsDB(DB);
		facturedb = new FactureDB(DB);
		dbVente = new VenteDB(DB);
		try {
			logincontrol = new LoginControl(dB);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbreglage = new ReglageDB(dB);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Facture4.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		InitialTableProduits();
		initListeProduits();
		btnImprimer();
		setReglage();
		try {
			numFact.setText(facturedb.getFactureLastId().toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		qte.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String s = "";
				for (char c : newValue.toCharArray()) {
					if (((int) c >= 48 && (int) c <= 57 || (int) c == 46)) {
						s += c;
					}
				}
				qte.setText(s);
			}
		});
		
		tableproduits.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends ItemFacture> observable, ItemFacture oldValue, ItemFacture newValue) -> {
					if (newValue != null) {
						initListeProduits();
						int jj = listeproduits.getItems().indexOf(newValue.getNom());
						listeproduits.getSelectionModel().select(jj);
						listeproduits.getFocusModel().focus(jj);
						listeproduits.scrollTo(jj);
						qte.setText(newValue.getQuantite().toString());		
					}

				});

		
	}

	public void btnImprimer() {
		ImageView img = new ImageView("/img/iconeSave.png");
		img.setFitWidth(75);
		img.setFitHeight(60);
		btnImprimer.setGraphic(img);
	}

	public void InitialTableProduits() {
		columnNom.setCellValueFactory(new PropertyValueFactory("nom"));
		columnPrixunitaire.setCellValueFactory(new PropertyValueFactory("prixunitaire"));
		columnPrixtotal.setCellValueFactory(new PropertyValueFactory("prixtotal"));
		columnQte.setCellValueFactory(new PropertyValueFactory<ItemFacture, Integer>("quantite"));
		// columnQte.setEditable(true);

	}
	public void initListeProduits()
	{
		listeproduits.getItems().clear();
		LSN.clear();
		LSP.clear();
		LSP = produitsdb.load();
		for (Produits p : LSP)
			LSN.add(p.getNom());
		listeproduits.setItems(LSN);
	}

	@FXML
	public void ajouterClicked(ActionEvent event) throws SQLException {
		if (qte.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "La quantité est vide !");
			alert.showAndWait();
		} else {

			//String nomp = comboproduit.getSelectionModel().getSelectedItem();
			String nomp = listeproduits.getSelectionModel().getSelectedItem();
			Integer q = Integer.parseInt(qte.getText());
			ItemFacture recupere = produitsdb.getPrixTVA(nomp);
			Double pu = recupere.getPrixunitaire();
			boolean k = false;

			for (ItemFacture itemm : tableproduits.getItems()) {

				if (itemm.getNom().equals(nomp)) {
					int j = tableproduits.getItems().indexOf(itemm);
					itemm.setQuantite(itemm.getQuantite() + q);
					itemm.setPrixtotal(itemm.getQuantite() * itemm.getPrixunitaire());
					tableproduits.getItems().set(j, itemm);
					k = true;
					tableproduits.getSelectionModel().select(j);
					ClearandInit();
					Alert alert = new Alert(AlertType.INFORMATION,
							"Ce produit existe déja ! ça quantité a été augmenté ! ");
					alert.showAndWait();
					break;
				}
			}
			if (k == false && !listeproduits.getSelectionModel().getSelectedItems().isEmpty()) {

				ItemFacture f = new ItemFacture(nomp, q, pu, pu * q, recupere.getTva(),"");
				// System.out.println("f = "+f);
				items.add(f);
				tableproduits.setItems(items);

				ClearandInit();
				tableproduits.getSelectionModel().clearSelection();
			}
			
			

		}
	}


	@FXML
	public void enregistrerClicked(ActionEvent event) throws SQLException {
		ItemFacture selectedFact = tableproduits.getSelectionModel().getSelectedItem();
		int i = tableproduits.getSelectionModel().getSelectedIndex();
		if (selectedFact != null)
			if (qte.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR, "La quantité est vide !");
				alert.showAndWait();
			} else {

				 //System.out.println("old f " + selectedFact);
				//String nomp = comboproduit.getSelectionModel().getSelectedItem();
				String nomp = listeproduits.getSelectionModel().getSelectedItem();
				Integer q = Integer.parseInt(qte.getText());
				ItemFacture recupere = produitsdb.getPrixTVA(nomp);
				Double pu = recupere.getPrixunitaire();
				ItemFacture f = new ItemFacture(nomp, q, pu, pu * q,recupere.getTva(),"");
				 //System.out.println("new f " + f);
				tableproduits.getItems().set(i, f);
				tableproduits.refresh();
				ClearandInit();
				tableproduits.getSelectionModel().clearSelection();

			}
	}

	@FXML
	public void supprimerClicked(ActionEvent event) throws SQLException {
		ItemFacture selectedFact = tableproduits.getSelectionModel().getSelectedItem();

		if (selectedFact != null) {
			tableproduits.getItems().remove(selectedFact);
			ClearandInit();
			tableproduits.getSelectionModel().clearSelection();

		}

	}

	@FXML
	public void selectionnerClicked(ActionEvent Event) throws SQLException {
		EnregistrerClientController enrClient = new EnregistrerClientController(DB, nomclient, adresse, telephone,
				idclient);
		Stage primaryStage = new Stage();
		primaryStage.setScene(new Scene(enrClient));
		primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Sélection ou ajouter client");
		primaryStage.showAndWait();

	}

	public void ImprimerClicked(ActionEvent event) throws SQLException {
		ObservableList<ItemFacture> mesprods = tableproduits.getItems();
		if (!nomclient.getText().equals("")) {
			if (mesprods != null && !totalnet.getText().equals("")) {
			/*	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				String dateHeure = LocalDateTime.now().format(formatter).toString();
				Double ttn = Double.parseDouble(totalnet.getText());
				Double tvv = Double.parseDouble(tva.getText());
				Double ttb = ttn;
				Integer idc = Integer.parseInt(idclient.getText());
				facturedb.add(tableproduits.getItems(), dateHeure, ttn, tvv, ttb, idc, remarque.getText().toString());
				dbVente.addVente(numeroVente, dateHeure, logincontrol.getName(), logincontrol.getLastName(),
						"Produits: " + String.valueOf(numeroVente), tva1, ttb, "Cash",0);
				 for (int i = 0; i < tableproduits.getItems().size(); i++) {
					 dbVente.addProduitsV( tableproduits.getItems().get(i).getNom(), tableproduits.getItems().get(i).getQuantite(), tableproduits.getItems().get(i).getPrixtotal(),  tableproduits.getItems().get(i).getTva(), (double) Math.round(((tableproduits.getItems().get(i).getPrixtotal() * tableproduits.getItems().get(i).getTva()) / 100) * 100) / 100,dbVente.getPk(), 0);
					 tva1 += (double) Math.round(((tableproduits.getItems().get(i).getPrixtotal() * tableproduits.getItems().get(i).getTva()) / 100) * 100) / 100;
					 dbVente.UpdateTVA(dbVente.getPk(), tva1,"Ticket:", "FACTURE");
				 }
				*/
				SavePDF();
				qte.clear();
				//combocategorie.getSelectionModel().select(0);
				//comboproduit.getSelectionModel().select(0);
				listeproduits.getSelectionModel().select(0);
				tableproduits.getItems().clear();
				nomclient.setText("");
				telephone.setText("");
				adresse.setText("");
				idclient.setText("");
				remarque.setText("");
				Integer l = Integer.parseInt(numFact.getText());
				l++;
				numFact.setText(l.toString());
				updateFactDetails();
				initListeProduits();
				Alert alert = new Alert(AlertType.INFORMATION, "Facture sauvegardé avec sucées !");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING, "Veuillez ajouter au moins un produits !");
				alert.showAndWait();
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING, "Veuillez selectionner un client !");
			alert.showAndWait();
		}

	}

	public void updateFactDetails() {
		Double ttn = 0.0, tv = 0.0, ttb = 0.0;
		for (ItemFacture i : tableproduits.getItems()) {
			ttn += i.getPrixtotal();
			tv += ((i.getPrixunitaire()*i.getTva() * i.getQuantite()) / 100);
		}
		ttb = ttn + tv;
		totalnet.setText(String.format("%.2f",Double.parseDouble(ttn.toString())));
		tva.setText(String.format("%.2f",Double.parseDouble(tv.toString())));
		totalbrut.setText(String.format("%.2f",Double.parseDouble(ttn.toString())));

	}

	public void ClearandInit() {
		qte.clear();
		//combocategorie.getSelectionModel().select(0);
		//comboproduit.getSelectionModel().select(0);
		tableproduits.refresh();
		LSN.clear();
		LSP.clear();
		initListeProduits();
		updateFactDetails();
	}

	@FXML
	public void recupererproduits(KeyEvent event) throws SQLException {
		listeproduits.getItems().clear();
		LSN.clear();
		LSP.clear();
		String s = produitrecherche.getText();

		if (s != null) {
			LSP = produitsdb.searchProduits(s);
		} else {
			LSP = produitsdb.load();
		}
		for (Produits p : LSP)
			LSN.add(p.getNom());
		listeproduits.setItems(LSN);

	}
	
	
	
	public void SavePDF() {
		
		try
        {
			String t = myreglage.getFactures();
			t=t.replace("\\","//" );
			String nameFile = t+"//facture"+numFact.getText()+".pdf";
			System.out.println("path   "+nameFile);
			//String imgESE =   myreglage.getImage();
			File file = new File(nameFile );
	        file.getParentFile().mkdirs();
	        manipulatePdf(nameFile);
	        print(nameFile);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
		
	}
	 //************************************************** tout objet à imprimer doit etre declaré final
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
        job.setPrintable(new OutprintFacture(text, nbr),pf);
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

	    protected void manipulatePdf(String dest) throws Exception {
	    	PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
	        Document doc = new Document(pdfDoc);   
    	    String logoits = myreglage.getImage();       
    	  //  ImageData data = ImageDataFactory.create(logoits);  
    	    
    		//------------------- HEADER  ----------------------------
    	    
    	    Text t11 = new Text("Tacos Planet\n").setFontSize(18);
   	        Text t12 = new Text("\n"+"Avenue Louis-Ruchonnet 2B" + "\n"+"1003 Lausanne").setFontSize(18);
   	        Text t111 = new Text("\n021 320 44 44\n\n").setFontSize(18);
    	    Paragraph paraEntre =  new Paragraph().add(t11).add(t12).add(t111);
    	    paraEntre.setFixedPosition(38, 640, 240);
    	    doc.add(paraEntre);
    	    
      		Text nom2 = new Text("\n\n"+nomclient.getText()).setFontSize(13);
      		Text adr2 = new Text("\n"+adresse.getText()).setFontSize(13);
      		Text tel2 = new Text("\n"+"0"+telephone.getText()+"\n").setFontSize(13);
    		Paragraph paraheader = new Paragraph().add(nom2).add(adr2).add(tel2);
    		paraheader.setFixedPosition(350, 540, 240);
    		doc.add(paraheader); 
    		
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	        String dateF = LocalDateTime.now().format(formatter).toString();
	        Text date = new Text("\n"+"Le "+dateF).setFontSize(13);
	        Paragraph ds= new Paragraph().add(date);
    		doc.add(ds);
    	
    		
    		Text quit = new Text("\n"+"Quittance\n");
    		Paragraph qu= new Paragraph().add(quit);
    		doc.add(qu);
       		doc.add(new Paragraph(new Text("\n" +"---------------------------------------------------------------")).setWidth(260));
       		double somme = (double) 0.00;
        
       		Text article = new Text("Article").setFontSize(16);
        	Paragraph articl = new Paragraph().add(article).setWidth(260);
        	doc.add(articl);
        	Text unite = new Text("Unité").setFontSize(16);
        	Paragraph unit = new Paragraph().add(unite).setWidth(260);
        	doc.add(unit);
        	Text prix = new Text("Prix").setFontSize(16);
        	Paragraph pri = new Paragraph().add(prix).setWidth(260);
        	doc.add(pri);
        	
	        for (int i = 0; i < tableproduits.getItems().size(); i++) {
	        	ItemFacture f= tableproduits.getItems().get(i);
	          	somme+= f.getTva();
   	        	Text nomprod = new Text(f.getNom());
   	        	Paragraph nompro = new Paragraph().add(nomprod).setWidth(260);
   	        	doc.add(nompro);
   	        	Text qteprod = new Text(f.getQuantite()+"");
   	        	Paragraph qtepro = new Paragraph().add(qteprod).setWidth(260);
   	        	doc.add(qtepro);
        		Text prixprod = new Text(String.format("%.2f",f.getPrixtotal()));
        		Paragraph prixpro = new Paragraph().add(prixprod).setWidth(260);
   	        	doc.add(prixpro);	
   	        	
	        }
	        nbr = tableproduits.getItems().size() * 3;
	        doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setWidth(260));
    		Text ttb1 = new Text("TOTAL : ").setFontSize(14).setBold();
    		Text ttb2 = new Text(String.format("%.2f", (double) Math.round(Double.parseDouble(totalbrut.getText())))).setFontSize(13);
    		Text tva1 = new Text("\nTVA : ").setFontSize(14).setBold();
    		Text tva2 = new Text(String.format("%.2f", (double) Math.round(Double.parseDouble(tva.getText())))+"\n").setFontSize(13);
    		Text numTVA = new Text("TVA N° : CHE-309-773.386\n");
    		Paragraph parfooter = new Paragraph().add(ttb1).add(ttb2).add(tva1).add(tva2).add(numTVA);
    		parfooter.setRelativePosition(415,260,0,0);
    		doc.add(parfooter);            
    		doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setWidth(260));
    		Text mercitick = new Text("Toute l'équipe de Tacos Planet vous souhaite\n un bon appétit, à bientôt !\n");
    		Paragraph mt = new Paragraph().add(mercitick).setWidth(260);
    		doc.add(mt);
    		Text site = new Text("www.tacosdelagare.ch");
    		Paragraph st = new Paragraph().add(site).setWidth(260);
    		doc.add(st);
    		doc.add(new Paragraph(new Text("---------------------------------------------------------------")).setWidth(260));
    		Paragraph rqfooter3 = new Paragraph(new Text("\n"+remarque.getText())); 
    		rqfooter3.setFontSize(14);
    		rqfooter3.setRelativePosition(0,280,0,0);
    		doc.add(rqfooter3);
	        doc.close();
	      
	    }
	    
		public static int getNbr() {
			return nbr;
		}

		public static void setNbr(int nbr) {
			CashControl.nbr = nbr;
		}
		
		public void setReglage()
		{
	         Reglage r = dbreglage.getReglage(logincontrol.getId_utilisateur());
	         if (r != null)
	 		{
	 			myreglage=r;
	 		
	 		}
			
		}

		
}

