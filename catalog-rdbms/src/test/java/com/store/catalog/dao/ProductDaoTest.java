package com.store.catalog.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.store.catalog.model.Category;
import com.store.catalog.model.Product;

import static com.store.catalog.utils.ConstantUtils.*;

public class ProductDaoTest extends AbstractBaseDaoTestCase {

    
    @Autowired
    private ProductDao productDao ;
    
    @Autowired
    private CategoryDao categoryDao ;    
    
    private Product product = null;
    
    
    @Before
    public void setUp(){
    	loadProduct();
    }
    
    @After
    public void tearDown(){
    	categoryDao = null;
    	productDao = null;
    }

    @Test
    public void testCreateProduct() throws Exception {
        throw new Exception("not yet implemented");
    }    
   
    @Test
    public void testUpdateProduct() throws Exception {
        throw new Exception("not yet implemented");
    }    
    
    
    @Test
    public void testGetProduct() throws Exception {
        throw new Exception("not yet implemented");
    }   

    
    @Test
    public void testRemoveProduct() throws Exception {
        throw new Exception("not yet implemented");
    }

    
    
    @Test
    public void testGetProducts() throws Exception {
        throw new Exception("not yet implemented");
    }    
    

    @Test
    public void testGetProductsWithCategoryId() throws Exception {
        throw new Exception("not yet implemented");
    }    

    
    @Test
    public void testGetProductsByCategoryName() throws Exception {
        throw new Exception("not yet implemented");
    }        
	
	
	private Category getCategory() {
		Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);

        categoryDao.save(category);
		return category;
	}    
	
	private Category getCategory2() {
		Category category = new Category();
        category.setName("catName2");
        category.setDescription("description2");

        categoryDao.save(category);
		return category;
	}    
    
	
    /**
     * 
     * create an instanciated object. The one declared as private field in the test class
     */	
	private void loadProduct() {
    	product = new Product();
    	product.setName(PRODUCT_NAME);
    	product.setDescription(PRODUCT_DESCRIPTION);
    	product.setCategory(getCategory());
	}
	
}
