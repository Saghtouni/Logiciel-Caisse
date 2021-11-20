package DataBase;

import java.awt.List;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DialogueAlerte.DialogueAlerteSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.stage.Stage;
import objects.Utilisateurs;
import objects.Adminstrateur;
import objects.CheckMenu;

public class UtilisateursDB   {
	
	
	static long id;
	public UtilisateursDB(Connection DB) throws SQLException {
		super();
		this.DB = DB;
	}

	public Connection getDB() {
		return DB;
	}

	public void setDB(Connection dB) {
		DB = dB;
	}

	private Connection DB = null;
	private ObservableList<Utilisateurs> data = FXCollections.observableArrayList();
	private Utilisateurs utilisateur;
	private DialogueAlerteSql dialogueAlerteSql ;

	public ObservableList<Utilisateurs> load(){
		try {
			//System.out.println("1st connection");
			PreparedStatement stm = DB.prepareStatement("select * from utilisateur");
			ResultSet Rs = stm.executeQuery();
			while(Rs.next()) {
				data.add(new Utilisateurs((int) Rs.getObject(1), (String) Rs.getObject(2), (String) Rs.getObject(3), (String) Rs.getObject(4), (String) Rs.getObject(5), (String) Rs.getObject(6), (int) Rs.getObject(7),(String) Rs.getObject(8), (String) Rs.getObject(9) , (String) Rs.getObject(10), (int) Rs.getObject(11), (String) Rs.getObject(12)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			return data;
   }
	  public ObservableList<Utilisateurs> searchUser(String s) throws SQLException{
		  	try {
				
				PreparedStatement stm = DB.prepareStatement("select * from utilisateur where lower(nom) like '%"+s+"%' OR lower(prenom) LIKE '%"+s+"%' ");
				//System.out.println("requete  =  "+stm);
				ResultSet Rs = stm.executeQuery();
				while(Rs.next()) 
				{
					data.add(new Utilisateurs((int) Rs.getObject(1), (String) Rs.getObject(2), (String) Rs.getObject(3), (String) Rs.getObject(4), (String) Rs.getObject(5), (String) Rs.getObject(6), (int) Rs.getObject(7),(String) Rs.getObject(8), (String) Rs.getObject(9) , (String) Rs.getObject(10), (int) Rs.getObject(11), (String) Rs.getObject(12)));
				}
				Rs.close();
				stm.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				return data;
	        
	    }

   public void add( String nom,  String prenom, String pseudo,  String email,  String mot_passe,  int telephone, String date_naissance, String pays, String adresse, int code_postal, String ville) {
	   PreparedStatement stm;
	   data.add(new Utilisateurs(nom, prenom, pseudo, email, mot_passe, telephone, date_naissance, pays, adresse, code_postal, ville));
			try {
				stm = DB.prepareStatement("INSERT INTO utilisateur (nom, prenom, pseudo, email, mot_passe, telephone, date_naissance, pays, adresse, code_postal, ville) Values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );
				stm.setString(1, nom);
				stm.setString(2, prenom);
				stm.setString(3, pseudo);
				stm.setString(4, email);
				stm.setString(5, mot_passe);
				stm.setInt(6, telephone);
				stm.setString(7, date_naissance);
				stm.setString(8, pays);
				stm.setString(9, adresse);
				stm.setInt(10, code_postal);
				stm.setString(11, ville);
				stm.executeUpdate();
				stm.close();
			} catch (SQLException e) {
				e.toString();
			}  
   }
   public long getLAstID() {
	   
	   try {
			
			PreparedStatement stm = DB.prepareStatement("select nextval('utilisateur_id_seq'::regclass)");
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
   public void update(int id, String nom,  String prenom, String pseudo,  String email,  String mot_passe,  int telephone, String date_naissance, String pays, String adresse, int code_postal, String ville) {
	   PreparedStatement stm;
	   try {
		    stm = DB.prepareStatement("UPDATE utilisateur SET id = ?, nom = ?, prenom = ?, pseudo = ?, email = ?, mot_passe = ?, telephone = ?, date_naissance = ?, pays = ?, adresse = ?, code_postal = ?, ville = ? WHERE id ="+ id);
			stm.setInt(1, id);
			stm.setString(2, nom);
			stm.setString(3, prenom);
			stm.setString(4, pseudo);
			stm.setString(5, email);
			stm.setString(6, mot_passe);
			stm.setInt(7, telephone);
			stm.setString(8, date_naissance);
			stm.setString(9, pays);
			stm.setString(10, adresse);
			stm.setInt(11, code_postal);
			stm.setString(12, ville);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.toString();
		}
   }
   
   public Utilisateurs get(int id) {  
	   try {
			PreparedStatement stm = DB.prepareStatement("select *from utilisateur where id= "+ id );
			ResultSet Rs = stm.executeQuery();	
			while(Rs.next()) {
				utilisateur = new Utilisateurs((int) Rs.getObject(1), (String) Rs.getObject(2), (String) Rs.getObject(3), (String) Rs.getObject(4), (String) Rs.getObject(5), (String) Rs.getObject(6), (int) Rs.getObject(7), (String) Rs.getObject(8), (String) Rs.getObject(9) , (String) Rs.getObject(10), (int) Rs.getObject(11), (String) Rs.getObject(12));	
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return utilisateur;
   		}
   
   public void delete(int id) {
	   PreparedStatement stm;
	try {
		stm = DB.prepareStatement("DELETE FROM utilisateur WHERE id="+ id);
		stm.executeUpdate();
		stm.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}		
   }
}
