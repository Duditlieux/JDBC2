package testingwithhsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DAO {
	private final DataSource myDataSource;
	
	public DAO(DataSource dataSource) {
		myDataSource = dataSource;
	}

	/**
	 * Renvoie le nom d'un client à partir de son ID
	 * @param id la clé du client à chercher
	 * @return le nom du client (LastName) ou null si pas trouvé
	 * @throws SQLException 
	 */
	public String nameOfCustomer(int id) throws SQLException {
            String result = null;

            String sql = "SELECT LastName FROM Customer WHERE ID = ?";
            try (Connection myConnection = myDataSource.getConnection(); 
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
                statement.setInt(1, id); // On fixe le 1° paramètre de la requête
                try ( ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                        // il y a au plus un enregistrement)
                        // On récupère les champs de l'enregistrement courant
                        result = resultSet.getString("LastName");
                    }
                }
            }
            // dernière ligne : on renvoie le résultat
            return result;
	}
	
        public int addProduct(int id, String name, int price)throws SQLException{
            String sql = "INSERT INTO Product VALUES(?,?,?);";
            try (Connection myConnection = myDataSource.getConnection(); 
		PreparedStatement statement = myConnection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setInt(3, price);
                int temp = statement.executeUpdate();
                System.out.println(temp);
                return temp;
            }
        }
        
        public Product findProduct(int id)throws SQLException{
            Product result = null;

            String sql = "SELECT * FROM product WHERE ID = ?";
            try (Connection myConnection = myDataSource.getConnection(); 
                PreparedStatement statement = myConnection.prepareStatement(sql)) {
                statement.setInt(1, id); // On fixe le 1° paramètre de la requête
                try ( ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // est-ce qu'il y a un résultat ? (pas besoin de "while", 
                        // il y a au plus un enregistrement)
                        // On récupère les champs de l'enregistrement courant
                        String name = resultSet.getString("Name");
                        int price = (int)(resultSet.getDouble("Price")*10);
                        result = new Product(name, price);
                    }
                }
            }
            // dernière ligne : on renvoie le résultat
            return result;
        }
        
}
