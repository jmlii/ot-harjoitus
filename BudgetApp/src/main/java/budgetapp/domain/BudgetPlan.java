package budgetapp.domain;

/**
 * Budjetin tietoja kuvaava luokka
 */

public class BudgetPlan {
    
    private int id;
    private int total;
    private int allocated;
    private int unallocated;
    private int unused;
    private int spent;
       
    public BudgetPlan() {
        this.id = 0;
        this.total = 0;
        this.allocated = 0;
        this.unallocated = 0;
        this.unused = 0;
        this.spent = 0;
    }

    public int getId() {
        return id;
    }

    public int getTotal() {
        return total;
    }

    public int getAllocated() {
        return allocated;
    }

    public int getUnallocated() {
        return unallocated;
    }

    public int getUnused() {
        return unused;
    }

    public int getSpent() {
        return spent;
    }

    
}
