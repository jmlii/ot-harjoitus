package budgetapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Budjetin kategoriaa kuvaavan luokan testit
 */

public class CategoryTest {
    
    public CategoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructorSetsIncomeAsFalseIfInformationNotGiven() {
        Category category = new Category("TestCategory");
        assertEquals(false, category.isIncomeCategory());
    }
    
    @Test
    public void setIncomeCategorySetsCorrecttBooleanValue() {
        Category category = new Category("test", false);
        category.setIncomeCategory(true);
        assertTrue(category.isIncomeCategory());
    }
    
    @Test
    public void setIncomeCategoryTrueSetsCorrectBooleanValue() {
        Category category = new Category("test", false);
        category.setIncomeCategoryTrue();
        assertTrue(category.isIncomeCategory());
    }
    
    @Test
    public void setIncomeCategoryFalseSetsCorrectBooleanValue() {
        Category category = new Category("test", true);
        category.setIncomeCategoryFalse();
        assertFalse(category.isIncomeCategory());
    }
    
    @Test
    public void categoriesAreSortedInRightAlphabeticalOrder() {
        Category category1 = new Category(1, "test", true);
        Category category2 = new Category(3, "other", false);
        Category category3 = new Category(5, "new", false);
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        Collections.sort(categories);
        assertEquals("[new, other, test]", categories.toString());
    }

    @Test
    public void equalWhenSameIdAndNameAndIncomeCategoryValue() {
        Category category1 = new Category(1, "test", false);
        Category category2 = new Category(1, "test", false);
        assertTrue(category1.equals(category2));
    }
    
    @Test
    public void notEqualWhenDifferentIdSameNameSameIncomeCategoryValue() {
        Category category1 = new Category(1, "test", false);
        Category category2 = new Category(2, "test", false);
        assertFalse(category1.equals(category2));
    }
    
    @Test
    public void notEqualWhenSameIdDifferentNameSameIncomeCategoryValue() {
        Category category1 = new Category(1, "test", false);
        Category category2 = new Category(1, "testOther", false);
        assertFalse(category1.equals(category2));
    }
    
    @Test
    public void notEqualWhenSameIdSameNameDifferentIncomeCategoryValue() {
        Category category1 = new Category(1, "test", true);
        Category category2 = new Category(1, "test", false);
        assertFalse(category1.equals(category2));
    } 
    
    @Test
    public void nonEqualWhenDifferentType() {
        Category category = new Category(1, "test", true);
        Object o = new Object();
        assertFalse(category.equals(o));
    }
    
    @Test 
    public void EqualWhenSameObject() {
        Category category1 = new Category("test", true);
        Category category2 = category1;
        assertTrue(category1.equals(category2));
    }

}
