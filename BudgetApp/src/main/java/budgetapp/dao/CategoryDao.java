package budgetapp.dao;

import budgetapp.domain.Category;
import java.util.List;

/**
 * DAO pattern interface for Category class, serves between application logic and concrete DAO class.
 */
public interface CategoryDao {
    
    /**
     * Used for saving a new category to a storage.
     * @param category Category set in the application service class
     * @throws Exception On error with saving the category to be created
     */
    void create(Category category) throws Exception;
 
    /**
     * Used for retrieving a category's data from a storage. 
     * @param key ID number given as a key in the application service class
     * @return Category object
     * @throws Exception On error with retrieving the category data from the storage
     */
    Category read(Integer key) throws Exception;
    
    /**
     * Used for reading a category's data from a storage using the category name.
     * @param name Name set in the application service class
     * @return Category object
     * @throws Exception On error with retrieving the category data
     */
    Category readFromName(String name) throws Exception;
    
    /**
     * Used for updating a category's data to a storage.
     * @param category Category set in the application service class
     * @return Updated category object
     * @throws Exception On error with updating the category data
     */
    Category update(Category category) throws Exception;
    
    /**
     * Used for deleting a category from a storage.
     * @param key ID number given as a key in the application service class
     * @throws Exception On error with deleting the category
     */
    void delete(Integer key) throws Exception;
    
    /**
     * Used for listing all categories in a storage.
     * @return List object containing categories
     * @throws Exception On error with retrieving the categories
     */
    List<Category> listAll() throws Exception;
}

