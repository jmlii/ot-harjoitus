package budgetapp.domain;

import budgetapp.dao.TransactionDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fake TransactionDao class for JUnit testing purposes
 */
public class FakeTransactionDao implements TransactionDao {
    
    List<Transaction> transactions;
    int nextFreeId = 1;
    
    public FakeTransactionDao() {
        transactions = new ArrayList<>();
    }

    @Override
    public void create(Transaction transaction) throws Exception {
        transaction.setId(nextFreeId);
        nextFreeId += 1;
        transactions.add(transaction);
    }

    @Override
    public Transaction read(Integer key) throws Exception {
        Transaction transaction = null;
        for (Transaction t : transactions) {
            if (t.getId() == key) {
                transaction = t;
            }
        }
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) throws Exception {
        transaction.setCategory(transaction.getCategory());
        transaction.setDescription(transaction.getDescription());
        transaction.setAmount(transaction.getAmount());
        transaction.setDate(transaction.getDate());
        return transaction;
    }

    @Override
    public void delete(Integer key) throws Exception {
        for (Transaction t : transactions) {
            if (t.getId() == key) {
                transactions.remove(t);
            }
        }
    }

    @Override
    public List<Transaction> listAll() throws Exception {
        return transactions;
    }

    @Override
    public List<Transaction> listFromCategoryInDateOrder(Category category) throws Exception {
        List<Transaction> fromCategory = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equals(category)) {
                fromCategory.add(t);
            }
        }
        Collections.sort(fromCategory);
        return fromCategory;
    }

    @Override
    public List<Transaction> listInDateOrder() throws Exception {
        Collections.sort(transactions);
        return transactions;
    }
           
}
