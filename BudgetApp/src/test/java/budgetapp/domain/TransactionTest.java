package budgetapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

/**
 * Budjetin riviä kuvaavan luokan testit
 */

public class TransactionTest {
    
    public TransactionTest() {
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
    public void ConstructorSetsCorrectCategory() {
        Transaction transaction = new Transaction(new Category("Income", true), "salary", 3000, LocalDate.of(2020, 03, 02));
        assertEquals("Income", transaction.getCategory().toString());
    }
    
    @Test
    public void ConstructorSetsCorrectDescription() {
        Transaction transaction = new Transaction(new Category("Income", true), "salary", 3000, LocalDate.of(2020, 03, 02));
        assertEquals("salary", transaction.getDescription());
    }  
    
    @Test 
    public void ConstructorSetsCorrectAmount() {
        Transaction transaction = new Transaction(new Category("Housing"), "rent", 600, LocalDate.of(2020, 03, 02));
        assertEquals(600, transaction.getAmount());
    }  
    
    @Test 
    public void ConstructorSetsCorrectDateSpent() {
        Transaction transaction = new Transaction(new Category("Housing"), "rent", 600, LocalDate.of(2020, 03, 02));
        assertEquals("2020-03-02", transaction.getDate().toString());
    }  
    
    @Test
    public void ToStringReturnsCorrectFormatForExpense() {
       Transaction transaction = new Transaction(new Category("Housing"), "rent", 600, LocalDate.of(2020, 03, 02));
       assertEquals("0, Housing, rent, 600, 2020-03-02", transaction.toString());
    }
    
    @Test
    public void ToStringReturnsCorrectFormatForIncome() {
       Transaction transaction = new Transaction(new Category("Income", true), "salary", 3000, LocalDate.of(2020, 03, 02));
       assertEquals("0, Income, salary, 3000, 2020-03-02", transaction.toString());
    }

}
