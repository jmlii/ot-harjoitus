package budgetapp.domain;

import java.time.LocalDate;

/**
 * Class for transaction.
 */
public class Transaction implements Comparable<Transaction> {
    
    private int id;
    private Category category;
    private String description;
    private int amount;
    private LocalDate date;
    
    public Transaction(int id, Category category, String description, int amount, LocalDate date) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Category category, String description, int amount, LocalDate date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * Compares the transaction with a given transaction by their dates, can be used for setting transactions in date order, later date first.
     * @param otherTransaction the transaction to be compared with
     * @return value 0 if dates are equal, value less than 0 if the date of this transaction is greater (i.e. later) than the date of the other transaction, and value greater than 0 if the date of this category is smaller than (i.e. before) the date of the other transaction
     */
    @Override
    public int compareTo(Transaction otherTransaction) {
        return otherTransaction.date.compareTo(this.date);
    }
    
}
