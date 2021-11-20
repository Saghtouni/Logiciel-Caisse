package DataBase;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDB {
	Connection db =null;
	String url = "jdbc:postgresql://185.201.8.52:5432/patte2";
    String username = "postgres";
    String password = "admin.002";
	
	public Connection getDb() throws SQLException {
		return db;
	}
	public void setDb(Connection db) {
		this.db = db;
	}
	public ConnectionDB() throws SQLException
	{
		super();
		db=this.connect();
	}
	public Connection connect() throws SQLException {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/restaurant", "postgres", "souad002");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}


        /*
        db= DriverManager.getConnection(url, username, password);
    

        return db;*/
	}
}

