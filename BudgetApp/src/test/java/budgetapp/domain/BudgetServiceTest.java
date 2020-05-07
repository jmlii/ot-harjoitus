package budgetapp.domain;

import java.time.LocalDate;
import java.util.List;
import javafx.scene.chart.PieChart;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for class BudgetService
 */
public class BudgetServiceTest {
    
    FakeCategoryDao categoryDao;
    FakeTransactionDao transactionDao;
    BudgetService service;
    
    public BudgetServiceTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        categoryDao = new FakeCategoryDao();
        transactionDao = new FakeTransactionDao();
        service = new BudgetService(categoryDao, transactionDao);
    }

    @Test
    public void atStart13DefaultCategoriesAreCreated() throws Exception {
        List<Category> categories = service.listAllCategories();
        assertEquals(13, categories.size());
    }
    
    @Test
    public void categoryNotCreatedIfNameAlreadyInUse() throws Exception {
        Category c1 = new Category("Housing");
        Category c2 = new Category("Income");
        categoryDao.create(c1);
        categoryDao.create(c2);
        assertEquals(12, service.listExpenseCategories().size());
    }
    
    @Test
    public void addIncomeTransactionAddsIncomeCorrectly() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        assertEquals(1, service.listTransactions().size());
        Transaction t = service.listTransactions().get(0);
        assertEquals("Income", t.getCategory().toString());
        assertEquals(200, t.getAmount());
        assertEquals("1970-01-01", t.getDate().toString());
        }
    
    @Test
    public void addExpenseTransactionAddsExpenseCorrectly() throws Exception {
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        assertEquals(1, service.listTransactions().size());
        Transaction t = service.listTransactions().get(0);
        assertEquals("Other", t.getCategory().toString());
        assertEquals(-200, t.getAmount());
        assertEquals("1970-01-01", t.getDate().toString());
    }
    
    @Test
    public void addExpenseTransactionSetsCategoryAsOtherIfGivenCategoryNotInTheList() throws Exception {
        service.addExpenseTransaction(categoryDao.readFromName("FakeCategory"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        assertEquals(1, service.listTransactions().size());
        Transaction t = service.listTransactions().get(0);
        assertEquals("Other", t.getCategory().toString());
        assertEquals(-200, t.getAmount());
        assertEquals("1970-01-01", t.getDate().toString());
    }
    
    @Test
    public void editIncomeTransactionUpdatesCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("FakeCategory"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.editIncomeTransaction(service.listTransactions().get(0), "edited testIncome", 150, LocalDate.of(1970, 01, 31));
        assertEquals(1, service.listTransactions().get(0).getId());
        assertEquals("edited testIncome", service.listTransactions().get(0).getDescription());    
    }
    
    @Test
    public void editIncomeTransactionEditsTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        String transactionBeforeEditing = service.listTransactions().get(0).getDescription();
        service.editIncomeTransaction(service.listTransactions().get(0), "edited testIncome", 150, LocalDate.of(1970, 01, 31));
        assertEquals(false, transactionBeforeEditing.equals(service.listTransactions().get(0).getDescription()));
    }
    
    @Test
    public void editExpenseTransactionEditsCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("FakeCategory"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.editExpenseTransaction(service.listTransactions().get(1), categoryDao.readFromName("Groceries"), "edited testExpense", 120, LocalDate.of(1970, 01, 31));
        assertEquals(2, service.listTransactions().get(0).getId());
        assertEquals("edited testExpense", service.listTransactions().get(0).getDescription());
    }
    
    @Test
    public void editExpenseTransactionEditsTransaction() throws Exception {
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        String transactionBeforeEditing = service.listTransactions().get(0).getDescription();
        service.editExpenseTransaction(service.listTransactions().get(0), categoryDao.readFromName("Groceries"), "edited testExpense", 120, LocalDate.of(1970, 01, 31));
        assertEquals(false, transactionBeforeEditing.equals(service.listTransactions().get(0).getDescription()));
    }
    
    @Test
    public void editExpenseTransactionSetsCategoryAsOtherIfGivenCategoryNotInTheList() throws Exception {
        service.addExpenseTransaction(categoryDao.readFromName("Groceries"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.editExpenseTransaction(service.listTransactions().get(0), categoryDao.readFromName("AnotherFakeCategory"), "edited testExpense", 120, LocalDate.of(1970, 01, 31));
        assertEquals("Other", service.listTransactions().get(0).getCategory().toString());
    }
    
    @Test
    public void deleteTransactionDeletesCorrectTransaction() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("FakeCategory"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.deleteTransaction(2);
        assertEquals(2, service.listTransactions().size());
        assertEquals(1, service.listTransactions().get(0).getId());
        assertEquals(3, service.listTransactions().get(1).getId());
    }
    
    @Test
    public void getIncomeSumReturnsCorrectValue() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        assertEquals(850, service.getIncomeSum());
    }
    
    @Test
    public void getExpensesSumReturnsCorrectValue() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        assertEquals(-400, service.getExpensesSum());
    }
    
    @Test
    public void getBalanceReturnsCorrectValue() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        assertEquals(450, service.getBalance());
    }
    
    @Test
    public void getCategorySumReturnsCorrectValue() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Housing"), "testExpense", 50, LocalDate.of(1970, 01, 01));
        assertEquals(-400, service.getCategorySum(categoryDao.readFromName("Other")));
        assertEquals(850, service.getCategorySum(categoryDao.readFromName("Income")));
        assertEquals(-50, service.getCategorySum(categoryDao.readFromName("Housing")));
    }

    @Test
    public void listTransactionsReturnsAllTransactionsInDateOrder() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 31));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 04, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 05, 15));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 31));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 03, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Housing"), "testExpense", 100, LocalDate.of(1970, 01, 01));
        
        assertEquals(6, service.listTransactions().size());
        assertEquals("1970-05-15", service.listTransactions().get(0).getDate().toString());
        assertEquals("1970-01-01", service.listTransactions().get(5).getDate().toString());

    }
    
    @Test
    public void listTransactionsFromCategoryReturnsCorrectTransactions() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 04, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 05, 15));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Housing"), "testExpense", 100, LocalDate.of(1970, 01, 01));
        
        assertEquals(2, service.listTransactionsFromCategory(categoryDao.readFromName("Other")).size());
        assertEquals("Other", service.listTransactionsFromCategory(categoryDao.readFromName("Other")).get(0).getCategory().getName());
        assertEquals("1970-05-15", service.listTransactionsFromCategory(categoryDao.readFromName("Other")).get(0).getDate().toString());
        
    }
    
    @Test
    public void listAllCategoriesReturnsListOfAllCategories() throws Exception {
        List<Category> categories = service.listAllCategories();
        assertEquals(13, categories.size());
        assertEquals("Income", categories.get(0).getName());
    }
    
    @Test
    public void listExpenseCategoriesReturnsExpenseCategories() throws Exception {
        List<Category> categories = service.listExpenseCategories();
        assertEquals(12, categories.size());
        assertEquals("Culture and entertainment", categories.get(0).getName());
    }
    
    @Test
    public void getIncomeCategoryReturnsIncomeCategory() throws Exception {
        assertEquals("Income", service.getIncomeCategory().getName());
    }
    
    @Test
    public void listCategoryPieChartDataAddsDataNothingIfNoExpenses() throws Exception {
        assertEquals("Nothing", service.listCategoryPieChartData().get(0).getName());
    }
    
    @Test
    public void listCategoryPieChartDataDoesNotAddDataForIncome() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Housing"), "testExpense", 50, LocalDate.of(1970, 01, 01));
        boolean incomeFound = false;
        for (PieChart.Data d : service.listCategoryPieChartData()) {
            if (d.getName().equals("Income")) {
                incomeFound = true;
            }
        }        
        assertFalse(incomeFound);
    }
    
    @Test
    public void listCategoryPieChartDataAddsDataForExpenses() throws Exception {
        service.addIncomeTransaction("testIncome", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Other"), "testExpense", 200, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome2", 150, LocalDate.of(1970, 01, 01));
        service.addIncomeTransaction("testIncome3", 500, LocalDate.of(1970, 01, 01));
        service.addExpenseTransaction(categoryDao.readFromName("Housing"), "testExpense", 100, LocalDate.of(1970, 01, 01));
        assertEquals("Other", service.listCategoryPieChartData().get(11).getName());
        assertEquals("80.0", String.valueOf(service.listCategoryPieChartData().get(11).getPieValue()));
    }
    
}
 