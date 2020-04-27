package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.util.List;

/**
 * DAO pattern interface for Transaction class, serves between application logic and concrete DAO class
 */
public interface TransactionDao {
    void create(Transaction transaction) throws Exception;
    Transaction read(Integer key) throws Exception;
    Transaction update(Transaction transaction) throws Exception;
    void delete(Integer key) throws Exception;
    List<Transaction> listAll() throws Exception;
    List<Transaction> listFromCategory(Category category) throws Exception;
    List<Transaction> listInDateOrder() throws Exception;
}
