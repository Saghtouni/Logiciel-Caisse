package DataBase;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import Fenetre.CaisseControl;
import Fenetre.ProduitVenteControl;
import Fenetre.ProduitsControl;
import Fenetre.TableControler;
import Fenetre.TicketsAttenteController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Analyse;
import objects.Produits;
import objects.Vente;
import objects.produitsTable;

public class VenteDB {
	private Connection DB = null;
	Produits produit ;
	public VenteDB(Connection dB) {
		super();
		DB = dB;
	}


	private ObservableList<Analyse> Analyse = FXCollections.observableArrayList();
	private ObservableList<Analyse> AnalyseSuite = FXCollections.observableArrayList();
	private ObservableList<Vente> vente = FXCollections.observableArrayList();
	private ObservableList<produitsTable> produitsTabl = FXCollections.observableArrayList();
	private Analyse analyse;
	private static int pk;
	private static int pk2;
	private static int pk3;
	
	public void addVente(String date_heure, String nom_utilisateur, String prenom_utilisateur, String produit, Double tva, Double somme, String type,  int table) {
		 PreparedStatement stm;
		try {
			stm = DB.prepareStatement("INSERT INTO ventes (date_heure,nom_utilisateur,prenom_utilisateur,produit,tva,somme,status,modepaiement, tabl) Values( ?, ?, ?, ?, ?, ?,'valide',?,?)", Statement.RETURN_GENERATED_KEYS );
			stm.setString(1, date_heure);
			stm.setString(2, nom_utilisateur);
			stm.setString(3, prenom_utilisateur);
			stm.setString(4, produit);
			stm.setDouble(5,  tva);

			stm.setDouble(6, somme);
			stm.setString(7, type);
			stm.setInt(8, table);
			stm.executeUpdate();
			//System.out.println("stm    = "+stm );
			// RETURN les id généres automatiquement afin de l'utilisr pour inserer les details des produits dans produitcaisse
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			this.pk = rs.getInt(1);
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateVente(int id, String date_heure, String nom_utilisateur, String prenom_utilisateur, String produit, Double tva, Double somme, String type,  int table) {
		 PreparedStatement stm;
		try {
			stm = DB.prepareStatement("update ventes SET date_heure = ?,nom_utilisateur=?,prenom_utilisateur=?,produit=?,tva=?,somme=?,status='non valide',modepaiement=?, tabl=? where id="+id, Statement.RETURN_GENERATED_KEYS );
			stm.setString(1, date_heure);
			stm.setString(2, nom_utilisateur);
			stm.setString(3, prenom_utilisateur);
			stm.setString(4, produit);
			stm.setDouble(5,  tva);
			stm.setDouble(6, somme);
			stm.setString(7, type);
			stm.setInt(8, table);
			stm.executeUpdate();
			//System.out.println("stm    = "+stm );
			// RETURN les id généres automatiquement afin de l'utilisr pour inserer les details des produits dans produitcaisse
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			this.pk2 = rs.getInt(1);
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteVente(int id) {
		   PreparedStatement stm;
		try {
			stm = DB.prepareStatement("DELETE FROM ventes WHERE id="+ id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	   }

public void addProduitsV( String produit, int quantité, Double prix, Double tva, Double tvaC, int numeroVente , int nbrTable) {
	 PreparedStatement stm;
	try {
		stm = DB.prepareStatement("INSERT INTO produitscaisse (produit,prix,quantite,tva,numerovente, tabl) Values(?, ?, ?, ?,?, ?)" );
		stm.setString(1, produit);
		stm.setDouble(2, prix);
		stm.setInt(3, quantité);
		stm.setDouble(4, tva);
		stm.setInt(5, numeroVente);
		stm.setInt(6, nbrTable);
		stm.executeUpdate();
		
		stm.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public int getPk() {
	return pk;
}


public void setPk(int pk) {
	this.pk = pk;
}


public ObservableList<Vente> loadProduitsV() {
	try {
		PreparedStatement stm = DB.prepareStatement("select * from produitscaisse ORDER BY id");
		ResultSet Rs = stm.executeQuery();
		vente.clear();
		while(Rs.next()) {
			vente.add(new Vente((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(Double) Rs.getObject(3), (Integer) Rs.getObject(4), (Double) Rs.getObject(5), (Integer) Rs.getObject(6), (Integer) Rs.getObject(7)));
		}
		Rs.close();
		stm.close();
		} catch (SQLException e) {
		e.printStackTrace();	
		}
		return vente;	
}

public ObservableList<Vente> loadProduitsVS(int id) {
	try {
		vente.clear();
		PreparedStatement stm = DB.prepareStatement("select *from produitscaisse where numerovente=" +id +" ORDER BY id");
		ResultSet Rs = stm.executeQuery();
		while(Rs.next()) {
			vente.add(new Vente((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(Double) Rs.getObject(3), (Integer) Rs.getObject(4), (Double) Rs.getObject(5) ,(Integer) Rs.getObject(6),  (Integer) Rs.getObject(7)));
		}
		Rs.close();
		stm.close();
		} catch (SQLException e) {
		e.printStackTrace();	
		}
		return vente;	
}

public ObservableList<Analyse> loadAnalyse() {
	try {
		PreparedStatement stm = DB.prepareStatement("select *from ventes ORDER BY (date_heure)");
		ResultSet Rs = stm.executeQuery();
		while(Rs.next()) {
			Button button = new Button((String) Rs.getObject(5));
			button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ProduitVenteControl produitsV = new ProduitVenteControl();
					int id = Integer.parseInt(arg0.getSource().toString().substring(34).replace("'", "").replace(" ", "")); 
				
					produitsV.setProduitsV(loadProduitsVS(id));
		    		Stage primaryStage = new Stage();
					primaryStage.setScene(new Scene(produitsV));
					primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
					primaryStage.show();
				} 
            });
			Analyse.add(new Analyse((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (String) Rs.getObject(4),button , (Double) Rs.getObject(6), (Double) Rs.getObject(7), (String) Rs.getObject(9)));
	
		}
		Rs.close();
		stm.close();
		} catch (SQLException e) {
		e.printStackTrace();	
		}
		return Analyse;	
}


public ObservableList<Analyse> displayAllTickets() {
	try {
		PreparedStatement stm = DB.prepareStatement("select v.id,v.date_heure,sum(p.prix) as somme,v.produit,v.status, v.tabl from public.produitscaisse p join ventes v on p.numerovente=v.id where v.status='non valide' group by(v.id)  ");
		ResultSet Rs = stm.executeQuery();
		Analyse.clear();
		while(Rs.next()) {
			Button button = new Button((String) Rs.getObject(4));
			button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ProduitVenteControl produitsV = new ProduitVenteControl();
					int id = Integer.parseInt(arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
					produitsV.setProduitsV(loadProduitsVS(id));
		    		Stage primaryStage = new Stage();
					primaryStage.setScene(new Scene(produitsV));
					primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
					primaryStage.show();
				} 
            });
			String stat= (String) Rs.getObject(5);
			int idd = (Integer) Rs.getObject(1);
			
			Button button2 = new Button(stat);
			button2.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			
			
			
			Analyse.add(new Analyse( (String) Rs.getObject(2),(Double) Rs.getObject(3),button,button2, (Integer) Rs.getObject(6)));
		}
		Rs.close();
		stm.close();
		} catch (SQLException e) {
		e.printStackTrace();	
		}
		return Analyse;	
}

public ObservableList<Analyse> SuiteTicket(){
	try {
		PreparedStatement stm = DB.prepareStatement("select id, tabl, somme, tva from ventes where status ='non valide'");
		ResultSet Rs = stm.executeQuery();
		Analyse.clear();
		while(Rs.next()) {
			AnalyseSuite.add(new Analyse((Integer) Rs.getObject(1), (Integer) Rs.getObject(2), (Double) Rs.getObject(3), (Double) Rs.getObject(4)));
		
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}	
	return AnalyseSuite;
}

public void setProduiTable(CaisseControl caisse,  TableControler tableCont) {
	int id = 0;
	try {
		PreparedStatement stm = DB.prepareStatement("select v.id from public.produitscaisse p join ventes v on p.numerovente=v.id where v.status='non valide' and v.tabl ="+tableCont.nbrTabl);
		ResultSet Rs = stm.executeQuery();
		
		while(Rs.next()) {
			id = (Integer) Rs.getObject(1);
		}
		
		PreparedStatement stm2;
			stm2 = DB.prepareStatement("Select produit,prix,quantite,tabl FROM produitscaisse WHERE numerovente="+id);
			ResultSet Rs2 = stm2.executeQuery();
			ObservableList<Vente> item = FXCollections.observableArrayList();
			caisse.tableCaisse.getItems().clear();
			ObservableList<Produits> produits = caisse.produitsdb.load();
			
			while(Rs2.next())
			{		
				for(int i = 0; i < produits.size(); i++) {
					if(produits.get(i).getNom().equals((String)Rs2.getObject(1))){
						produit = produits.get(i);
						break;
					}
				}
				
				if((Double) Rs2.getObject(2) != 0.0)
					item.add(new Vente(0, (String)	Rs2.getObject(1),(Double) Rs2.getObject(2), (Integer) Rs2.getObject(3), produit.getTVA(), (produit.getTVA()*produit.getPrixvente()) / 100, 0,(Integer) Rs2.getObject(4) ));
				else {
					Vente vente = new Vente();
					vente.setNomProduits("     Suite! :");
					item.add(vente);
				}
					
					
			}
			
			caisse.tableCaisse.setItems(item);
			caisse.tableCaisse.refresh();
			stm2.close();
			
			/*PreparedStatement stm1;
				stm1 = DB.prepareStatement("DELETE FROM produitscaisse WHERE numerovente="+id);
				stm1.executeUpdate();
				stm1.close();
				stm1 = DB.prepareStatement("DELETE FROM ventes WHERE id="+id);
				stm1.executeUpdate();
				stm1.close();*/
			
			caisse.updateTicketDetails();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}	
}

public ObservableList<Analyse> displayAllTicketsAttente(CaisseControl caisse, TableView<objects.Analyse> tabletickets) {
	try {
		PreparedStatement stm = DB.prepareStatement("select v.id,v.date_heure,sum(p.prix) as somme,v.produit,v.status,v.tabl from public.produitscaisse p join ventes v on p.numerovente=v.id where v.status='non valide' group by(v.id)  ");
		ResultSet Rs = stm.executeQuery();
		Analyse.clear();
		while(Rs.next()) {
			Button button = new Button((String) Rs.getObject(4));
			button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ProduitVenteControl produitsV = new ProduitVenteControl();
					int id = Integer.parseInt(arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
					produitsV.setProduitsV(loadProduitsVS(id));
		    		Stage primaryStage = new Stage();
					primaryStage.setScene(new Scene(produitsV));
					primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
					primaryStage.show();
				} 
            });
			String stat= (String) Rs.getObject(5);
			int idd = (Integer) Rs.getObject(1);
			
			Button button2 = new Button(stat);
			button2.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {

						
					PreparedStatement stm2;
					try {
						stm2 = DB.prepareStatement("Select produit,prix,quantite,tabl FROM produitscaisse WHERE numerovente="+idd);
						//System.out.println("stm2  =  "+stm2);
						ResultSet Rs2 = stm2.executeQuery();
						ObservableList<Vente> item = 		FXCollections.observableArrayList();
						caisse.tableCaisse.getItems().clear();

						//System.out.println("old size "+tableCaisseT.getItems().size());
						 //Produits produit = new Produits(); <-----------
						
						 ObservableList<Produits> produits = caisse.produitsdb.load();
						 //System.out.println("size table produits "+produits.size());
						while(Rs2.next())
						{
							
							for(int i = 0; i < produits.size(); i++) {
		    					if(produits.get(i).getNom().equals((String)Rs2.getObject(1))){
		    						produit = produits.get(i);
		    						//System.out.println(produits.get(i)+"   found   "+produit.getId());
		    						break;
		    					}
							}
							//System.out.println("pT "+produit.toString());
							item.add(new Vente(0, (String)	Rs2.getObject(1),(Double) Rs2.getObject(2), (Integer) Rs2.getObject(3), produit.getTVA(), (produit.getTVA()*produit.getPrixvente()) / 100, 0,(Integer) Rs2.getObject(4) ));
							caisse.updateTicketDetails();
							
							//somme = Double.parseDouble(AfficcheurSomme.getText());
							
						}
						caisse.tableCaisse.setItems(item);
						//System.out.println("new size "+caisse.tableCaisse.getItems().size());
						caisse.tableCaisse.refresh();
						//System.out.println(" ok "+item);
						//System.out.println("focus"+tabletickets.getSelectionModel().getFocusedIndex()+"   selected "+tabletickets.getSelectionModel().getSelectedIndex());
						tabletickets.getItems().remove(tabletickets.getSelectionModel().getFocusedIndex());
						tabletickets.refresh();
						
						stm2.close();
						// suppression de laticket 
						PreparedStatement stm;
						try {
							stm = DB.prepareStatement("DELETE FROM produitscaisse WHERE numerovente="+idd);
							stm.executeUpdate();
							stm.close();
							stm = DB.prepareStatement("DELETE FROM ventes WHERE id="+idd);
							stm.executeUpdate();
							stm.close();
							//System.out.println("deleted ");
							//tabletickets.refresh();
							//Analyse=displayAllTicketsAttente(caisse,tabletickets,aff);
							Stage primaryStage = (Stage) tabletickets.getScene().getWindow();
							primaryStage.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}	
						
						caisse.updateTicketDetails();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
	    		
				} 
            });
			Button button3 = new Button("Supprimer");
			button3.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					   PreparedStatement stm;
					try {
						stm = DB.prepareStatement("DELETE FROM produitscaisse WHERE numerovente="+idd);
						stm.executeUpdate();
						stm.close();
						stm = DB.prepareStatement("DELETE FROM ventes WHERE id="+idd);
						stm.executeUpdate();
						stm.close();
						//System.out.println("deleted ");
						//ticketsAttenteController.tabletickets.refresh();
						Analyse=displayAllTicketsAttente(caisse,tabletickets);
					} catch (SQLException e) {
						e.printStackTrace();
					}	
	    		
				} 
            });
			
			Analyse.add(new Analyse( (String) Rs.getObject(2),(Double) Rs.getObject(3),button,button2,button3,(Integer) Rs.getObject(6) ));
		}
		Rs.close();
		stm.close();
		} catch (SQLException e) {
		e.printStackTrace();	
		}
		return Analyse;	
}

	public void updatestatusvalide(int id)
	{
		PreparedStatement stm;
		try {
			stm = DB.prepareStatement("UPDATE ventes SET status='valide' WHERE id=?" );
			stm.setInt(1,id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	public void updatestatusnonvalide(int id)
	{
		PreparedStatement stm;
		try {
			stm = DB.prepareStatement("UPDATE ventes SET status='non valide' WHERE id=?" );
			stm.setInt(1,id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}


	public void UpdateTVA(int id, Double tva,String msg,String type) {
		PreparedStatement stm;
		try {
			stm = DB.prepareStatement("UPDATE ventes SET tva=?,produit=?,modepaiement= ? WHERE id=?" );
			stm.setDouble(1,tva);
			stm.setString(2, msg+id);
			stm.setString(3, type);
			stm.setInt(4,id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}


	public ObservableList<objects.Analyse> displayAllTicketsUtilisateur(String nom_utilisateur, String prenom_utilisateur) throws SQLException {
		
		PreparedStatement stm = DB.prepareStatement("select v.id,sum(p.prix) as somme,v.produit,v.tva, v.tabl \r\n" + 
				"from public.produitscaisse p join ventes v on p.numerovente=v.id \r\n" + 
				"where TO_CHAR(NOW(), 'dd-mm-YYYY')=SUBSTRING(v.date_heure FROM 1 FOR 10)\r\n" + 
				"and v.nom_utilisateur=? and prenom_utilisateur=? and status=? \r\n" + 
				"group by(v.id) ");
		
		stm.setString(1,nom_utilisateur);
		stm.setString(2, prenom_utilisateur);
		stm.setString(3, "valide");
		
		ResultSet Rs = stm.executeQuery();
		//System.out.println("requete = "+stm);
		Analyse.clear();
		while(Rs.next()) {
			Button button = new Button((String) Rs.getObject(3));
			button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ProduitVenteControl produitsV = new ProduitVenteControl();
					int id = Integer.parseInt(arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
					System.out.println("ID :" +arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
					produitsV.setProduitsV(loadProduitsVS(id));
		    		Stage primaryStage = new Stage();
					primaryStage.setScene(new Scene(produitsV));
					primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
					primaryStage.show();
				} 
            });
			Double tva = (Double) Rs.getObject(4);
			int idd = (Integer) Rs.getObject(1);
			/*String stat= (String) Rs.getObject(4);
			
			Button button2 = new Button(stat);
			button2.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");*/
			if(tva != 0.0)
				Analyse.add(new Analyse( (Double) Rs.getObject(2),button, tva, (Integer) Rs.getObject(5)));
		}
		Rs.close();
		stm.close();
		
		return Analyse;	
	}
	
	
public ObservableList<objects.Analyse> displayAllTicketsUtilisateurDate(String nom_utilisateur, String prenom_utilisateur, String date) throws SQLException {
		
		PreparedStatement stm = DB.prepareStatement("select v.id,sum(p.prix) as somme,v.produit,v.tva, v.tabl \r\n" + 
				"from public.produitscaisse p join ventes v on p.numerovente=v.id \r\n" + 
				"where SUBSTRING(v.date_heure FROM 1 FOR 10)= ?\r\n" + 
				"and v.nom_utilisateur=? and prenom_utilisateur=? and status=? \r\n" + 
				"group by(v.id) ");
		
		stm.setString(1, date);
		stm.setString(2,nom_utilisateur);
		stm.setString(3, prenom_utilisateur);
		stm.setString(4, "valide");
		
		ResultSet Rs = stm.executeQuery();
		//System.out.println("requete = "+stm);
		Analyse.clear();
		while(Rs.next()) {
			Button button = new Button((String) Rs.getObject(3));
			button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ProduitVenteControl produitsV = new ProduitVenteControl();
					int id = Integer.parseInt(arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
					System.out.println("ID :" +arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
					produitsV.setProduitsV(loadProduitsVS(id));
		    		Stage primaryStage = new Stage();
					primaryStage.setScene(new Scene(produitsV));
					primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
					primaryStage.show();
				} 
            });
			Double tva = (Double) Rs.getObject(4);
			int idd = (Integer) Rs.getObject(1);
			/*String stat= (String) Rs.getObject(4);
			
			Button button2 = new Button(stat);
			button2.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");*/
			if(tva != 0.0)
				Analyse.add(new Analyse( (Double) Rs.getObject(2),button, tva, (Integer) Rs.getObject(5)));
		}
		Rs.close();
		stm.close();
		
		return Analyse;	
	}


	public ObservableList<Analyse> displayAllTicketsFiltered(String datedeb, String datef, String name, String lastName) {
		
		
		try {
			PreparedStatement stm = DB.prepareStatement("select v.id,v.date_heure,sum(p.prix) as somme,v.produit,v.status, v.tabl from public.produitscaisse p join ventes v on p.numerovente=v.id  where v.nom_utilisateur ="+ "'" +name +"'" + " AND v.prenom_utilisateur ="+ "'" +lastName +"'" + " AND SUBSTRING(v.date_heure FROM 1 FOR 10) >=? AND SUBSTRING(v.date_heure FROM 1 FOR 10) <= ? AND status='valide' group by(v.id) order by(date_heure);   ");
			
			stm.setString(1, datedeb);
			stm.setString(2, datef);
			//System.out.println(stm);
			
			ResultSet Rs = stm.executeQuery();
			Analyse.clear();
			while(Rs.next()) {
				Button button = new Button((String) Rs.getObject(4));
				button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						ProduitVenteControl produitsV = new ProduitVenteControl();
						int id = Integer.parseInt(arg0.getSource().toString().substring(34).replace("'", "").replace(" ", ""));
						produitsV.setProduitsV(loadProduitsVS(id));
			    		Stage primaryStage = new Stage();
						primaryStage.setScene(new Scene(produitsV));
						primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
						primaryStage.show();
					} 
	            });
				String stat= (String) Rs.getObject(5);
				int idd = (Integer) Rs.getObject(1);
				
				Button button2 = new Button(stat);
				button2.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
				button2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if(stat=="valide")
						{
							updatestatusnonvalide(idd);	
							button2.setText("non valide");
						}
						{
							updatestatusvalide(idd);
							button2.setText("valide");
						}		    		
					} 
	            });
				
				Analyse.add(new Analyse( (String) Rs.getObject(2),(Double) Rs.getObject(3),button,button2, (Integer) Rs.getObject(6)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return Analyse;	
			
			
			
			
	}
	
	
	public Map<String,Double> getCAFiltered(String datedeb,String datef)
	{

		
		
		Map<String,List<Double>> MCA  = new HashMap<String,List<Double>>();
		Double k=0.0;
		try {
			PreparedStatement stm = DB.prepareStatement("select somme,SUBSTRING(date_heure FROM 1 FOR 10) FROM ventes where SUBSTRING(date_heure FROM 1 FOR 10) >=? AND SUBSTRING(date_heure FROM 1 FOR 10) <= ? AND status='valide'    ");
			stm.setString(1, datedeb);
			stm.setString(2, datef);
			//System.out.println(stm);
			
			ResultSet Rs = stm.executeQuery();
			//Double x=0.0;
			while (Rs.next())
			{
				List<Double> LM = new ArrayList<Double>();
				//System.out.println("resultat  =  "+(String) Rs.getObject(2)+" somme "+(double) Rs.getObject(1));
				if (MCA.get((String) Rs.getObject(2)) ==null)
				{
					LM.add((double) Rs.getObject(1));
				}
				else
				{
					LM=MCA.get((String) Rs.getObject(2));
					LM.add((double) Rs.getObject(1));
				}
				
				MCA.put((String) Rs.getObject(2), LM);
			}

			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
		        

		Map<String,Double> RES = new HashMap<String,Double>();
        

		for (Map.Entry<String, List<Double>> x : MCA.entrySet() )
		{
			//System.out.println("Map = key : " + x.getKey() +"  value : "	+x.getValue());
			k=0.0;
			for(Double v : x.getValue())
			{
				k=k+v;
			}
			RES.put(x.getKey(), k);
			
		}
		Map<String, Double>RES2= new TreeMap<String, Double>();
        RES2.putAll(RES);
        //System.out.println("RES 2 =    "+RES2);
			return RES2;
	}
	

	public Map<String,Long> getTopCaissierFiltered(String datedeb,String datef)
	{

		
		
		Map<String,Long> res  = new TreeMap<String,Long>();
		try {
			PreparedStatement stm = DB.prepareStatement("select count(*),nom_utilisateur FROM ventes where SUBSTRING(date_heure FROM 1 FOR 10) >=? AND SUBSTRING(date_heure FROM 1 FOR 10) <= ? AND status='valide' group by (nom_utilisateur)    ");
			stm.setString(1, datedeb);
			stm.setString(2, datef);
		
			
			ResultSet Rs = stm.executeQuery();
	       while (Rs.next() && res.size()<6 )
				{
					//System.out.println("size res = "+res.size());
						if (Rs.getObject(1) != null && Rs.getObject(2) != null)
					res.put((String) Rs.getObject(2) ,(Long) Rs.getObject(1));
				}
			

			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}

       // System.out.println("Map = "+res);

		        
			return res;
	}
	
	public Map<String,Long> getTopProduitFiltered(String datedeb,String datef)
	{

		
		
		Map<String,Long> res  = new TreeMap<String,Long>();
		try {
			PreparedStatement prods= DB.prepareStatement("select distinct(nom) from public.produits ");
			//System.out.println("prods"+prods);
			
			ResultSet Rs2 = prods.executeQuery();
			List<String> ls = new ArrayList();
			while (Rs2.next() )
			{
				ls.add((String) Rs2.getObject(1));
			}
			PreparedStatement stm = DB.prepareStatement("select count(*) ,p.produit from public.produitscaisse p join ventes v on p.numerovente=v.id  where SUBSTRING(date_heure FROM 1 FOR 10) >=? AND SUBSTRING(date_heure FROM 1 FOR 10) <= ? AND v.somme>0 group by(p.produit) ");
			stm.setString(1, datedeb);
			stm.setString(2, datef);
			//System.out.println("prods"+stm);

			ResultSet Rs = stm.executeQuery();
			while (Rs.next() )
			{
				//System.out.println("size res = "+res.size());
				
				if(ls.contains(Rs.getObject(2)))
				res.put((String) Rs.getObject(2) ,(Long) Rs.getObject(1));
			}

			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}

       // System.out.println("Map = "+res);

		        
			return res;
	}


	public List<Analyse> loadAnalyseFiltred(String datef, String  datedeb) {
		List<Analyse> AS = new ArrayList<Analyse>();
		try {
			PreparedStatement stm = DB.prepareStatement("select * from ventes where SUBSTRING(date_heure FROM 1 FOR 10) >=? AND SUBSTRING(date_heure FROM 1 FOR 10) <= ? AND status = ? ORDER BY (date_heure)");
			stm.setString(1, datedeb);
			stm.setString(2, datef);
			stm.setString(3, "valide");
			System.out.println("stmfiltered  =  "+stm);
			ResultSet Rs = stm.executeQuery();
			while(Rs.next()) {
				Button button = new Button((String) Rs.getObject(5));
				button.setStyle("-fx-background-color:#c7dbd1;-fx-font-weight: bold;-fx-font-size: 24;");
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						ProduitVenteControl produitsV = new ProduitVenteControl();
						int id = Integer.parseInt(arg0.getSource().toString().substring(42).replace("'", "").replace(" ", ""));
						System.out.print("IDDD == " + id);
						produitsV.setProduitsV(loadProduitsVS(id));
			    		Stage primaryStage = new Stage();
						primaryStage.setScene(new Scene(produitsV));
						primaryStage.getIcons().add(new Image("/img/its_icoTR.png"));
						primaryStage.show();
					} 
	            });
				AS.add(new Analyse((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (String) Rs.getObject(4),button , (Double) Rs.getObject(6), (Double) Rs.getObject(7), (String) Rs.getObject(9)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
		System.out.println("Analyse ="+AS.size());
			return AS;	
		
		
		
	}

	public static int getPk2() {
		return pk2;
	}

	public static void setPk2(int pk2) {
		VenteDB.pk2 = pk2;
	}

	public void deletPrTable(int nbrTabl) {
		int id = 0;
		try {
			PreparedStatement stm = DB.prepareStatement("select v.id from public.produitscaisse p join ventes v on p.numerovente=v.id where v.status='non valide' and v.tabl ="+nbrTabl);
			ResultSet Rs = stm.executeQuery();
			
			while(Rs.next()) {
				id = (Integer) Rs.getObject(1);
			}
			stm.close();
			
			PreparedStatement stm1;
			stm1 = DB.prepareStatement("DELETE FROM produitscaisse WHERE numerovente="+id);
			stm1.executeUpdate();
			stm1.close();
			stm1 = DB.prepareStatement("DELETE FROM ventes WHERE id="+id);
			stm1.executeUpdate();
			stm1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void deletPrTabl2(int nbrTabl) {
		int id = 0;
		try {
			PreparedStatement stm = DB.prepareStatement("select v.id from public.produitscaisse p join ventes v on p.numerovente=v.id where v.status='non valide' and v.tabl ="+nbrTabl);
			ResultSet Rs = stm.executeQuery();
			
			while(Rs.next()) {
				id = (Integer) Rs.getObject(1);
			}
			stm.close();
			
			PreparedStatement stm1;
			stm1 = DB.prepareStatement("DELETE FROM ventes WHERE id="+id);
			stm1.executeUpdate();
			stm1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public String getDateH(int numeroVente) {
		String date = null;
		try {
			PreparedStatement stm = DB.prepareStatement("select date_heure from ventes where id =" +numeroVente);
			ResultSet Rs = stm.executeQuery();
			
			while(Rs.next()) {
				date = (String) Rs.getObject(1);
			}
			Rs.close();
			stm.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return date;
		
	}
	public String getPaiement(int numeroVente) {
		String type = null;
		try {
			PreparedStatement stm = DB.prepareStatement("select modepaiement from ventes where id =" +numeroVente);
			ResultSet Rs = stm.executeQuery();
			
			while(Rs.next()) {
				type = (String) Rs.getObject(1);
			}
			Rs.close();
			stm.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return type;
		
	}
	
	
	public String getUser(int numeroVente) {
		String type = null;
		try {
			PreparedStatement stm = DB.prepareStatement("select nom_utilisateur from ventes where id =" +numeroVente);
			ResultSet Rs = stm.executeQuery();
			
			while(Rs.next()) {
				type = (String) Rs.getObject(1);
			}
			Rs.close();
			stm.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return type;
		
	}
	
	public void addVenteTable(String produit, double prix, int quantite, Double tva, int table) {
		 PreparedStatement stm;
		 String sql = "INSERT INTO produitstable (produit, prix, quantite,tva, tabl) VALUES (?, ?, ?,?, ?)";
			try {
				stm = DB.prepareStatement(sql);
				stm.setString(1, produit);
				stm.setDouble(2, prix);
				stm.setInt(3, quantite);
				stm.setDouble(4, tva);
				stm.setInt(5, table);
				stm.executeUpdate();
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public ObservableList<produitsTable> loadProduitsT(int table) {
		try {
			PreparedStatement stm = DB.prepareStatement("select * from produitstable WHERE tabl="+table);
			ResultSet Rs = stm.executeQuery();
			
			vente.clear();
			while(Rs.next()) {
				produitsTabl.add(new produitsTable(  (String) Rs.getObject(2),(Double) Rs.getObject(3), (Integer) Rs.getObject(4), (Double) Rs.getObject(5), (Integer) Rs.getObject(6)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return produitsTabl;	
	}
	public void deletProduitsTabl(int table) {
		   PreparedStatement stm;
			try {
				stm = DB.prepareStatement("DELETE FROM produitstable WHERE tabl="+ table);
				stm.executeUpdate();
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	}
}
