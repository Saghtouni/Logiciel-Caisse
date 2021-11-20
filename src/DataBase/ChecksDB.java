package DataBase;

import java.sql.Connection;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DialogueAlerte.DialogueAlerteSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.CheckMenu;
import objects.Utilisateurs;

public class ChecksDB {
	
	public ChecksDB(Connection db) throws SQLException {
		super();
		this.DB=db;
		//System.out.println("from checkc "+db);
	}
	private Connection DB = null;
	private UtilisateursDB Utilisateursdb = null;
	private ObservableList<CheckMenu> dataChecks  = FXCollections.observableArrayList(); //get ALL
	private CheckMenu ChecksUtilisateur; //get Parameter 
	private ObservableList<Utilisateurs> utilisateur;
	private DialogueAlerteSql dialogueAlerteSql;
	
	public void add(long idUtilsateur){
		PreparedStatement stm1;
		try {
			stm1 = DB.prepareStatement("insert into checks values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stm1.setLong(1, idUtilsateur);
			stm1.setInt(2, 0);
			stm1.setInt(3, 0);
			stm1.setInt(4, 0);
			stm1.setInt(5, 0);
			stm1.setInt(6, 0);
			stm1.setInt(7, 0);
			stm1.setInt(8, 0);
			stm1.setInt(9, 0);
			stm1.setString(10, "");
			stm1.executeUpdate();	
			stm1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<CheckMenu> load(){
		try {
			//System.out.println("db = "+DB);
			PreparedStatement stm = DB.prepareStatement("select *from checks ");
			ResultSet Rs = stm.executeQuery();
			while(Rs.next()) {
				dataChecks.add(new CheckMenu((int) Rs.getObject(1), (int) Rs.getObject(2), (int) Rs.getObject(3), (int) Rs.getObject(4), (int) Rs.getObject(5), (int) Rs.getObject(6), (int) Rs.getObject(7), (int) Rs.getObject(8), (int) Rs.getObject(9),(String) Rs.getObject(10)));	
			}
			Rs.close();
			stm.close();	
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return dataChecks;
	}
	
	public void update(int id, int number, String name) {
		try {
			PreparedStatement stm = DB.prepareStatement("update checks set " + name +" = "+ number +"where id = " +id);
			stm.executeUpdate();
			stm.close();
			} catch (SQLException e) {
			dialogueAlerteSql = new DialogueAlerteSql(e, "Dialogue d'exception", "Regardez, la boîte de dialogue d'exception", "Problème de connexion à la base de données check devis( UtilisateurControl)!");	
			}
	}
	
	public CheckMenu getCheckUtil(int id){
		try { 
			PreparedStatement stm = DB.prepareStatement("select *from checks where id = "+ id );
			ResultSet Rs = stm.executeQuery();
			while(Rs.next()) {
				ChecksUtilisateur = new CheckMenu((int) Rs.getObject(1), (int) Rs.getObject(2), (int) Rs.getObject(3), (int) Rs.getObject(4), (int) Rs.getObject(5), (int) Rs.getObject(6), (int) Rs.getObject(7), (int) Rs.getObject(8), (int) Rs.getObject(9),(String) Rs.getObject(10));	
			}
			Rs.close();
			stm.close();	
		} catch (SQLException e) {
			e.printStackTrace();		
		}	
		return ChecksUtilisateur;	
	}
	
	public void delete(int id) {	
		PreparedStatement stm;
		try {
			stm = DB.prepareStatement("DELETE FROM checks WHERE id="+ id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
				
		}
	}
	
	public void setRemarque(int id, String remarque) {
		if(remarque == null) 
			remarque = "";
		String update = "UPDATE checks SET remarque = WHERE utilisateur = " + id;
		try {
			PreparedStatement stm = DB.prepareStatement("update checks set remarque =" + "'" + remarque + "'" + " where id =" +id);
			stm.executeUpdate();
			stm.close();
			} catch (SQLException e) {
			dialogueAlerteSql = new DialogueAlerteSql(e, "Dialogue d'exception", "Regardez, la boîte de dialogue d'exception", "Problème de connexion à la base de données check devis( UtilisateurControl)!");
			}
		
	}
	
	public String getRemarque(int id) {
		String Remarque = null;
		try {
			PreparedStatement stm = DB.prepareStatement("SELECT remarque FROM checks WHERE id = "+ id );
			ResultSet Rs = stm.executeQuery();
			while(Rs.next()) {
				Remarque = (String) Rs.getObject(1);
			}
			Rs.close();
			stm.close();
			}catch (SQLException e) {
			dialogueAlerteSql = new DialogueAlerteSql(e, "Dialogue d'exception", "Regardez, la boîte de dialogue d'exception", "Problème de connexion à la base de données check devis( UtilisateurControl)!");
			}
		return Remarque;
	}
}
