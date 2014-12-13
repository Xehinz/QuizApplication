package persistency;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

abstract class DBTemplate {
	
	String JDBCConnectionString;	
	
	DBTemplate(String jdbcConnectionString) {
		this.JDBCConnectionString = jdbcConnectionString;
	}
	
	protected abstract String getLeesQuery();
	
	protected abstract <T> T maakObject(Object[] rij);
	
	protected abstract <T> String getSchrijfStatement(T object) throws IOException;
	
	protected abstract String getDeleteStatement();
	
	public <T> ArrayList<T> lees() throws IOException {
		
		ArrayList<T> objecten = new ArrayList<T>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException cnfEx) {
			throw new IOException("jdbc Driver niet gevonden", cnfEx);
		} catch (Exception ex) {
			throw new IOException("Fout bij het registreren van de Driver:\n" + ex.getMessage(), ex);
		}
		
		try (Connection connection = DriverManager.getConnection(JDBCConnectionString);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(getLeesQuery())) {
			
			ResultSetMetaData metaData = resultSet.getMetaData();
			Object[] rij = new Object[metaData.getColumnCount()];
			
			while (resultSet.next()) {
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					rij[i] = resultSet.getObject(i + 1);
				}
				objecten.add((T)maakObject(rij));
			}
			
			return objecten;
			
		} catch (Exception ex) {
			throw new IOException("Fout bij het lezen van data uit de database:\n" + ex.getMessage(), ex);
		}
	}
	
	public <T> void schrijf(ArrayList<T> objecten) throws IOException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException cnfEx) {
			throw new IOException("jdbc Driver niet gevonden", cnfEx);
		} catch (Exception ex) {
			throw new IOException("Fout bij het registreren van de Driver:\n" + ex.getMessage(), ex);
		}
		
		try (Connection connection = DriverManager.getConnection(JDBCConnectionString);
				Statement statement = connection.createStatement()) {
			
			statement.executeUpdate(getDeleteStatement());
			
			for (T object : objecten) {
				statement.executeUpdate(getSchrijfStatement(object));
			}
			
		} catch (Exception ex) {
			throw new IOException("Fout bij het schrijven van data naar de database:\n" + ex.getMessage(), ex);
		}
		
	}

}
