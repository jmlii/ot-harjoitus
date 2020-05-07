package budgetapp.dao;

import budgetapp.domain.Category;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for class SQLCategoryDao
 */
public class SQLCategoryDaoTest {
    
    Connection connection;
    CategoryDao cDao;
    
    public SQLCategoryDaoTest() {
    }
    
    @Before
    public void setUp() throws SQLException, Exception {
        // Tests use H2 in-memory database, not the actual database which the application uses. 
        // In-memory database only keeps the contents while the connection is open and do not save it for another session.
        connection = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");    
        cDao = new SQLCategoryDao(connection); 
        Category incomeTest = new Category("Income", true);
        Category expenseTest = new Category("Expense");
        cDao.create(incomeTest); 
        cDao.create(expenseTest);
    }
    
    @Test
    public void categoryTableExistsInDatabase() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SHOW TABLES");
        ResultSet rs = stmt.executeQuery();
        boolean exists = false;
        while (rs.next()) {
            if (rs.getString(1).equalsIgnoreCase("category")) {
                exists = true;
            }
        }
        assertTrue(exists);
    }
    
    @Test
    public void CategoryIsCreatedAndReadCorrectly() throws Exception {
        cDao.create(new Category("testCategory"));
        Category c = cDao.read(3);
        assertEquals("testCategory", c.getName());
        assertFalse(c.isIncomeCategory());
    }
    
    @Test
    public void createCategoryOnlyAddsCategoriesWithDifferentNames() throws Exception {
        cDao.create(new Category("Expense"));
        Category c = cDao.read(3);
        assertEquals(null, c);
    }
    
    @Test
    public void readFromNameReturnsCorrectCategory() throws Exception {
        Category c = cDao.readFromName("Income");
        assertEquals("Income", c.getName());
    }
    
    @Test
    public void readFromNameReturnsNullIfNameNotInCategories() throws Exception {
        Category c = cDao.readFromName("NotExisting");
        assertEquals(null, c);
    }
    
    @Test
    public void updateGetsCorrectCategoryAndUpdates() throws Exception {
        cDao.create(new Category("testCategory"));
        Category c = cDao.read(3);
//        boolean isIncomeBeforeUpdate = c.isIncomeCategory();
        c.setName("UpdatedTestCategory");
//        c.setIncomeCategoryTrue();
        cDao.update(c);
        assertEquals("UpdatedTestCategory", cDao.read(3).getName());
//        assertFalse(isIncomeBeforeUpdate == cDao.read(3).isIncomeCategory());
    }
    
    @Test 
    public void deleteDeletesCorrectCategory() throws Exception {
        cDao.create(new Category("testCategory"));
        assertTrue(cDao.read(3) != null);
        cDao.delete(3);
        assertTrue(cDao.read(3) == null);
    }
    
    @Test
    public void categoriesAreListedCorrectly() throws Exception {
        List<Category> list = cDao.listAll();
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals("Income", list.get(0).getName());        
        assertEquals(true, list.get(0).isIncomeCategory());
        assertEquals(2, list.get(1).getId());        
        assertEquals("Expense", list.get(1).getName());
        assertEquals(false, list.get(1).isIncomeCategory());
    }

}
