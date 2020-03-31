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

public class RowTest {
    
    public RowTest() {
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
    public void ConstructorSetsCorrectDescription() {
        Row row = new Row("salary", 3000);
        assertEquals("salary", row.getDescription());
    }

    @Test
    public void ConstructorSetsCorrectBudget() {
        Row row = new Row("salary", 3000);
        assertEquals(3000, row.getBudget());
    }    
    
    @Test 
    public void ConstructorSetsCorrectExpense() {
        Row row = new Row("rent", 600, LocalDate.of(2020, 03, 02));
        assertEquals(600, row.getExpense());
    }  
    
    @Test 
    public void ConstructorSetsCorrectDateSpent() {
        Row row = new Row("rent", 600, LocalDate.of(2020, 03, 02));
        assertEquals("2020-03-02", row.getDateSpent().toString());
    }  
    
    @Test
    public void ToStringReturnsCorrectFormatForExpense() {
       Row row = new Row("rent", 600, LocalDate.of(2020, 03, 02));
       row.setCategory(new Category("Housing"));
       assertEquals("0, Housing, rent, 0, 600, 2020-03-02", row.toString());
    }
    
    @Test
    public void ToStringReturnsCorrectFormatForIncome() {
       Row row = new Row("salary", 3000);
       row.setCategory(new Category("Income"));
       assertEquals("0, Income, salary, 3000, 0, null", row.toString());
    }

}
