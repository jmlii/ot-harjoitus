package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {
    void create(Transaction transaction) throws Exception;
    Transaction read(Integer key) throws Exception;
    Transaction update(Transaction transaction) throws Exception;
    void delete(Integer key) throws Exception;
    List<Transaction> listAll() throws Exception;
    List<Transaction> listByCategory(Category category) throws Exception;
}
