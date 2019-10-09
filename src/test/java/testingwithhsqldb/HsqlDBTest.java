package testingwithhsqldb;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;


public class HsqlDBTest {
	private static DataSource myDataSource;
	private static Connection myConnection ;
	
	private DAO myObject;
	
	@Before
	public  void setUp() throws IOException, SqlToolError, SQLException {
		// On crée la connection vers la base de test "in memory"
		myDataSource = getDataSource();
		myConnection = myDataSource.getConnection();
		// On crée le schema de la base de test
		executeSQLScript(myConnection, "schema.sql");
		// On y met des données
		executeSQLScript(myConnection, "bigtestdata.sql");		

            	myObject = new DAO(myDataSource);
	}
	
	private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
		// On initialise la base avec le contenu d'un fichier de test
		String sqlFilePath = HsqlDBTest.class.getResource(filename).getFile();
		SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

		sqlFile.setConnection(connexion);
		sqlFile.execute();
		sqlFile.closeReader();		
	}
		
	@After
	public void tearDown() throws IOException, SqlToolError, SQLException {
		myConnection.close(); // La base de données de test est détruite ici
             	myObject = null; // Pas vraiment utile

	}

	@Test
	public void findExistingCustomer() throws SQLException {
		String name = myObject.nameOfCustomer(0);
		assertNotNull("Customer exists, name should not be null", name);
		assertEquals("Bad name found !", "Steel", name);
	}

	@Test
	public void nonExistingCustomerReturnsNull() throws SQLException {
		String name = myObject.nameOfCustomer(-1);
		assertNull("name should be null, customer does not exist !", name);
	}
        
        @Test
        public void addProductTest() throws SQLException{
            assertEquals(1, myObject.addProduct(999, "GNEUGNEU", 852));
        }
        
        @Test(expected = SQLException.class)
        public void addProductTest1() throws SQLException{
            assertEquals(0, myObject.addProduct(998, "GNEUGNEU2", -1));
        }

        @Test
        public void findProductTest() throws SQLException{
            assertEquals(54, myObject.findProduct(0).getPrice());
            assertEquals(null, myObject.findProduct(-951));
        }
        
	public static DataSource getDataSource() {
		org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}
        
}
