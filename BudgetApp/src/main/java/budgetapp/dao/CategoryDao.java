package budgetapp.dao;

import budgetapp.domain.Category;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 */
public interface CategoryDao {
    void create(Category category) throws SQLException;
    Category read(Integer key) throws SQLException;
    Category readFromName(String name) throws SQLException;
    Category update(Category category) throws SQLException;
    void delete(Integer key) throws SQLException;
    List<Category> listAll() throws SQLException;
    boolean nameExists(String categoryName) throws SQLException;
}

