package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Transaction;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {
    void create(Transaction transaction) throws SQLException;
    Transaction read(Integer key) throws SQLException;
    Transaction update(Transaction transaction) throws SQLException;
    void delete(Integer key) throws SQLException;
    List<Transaction> listAll() throws SQLException;
    List<Transaction> listBycategory(Category category) throws SQLException;
}
