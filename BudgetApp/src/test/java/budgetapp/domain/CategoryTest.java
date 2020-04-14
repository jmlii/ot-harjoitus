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
    
    Category category1, category2, category3;
    
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
        category1 = new Category(1, "test", true);
        category2 = new Category(2, "anotherTest", false);
        category3 = new Category("yetAnother");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructorSetsIncomeAsFalseIfInformationNotGiven() {
        assertEquals(false, category3.isIncomeCategory());
    }
    
    @Test
    public void setIncomeCategorySetsCorrecttBooleanValue() {
        category2.setIncomeCategory(true);
        assertTrue(category2.isIncomeCategory());
    }
    
    @Test
    public void setIncomeCategoryTrueSetsCorrectBooleanValue() {
        category2.setIncomeCategoryTrue();
        assertTrue(category2.isIncomeCategory());
    }
    
    @Test
    public void setIncomeCategoryFalseSetsCorrectBooleanValue() {
        category1.setIncomeCategoryFalse();
        assertFalse(category1.isIncomeCategory());
    }
    
    @Test
    public void categoriesAreSortedInRightAlphabeticalOrder() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        Collections.sort(categories);
        assertEquals("[anotherTest, test, yetAnother]", categories.toString());
    }

    @Test
    public void equalWhenSameIdAndNameAndIncomeCategoryValue() {
        Category new1 = new Category(1, "test", false);
        Category new2 = new Category(1, "test", false);
        assertTrue(new1.equals(new2));
    }
    
    @Test
    public void notEqualWhenDifferentIdSameNameSameIncomeCategoryValue() {
        Category new1 = new Category(1, "test", false);
        Category new2 = new Category(2, "test", false);
        assertFalse(new1.equals(new2));
    }
    
    @Test
    public void notEqualWhenSameIdDifferentNameSameIncomeCategoryValue() {
        Category new1 = new Category(1, "test", false);
        Category new2 = new Category(1, "testOther", false);
        assertFalse(new1.equals(new2));
    }
    
    @Test
    public void notEqualWhenSameIdSameNameDifferentIncomeCategoryValue() {
        Category new1 = new Category(1, "test", true);
        Category new2 = new Category(1, "test", false);
        assertFalse(new1.equals(new2));
    } 
    
    @Test
    public void nonEqualWhenDifferentType() {
        Object o = new Object();
        assertFalse(category1.equals(o));
    }
    
    @Test 
    public void EqualWhenSameObject() {
        Category test = category1;
        assertTrue(test.equals(category1));
    }

}
