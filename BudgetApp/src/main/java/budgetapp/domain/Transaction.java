package budgetapp.domain;

import java.time.LocalDate;

/**
 * Class for transaction
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
    
    @Override
    public int compareTo(Transaction otherTransaction) {
        return otherTransaction.date.compareTo(this.date);
    }

    
    @Override
    public String toString() {
        return id + ", " + category.toString() + ", " + description  + ", " + amount + ", " + date;
    }
    
}
