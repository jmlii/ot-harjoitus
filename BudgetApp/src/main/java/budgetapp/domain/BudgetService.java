package budgetapp.domain;

import budgetapp.dao.CategoryDao;
import budgetapp.dao.TransactionDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;


/**
 * Class for application logic.
 */
public class BudgetService {
        
    private CategoryDao categoryDao;
    private TransactionDao transactionDao;

    /**
     * Constructor for the application service class also sets up the category list defined in this class and saves categories to the database if they are not there yet.
     * @param categoryDao CategoryDao object defined in the application initialization
     * @param transactionDao TransactionDao object defined in the application initialization
     * @see CategoryDao
     * @see TransactionDao
     * @throws Exception On error with Dao-objects
     */
    public BudgetService(CategoryDao categoryDao, TransactionDao transactionDao) throws Exception {
        this.categoryDao = categoryDao;
        this.transactionDao = transactionDao;
        
        addCategories();   
    }
    
    /**
     * Creates a list of transactions having the given category.
     * @param category Category selected by the user in the UI
     * @see budgetapp.dao.TransactionDao#listFromCategoryInDateOrder(budgetapp.domain.Category) 
     * @return ArrayList object including all transactions of the wanted category
     * @throws Exception On error with SQL query
     */
    public List<Transaction> listTransactionsFromCategory(Category category) throws Exception {
        return transactionDao.listFromCategoryInDateOrder(category);
    }
    
    /**
     * Creates a list of all transactions in date order.
     * @see budgetapp.dao.TransactionDao#listInDateOrder()      
     * @return ArrayList object including all transactions in date order
     * @throws Exception On error with SQL query
     */
    public List<Transaction> listTransactions() throws Exception {
        return transactionDao.listInDateOrder();
    }

    /**
     * Creates a list of all categories.
     * @see budgetapp.dao.CategoryDao#listAll()      
     * @return ArrayList object including all categories
     * @throws Exception On error with SQL query
     */
    public List<Category> listAllCategories() throws Exception {
        List<Category> categories = categoryDao.listAll();
        return categories;
    }    
    
    /**
     * Creates a list of all expense categories.
     * @see budgetapp.dao.CategoryDao#listAll()
     * @see budgetapp.domain.Category#isIncomeCategory()      
     * @return ArrayList object including all expense categories (where Category property "incomeCategory" is false)
     * @throws Exception On error with SQL query
     */
    public List<Category> listExpenseCategories() throws Exception {
        List<Category> expenseCategories = new ArrayList<>();
        for (Category category : categoryDao.listAll()) {
            if (!category.isIncomeCategory()) {
                expenseCategories.add(category);
            }
        }
        return expenseCategories;
    }
    
    /**
     * Returns income category.
     * @see budgetapp.dao.CategoryDao#readFromName(java.lang.String)      
     * @return Category "Income"
     * @throws Exception On error with SQL query
     */
    public Category getIncomeCategory() throws Exception {
        return categoryDao.readFromName("Income");
    }
    
    /**
     * Adds a new income transaction to be handled by the private method addTransaction(...).
     * @param description Description given by the user in the UI
     * @param amount Amount given by the user in the UI
     * @param date Date given by the user in the UI
     * @see budgetapp.dao.CategoryDao#readFromName(java.lang.String) 
     * @see budgetapp.domain.BudgetService#addTransaction(budgetapp.domain.Category, java.lang.String, int, java.time.LocalDate) 
     * @throws Exception On error with SQL query
     */
    public void addIncomeTransaction(String description, int amount, LocalDate date) throws Exception {
        Category category = categoryDao.readFromName("Income");
        addTransaction(category, description, amount, date);
    }
    
    /**
     * Adds a new expense transaction to be handled by the private method addTransaction(...).
     * @param category Expense category chosen by the user in the UI
     * @param description Description given by the user in the UI
     * @param amount Amount given by the user in the UI
     * @param date Date given by the user in the UI
     * @see budgetapp.dao.CategoryDao#readFromName(java.lang.String) 
     * @see budgetapp.domain.BudgetService#addTransaction(budgetapp.domain.Category, java.lang.String, int, java.time.LocalDate) 
     * @throws Exception On error with SQL query
     */
    public void addExpenseTransaction(Category category, String description, int amount, LocalDate date) throws Exception {
        int expense = amount * -1;
        if (category == null) {
            category = categoryDao.readFromName("Other");
        } 
        addTransaction(category, description, expense, date);
    }
    
    /**
     * Gets transaction data from other methods in this class and passes them on to data handling classes.
     * @param category Category passed by another method in this class
     * @param description Description passed by another method in this class
     * @param amount Amount passed by another method in this class
     * @param date Date passed by another method in this class
     * @see budgetapp.dao.TransactionDao#create(budgetapp.domain.Transaction) 
     * @throws Exception On error with SQL query
     */
    private void addTransaction(Category category, String description, int amount, LocalDate date) throws Exception {
        Transaction transaction = new Transaction(category, description, amount, date);
        transactionDao.create(transaction);
    }

    /**
     * Edits income transaction data.
     * @param transaction Transaction chosen in the UI to be edited
     * @param description Description given by the user in the UI
     * @param amount Amount given by the user in the UI
     * @param date Date given by the user in the UI
     * @see budgetapp.dao.TransactionDao#update(budgetapp.domain.Transaction) 
     * @see budgetapp.domain.Transaction
     * @throws Exception On error with SQL query
     */
    public void editIncomeTransaction(Transaction transaction, String description, int amount, LocalDate date) throws Exception {
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transactionDao.update(transaction);
    }
    
    /**
     * Edits expense transaction data.
     * @param transaction Transaction chosen in the UI to be edited
     * @param category Category chosen by the user in the UI
     * @param description Description given by the user in the UI
     * @param amount Amount given by the user in the UI
     * @param date Date given by the user in the UI
     * @see budgetapp.dao.TransactionDao#update(budgetapp.domain.Transaction) 
     * @throws Exception On error with SQL query
     */
    public void editExpenseTransaction(Transaction transaction, Category category, String description, int amount, LocalDate date) throws Exception {
       if (category == null) {
            category = categoryDao.readFromName("Other");
        } 
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setAmount(amount * -1);
        transaction.setDate(date);
        transactionDao.update(transaction);
    }
    
    /**
     * Deletes a transaction. 
     * @param key Id of the transaction to be deleted
     * @see budgetapp.dao.TransactionDao#delete(java.lang.Integer)     
     * @throws Exception On error with SQL query
     */
    public void deleteTransaction(Integer key) throws Exception {
        transactionDao.delete(key);
    }
    
    /**
     * Total sum of all the incomes.
     * @see budgetapp.dao.CategoryDao#readFromName(java.lang.String) 
     * @see budgetapp.dao.TransactionDao#listFromCategoryInDateOrder(budgetapp.domain.Category) 
     * @return Int value of all the incomes
     * @throws Exception On error with SQL query
     */
    public int getIncomeSum() throws Exception {
        Category income = categoryDao.readFromName("Income");
        List<Transaction> incomeTransactions = transactionDao.listFromCategoryInDateOrder(income);
        int incomeSum = 0;
        for (Transaction t : incomeTransactions) {
            incomeSum += t.getAmount();
        }
        return incomeSum;
    }
    
    /**
     * Total sum of all the expenses.
     * @see budgetapp.dao.TransactionDao#listAll() 
     * @return Int value of all the expenses
     * @throws Exception On error with SQL query
     */
    public int getExpensesSum() throws Exception {
        int expensesSum = 0; 
        for (Transaction t : transactionDao.listAll()) {
            if (!t.getCategory().isIncomeCategory()) {
                expensesSum += t.getAmount();
            }
        }
        return expensesSum;
    }
    
    /**
     * Balance of all the incomes and expenses.
     * @see budgetapp.dao.
     * @return Int value of incomes minus expenses
     * @throws Exception On error with SQL query
     */
    public int getBalance() throws Exception {
        int balance = 0;
        for (Transaction t : transactionDao.listAll()) {
            balance += t.getAmount();
        }
        return balance;
    }
    
    /**
     * Sum of all the transactions in the given category.
     * @param category Category chosen by the user in the UI
     * @see budgetapp.dao.TransactionDao#listAll() 
     * @return int value of the sum
     * @throws Exception On error with SQL query
     */
    public int getCategorySum(Category category) throws Exception {
        int sum = 0;
        for (Transaction t : transactionDao.listAll()) {
            if (t.getCategory().equals(category)) {
                sum += t.getAmount();
            }
        }
        return sum;
    }
    
    /**
     * Creates a list of expense categories and their share of total expenses in PieChart.Data format.
     * @see budgetapp.domain.BudgetService#listExpenseCategories() 
     * @return ObservableList object containing pie chart data of expense categories, or a data node named Nothing if there are no expenses.
     * @throws Exception On error with SQL query
     */
    public ObservableList<PieChart.Data> listCategoryPieChartData() throws Exception {
        ObservableList<PieChart.Data> categoryPieChartData = FXCollections.observableArrayList();
        Double expensesSum = Double.valueOf(getExpensesSum()) * -1;
        if (expensesSum == 0) {
            categoryPieChartData.add(new PieChart.Data("Nothing", 100.00));
        } else {
            for (Category category : listExpenseCategories()) {
                String categoryName = category.getName();
                Double categorySum = Double.valueOf(getCategorySum(category)) * -1;
                double categoryShare = categorySum * 100 / expensesSum;
                categoryPieChartData.add(new PieChart.Data(categoryName, categoryShare));
            }
        }
        return categoryPieChartData;
    }
    
    private void addCategories() throws Exception {
        List<Category> categories = createCategoryList();
        for (Category category : categories) {
            categoryDao.create(category);
        }
    }
    
    private List<Category> createCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Housing"));
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
        categories.add(new Category("Other"));        
        categories.add(0, new Category("Income", true));
        return categories;
    }
     
}
