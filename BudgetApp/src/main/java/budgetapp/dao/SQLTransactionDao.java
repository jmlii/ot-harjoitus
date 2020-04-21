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
 * 
 */
public class SQLTransactionDao implements TransactionDao {
    
    private Connection connection;
    private CategoryDao categoryDao;
    
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

    @Override
    public void create(Transaction transaction) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Transaction "
                + " (category_id, description, amount, date)"
                + " VALUES (?, ?, ?, ?)");
        stmt.setInt(1, transaction.getCategory().getId());
        stmt.setString(2, transaction.getDescription());
        stmt.setInt(3, transaction.getAmount());
        stmt.setObject(4, transaction.getDate());
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public Transaction read(Integer key) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Transaction transaction = new Transaction(rs.getInt("id"), 
                categoryDao.read(rs.getInt("category_id")),
                rs.getString("description"), 
                rs.getInt("amount"),
                rs.getObject("date", LocalDate.class));
        stmt.close();
        rs.close();
        return transaction;
    }
    
    @Override
    public Transaction update(Transaction transaction) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Transaction"
                + " SET category_id = ?, description = ?, amount = ?, date = ?"
                + " WHERE id = ?");
        stmt.setInt(1, transaction.getCategory().getId());
        stmt.setString(2, transaction.getDescription());
        stmt.setInt(3, transaction.getAmount());
        stmt.setObject(4, transaction.getDate());
        stmt.setInt(5, transaction.getId());
        stmt.executeUpdate();
        stmt.close();
        return transaction;
    }
    
    @Override
    public void delete(Integer key) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Transaction"
                + " WHERE ID = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public List<Transaction> listAll() throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction");
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction(rs.getInt("id"), 
                    categoryDao.read(rs.getInt("category_id")),
                    rs.getString("description"), 
                    rs.getInt("amount"),
                    rs.getObject("date", LocalDate.class));
            transactions.add(transaction);
        }
        stmt.close();
        rs.close();
        return transactions;
    }
    
    @Override
    public List<Transaction> listByCategory(Category category) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction"
                + " WHERE category_id = ?");
        stmt.setInt(1, category.getId());
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction(rs.getInt("id"), 
                    categoryDao.read(rs.getInt("category_id")),
                    rs.getString("description"), 
                    rs.getInt("amount"),
                    rs.getObject("date", LocalDate.class));
            transactions.add(transaction);
        }
        stmt.close();
        rs.close();
        return transactions;
    }
    
    @Override
    public List<Transaction> listInDateOrder() throws Exception  {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction"
                + " ORDER BY date DESC");
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction(rs.getInt("id"), 
                    categoryDao.read(rs.getInt("category_id")),
                    rs.getString("description"), 
                    rs.getInt("amount"),
                    rs.getObject("date", LocalDate.class));
            transactions.add(transaction);
        }
        stmt.close();
        rs.close();
        return transactions;
    }
    
}
