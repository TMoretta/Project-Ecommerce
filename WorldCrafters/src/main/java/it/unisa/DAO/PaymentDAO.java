package it.unisa.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import it.unisa.utils.DriverManagerConnectionPool;

import java.sql.Date;

public class PaymentDAO {
	
	public int setPayment(Date date, Time time, double amount, int userId, String accountholder, String cardNumber, int expMonth, int expYear, int cvv) {
		
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    int generatedId = -1; // Inizializziamo con un valore di errore

	    try {
	        connection = DriverManagerConnectionPool.getConnection();
	        String query = "INSERT INTO payment (date, time, amount, userId, accountholder, cardNumber, expMonth, expYear, cvv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	        // Passiamo il flag Statement.RETURN_GENERATED_KEYS al PreparedStatement
	        statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        statement.setDate(1, date);
	        statement.setTime(2, time);
	        statement.setDouble(3, amount);
	        statement.setInt(4, userId);
	        statement.setString(5, accountholder);
	        statement.setString(6, cardNumber);
	        statement.setInt(7, expMonth);
	        statement.setInt(8, expYear);
	        statement.setInt(9, cvv); 
	        statement.executeUpdate();

	        // Otteniamo l'ID generato
	        resultSet = statement.getGeneratedKeys();
	        if (resultSet.next()) {
	            generatedId = resultSet.getInt(1);
	        }

	        connection.commit();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return generatedId; // Ritorniamo l'ID generato
	}
	
}