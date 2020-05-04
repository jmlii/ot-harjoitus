package budgetapp.domain;

import budgetapp.dao.CategoryDao;
import java.util.ArrayList;
import java.util.List; 

/**
 * Fake CategoryDao class for JUnit testing purposes
 */
public class FakeCategoryDao implements CategoryDao {
    
    List<Category> categories;
    int nextFreeId=1;
    
    public FakeCategoryDao() {
        categories = new ArrayList<>();
    }
    
    @Override
    public void create(Category category) throws Exception {
        if (readFromName(category.getName()) == null) {
        //if (!nameExists(category.getName())) {
            category.setId(nextFreeId);
            nextFreeId += 1;
            categories.add(category);
        }
    }
    
    @Override
    public Category read(Integer key) throws Exception {
        Category category = new Category("fake");
        for (Category c : categories) {
            if (c.getId() == key) {
                category = c;
            }
        }
        return category;
    }
    
    @Override
    public Category readFromName(String name) throws Exception {
        Category category = null;
        for (Category c : categories) {
            if (c.getName().equals(name)) {
                category = c;
            }
        }
        return category;
    }
    
    @Override
    public Category update(Category category) throws Exception {
        category.setName(category.getName());
        category.setIncomeCategory(category.isIncomeCategory());
        return category;
    }    
    
    @Override
    public void delete(Integer key) throws Exception {
        for (Category c : categories) {
            if (c.getId() == key) {
                categories.remove(c);
            }
        }
    }    
    
    @Override
    public List<Category> listAll() throws Exception {
        return categories;
    }
    
}
