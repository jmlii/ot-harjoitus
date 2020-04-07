package budgetapp.domain;

/**
 * Sovelluslogiikasta vastaava luokka
 */

import budgetapp.dao.CategoryDao;
import budgetapp.dao.TransactionDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BudgetService {
    
    private ArrayList<Transaction> transactions;
    
    private CategoryDao categoryDao;
    private TransactionDao transactionDao;
        
    public BudgetService(CategoryDao categoryDao, TransactionDao transactionDao) throws SQLException {
        this.categoryDao = categoryDao;
        this.transactionDao = transactionDao;
        Connection connection = DriverManager.getConnection("jdbc:h2:./budgetapp", "sa", "");
     
        PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Category ("
                        + "id SERIAL, "
                        + "name VARCHAR(64), "
                        + "incomeCategory BOOLEAN NOT NULL, "
                        + "PRIMARY KEY (id), "
                        + "UNIQUE (name))");
        statement.executeUpdate();        
        PreparedStatement statement2 = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Transaction ("
                        + "id SERIAL, "
                        + "category_id INTEGER, "
                        + "name VARCHAR(64), "
                        + "description VARCHAR(64), "
                        + "amount INTEGER, "
                        + "date DATE, "
                        + "PRIMARY KEY(id), "
                        + "FOREIGN KEY (category_id) REFERENCES Category(id))");
        statement2.executeUpdate();
        statement.close();
        statement2.close();
        connection.close();
        
        this.transactions = new ArrayList<>();
        addCategories(categoryDao);   
    }
    
    public void printTransactions() throws SQLException {
        if (transactionDao.listAll().isEmpty()) {
            System.out.println("No transactions");
        } else {
            for (Transaction transaction : transactionDao.listAll()) {
                System.out.println(transaction);
            }
        }
    }
    
    public void addIncomeTransaction(String description, int amount, LocalDate date) throws SQLException {
        Category category = categoryDao.readFromName("Income");
        addTransaction(category, description, amount, date);
    }
    
    public void addExpenseTransaction(String categoryAsString, String description, int amount, LocalDate date) throws SQLException {
        int expense = amount * -1;
        Category category = categoryDao.readFromName(categoryAsString);
        if (category == null) {
            category = categoryDao.readFromName("Unknown");
        } 
        addTransaction(category, description, expense, date);
    }
    
    public void addTransaction(Category category, String description, int amount, LocalDate date) throws SQLException {
        Transaction transaction = new Transaction(category, description, amount, date);
        transactionDao.create(transaction);
    }
    
    public void showCategories() throws SQLException {
        if (categoryDao.listAll().isEmpty()) {
            System.out.println("No categories");
        } else {
            for (Category category : categoryDao.listAll()) {
                System.out.println(category);
            }
        }
        System.out.println("");
    }
    
    public Transaction getTransaction(Integer key) throws SQLException {
        return transactionDao.read(key);
    }
    
    public boolean returnsTransaction(Integer key) throws SQLException {
        return (transactionDao.read(key) != null);
    }
    
    public void editIncomeTransaction(Integer key, String description, int amount, LocalDate date) throws SQLException {
        Transaction transaction = transactionDao.read(key);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transactionDao.update(transaction);
    }
    
    public void editExpenseTransaction(Integer key, String categoryAsString, String description, int amount, LocalDate date) throws SQLException {
        Transaction transaction = transactionDao.read(key);
        Category category = categoryDao.readFromName(categoryAsString);
        if (category == null) {
            category = categoryDao.readFromName("Unknown");
        } 
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setAmount(amount * -1);
        transaction.setDate(date);
    }
    
    public void deleteTransaction(Integer key) throws SQLException {
        transactionDao.delete(key);
         
    }
    
    private void addCategories(CategoryDao categoryDao) throws SQLException {
        List<Category> categories = createCategoryList();
        for (Category category : categories) {
            String name = category.getName();
            if (!categoryDao.nameExists(name)) {
                categoryDao.create(category);
            }
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
        categories.add(new Category("Others"));
        categories.add(new Category("Unknown"));
        return categories;
    }
     
}
