package budgetapp.domain;

/**
 * Class for application logic
 */

import budgetapp.dao.CategoryDao;
import budgetapp.dao.TransactionDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BudgetService {
        
    private CategoryDao categoryDao;
    private TransactionDao transactionDao;

    public BudgetService(CategoryDao categoryDao, TransactionDao transactionDao) throws Exception {
        this.categoryDao = categoryDao;
        this.transactionDao = transactionDao;
        
        addCategories(categoryDao);   
    }
    
    public List<Transaction> listTransactions() throws Exception {
        return transactionDao.listAll();
    }
    
    public List<Transaction> listTransactionsInDateOrder() throws Exception {
        return transactionDao.listInDateOrder();
    }
    
    public void addIncomeTransaction(String description, int amount, LocalDate date) throws Exception {
        Category category = categoryDao.readFromName("Income");
        addTransaction(category, description, amount, date);
    }
    
    public void addExpenseTransaction(String categoryAsString, String description, int amount, LocalDate date) throws Exception {
        int expense = amount * -1;
        Category category = categoryDao.readFromName(categoryAsString);
        if (category == null) {
            category = categoryDao.readFromName("Unknown");
        } 
        addTransaction(category, description, expense, date);
    }
    
    public void addTransaction(Category category, String description, int amount, LocalDate date) throws Exception {
        Transaction transaction = new Transaction(category, description, amount, date);
        transactionDao.create(transaction);
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
    
    public List<Category> listAllCategories() throws Exception {
        List<Category> categories = categoryDao.listAll();
        return categories;
    }
    
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
    
    public void editExpenseTransaction(Integer key, String categoryAsString, String description, int amount, LocalDate date) throws Exception {
        Transaction transaction = transactionDao.read(key);
        Category category = categoryDao.readFromName(categoryAsString);
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
        List<Transaction> incomeTransactions = transactionDao.listByCategory(income);
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
