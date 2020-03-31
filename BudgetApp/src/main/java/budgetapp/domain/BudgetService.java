package budgetapp.domain;

/**
 * Sovelluslogiikasta vastaava luokka; Dao-rajapinnat sovelluslogiikan ja luokkien väliin lisätään myöhemmin
 */

import java.util.ArrayList;
import java.util.Collections;


public class BudgetService {
    
    private ArrayList<Category> categories;
    private ArrayList<Row> rows;
    
    public BudgetService() {
        this.rows = new ArrayList<>();
        this.categories = new ArrayList<>();
        
        
        categories.add(new Category("Housing"));
        categories.add(new Category("Groceries"));
        categories.add(new Category("Transportation"));
        categories.add(new Category("Holiday"));
        categories.add(new Category("Hobbies"));
        categories.add(new Category("Culture and entertainment"));
        categories.add(new Category("Restaurants and cafes"));
        categories.add(new Category("Healthcare"));
        categories.add(new Category("Wellbeing"));
        categories.add(new Category("Shopping"));
        categories.add(new Category("Savings and investments"));
        Collections.sort(categories);
        categories.add(0, new Category("Income", true));
        categories.add(new Category("Others"));
    }
    
    public void printRows() {
        if (rows.isEmpty()) {
            System.out.println("No budget rows");
        } else {
            for (Row row : rows) {
                System.out.println(row);
            }
        }
    }
    
    public void addIncomeRow(String description, int budget) {
        
        Row row = new Row(description, budget);
        Category income = null;
        for (Category category : categories) {
            if (category.getName().equals("Income")) {
                income = category;
            }
        }
        row.setCategory(income);
        int id = rows.size() + 1;
        row.setId(id);
        rows.add(row);
    }
    
}
