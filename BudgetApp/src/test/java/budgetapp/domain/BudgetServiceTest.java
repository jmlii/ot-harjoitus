package budgetapp.domain;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 */
public class BudgetServiceTest {
    
    FakeCategoryDao categoryDao;
    FakeTransactionDao transactionDao;
    BudgetService service;
    
    public BudgetServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        categoryDao = new FakeCategoryDao();
        transactionDao = new FakeTransactionDao();
        service = new BudgetService(categoryDao, transactionDao);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void atStart13DefaultExpenseCategoriesAreCreated() throws Exception {
        List<Category> categories = service.listExpenseCategories();
        assertEquals(13, categories.size());
    }
    
    @Test
    public void categoryNotCreatedIfNameAlreadyInUse() throws Exception {
        Category c1 = new Category("Housing");
        Category c2 = new Category("Income");
        categoryDao.create(c1);
        categoryDao.create(c2);
        assertEquals(13, service.listExpenseCategories().size());
    }
    
    @Test
    public void addIncomeTransactionAddsIncomeCorrectly() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        assertEquals(1, service.listTransactions().size());
        Transaction t = service.listTransactions().get(0);
        assertEquals("Income", t.getCategory().toString());
        assertEquals(200, t.getAmount());
        assertEquals("1970-01-01", t.getDate().toString());
        assertEquals("1, Income, testIncome, 200, 1970-01-01", service.listTransactions().get(0).toString());
    }
    
    @Test
    public void addExpenseTransactionAddsExpenseCorrectly() throws Exception {
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        assertEquals(1, service.listTransactions().size());
        Transaction t = service.listTransactions().get(0);
        assertEquals("Other", t.getCategory().toString());
        assertEquals(-200, t.getAmount());
        assertEquals("1970-01-01", t.getDate().toString());
        assertEquals("1, Other, testExpense, -200, 1970-01-01", service.listTransactions().get(0).toString());
    }
    
    @Test
    public void addExpenseTransactionSetsCategoryAsUnknownIfGivenCategoryNotInTheList() throws Exception {
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        assertEquals(1, service.listTransactions().size());
        Transaction t = service.listTransactions().get(0);
        assertEquals("Unknown", t.getCategory().toString());
        assertEquals(-200, t.getAmount());
        assertEquals("1970-01-01", t.getDate().toString());
        assertEquals("1, Unknown, testExpense, -200, 1970-01-01", service.listTransactions().get(0).toString());
    }
    
    @Test
    public void getTransactionReturnsCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        assertEquals("2, Other, testExpense, -200, 1970-01-01", service.getTransaction(2).toString());
    }
    
    @Test
    public void editIncomeTransactionUpdatesCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.editIncomeTransaction(1, "edited testIncome", 150, LocalDate.of(1970, 01, 31));
        assertEquals("1, Income, edited testIncome, 150, 1970-01-31", service.getTransaction(1).toString());
    }
    
    @Test
    public void editIncomeTransactionEditsTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        String transaction1BeforeEditing = service.getTransaction(1).toString();
        service.editIncomeTransaction(1, "edited testIncome", 150, LocalDate.of(1970, 01, 31));
        assertEquals(false, transaction1BeforeEditing.equals(service.getTransaction(1).toString()));
    }
    
    @Test
    public void editExpenseTransactionEditsCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.editExpenseTransaction(2, "Unknown", "edited testExpense", 120, LocalDate.of(1970, 01, 31));
        assertEquals("2, Unknown, edited testExpense, -120, 1970-01-31", service.getTransaction(2).toString());
    }
    
    @Test
    public void editExpenseTransactionEditsTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        String transaction2BeforeEditing = service.getTransaction(2).toString();
        service.editExpenseTransaction(2, "Unknown", "edited testExpense", 120, LocalDate.of(1970, 01, 31));
        assertEquals(false, transaction2BeforeEditing.equals(service.getTransaction(2).toString()));
    }
    
    @Test
    public void editExpenseTransactionSetsCategoryAsUnknownIfGivenCategoryNotInTheList() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.editExpenseTransaction(3, "AnotherFakeCategory", "edited testExpense", 120, LocalDate.of(1970, 01, 31));
        assertEquals("Unknown", service.getTransaction(3).getCategory().toString());
    }
    
    @Test
    public void deleteTransactionDeletesCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("Other", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction("FakeCategory", "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.deleteTransaction(2);
        assertEquals(null, service.getTransaction(2));
        assertTrue(service.getTransaction(1) != null);
        assertTrue(service.getTransaction(3) != null);
    }
    
}
