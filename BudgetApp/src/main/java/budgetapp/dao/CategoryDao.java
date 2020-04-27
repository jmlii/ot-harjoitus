package budgetapp.dao;

import budgetapp.domain.Category;
import java.util.List;

/**
 * DAO pattern interface for Category class, serves between application logic and concrete DAO class
 */
public interface CategoryDao {
    void create(Category category) throws Exception;
    Category read(Integer key) throws Exception;
    Category readFromName(String name) throws Exception;
    Category update(Category category) throws Exception;
    void delete(Integer key) throws Exception;
    List<Category> listAll() throws Exception;
}

