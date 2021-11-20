package DataBase;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Client;
import objects.Produits;

public class ClientsDB {
	
	private Connection DB = null;
	private ObservableList<Client> data = FXCollections.observableArrayList();

	
	public ClientsDB(Connection dB) {
		super();
		DB = dB;
	}
	

	public int add(String nomprenom,  long telephone,  String adresse) {
		   PreparedStatement stm;
		   int idrecup = 0;
				try {
					stm = DB.prepareStatement("INSERT INTO client (nom,telephone,adresse) Values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS );
					stm.setString(1, nomprenom);
					stm.setLong(2, telephone);
					stm.setString(3, adresse);

					stm.executeUpdate();
					// RETURN les id généres automatiquement afin de l'utilisr pour inserer les details des produits dans produitcaisse
					ResultSet rs = stm.getGeneratedKeys();
					rs.next();
					idrecup = rs.getInt(1);
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return idrecup;
	 	}
	public ObservableList<Client> load(){
		try {
			PreparedStatement stm = DB.prepareStatement("select *from client");
			ResultSet Rs = stm.executeQuery();
			data.clear();
			while(Rs.next()) {
				data.add(new Client ((Integer) Rs.getObject(1),  (String) Rs.getObject(2), (String) Rs.getObject(3),(Long) Rs.getObject(4)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return data;	
   }
	
	
	public ObservableList<Client> SearchByNameORAdresse(String s){
		try {
			PreparedStatement stm = DB.prepareStatement("select *  from client where lower(nom) like '%"+s.toLowerCase()+"%' OR lower(adresse) like '%"+s.toLowerCase()+"%'");
			//System.out.println("req = "+stm);
			ResultSet Rs = stm.executeQuery();
			data.clear();
			while(Rs.next()) {
				data.add(new Client ((Integer) Rs.getObject(1),  (String) Rs.getObject(2), (String) Rs.getObject(3),(Long) Rs.getObject(4)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return data;	
   }
	

	public Client getClientById(int idd) throws SQLException
	{
			Client c = new Client();
			PreparedStatement stm = DB.prepareStatement("select *  from client where id= ?");
			stm.setInt(1, idd);
			ResultSet Rs = stm.executeQuery();	
			while(Rs.next()) {
				c.setId(idd);
				c.setNom((String) Rs.getObject(2));
				c.setAdresse((String) Rs.getObject(3));
				c.setTelephone((Long) Rs.getObject(4));
			}
			Rs.close();
			stm.close();	

			return c;	
		
	}


	public ObservableList<Client> SearchPhone( Long s) {
		try {
			PreparedStatement stm = DB.prepareStatement("select *  from client where CAST(telephone AS varchar(30))  like '%"+s+"%'");
			//System.out.println("req = "+stm);
			ResultSet Rs = stm.executeQuery();
			data.clear();
			while(Rs.next()) {
				data.add(new Client ((Integer) Rs.getObject(1),  (String) Rs.getObject(2), (String) Rs.getObject(3),(Long) Rs.getObject(4)));
			}
			Rs.close();
			stm.close();
			} catch (SQLException e) {
			e.printStackTrace();	
			}
			return data;
	}
	
	
	
	

}
