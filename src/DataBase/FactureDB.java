package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Client;
import objects.Facture;
import objects.ItemFacture;

public class FactureDB {

	private Connection DB = null;
	private ObservableList<Facture> data = FXCollections.observableArrayList();

	public FactureDB(Connection dB2) {
		DB=dB2;
	}

	public void add(ObservableList<ItemFacture> itemsF, String date, Double totalnet, Double tva, Double totalbrut,
			Integer client, String remarque) throws SQLException {

		PreparedStatement stm2;
		int idrecup = 0;
		// data.add(new Facture(date, totalnet, tva, totalbrut, client, remarque));
		try {
			stm2 = DB.prepareStatement(
					"INSERT INTO Facture (date, totalnet, tva, totalbrut, client, remarque) Values(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			stm2.setString(1, date);
			stm2.setDouble(2, totalnet);
			stm2.setDouble(3, tva);
			stm2.setDouble(4, totalbrut);
			stm2.setInt(5, client);
			stm2.setString(6, remarque);
			stm2.executeUpdate();
			// RETURN les id généres automatiquement afin de l'utilisr pour inserer les
			// details des produits dans produitcaisse
			ResultSet rs = stm2.getGeneratedKeys();
			rs.next();
			idrecup = rs.getInt(1);
			//System.out.println("id facture crée"+idrecup);
			stm2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PreparedStatement stm = null;
		
		for (ItemFacture i : itemsF) {
			stm = DB.prepareStatement("INSERT INTO itemfacture (nom, quantite, prixunitaire, prittotal, numerofacture, tva) Values(?,?,?,?,?,?)");
			stm.setString(1, i.getNom());
			stm.setInt(2, i.getQuantite());
			stm.setDouble(3, i.getPrixunitaire());
			stm.setDouble(4, i.getPrixtotal());
			stm.setInt(5, idrecup);
			stm.setDouble(6, (i.getTva()*i.getQuantite()/100));
			//System.out.println("requete  = "+stm);
			stm.executeUpdate();

		}
		stm.close();

	}
	public Integer getFactureLastId() throws SQLException
	{
		Integer x=0;
		
		PreparedStatement stm = DB.prepareStatement("SELECT id FROM Facture ORDER BY ID DESC LIMIT 1");
		ResultSet Rs = stm.executeQuery();	
		while(Rs.next()) {
			x=(Integer) Rs.getObject(1)+1;
		}
		Rs.close();
		stm.close();	
		return x;
	}
	
	/*
	public Facture getFactureById(int idd) throws SQLException
	{
		Facture c = new Facture();
		PreparedStatement stm = DB.prepareStatement("select *  from facture where id= ?");
		stm.setInt(1, idd);
		ResultSet Rs = stm.executeQuery();	
		while(Rs.next()) {
			c.setId((Integer) Rs.getObject(1));
		}
		Rs.close();
		stm.close();	
		return c;
	}
*/
}
