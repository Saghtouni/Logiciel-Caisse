package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DialogueAlerte.DialogueAlerteSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.ItemFacture;
import objects.Produits;
import objects.Utilisateurs;

public class ProduitsDB {
	
	private Connection DB = null;

	public ProduitsDB(Connection dB) {
		super();
		DB = dB;
	}

	private ObservableList<Produits> data = FXCollections.observableArrayList();
	private ObservableList<String> type = FXCollections.observableArrayList();
	private ObservableList<String> NomProduit = FXCollections.observableArrayList();
	private ObservableList<String> souType = FXCollections.observableArrayList();
	private ObservableList<Produits> dataType = FXCollections.observableArrayList();
	private Produits produits;
	private DialogueAlerteSql dialogueAlerteSql;
	
	public ObservableList<Produits> load(){
		data.clear(); 
		try {
			PreparedStatement stm = DB.prepareStatement("select *from produits ORDER BY nom DESC");
			ResultSet Rs = stm.executeQuery();
			while(Rs.next()) {
				data.add(new Produits((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (Double) Rs.getObject(4), (Double) Rs.getObject(5), (String) Rs.getObject(6), (Integer) Rs.getObject(7),  (String) Rs.getObject(8),(Double) Rs.getObject(9), (String) Rs.getObject(10), (String) Rs.getObject(11), (String) Rs.getObject(13) ));
				
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return data;	
   }
	   public long getLAstID() {
		   long id = 0;
		   try {
				
				PreparedStatement stm = DB.prepareStatement("select nextval('produits_id_seq'::regclass)");
				//System.out.println("requete  =  "+stm);
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) 
				{
					id = (long) Rs.getObject(1);
					System.out.println(id);
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
		   return id;
	   }
	
	public void add( String reference,  String nom, Double prixA,  Double prixV,  String type, int quantite,  String fornisseur, Double tva, String image, String code_bare, String sousType ) {
		   PreparedStatement stm;
				try {
					stm = DB.prepareStatement("INSERT INTO produits (reference, nom, prixAchat, prixVente, type, quantite, fornisseur, tva, image, code_bare, soustype) Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );
					stm.setString(1, reference);
					stm.setString(2, nom);
					stm.setDouble(3, prixA);
					stm.setDouble(4, prixV);
					stm.setString(5,  type);
					stm.setInt(6, quantite);
					stm.setString(7, fornisseur);
					stm.setDouble(8, tva);
					stm.setString(9, image);
					stm.setString(10, code_bare);
					stm.setString(11, sousType);
					stm.executeUpdate();
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				load();
	 	}
	
	 public void update(int id, String reference,  String nom, Double prixA,  Double prixV,  String type, int quantite,  String fornisseur, Double tva, String image, String code_bare, String sousType) {
		   PreparedStatement stm;
		  
			try {
					//System.out.println("OK");
					stm = DB.prepareStatement("UPDATE produits SET  reference = ?, nom = ?, prixachat = ?, prixvente = ?, type = ?, quantite = ?, fornisseur = ?, tva = ?,image = ?, code_bare = ?, soustype = ? WHERE id =" + id );
					stm.setString(1, reference);
					stm.setString(2, nom);
					stm.setDouble(3, prixA);
					stm.setDouble(4, prixV);
					stm.setString(5,  type);
					stm.setInt(6, quantite);
					stm.setString(7, fornisseur);
					stm.setDouble(8, tva);
					stm.setString(9, image);
					stm.setString(10, code_bare);
					stm.setString(11, sousType);
					stm.executeUpdate();
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	   }
	 
	 public Produits get(int id) {
		   try { 
				PreparedStatement stm = DB.prepareStatement("select *from produits where id= "+ id );
				ResultSet Rs = stm.executeQuery();	
				while(Rs.next()) {
					produits = new Produits((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (Double) Rs.getObject(4), (Double) Rs.getObject(5), (String) Rs.getObject(6), (Integer) Rs.getObject(7),  (String) Rs.getObject(8),(Double) Rs.getObject(9), (String) Rs.getObject(10), (String) Rs.getObject(11), (String) Rs.getObject(13));	
				}
				Rs.close();
				stm.close();	
				} catch (SQLException e) {
				e.printStackTrace();	
				}
				return produits; 
	   		}
	 
	 public ObservableList<String> getSouType(String name) {
		   try { 
				PreparedStatement stm = DB.prepareStatement("select DISTINCT sousType from produits  where type= "+ "'" +name +"'" +"and sousType <> ''");
				ResultSet Rs = stm.executeQuery();	
				
				while(Rs.next()) {
					
						souType.add((String) Rs.getObject(1));
						System.out.println((String) Rs.getObject(1));
					
						
				}
				Rs.close();
				stm.close();	
				} catch (SQLException e) {
				e.printStackTrace();	
				}
				return souType; 
	   		}
	 
	 public ObservableList<String> getProduits(String name) {
		   try { 
				PreparedStatement stm = DB.prepareStatement("select DISTINCT nom from produits  where sousType= "+ "'" +name +"'");
				ResultSet Rs = stm.executeQuery();	
				
				while(Rs.next()) {
					
						souType.add((String) Rs.getObject(1));
						System.out.println((String) Rs.getObject(1));
					
						
				}
				Rs.close();
				stm.close();	
				} catch (SQLException e) {
				e.printStackTrace();	
				}
				return souType; 
	   		}
	 public Produits getDivers() {
		   try { 
				PreparedStatement stm = DB.prepareStatement("select *from produits where nom='Article' " );
				ResultSet Rs = stm.executeQuery();	
				while(Rs.next()) {
					produits = new Produits((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (Double) Rs.getObject(4), (Double) Rs.getObject(5), (String) Rs.getObject(6), (Integer) Rs.getObject(7),  (String) Rs.getObject(8),(Double) Rs.getObject(9), (String) Rs.getObject(10), (String) Rs.getObject(11), (String) Rs.getObject(13));	
				}
				Rs.close();
				stm.close();	
				} catch (SQLException e) {
				e.printStackTrace();	
				}
				return produits; 
	   		}
	 public void delete(int id) {
		   PreparedStatement stm = null;
		try {
			stm = DB.prepareStatement("DELETE FROM produits WHERE id="+ id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			dialogueAlerteSql = new DialogueAlerteSql(e, "Dialogue d'exception", "Regardez, la boîte de dialogue d'exception", "problème de connexion à la base de données!");
		} 
	 }
	 
	 public ObservableList<String> getType(){
			try {
				type.clear();
				PreparedStatement stm = DB.prepareStatement("SELECT DISTINCT type FROM produits ORDER BY type ASC ");
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) {
					type.add((String) Rs.getObject(1));
					System.out.println("type Base: " +(String) Rs.getObject(1) );
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
			//System.out.println("size  = "+type.size());
				return type;	
	   }
	 
	 public ObservableList<String> getNomProduits(String type){
			try {
				PreparedStatement stm = DB.prepareStatement("SELECT distinct nom FROM produits WHERE type ="+ "'" +type +"'" + "ORDER BY nom DESC");
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) {
					NomProduit.add((String) Rs.getObject(1));
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				return NomProduit;	
	   }
	 
	 public Produits getName(String nomProduit) {
		   try {
				PreparedStatement stm = DB.prepareStatement("select *from produits where nom= "+ "'" + nomProduit + "'" );
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) {
					produits = new Produits((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (Double) Rs.getObject(4), (Double) Rs.getObject(5), (String) Rs.getObject(6), (Integer) Rs.getObject(7),  (String) Rs.getObject(8),(Double) Rs.getObject(9), (String) Rs.getObject(10), (String) Rs.getObject(11), (String) Rs.getObject(13));	
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				return produits;
	   		}
	 
	  public ObservableList<Produits> searchProduits(String s) throws SQLException{
		  	try {
				
				PreparedStatement stm = DB.prepareStatement("select * from produits where lower(nom) like '%"+s.toLowerCase()+"%' ");
				//System.out.println("requete  =  "+stm);
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) 
				{
					data.add(new Produits((Integer) Rs.getObject(1),  (String) Rs.getObject(2),(String) Rs.getObject(3), (Double) Rs.getObject(4), (Double) Rs.getObject(5), (String) Rs.getObject(6), (Integer) Rs.getObject(7),  (String) Rs.getObject(8),(Double) Rs.getObject(9), (String) Rs.getObject(10), (String) Rs.getObject(11), (String) Rs.getObject(13)));
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				return data;
	        
	    }
	  public ItemFacture getPrixTVA(String s) throws SQLException{
		  ItemFacture x = new ItemFacture();
		  	try {
				
				PreparedStatement stm = DB.prepareStatement("select prixvente,tva from produits where nom like '"+s+"' ");
				//System.out.println("requete  =  "+stm);
				ResultSet Rs = stm.executeQuery();
				
				while(Rs.next()) 
				{
					x.setPrixunitaire((Double) Rs.getObject(1));
					x.setTva((Double) Rs.getObject(2));

				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				return x;
	        
	    }
	  public String getCategorie(String nom){
		  String f="";
			try {
				PreparedStatement stm = DB.prepareStatement("SELECT distinct type FROM produits WHERE nom ="+ "'" +nom +"' ");
				//System.out.println("requete  =  "+stm);
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) {
					f=(String) Rs.getObject(1);
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				return f;	
	   }

	  
}
