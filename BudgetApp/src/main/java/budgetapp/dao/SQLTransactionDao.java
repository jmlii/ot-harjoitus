package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pattern concrete class to handle methods for saving transaction data to the database and for retrieving it
 */
public class SQLTransactionDao implements TransactionDao {
    
    private Connection connection;
    private CategoryDao categoryDao;
    
    /**
     * Constructor sets the database connection to the class and creates table Transaction to the database if the table does not yet exist
     * @param connection Database connection defined in the application configuration properties
     * @param categoryDao CategoryDao object defined in the application initialization
     * @throws SQLException On error with SQL query
     */
    public SQLTransactionDao(Connection connection, CategoryDao categoryDao) throws SQLException {
        this.connection = connection;
        this.categoryDao = categoryDao;
        PreparedStatement stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Transaction ("
                        + "id SERIAL, "
                        + "category_id INTEGER, "
                        + "description VARCHAR(64), "
                        + "amount INTEGER, "
                        + "date DATE, "
                        + "PRIMARY KEY(id), "
                        + "FOREIGN KEY (category_id) REFERENCES Category(id))");
        stmt.executeUpdate();
        stmt.close();     
    }
    
    /**
     * Creates a new transaction to the database
     * @param transaction Transaction set in the application service class
     * @throws Exception On error with SQL query
     */
    @Override
    public void create(Transaction transaction) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Transaction "
                + " (category_id, description, amount, date)"
                + " VALUES (?, ?, ?, ?)");
        setTransactionValues(stmt, transaction);
        stmt.executeUpdate();
        stmt.close();
    }
    
    /**
     * Reads a transaction item from the database using the ID number to identify the wanted transaction
     * @param key ID number given as a key in the application service class
     * @return Transaction retrieved from the database with the given key as id number
     * @throws Exception On error with SQL query
     */
    @Override
    public Transaction read(Integer key) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Transaction transaction = createTransaction(rs);
        stmt.close();
        rs.close();
        return transaction;
    }
       
    /**
     * Updates the data of a given transaction in the database
     * @param transaction Transaction set in the application service class
     * @return Updated transaction set in the application service class
     * @throws Exception On error with SQL query
     */
    @Override
    public Transaction update(Transaction transaction) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Transaction"
                + " SET category_id = ?, description = ?, amount = ?, date = ?"
                + " WHERE id = ?");
        setTransactionValues(stmt, transaction);
        stmt.setInt(5, transaction.getId());
        stmt.executeUpdate();
        stmt.close();
        return transaction;
    }
    
    /**
     * Deletes a transaction item from the database using the ID number to identify the wanted transaction
     * @param key ID number given as a key in the application service class
     * @throws Exception On error with SQL query
     */
    @Override
    public void delete(Integer key) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Transaction"
                + " WHERE ID = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
    }
    
    /**
     * Creates a list of all transactions in the database
     * @return ArrayList object including all transactions in the database
     * @throws Exception On error with SQL query
     */
    @Override
    public List<Transaction> listAll() throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction");
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = createTransactionList(rs);
        stmt.close();
        rs.close();
        return transactions;
    }
    
    /**
     * Creates a list of transactions from the database, having the stated category 
     * @param category Category set in the application service class
     * @see Category
     * @return ArrayList object including all transactions of the wanted category
     * @throws Exception On error with SQL query
     */
    @Override
    public List<Transaction> listFromCategory(Category category) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction"
                + " WHERE category_id = ? ORDER BY date DESC");
        stmt.setInt(1, category.getId());
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = createTransactionList(rs);
        stmt.close();
        rs.close();
        return transactions;
    }
    
    /**
     * Creates a list of all transactions in the database, sorted in descending date order (latest transaction first)
     * @return ArrayList object including all transactions in the database, sorted in descending date order
     * @throws Exception On error with SQL query
     */
    @Override
    public List<Transaction> listInDateOrder() throws Exception  {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction"
                + " ORDER BY date DESC");
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = createTransactionList(rs);
        stmt.close();
        rs.close();
        return transactions;
    }
    
    /**
     * Sets values to a transaction to be transmitted to the database
     * @param stmt PreparedStatement defined in the method using this method
     * @param transaction Transaction set in the method using this method
     * @throws SQLException On error with SQL query
     */
    private void setTransactionValues(PreparedStatement stmt, Transaction transaction) throws SQLException {
        stmt.setInt(1, transaction.getCategory().getId());
        stmt.setString(2, transaction.getDescription());
        stmt.setInt(3, transaction.getAmount());
        stmt.setObject(4, transaction.getDate());
    }
    
    /**
     * Creates a transaction object from the given Resultset
     * @param rs ResultSet returned by the query stated in the method that uses this method
     * @return Transaction created from the ResultSet
     * @throws Exception On error with SQL query
     */
    private Transaction createTransaction(ResultSet rs) throws Exception {
        Transaction transaction = new Transaction(rs.getInt("id"), 
                categoryDao.read(rs.getInt("category_id")),
                rs.getString("description"), 
                rs.getInt("amount"),
                rs.getObject("date", LocalDate.class));
        return transaction;
    }
    
    /**
     * Creates a list of transactions from the given ResultSet
     * @param rs ResultSet returned by the query stated in the method that uses this method
     * @return ArrayList object including transactions from the ResultSet
     * @throws Exception On error with SQL query
     */
    private List<Transaction> createTransactionList(ResultSet rs) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = createTransaction(rs);
            transactions.add(transaction);
        }
        return transactions;
    }
    
}
