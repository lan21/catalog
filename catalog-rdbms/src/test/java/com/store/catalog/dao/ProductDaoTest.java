package com.store.catalog.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Random;

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
    @Deprecated
    public void testCreateProduct() throws Exception {
    	productDao.save(product);    	
    	assertNotNull(product.getId());
    	
    }    
   
    @Test
    public void testUpdateProduct() throws Exception {
        productDao.save(product);
        
        Product notUpdatedProduct = productDao.findOne(product.getId());
        
        final String NEW_NAME = "product updated";
        final String NEW_DESC = "description updated";
        product.setName(NEW_NAME);
        product.setDescription(NEW_DESC);
        
        productDao.save(product);
        
        Product updatedProduct = productDao.findOne(product.getId());
        
        assertEquals("Have same id",notUpdatedProduct,updatedProduct);
        assertEquals(NEW_NAME,updatedProduct.getName());
        assertEquals(NEW_DESC,updatedProduct.getDescription());
        //findOne retourne une référence et donc si l'objet est modifié, c'est répercuté partout
        assertEquals("Ancient product also change after update",NEW_NAME,notUpdatedProduct.getName());
        assertEquals("Ancient product also change after update",NEW_DESC,notUpdatedProduct.getDescription());        
    }    
    
    
    @Test
    public void testGetProduct() throws Exception {
    	productDao.save(product);
        
        Product foundProduct = productDao.findOne(product.getId());
        
        assertNotNull(foundProduct);
        assertEquals(product,foundProduct);
    }   

    
    @Test
    public void testRemoveProduct() throws Exception {
    	productDao.save(product);
        Product foundProduct = productDao.findOne(product.getId());        
        assertNotNull(foundProduct);
        
        productDao.delete(product);
        
        assertEquals("Effectively removed",0,getIterableSize(productDao.findAll()));
    }

    
    
    @Test
    public void testGetProducts() throws Exception {
    	productDao.save(product);
                
        assertEquals("Only one element",1,getIterableSize(productDao.findAll()));
        
        Product newProduct = new Product();
        newProduct.setId(new Random().nextLong());
        newProduct.setName("new product");
        newProduct.setDescription("new description");
        newProduct.setCategory(getCategory());
        
        productDao.save(newProduct);
        
        assertEquals("Now two element",2,getIterableSize(productDao.findAll()));
    }    
    

    @Test
    public void testGetProductsWithCategoryId() throws Exception {
    	productDao.save(product);
    	
    	Category cat1 = product.getCategory();
    	Category cat2 = getCategory2();
                
        Product newProduct = new Product();
        newProduct.setId(new Random().nextLong());
        newProduct.setName("new product");
        newProduct.setDescription("new description");
        newProduct.setCategory(cat1);
        productDao.save(newProduct);
        
        Product newProduct2 = new Product();
        newProduct2.setId(new Random().nextLong());
        newProduct2.setName("new product 2");
        newProduct2.setDescription("new description 2");
        newProduct2.setCategory(cat2);        
        productDao.save(newProduct2);
        
        assertEquals("Only two product of category 1",2,getIterableSize(productDao.findByCategoryId(cat1.getId())));
        assertEquals("Only one product of category 2",1,getIterableSize(productDao.findByCategoryId(cat2.getId())));
    }    

    
    @Test
    public void testGetProductsByCategoryName() throws Exception {
productDao.save(product);
    	
    	Category cat1 = product.getCategory();
    	Category cat2 = getCategory2();
                
        Product newProduct = new Product();
        newProduct.setId(new Random().nextLong());
        newProduct.setName("new product");
        newProduct.setDescription("new description");
        newProduct.setCategory(cat1);
        productDao.save(newProduct);
        
        Product newProduct2 = new Product();
        newProduct2.setId(new Random().nextLong());
        newProduct2.setName("new product 2");
        newProduct2.setDescription("new description 2");
        newProduct2.setCategory(cat2);        
        productDao.save(newProduct2);
        
        assertEquals("Only two product of category 1",2,getIterableSize(productDao.findByCategoryName(cat1.getName())));
        assertEquals("Only one product of category 2",1,getIterableSize(productDao.findByCategoryName(cat2.getName())));
    }        
	
	
	private Category getCategory() {
		Category category = new Category();
        category.setId(new Random().nextLong());
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);

        categoryDao.save(category);
		return category;
	}    
	
	private Category getCategory2() {
		Category category = new Category();
        category.setId(new Random().nextLong());
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
        product.setId(new Random().nextLong());
    	product.setName(PRODUCT_NAME);
    	product.setDescription(PRODUCT_DESCRIPTION);
    	product.setCategory(getCategory());
	}
	
}
