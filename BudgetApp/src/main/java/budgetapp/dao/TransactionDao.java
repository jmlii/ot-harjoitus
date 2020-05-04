package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.util.List;

/**
 * DAO pattern interface for Transaction class, serves between application logic and concrete DAO class.
 */
public interface TransactionDao {
    
    /**
     * Used for saving a new transaction to a storage.
     * @param transaction Transaction set in the application service class
     * @throws Exception On error with saving the transaction to be created
     */
    void create(Transaction transaction) throws Exception;
    
    /**
     * Used for retrieving a transaction's information from a storage.
     * @param key ID number given as a key in the application service class
     * @return Transaction object
     * @throws Exception On error with retrieving the transaction data
     */
    Transaction read(Integer key) throws Exception;
    
    /**
     * Used for updating a transaction's data to a storage.
     * @param transaction Transaction set in the application service class
     * @return Transaction object
     * @throws Exception On error with updating the transaction data
     */
    Transaction update(Transaction transaction) throws Exception;
    
    /**
     * Used for deleting a transaction from a storage.
     * @param key ID number given as a key in the application service class
     * @throws Exception On error with deleting the transaction
     */
    void delete(Integer key) throws Exception;
    
    /**
     * Used for listing all transactions in a storage.
     * @return List object containing transactions
     * @throws Exception On error with retrieving the transactions
     */
    List<Transaction> listAll() throws Exception;
    
    /**
     * Used for listing transactions in a storage from the wanted category in date order.
     * @param category Category set in the application service class
     * @return List object containing transactions
     * @throws Exception On error with retrieving the transactions
     */
    List<Transaction> listFromCategoryInDateOrder(Category category) throws Exception;
    
    /**
     * Used for listing all transactions in the storage in date order.
     * @return List object containing transactions
     * @throws Exception On error with retrieving the transactions
     */
    List<Transaction> listInDateOrder() throws Exception;
}
