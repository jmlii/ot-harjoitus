package budgetapp.domain;

import java.time.LocalDate;

/**
 * Budjetin riviä kuvaava luokka
 */

public class Row {
    
    private int id;
    private String description;
    private int budget;
    private int expense;
    private LocalDate dateSpent;
    private Category category;

    public Row(int id, String description, int budget, int expense, LocalDate dateSpent, Category category) {
        this.id = id;
        this.description = description;
        this.budget = budget;
        this.expense = expense;
        this.dateSpent = dateSpent;
        this.category = category;
    }

    public Row(String description, int budget) {
        this.description = description;
        this.budget = budget;
    }
    
    public Row(String description, int expense, LocalDate dateSpent) {
        this.description = description;
        this.expense = expense;
        this.dateSpent = dateSpent;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public LocalDate getDateSpent() {
        return dateSpent;
    }

    public void setDateSpent(LocalDate dateSpent) {
        this.dateSpent = dateSpent;
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return id + ", " + category + ", " + description  + ", " + budget + ", " + expense + ", " + dateSpent;
    }
    
}
