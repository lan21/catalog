package com.store.catalog.dao;

import com.store.catalog.utils.ConstantUtils;
import static com.store.catalog.utils.ConstantUtils.*;
import com.store.catalog.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CategoryDaoTest  extends AbstractBaseDaoTestCase{

	
	
	@Autowired
    private CategoryDao categoryDao;
    
	
    private Category category = null;
    
    @Before
    public void setUp(){
    	loadCategory();
    }

    
    @After
    public void tearDown(){
    	category = null;
    	categoryDao = null;
    }


    @Test
    public void testCreateCategory() throws Exception {
        categoryDao.save(category);
        assertNotNull("primary key assigned",category.getId());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        categoryDao.save(category);
        
        category.setName(ConstantUtils.CATEGORY_NAME + "MyTest");
        category.setDescription(ConstantUtils.CATEGORY_DESCRIPTION + "MyTest");
        
        categoryDao.save(category);
        Category catMyTest = categoryDao.findOne(category.getId());
        assertEquals(category,catMyTest);
    }

    @Test
    public void testGetCategory() throws Exception {
        categoryDao.save(category);
        
        Category cat = categoryDao.findOne(category.getId());
        
        assertNotNull(cat);
        assertEquals(category,cat);
    }

    @Test
    public void testRemoveCategory() throws Exception {
        categoryDao.save(category);
        
        Category cat = categoryDao.findOne(category.getId());
        
        assertNotNull(cat);
        assertEquals(category,cat);
        
        categoryDao.delete(cat.getId());
        
        assertTrue(getIterableSize(categoryDao.findAll()) == 0);
    }


    @Test
    public void testGetCategories() throws Exception {
        categoryDao.save(category);
        
        
        assertTrue(getIterableSize(categoryDao.findAll()) == 1);
        
        //add another element
        Category cat2 = new Category();
        cat2.setName(ConstantUtils.CATEGORY_NAME+"2");
        cat2.setDescription(ConstantUtils.CATEGORY_DESCRIPTION+"2");
        categoryDao.save(cat2);
        
        //Verify that the list contains 2 elements
        assertTrue(getIterableSize(categoryDao.findAll()) == 2);
    }

    
    /**
     * 
     * create an instanciated object. The one declared as private field in the test class
     */   
	private void  loadCategory() {
		category = new Category();
		category.setName(ConstantUtils.CATEGORY_NAME);
		category.setDescription(ConstantUtils.CATEGORY_DESCRIPTION);
	}


    
}
