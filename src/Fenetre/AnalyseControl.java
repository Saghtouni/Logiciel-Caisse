package Fenetre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.controlsfx.control.PropertySheet.Item;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import DataBase.ReglageDB;
import DataBase.VenteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Analyse;
import objects.ItemFacture;
import objects.Produits;
import objects.Reglage;
import objects.Vente;

public class AnalyseControl extends AnchorPane implements Initializable {
	private Connection DB = null;
	VenteDB dbVente = null;
	private ReglageDB dbreglage = null;
	private ObservableList<Analyse> Analyse = FXCollections.observableArrayList();
	private LoginControl logincontrol = null;

	@FXML
	private Button AjoutButton;

	@FXML
	private Button supButton;
	@FXML
	private Button btnRechercher3;
	@FXML
	private DatePicker datedebut3;
	@FXML
	private DatePicker datefin3;

	@FXML
	private TableView<Analyse> tabCommande;

	@FXML
	private TableColumn<Analyse, Integer> IdColumn;

	@FXML
	private TableColumn<Analyse, String> dateColumn;

	@FXML
	private TableColumn<Analyse, String> nomColumn;

	@FXML
	private TableColumn<Analyse, String> prenomColumn;

	@FXML
	private TableColumn<Analyse, Button> produitColumn;

	@FXML
	private TableColumn<Analyse, Float> TVAColumn;

	@FXML
	private TableColumn<Analyse, Float> sommeColumn;
	
	@FXML
	private TableColumn<Analyse, String> paiementColumn;

	/********************************************
	 * onglet statistique
	 *******************************/
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
	private BarChart<String, Number> bar1;
	@FXML
	private BarChart<String, Number> bar2;
	@FXML
	private PieChart char1;

	@FXML
	private DatePicker datedebut2;
	@FXML
	private DatePicker datefin2;

	/********************************************
	 * onglet historique
	 *******************************/
	@FXML
	private TableView<Analyse> tableCA;
	@FXML
	private TableView<Analyse> tableCaissier;
	@FXML
	private TableView<Analyse> tableProduits;
	@FXML
	private Button btnRechercher2;
	@FXML
	private Button btnImprimer;
	@FXML
	private Button btnImprimer3;
	@FXML
	private TableColumn<Analyse, String> nomcol;
	@FXML
	private TableColumn<Analyse, Double> nbrvtcol;
	@FXML
	private TableColumn<Analyse, String> nomcol2;
	@FXML
	private TableColumn<Analyse, Double> nbrvtcol2;
	@FXML
	private TableColumn<Analyse, String> datecol;
	@FXML
	private TableColumn<Analyse, Double> cacol;

	DateTimeFormatter fOut = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.UK);
	private Reglage myreglage =null;
	

	
	
	public AnalyseControl(Connection db) throws SQLException {

		this.DB = db;
		logincontrol = new LoginControl(DB);
		dbVente = new VenteDB(DB);
		dbreglage = new ReglageDB(db);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Analyse.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	@FXML
	void AjoutButtonClicked(ActionEvent event) {
		EnregistreVControl enregistreV = new EnregistreVControl(DB);
		enregistreV.FirstNameUser.setText(logincontrol.getLastName());
		enregistreV.NameUser.setText(logincontrol.getName());
		Stage primaryStage = new Stage();
		primaryStage.setScene(new Scene(enregistreV));
		primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
		primaryStage.show();
	}

	@FXML
	void DateButtonCliecked(ActionEvent event) {

	}

	@FXML
	void supButtonClicked(ActionEvent event) {Alert alert = new Alert(AlertType.CONFIRMATION);
		Alert alert1 = new Alert(AlertType.ERROR);
		alert.setTitle("Boîte de dialogue de confirmation");
		alert.setHeaderText("Regardez, une boîte de dialogue de confirmation");
		alert.setContentText("Voulez vous supprimer cette vente?");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/its_icoTR.png"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			if(tabCommande.getSelectionModel().getSelectedItem() != null) {
				dbVente.deleteVente(tabCommande.getSelectionModel().getSelectedItem().getId());
				tabCommande.getItems().clear();
				tabCommande.setItems(lodVente());
			}
			else {
				alert1.setTitle("Suprimer vente");
				alert1.setHeaderText("Résultat");
				alert1.setContentText("il faut sélectionner une vente!");
				Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
				stage1.getIcons().add(new Image("/img/its_icoTR.png"));
				alert1.showAndWait();
			}
		}
		else {
			alert1.close();
		}
		
	}

	public void AjoutButton() {
		ImageView img = new ImageView("/img/iconeAjouter.png");
		img.setFitWidth(95);
		img.setFitHeight(65);
		AjoutButton.setGraphic(img);
	}
	
	public void imprimeButton() {
		ImageView img = new ImageView("/img/iconeSave.png");
		img.setFitWidth(30);
		img.setFitHeight(30);
		btnImprimer.setGraphic(img);
	}

	public void supButton() {
		ImageView img = new ImageView("/img/iconeSuprimer.png");
		img.setFitWidth(95);
		img.setFitHeight(65);
		supButton.setGraphic(img);
	}

	public void initTable() {
		IdColumn.setCellValueFactory(new PropertyValueFactory("id"));
		dateColumn.setCellValueFactory(new PropertyValueFactory("date_heure"));
		nomColumn.setCellValueFactory(new PropertyValueFactory("nomUtil"));
		prenomColumn.setCellValueFactory(new PropertyValueFactory("prenomUtil"));
		TVAColumn.setCellValueFactory(new PropertyValueFactory("tva"));
		sommeColumn.setCellValueFactory(new PropertyValueFactory("somme"));
		produitColumn.setCellValueFactory(new PropertyValueFactory("produit"));
		paiementColumn.setCellValueFactory(new PropertyValueFactory("modepaiement"));

	}

	public ObservableList<Analyse>  lodVente() {
		Analyse = dbVente.loadAnalyse();
		return Analyse;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabCommande.setItems(lodVente());
		initTable();
		AjoutButton();
		setReglage();
		supButton();
		imprimeButton();
		datedebut.setEditable(true);
		datefin.setEditable(true);
		datedebut2.setEditable(true);
		datefin2.setEditable(true);
		LocalDate nowd = LocalDateTime.now().toLocalDate();
		Date date = java.sql.Date.valueOf(nowd);
		// System.out.println(LocalDateTime.now().toLocalDate().format(fOut));
		datedebut.setValue(nowd);
		datefin.setValue(nowd);
		datedebut2.setValue(nowd);
		datefin2.setValue(nowd);
		datedebut3.setValue(nowd);
		datefin3.setValue(nowd);
		initbar1();
		initbar2();
		initbar3();
		nbrvtcol.setCellValueFactory(new PropertyValueFactory("id"));
		nomcol.setCellValueFactory(new PropertyValueFactory("nomUtil"));
		nbrvtcol2.setCellValueFactory(new PropertyValueFactory("id"));
		nomcol2.setCellValueFactory(new PropertyValueFactory("nomUtil"));
		datecol.setCellValueFactory(new PropertyValueFactory("date_heure"));
		cacol.setCellValueFactory(new PropertyValueFactory("somme"));
		tableCaissier.getColumns().clear();
		tableCaissier.getColumns().addAll(nomcol, nbrvtcol);
		inittableCaissier();
		//initbtnImprimer();
		Reglage r = dbreglage.getReglage(logincontrol.getId_utilisateur());
		if (r != null)
			if (r.getDroitsuppression() == 0) {

				supButton.setVisible(false);
				supButton.setManaged(false);
			}

	}

	@FXML
	void btnRechercherClicked(ActionEvent event) {
		initbar1();
		initbar2();
		initbar3();

	}

	public void initbar1() {
		LocalDate datedeb = datedebut.getValue();
		LocalDate datef = datefin.getValue();
		// System.out.println("récupere "+datedeb+" "+datef);
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);
		// System.out.println("Deb "+output1+" fin "+output2);
		Map<String, Double> results = dbVente.getCAFiltered(output1, output2);
		if (results == null)
			System.out.println("videe  ");
		else {
			XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();

			for (Map.Entry<String, Double> m : results.entrySet()) {

				dataSeries1.getData().add(new XYChart.Data<String, Number>(m.getKey(), m.getValue()));
				// System.out.println("Map = key : " + m.getKey() +" value : " +m.getValue());
			}

			bar1.getData().clear();
			bar1.getData().add(dataSeries1);
			bar1.setLegendVisible(false);
			bar1.getYAxis().setLabel("Valeur");
			bar1.getXAxis().setLabel("Date");
			// dataSeries1.getData().clear();
			if (datef != datedeb)
				bar1.setTitle("Chiffre d'affaire du : " + output1 + " -> " + output2);
			else
				bar1.setTitle("Chiffre d'affaire le : " + output1);

		}

	}

	public void initbar2() {
		LocalDate datedeb = datedebut.getValue();
		LocalDate datef = datefin.getValue();
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);

		Map<String, Long> results = dbVente.getTopCaissierFiltered(output1, output2);
		if (results == null)
			System.out.println("videe  ");
		else {
			XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();

			for (Map.Entry<String, Long> m : results.entrySet()) {

				dataSeries1.getData().add(new XYChart.Data<String, Number>(m.getKey(), m.getValue()));
				// System.out.println("Map = key : " + m.getKey() +" value : " +m.getValue());
			}

			bar2.getData().clear();
			bar2.getData().add(dataSeries1);
			bar2.setLegendVisible(false);
			bar2.getYAxis().setLabel("Nombre des ventes");
			bar2.getXAxis().setLabel("Nom Caissier");
			/*
			 * Node n = bar2.lookup(".data0.chart-bar"); n.setStyle("-fx-bar-fill: red"); n
			 * = bar2.lookup(".data1.chart-bar"); n.setStyle("-fx-bar-fill: blue"); n =
			 * bar2.lookup(".data2.chart-bar"); n.setStyle("-fx-bar-fill: green"); n =
			 * bar2.lookup(".data3.chart-bar"); n.setStyle("-fx-bar-fill: orange");
			 */
			// dataSeries1.getData().clear();
			if (output1 != output2)
				bar2.setTitle("Top " + results.size() + " caissier du : " + output1 + " -> " + output2);
			else
				bar2.setTitle("Top " + results.size() + " caissier du le : " + output1);

		}

	}

	public void initbar3() {
		LocalDate datedeb = datedebut.getValue();
		LocalDate datef = datefin.getValue();
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);

		Map<String, Long> results = dbVente.getTopProduitFiltered(output1, output2);
		if (results == null)
			System.out.println("videe  ");
		else {
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

			// int somme = results.values().stream().mapToInt(i -> i.intValue()).sum();
			// System.out.println("somme "+somme);
			results.entrySet().stream().map((en) -> {
				pieChartData.add(new PieChart.Data(en.getKey() + " = " + en.getValue(), en.getValue()));
				return en;
			}).forEachOrdered((en) -> {
				System.out.println("key " + en.getKey() + "val " + en.getValue());
			});

			char1.setData(pieChartData);
			char1.setTitle("Statistiques des produits");
			char1.setLabelLineLength(10);
			char1.setLegendSide(Side.LEFT);
		}
		/*
		 * final Label caption = new Label(""); caption.setTextFill(Color.DARKORANGE);
		 * caption.setStyle("-fx-font: 24 arial;");
		 * 
		 * for (final PieChart.Data data : char1.getData()) {
		 * data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,new
		 * EventHandler<MouseEvent>() { public void handle(MouseEvent e) {
		 * caption.setTranslateX(e.getSceneX()); caption.setTranslateY(e.getSceneY());
		 * caption.setText(String.valueOf(data.getPieValue()/somme*100) + "%"); } }); }
		 */

		// stats.getChildren().addAll(chart) ;
	}

	@FXML
	void btnRechercher2Clicked(ActionEvent event) {
		inittableCaissier();
		inittableProduits();
		inittableCA();

	}

	/*public void initbtnImprimer() {
		ImageView img = new ImageView("/img/iconeSave.png");
		img.setFitWidth(75);
		img.setFitHeight(60);
		btnImprimer.setGraphic(img);
		btnImprimer3.setGraphic(img);
	}*/

	public void inittableCaissier() {
		tableCaissier.getItems().clear();
		LocalDate datedeb = datedebut2.getValue();
		LocalDate datef = datefin2.getValue();
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);
		ObservableList<Analyse> LS = FXCollections.observableArrayList();
		Map<String, Long> results = dbVente.getTopCaissierFiltered(output1, output2);
		for (Map.Entry<String, Long> m : results.entrySet()) {

			tableCaissier.getItems().add(new Analyse(m.getValue(), m.getKey()));
			// System.out.println("size"+tableCaissier.getItems().size());
		}

	}

	public void inittableProduits() {
		tableProduits.getItems().clear();
		LocalDate datedeb = datedebut2.getValue();
		LocalDate datef = datefin2.getValue();
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);
		ObservableList<Analyse> LS = FXCollections.observableArrayList();
		Map<String, Long> results = dbVente.getTopProduitFiltered(output1, output2);
		for (Map.Entry<String, Long> m : results.entrySet()) {

			tableProduits.getItems().add(new Analyse(m.getValue(), m.getKey()));
			// System.out.println("size"+tableCaissier.getItems().size());
		}

	}

	public void inittableCA() {
		tableCA.getItems().clear();
		LocalDate datedeb = datedebut2.getValue();
		LocalDate datef = datefin2.getValue();
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);
		ObservableList<Analyse> LS = FXCollections.observableArrayList();
		Map<String, Double> results = dbVente.getCAFiltered(output1, output2);
		for (Map.Entry<String, Double> m : results.entrySet()) {

			tableCA.getItems().add(new Analyse(m.getValue(), m.getKey()));
			// System.out.println("size"+tableCaissier.getItems().size());
		}

	}

	@FXML
	public void ImpHistoriqueClicked(ActionEvent event) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
			String dateF = LocalDateTime.now().format(formatter).toString();
			System.out.println("myreglage  "+myreglage);
			String t = myreglage.getHistoriques();
			t=t.replace("\\","//" );
			String nameFile = t+"//Historique" + dateF + ".pdf";
			File file = new File(nameFile);
			file.getParentFile().mkdirs();
			manipulatePdf(nameFile);
			print(nameFile);
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public void manipulatePdf(String dest) throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dateF = LocalDateTime.now().format(formatter).toString();

		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		Document doc = new Document(pdfDoc);

		// Creating an ImageData object
		String logoits = myreglage.getImage();   
		ImageData data = ImageDataFactory.create(logoits);
		// Creating an Image object
		com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(data);
		image.scaleToFit(200, 200);
		image.setFixedPosition(380, 780);
		doc.add(image);

		// ------------------- HEADER ----------------------------
		Paragraph ds = new Paragraph("Le " + dateF);
		ds.setBold();
		ds.setFontSize(12);
		ds.setPaddingLeft(10);
		ds.setPaddingTop(20);
		doc.add(ds);

		Text t1 = new Text(
				"\nHistorque du période :" + datedebut2.getValue().toString() + " au " + datefin2.getValue().toString())
						.setFontSize(14).setBold();
		Paragraph paraheader = new Paragraph().add(t1);
		paraheader.setPaddingLeft(110);
		paraheader.setPaddingTop(20);
		doc.add(paraheader);
		// *********************************************************************************************
		// Table1 C

		Text t2 = new Text("Chiffre d'affaire").setFontSize(14).setBold();
		Paragraph paraheader2 = new Paragraph().add(t2);
		paraheader2.setPaddingLeft(230);
		paraheader2.setPaddingTop(20);
		doc.add(paraheader2);

		// ---------------------------TABLE ----------------
		Table table = new Table(2, true);
		table.setPaddingLeft(8);
		table.setPaddingTop(20);
		table.addHeaderCell(
				new Cell().setKeepTogether(true).add(new Paragraph("Date").setBold().setFontSize(14)).setWidth(50));
		table.addHeaderCell(
				new Cell().setKeepTogether(true).add(new Paragraph("Valeur").setBold().setFontSize(14)).setWidth(50));

		doc.add(table);
		for (int i = 0; i < tableCA.getItems().size(); i++) {
			Analyse f = tableCA.getItems().get(i);
			table.flush();
			table.addCell(
					new Cell().setKeepTogether(true).add(new Paragraph(f.getDate_heure()).setMargins(0, 0, 0, 0)));
			table.addCell(new Cell().setKeepTogether(true)
					.add(new Paragraph(f.getSomme().toString()).setMargins(0, 0, 0, 0)));
		}
		table.complete();

		// *********************************************************************************************
		// Table2 Caissier

		Text t3 = new Text("Caissier").setFontSize(14).setBold();
		Paragraph paraheader3 = new Paragraph().add(t3);
		paraheader3.setPaddingLeft(240);
		paraheader3.setPaddingTop(20);
		doc.add(paraheader3);

		// ---------------------------TABLE ----------------
		Table table2 = new Table(2, true);
		table2.setPaddingLeft(8);
		table2.setPaddingTop(20);
		table2.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("Caissier").setBold().setFontSize(14))
				.setWidth(200));
		table2.addHeaderCell(
				new Cell().setKeepTogether(true).add(new Paragraph("Nombre des ventes").setBold().setFontSize(14)));

		doc.add(table2);
		for (int i = 0; i < tableCaissier.getItems().size(); i++) {
			Analyse f = tableCaissier.getItems().get(i);
			table2.flush();
			table2.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getNomUtil()).setMargins(0, 0, 0, 0)));
			table2.addCell(new Cell().setKeepTogether(true)
					.add(new Paragraph(Long.toString(f.getId())).setMargins(0, 0, 0, 0)));
		}
		table2.complete();

		// *********************************************************************************************
		// Table3 produits

		Text t4 = new Text("Produits").setFontSize(14).setBold();
		Paragraph paraheader4 = new Paragraph().add(t4);
		paraheader4.setPaddingLeft(240);
		paraheader4.setPaddingTop(20);
		doc.add(paraheader4);

		// ---------------------------TABLE ----------------
		Table table3 = new Table(2, true);
		// table.setFixedPosition(70, 580, 450);
		table3.setPaddingLeft(8);
		table3.setPaddingTop(20);
		table3.addHeaderCell(new Cell().setKeepTogether(true)
				.add(new Paragraph("Nom Produit").setBold().setFontSize(14)).setWidth(200));
		table3.addHeaderCell(
				new Cell().setKeepTogether(true).add(new Paragraph("Nombre des ventes").setBold().setFontSize(14)));

		doc.add(table3);
		for (int i = 0; i < tableProduits.getItems().size(); i++) {
			Analyse f = tableProduits.getItems().get(i);
			table3.flush();
			table3.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getNomUtil()).setMargins(0, 0, 0, 0)));
			table3.addCell(new Cell().setKeepTogether(true)
					.add(new Paragraph(Long.toString(f.getId())).setMargins(0, 0, 0, 0)));
		}
		table3.complete();

		doc.close();
	}
	
	@FXML
	public void ImpventeClicked(ActionEvent event) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
			String dateF = LocalDateTime.now().format(formatter).toString();
			System.out.println("myreglage  "+myreglage);
			String t = myreglage.getHistoriques();
			t=t.replace("\\","//" );
			String nameFile = t+"//Ventes" + dateF + ".pdf";
			File file = new File(nameFile);
			file.getParentFile().mkdirs();
			manipulatePdfTickets(nameFile);
			print(nameFile);
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
	protected void manipulatePdfTickets(String dest) throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dateF = LocalDateTime.now().format(formatter).toString();

		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		Document doc = new Document(pdfDoc);

		// Creating an ImageData object
		String logoits = myreglage.getImage();   
		ImageData data = ImageDataFactory.create(logoits);
		// Creating an Image object
		com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(data);
		image.scaleToFit(200, 200);
		image.setFixedPosition(380, 780);
		doc.add(image);

		// ------------------- HEADER ----------------------------
		Paragraph ds = new Paragraph("Le " + dateF);
		ds.setBold();
		ds.setFontSize(12);
		ds.setPaddingLeft(10);
		ds.setPaddingTop(20);
		doc.add(ds);

		Text t1 = new Text(
				"\nLes ventes du période :" + datedebut2.getValue().toString() + " au " + datefin2.getValue().toString())
						.setFontSize(14).setBold();
		Paragraph paraheader = new Paragraph().add(t1);
		paraheader.setPaddingLeft(110);
		paraheader.setPaddingTop(20);
		doc.add(paraheader);
		// *********************************************************************************************
		// Table1 C

		Text t2 = new Text("Les tickets").setFontSize(14).setBold();
		Paragraph paraheader2 = new Paragraph().add(t2);
		paraheader2.setPaddingLeft(230);
		paraheader2.setPaddingTop(20);
		doc.add(paraheader2);

		// ---------------------------TABLE ----------------
		Table table = new Table(5, true);
		table.setPaddingLeft(8);
		table.setPaddingTop(20);
		table.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("Date").setBold().setFontSize(14)).setWidth(50));
		table.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("Nom et Prenom").setBold().setFontSize(14)).setWidth(50));
		table.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("N°Ticket").setBold().setFontSize(14)).setWidth(50));
		table.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("Total").setBold().setFontSize(14)).setWidth(50));
		table.addHeaderCell(new Cell().setKeepTogether(true).add(new Paragraph("TVA").setBold().setFontSize(14)).setWidth(50));
		doc.add(table);
		for (int i = 0; i < tabCommande.getItems().size(); i++) {
			Analyse f = tabCommande.getItems().get(i);
			
			table.flush();
			table.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getDate_heure()).setMargins(0, 0, 0, 0)));
			table.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getNomUtil()+" "+f.getPrenomUtil()).setMargins(0, 0, 0, 0)));
			table.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getProduit().toString().substring(f.getProduit().toString().length()-3, f.getProduit().toString().length()-1)).setMargins(0, 0, 0, 0)));
			table.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getSomme().toString()).setMargins(0, 0, 0, 0)));
			table.addCell(new Cell().setKeepTogether(true).add(new Paragraph(f.getTva().toString()).setMargins(0, 0, 0, 0)));
		}
		table.complete();

		

		doc.close();
	}

	@FXML
	void btnRechercher3Clicked(ActionEvent event) {
		LocalDate datedeb = datedebut3.getValue();
		LocalDate datef = datefin3.getValue();
		String output1 = datedeb.format(fOut);
		String output2 = datef.format(fOut);
		List<Analyse> LS = dbVente.loadAnalyseFiltred(output1, output2);
		System.out.println("size  = " + LS.size());
		tabCommande.getItems().clear();
		for (Analyse a : LS) {
			tabCommande.getItems().add(a);
		}
		tabCommande.refresh();

	}
	
	public void setReglage()
	{
         Reglage r = dbreglage.getReglage(logincontrol.getId_utilisateur());
         if (r != null)
 		{
 			myreglage=r;
 		
 		}
		
	}
	//************************************************** tout objet à imprimer doit etre declaré final
			public void print(String path) 
		    {
				path = path.replace("//","/" );
				//path = path.replace("\\\\","/" );
				System.out.println("new path a ouvrir "+path);
				try {
					FileInputStream in = new FileInputStream(path);
			    Doc doc1 = new SimpleDoc(in, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
			    PrintService service1 = PrintServiceLookup.lookupDefaultPrintService();
			        //service1.createPrintJob().print(doc1, null);
			    } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }   
}
