package budgetapp.dao;

import budgetapp.domain.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pattern concrete class to handle methods for saving category data to the database and for retrieving it
 */
public class SQLCategoryDao implements CategoryDao {
    
    private Connection connection;
    
    /**
     * Constructor sets the database connection to the class and creates table Category to the database if the table does not yet exist
     * @param connection Database connection defined in the application configuration properties
     * @throws SQLException On error with SQL query
     */
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
    
    /**
     * Creates a new category to the database
     * @param category Category set in the application service class
     * @throws Exception On error with SQL query
     */
    @Override
    public void create(Category category) throws Exception {
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
    
    /**
     * Reads a category item from the database using the ID number to identify the wanted category
     * @param key ID number given as a key in the application service class
     * @return Category retrieved from the database with the given key as id number 
     * @throws Exception On error with SQL query
     */
    @Override
    public Category read(Integer key) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Category category = createCategory(rs);
        stmt.close();
        rs.close();
        return category;
    }
    
    /**
     * Reads a category item from the database using the category name to identify the wanted category
     * @param name Category name given in the application service class
     * @return Category retrieved from the database with the given name
     * @throws Exception On error with SQL query
     */
    @Override
    public Category readFromName(String name) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category WHERE name = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Category category = createCategory(rs);
        stmt.close();
        rs.close();
        return category;
    }
    
    /**
     * Updates the data of a given category in the database
     * @param category Category set in the application service class
     * @return Updated category set in the application service class
     * @throws Exception On error with SQL query
     */
    @Override
    public Category update(Category category) throws Exception {
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
    
    /**
     * Deletes a category item from the database using the ID number to identify the wanted category
     * @param key ID number given as a key in the application service class
     * @throws Exception On error with SQL query
     */
    @Override
    public void delete(Integer key) throws Exception {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Category"
                + " WHERE ID = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
    }
    
    /**
     * Creates a list of all categories included in the database
     * @return ArrayList object including all categories in the database
     * @throws Exception On error with SQL query
     */
    @Override
    public List<Category> listAll() throws Exception {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Category");
        ResultSet rs = stmt.executeQuery();
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Category category = createCategory(rs);
            categories.add(category);
        }
        stmt.close();
        rs.close();
        return categories;
    }
    
    /**
     * Creates a category object from the given ResultSet
     * @param rs ResultSet returned by the query stated in the method that uses this method
     * @return Category created from the ResultSet
     * @throws Exception On error with ResultSet returned by the SQL query
     */
    private Category createCategory(ResultSet rs) throws Exception {
        Category category = new Category(rs.getInt("id"), rs.getString("name"), 
                    rs.getBoolean("incomeCategory"));
        return category;
    }

}
