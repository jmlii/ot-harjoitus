package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
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
    
    public Connection createDbConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./budgetapp", "sa", "");
        return connection;
    }
    
    SQLCategoryDao sqlCategoryDao = new SQLCategoryDao();
    
    @Override
    public void create(Transaction transaction) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Transaction "
                + " (category_id, description, amount, date)"
                + " VALUES (?, ?, ?, ?)");
        stmt.setInt(1, transaction.getCategory().getId());
        stmt.setString(2, transaction.getDescription());
        stmt.setInt(3, transaction.getAmount());
        stmt.setObject(4, transaction.getDate());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @Override
    public Transaction read(Integer key) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Transaction transaction = new Transaction(rs.getInt("id"), 
                sqlCategoryDao.read(rs.getInt("category_id")),
                rs.getString("description"), 
                rs.getInt("amount"),
                rs.getObject("date", LocalDate.class));
        stmt.close();
        rs.close();
        connection.close();
        return transaction;
    }
    
    @Override
    public Transaction update(Transaction transaction) throws SQLException {
        Connection connection = createDbConnection();
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
        connection.close();
        return transaction;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Transaction"
                + " WHERE ID = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @Override
    public List<Transaction> listAll() throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction");
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction(rs.getInt("id"), 
                    sqlCategoryDao.read(rs.getInt("category_id")),
                    rs.getString("description"), 
                    rs.getInt("amount"),
                    rs.getObject("date", LocalDate.class));
            transactions.add(transaction);
        }
        stmt.close();
        rs.close();
        connection.close();
        return transactions;
    }
    
    @Override
    public List<Transaction> listBycategory(Category category) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Transaction"
                + " WHERE category_id = ?");
        stmt.setInt(1, category.getId());
        ResultSet rs = stmt.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction(rs.getInt("id"), 
                    sqlCategoryDao.read(rs.getInt("category_id")),
                    rs.getString("description"), 
                    rs.getInt("amount"),
                    rs.getObject("date", LocalDate.class));
            transactions.add(transaction);
        }
        stmt.close();
        rs.close();
        connection.close();
        return transactions;
    }
}
