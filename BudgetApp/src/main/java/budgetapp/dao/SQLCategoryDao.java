package budgetapp.dao;

import budgetapp.domain.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class SQLCategoryDao implements CategoryDao {
    
    private Connection connection;
    
    public SQLCategoryDao(Connection connection) throws SQLException {
        this.connection = connection; 
        PreparedStatement stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Category ("
                        + "id SERIAL, "
                        + "name VARCHAR(64), "
                        + "incomeCategory BOOLEAN NOT NULL, "
                        + "PRIMARY KEY (id), "
                        + "UNIQUE (name))");
        stmt.executeUpdate();   
        stmt.close();
    }
    
    @Override
    public void create(Category category) throws SQLException {
        //if (!nameExists(category.getName())) {
        if (readFromName(category.getName()) == null) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Category"
                    + " (name, incomeCategory)"
                    + " VALUES (?, ?)");
            stmt.setString(1, category.getName());
            stmt.setBoolean(2, category.isIncomeCategory());
            stmt.executeUpdate();
            stmt.close();            
        }
    }
    
    @Override
    public Category read(Integer key) throws SQLException {
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
        return category;
    }
    
    @Override
    public Category readFromName(String name) throws SQLException {
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
        return category;
    }
    
    @Override
    public Category update(Category category) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Category"
                + " SET name = ?, incomeCategory = ?"
                + " WHERE id = ?");
        stmt.setString(1, category.getName());
        stmt.setBoolean(2, category.isIncomeCategory());
        stmt.setInt(3, category.getId());
        stmt.executeUpdate();
        stmt.close();
        return category;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Category"
                + " WHERE ID = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public List<Category> listAll() throws SQLException {
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
        return categories;
    }

}
