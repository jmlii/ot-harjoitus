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
    
    Transaction transaction;
    
    public TransactionTest() {
    }
    
    @Before
    public void setUp() {
        transaction = new Transaction(new Category("Income", true), "salary", 3000, LocalDate.of(2020, 03, 02));
    }
    
    @Test
    public void ConstructorSetsCorrectCategory() {
        assertEquals("Income", transaction.getCategory().toString());
    }
    
    @Test
    public void ConstructorSetsCorrectDescription() {
        assertEquals("salary", transaction.getDescription());
    }  
    
    @Test 
    public void ConstructorSetsCorrectAmount() {
        assertEquals(3000, transaction.getAmount());
    }  
    
    @Test 
    public void ConstructorSetsCorrectDate() {
        assertEquals("2020-03-02", transaction.getDate().toString());
    }  
    
    @Test
    public void ToStringReturnsCorrectFormat() {
        assertEquals("0, Income, salary, 3000, 2020-03-02", transaction.toString());
    }

}
