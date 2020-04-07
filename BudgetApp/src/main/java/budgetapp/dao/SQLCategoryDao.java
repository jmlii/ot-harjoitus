package budgetapp.dao;

import budgetapp.domain.Category;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class SQLCategoryDao implements CategoryDao {
    
    private Connection createDbConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./budgetapp", "sa", "");
        return connection;
    }
    
    
    @Override
    public void create(Category category) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Category"
                + " (name, incomeCategory)"
                + " VALUES (?, ?)");
        stmt.setString(1, category.getName());
        stmt.setBoolean(2, category.isIncomeCategory());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @Override
    public Category read(Integer key) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Category category = new Category(rs.getInt("id"), rs.getString("name"), 
                rs.getBoolean("incomeCategory"));
        stmt.close();
        rs.close();
        connection.close();
        return category;
    }
    
    public Category readFromName(String name) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category WHERE name = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Category category = new Category(rs.getInt("id"), rs.getString("name"), 
                rs.getBoolean("incomeCategory"));
        stmt.close();
        rs.close();
        connection.close();
        return category;
    }
    
    @Override
    public Category update(Category category) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE Category"
                + " SET name = ?, incomeCategory = ?"
                + " WHERE id = ?");
        stmt.setString(1, category.getName());
        stmt.setBoolean(2, category.isIncomeCategory());
        stmt.setInt(3, category.getId());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
        return category;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Category"
                + " WHERE ID = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @Override
    public List<Category> listAll() throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category");
        ResultSet rs = stmt.executeQuery();
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category(rs.getInt("id"), rs.getString("name"), 
                    rs.getBoolean("incomeCategory"));
            categories.add(category);
        }
        stmt.close();
        rs.close();
        connection.close();
        return categories;
    }
        
    @Override
    public boolean nameExists(String categoryName) throws SQLException {
        Connection connection = createDbConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category WHERE name = ?");
        stmt.setString(1, categoryName);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}
