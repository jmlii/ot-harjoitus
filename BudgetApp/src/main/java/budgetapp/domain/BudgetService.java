package budgetapp.domain;

import budgetapp.dao.CategoryDao;
import budgetapp.dao.TransactionDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class for application logic
 */
public class BudgetService {
        
    private CategoryDao categoryDao;
    private TransactionDao transactionDao;

    /**
     * Constructor for the application service class also sets up the category list defined in this class and saves categories to the database if they are not there yet.
     * @param categoryDao CategoryDao object defined in the application initialization
     * @param transactionDao TransactionDao object defined in the application initialization
     * @throws Exception On error with Dao-objects
     */
    public BudgetService(CategoryDao categoryDao, TransactionDao transactionDao) throws Exception {
        this.categoryDao = categoryDao;
        this.transactionDao = transactionDao;
        
        addCategories(categoryDao);   
    }
    
    // this method is currently not in use but is kept here for possible future use
    public List<Transaction> listTransactions() throws Exception {
        return transactionDao.listAll();
    }
                
    public List<Transaction> listTransactionsFromCategory(Category category) throws Exception {
        return transactionDao.listFromCategory(category);
    }
    
    public List<Transaction> listTransactionsInDateOrder() throws Exception {
        return transactionDao.listInDateOrder();
    }

    public List<Category> listAllCategories() throws Exception {
        List<Category> categories = categoryDao.listAll();
        return categories;
    }    
    
    public List<Category> listExpenseCategories() throws Exception {
        List<Category> expenseCategories = new ArrayList<>();
        for (Category category : categoryDao.listAll()) {
            if (!category.isIncomeCategory()) {
                expenseCategories.add(category);
            }
        }
        return expenseCategories;
    }
    
    public void addIncomeTransaction(String description, int amount, LocalDate date) throws Exception {
        Category category = categoryDao.readFromName("Income");
        addTransaction(category, description, amount, date);
    }
    
    public void addExpenseTransaction(Category category, String description, int amount, LocalDate date) throws Exception {
        int expense = amount * -1;
        if (category == null) {
            category = categoryDao.readFromName("Unknown");
        } 
        addTransaction(category, description, expense, date);
    }
    
    public void addTransaction(Category category, String description, int amount, LocalDate date) throws Exception {
        Transaction transaction = new Transaction(category, description, amount, date);
        transactionDao.create(transaction);
    }

    // this method is currently not in use but is kept here for possible future use
    public Transaction getTransaction(Integer key) throws Exception {
        return transactionDao.read(key);
    }
    
    public void editIncomeTransaction(Integer key, String description, int amount, LocalDate date) throws Exception {
        Transaction transaction = transactionDao.read(key);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transactionDao.update(transaction);
    }
    
    public void editExpenseTransaction(Integer key, Category category, String description, int amount, LocalDate date) throws Exception {
        Transaction transaction = transactionDao.read(key);
        if (category == null) {
            category = categoryDao.readFromName("Unknown");
        } 
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setAmount(amount * -1);
        transaction.setDate(date);
        transactionDao.update(transaction);
    }
    
    public void deleteTransaction(Integer key) throws Exception {
        transactionDao.delete(key);
    }
    
    public int getIncomeSum() throws Exception {
        Category income = categoryDao.readFromName("Income");
        List<Transaction> incomeTransactions = transactionDao.listFromCategory(income);
        int incomeSum = 0;
        for (Transaction t : incomeTransactions) {
            incomeSum += t.getAmount();
        }
        return incomeSum;
    }
    
    public int getExpensesSum() throws Exception {
        int expensesSum = 0; 
        for (Transaction t : transactionDao.listAll()) {
            if (!t.getCategory().isIncomeCategory()) {
                expensesSum += t.getAmount();
            }
        }
        return expensesSum;
    }
    
    public int getBalance() throws Exception {
        int balance = 0;
        for (Transaction t : transactionDao.listAll()) {
            balance += t.getAmount();
        }
        return balance;
    }
    
    public int getCategorySum(Category category) throws Exception {
        int sum = 0;
        for (Transaction t : transactionDao.listAll()) {
            if (t.getCategory().equals(category)) {
                sum += t.getAmount();
            }
        }
        return sum;
    }
    
    private void addCategories(CategoryDao categoryDao) throws Exception {
        List<Category> categories = createCategoryList();
        for (Category category : categories) {
            categoryDao.create(category);
        }
    }
    
    private List<Category> createCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Housing", false));
        categories.add(new Category("Groceries"));
        categories.add(new Category("Transportation"));
        categories.add(new Category("Holiday"));
        categories.add(new Category("Hobbies"));
        categories.add(new Category("Culture and entertainment"));
        categories.add(new Category("Restaurants and cafes"));
        categories.add(new Category("Healthcare"));
        categories.add(new Category("Wellbeing"));
        categories.add(new Category("Shopping"));
        categories.add(new Category("Savings and investments"));
        Collections.sort(categories);
        categories.add(0, new Category("Income", true));
        categories.add(new Category("Other"));
        categories.add(new Category("Unknown"));
        return categories;
    }
     
}
