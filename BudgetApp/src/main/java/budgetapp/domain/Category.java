package budgetapp.domain;

/**
 * Class for category
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
    
    /**
     * Compares the category with a given category by their names, can be used for setting categories in alphabetical order
     * @param otherCategory the category to be compared with
     * @return value 0 if category names are equal, value less than 0 if the name of this category is before the name of the otherCategory in alphabetical order,  and value greater than 0 if the name of this category is later than the name of the otherCategory in alphabetical order
     */
    @Override
    public int compareTo(Category otherCategory) {
        return this.name.compareTo(otherCategory.name);
    }
    
    /**
     * Checks whether two categories have the same properties and are thus equal
     * @param o the object to be compared with
     * @return true if o is an instance of Category class and has the same id and same name as this category and either both are incomeCategories or neither is, otherwise false
     */
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
