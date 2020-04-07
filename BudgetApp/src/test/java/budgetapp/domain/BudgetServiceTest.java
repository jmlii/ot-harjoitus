package budgetapp.domain;

import budgetapp.dao.CategoryDao;
import budgetapp.dao.SQLCategoryDao;
import budgetapp.dao.SQLTransactionDao;
import budgetapp.dao.TransactionDao;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Sovelluslogiikasta vastaavan luokan testit
 */
public class BudgetServiceTest {
    
    public BudgetServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    @Test
    public void returnsTransactionReturnsCorrectValue() throws SQLException {
        TransactionDao tDao = new SQLTransactionDao();
        CategoryDao cDao = new SQLCategoryDao();
        BudgetService bs = new BudgetService(cDao, tDao);
        assertTrue(bs.returnsTransaction(4));
    }
}
