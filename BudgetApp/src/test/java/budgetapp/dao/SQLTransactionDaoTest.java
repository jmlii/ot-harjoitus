package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for class SQLTransactionDao
 */
public class SQLTransactionDaoTest {
    
    Connection connection;
    CategoryDao cDao;
    TransactionDao tDao;
    
    public SQLTransactionDaoTest() {
    }
        
    @Before
    public void setUp() throws SQLException, Exception {
        // Tests use H2 in-memory database, not the actual database which the application uses 
        // In-memory database only keeps the contents while the connection is open and do not save it for another session
        connection = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
        cDao = new SQLCategoryDao(connection);
        tDao = new SQLTransactionDao(connection, cDao);
        cDao.create(new Category("Income", true)); 
        cDao.create(new Category("Expense"));
        tDao.create(new Transaction(cDao.read(1), "setup test transaction 1", 100, LocalDate.of(1900, 01, 01)));
        tDao.create(new Transaction(cDao.read(2), "setup test transaction 2", 200, LocalDate.of(1900, 01, 31)));
    }

    @Test
    public void transactionTableExistsInDatabase() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SHOW TABLES");
        ResultSet rs = stmt.executeQuery();
        boolean exists = false;
        while (rs.next()) {
            if (rs.getString(1).equalsIgnoreCase("transaction")) {
                exists = true;
            }
        }
        assertTrue(exists);
    }
    
    @Test
    public void IncomeTransactionIsCreatedAndReadCorrectly() throws Exception {
        tDao.create(new Transaction(cDao.read(1), "test transaction", 300, LocalDate.of(1900, 12, 31)));
        Transaction t = tDao.read(3);
        assertEquals("test transaction", t.getDescription());
        assertEquals(300, t.getAmount());
        assertEquals("1900-12-31", t.getDate().toString());
        assertEquals("Income", t.getCategory().toString());
    }
    
    @Test
    public void updateGetsCorrectTransactionAndUpdates() throws Exception {
        tDao.create(new Transaction(cDao.read(1), "test transaction", 300, LocalDate.of(1900, 12, 31)));
        Transaction t = tDao.read(3);
        t.setCategory(cDao.read(2));
        t.setDescription("updated test transaction");
        t.setAmount(1500); 
        t.setDate(LocalDate.of(1900, 06, 15));
        tDao.update(t);
        assertEquals("updated test transaction", tDao.read(3).getDescription());
        assertEquals(1500, tDao.read(3).getAmount());
        assertEquals("1900-06-15", tDao.read(3).getDate().toString());
        assertEquals("Expense", tDao.read(3).getCategory().toString());
    }
    
    @Test 
    public void deleteDeletesCorrectTransaction() throws Exception {
        tDao.create(new Transaction(cDao.read(1), "test transaction", 300, LocalDate.of(1900, 12, 31)));
        assertTrue(tDao.read(3) != null);
        tDao.delete(3);
        assertTrue(tDao.read(3) == null);
    }
    
    @Test
    public void transactionsAreListedCorrectly() throws Exception {
        List<Transaction> list = tDao.listAll();
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals("Income", list.get(0).getCategory().getName());        
        assertEquals("setup test transaction 1", list.get(0).getDescription());
        assertEquals(2, list.get(1).getId());        
        assertEquals("Expense", list.get(1).getCategory().getName());
        assertEquals(200, list.get(1).getAmount());
    }
    
    @Test
    public void transactionsAreListedCorrectlyByCategory() throws Exception {
        tDao.create(new Transaction(cDao.read(1), "test transaction", 300, LocalDate.of(1900, 12, 31)));
        List<Transaction> list = tDao.listFromCategoryInDateOrder(cDao.read(1));
        assertEquals(2, list.size());
        assertEquals(3, list.get(0).getId());
        assertEquals("Income", list.get(0).getCategory().getName());        
        assertEquals("test transaction", list.get(0).getDescription());
        assertEquals(1, list.get(1).getId());        
        assertEquals("Income", list.get(1).getCategory().getName());
        assertEquals("setup test transaction 1", list.get(1).getDescription());
        assertEquals(100, list.get(1).getAmount());
    }
    
    @Test
    public void listInDateOrderReturnsListInCorrectOrder() throws Exception {
        tDao.create(new Transaction(cDao.read(1), "test transaction", 300, LocalDate.of(1900, 12, 31)));
        List<Transaction> list = tDao.listInDateOrder();
        assertEquals(3, list.size());
        assertEquals(3, list.get(0).getId());
        assertEquals("Income", list.get(0).getCategory().getName());        
        assertEquals("test transaction", list.get(0).getDescription());
        assertEquals(2, list.get(1).getId());
        assertEquals("Expense", list.get(1).getCategory().getName());
        assertEquals("setup test transaction 2", list.get(1).getDescription());
        assertEquals(200, list.get(1).getAmount());
        assertEquals(1, list.get(2).getId());        
        assertEquals("Income", list.get(2).getCategory().getName());
        assertEquals("setup test transaction 1", list.get(2).getDescription());
        assertEquals(100, list.get(2).getAmount());
    }
    
}
