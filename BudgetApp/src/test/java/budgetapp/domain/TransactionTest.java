package budgetapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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
    
    @Test
    public void transactionsAreSortedInDateOrderNewestFirst() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        transactions.add(new Transaction(new Category("Housing"), "rent", 300, LocalDate.of(2020, 03, 10)));
        transactions.add(new Transaction(new Category("Housing"), "rent", 300, LocalDate.of(2020, 02, 28)));
        transactions.add(new Transaction(new Category("Housing"), "rent", 300, LocalDate.of(2020, 03, 05)));
        Collections.sort(transactions);
        ArrayList<String> dates = new ArrayList<>();
        for (Transaction t : transactions) {
            dates.add(t.getDate().toString());
        }
        assertEquals("[2020-03-10, 2020-03-05, 2020-03-02, 2020-02-28]", dates.toString());
    }

}
