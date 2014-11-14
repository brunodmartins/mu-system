package dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.excepetions.DataAccessException;

public class ConnectionFactory{
	
	private static Connection con;
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost3306/test";
	private static final String usuario = "root";
	private static final String senha = "";
	
	public static Connection getConnection() throws DataAccessException {
	        try{
	            Class.forName(driver);	            
	            con = DriverManager.getConnection(url, usuario, senha);
	            return con;
	        }catch(ClassNotFoundException Driver){	        	
	            throw new DataAccessException("Driver not found!");
	        }catch(SQLException Fonte){
	        	throw new DataAccessException("Error on connection");
	        }
	}
	

	public static void close() throws SQLException {
		 con.close();
		
	}

}