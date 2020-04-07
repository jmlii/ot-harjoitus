package budgetapp.domain;

/**
 * Budjetin kategoriaa kuvaava luokka
 */

public class Category implements Comparable<Category> {
    private int id;
    private String name;
    private boolean incomeCategory;

    public Category(String name) {
        this.name = name;
        this.incomeCategory = false;
    }
    
    public Category(String name, boolean incomeCategory) {
        this.name = name;
        this.incomeCategory = incomeCategory;
    }
    
    public Category(int id, String name, boolean incomeCategory) {
        this.id = id;
        this.name = name;
        this.incomeCategory = incomeCategory;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(boolean incomeCategory) {
        this.incomeCategory = incomeCategory;
    }
    
    public void setIncomeCategoryTrue() {
        incomeCategory = true;
    }
    
    public void setIncomeCategoryFalse() {
        incomeCategory = false;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int compareTo(Category otherCategory) {
        return this.name.compareTo(otherCategory.name);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category other = (Category) o;
        if (this.id == other.id) {
            if (this.name.equals(other.name)) {
                return (this.incomeCategory == other.incomeCategory);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
}
