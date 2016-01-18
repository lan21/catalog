package com.store.catalog.dao;

import com.store.catalog.model.Product;
import com.store.catalog.model.Category;
import com.store.catalog.model.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static com.store.catalog.utils.ConstantUtils.*;

import static org.junit.Assert.*;

public class ItemDaoTest extends AbstractBaseDaoTestCase {
    


	private Item item = null;

    @Autowired
    private ItemDao itemDao ;
    
    @Autowired
    private ProductDao productDao ;
    
    @Autowired
    private CategoryDao categoryDao ;    
   
    @Before
    public void setUp(){
    	loadItem();
    }
    
    @After
    public void tearDown(){
    	categoryDao = null;
    	productDao = null;
    	itemDao  = null;
    	item = null;
    }


    @Test
    public void testCreateItem() throws Exception {
        itemDao.save(item);
        Item createdItem = itemDao.findOne(item.getId());
        assertNotNull(createdItem);
    }


    @Test
    public void testUpdateItem() throws Exception {
    	itemDao.save(item);
        
        final String NEW_NAME = "item updated";
        final String NEW_IMG = "item image updated";
        final double NEW_COST = 15d;
        item.setName(NEW_NAME);
        item.setImagePath(NEW_IMG);
        item.setUnitCost(NEW_COST);
        
        itemDao.save(item);
        
        Item updatedItem = itemDao.findOne(item.getId());
        
        assertEquals(NEW_NAME,updatedItem.getName());
        assertEquals(NEW_IMG,updatedItem.getImagePath());
        assertEquals(NEW_COST,updatedItem.getUnitCost(),0.0001d);
    }

    @Test
    public void testGetItem() throws Exception {
    	itemDao.save(item);
    	Item foundItem = itemDao.findOne(item.getId());
    	assertNotNull(foundItem);
        assertEquals(item,foundItem);
    }


    @Test
    public void testRemoveItem() throws Exception {
    	itemDao.save(item);
    	Item foundItem = itemDao.findOne(item.getId());
    	assertNotNull(foundItem);
    	
    	itemDao.delete(item);
    	assertEquals("Effectively removed",0,getIterableSize(itemDao.findAll()));
    }


    @Test
    public void testGetItems() throws Exception {
    	itemDao.save(item);
        
        assertEquals("Only one element",1,getIterableSize(itemDao.findAll()));
        
        Item item2 = getAnotherItem();
        
        itemDao.save(item2);
        
        assertEquals("Now two element",2,getIterableSize(itemDao.findAll()));
    }


    @Test
    public void testGetItemsWithProductId() throws Exception {
    	itemDao.save(item);       
        Item item2 = getAnotherItem();
        item2.setProduct(item.getProduct());
        itemDao.save(item2);
        
        Item item3 = getAnotherItem();
        Product product2 = getProduct("prod2", "product 2");
        item3.setProduct(product2);
        itemDao.save(item3);
        
        assertEquals("itemDao have 3 element",3,getIterableSize(itemDao.findAll()));
        assertEquals("Only two items of product 1",2,getIterableSize(itemDao.findByProductId(item.getProduct().getId())));
        assertEquals("Only one item of product 2",1,getIterableSize(itemDao.findByProductId(item3.getProduct().getId())));
    }    


    @Test
    public void testSearchItem() throws Exception {
    	itemDao.save(item);       
        Item item2 = getAnotherItem();
        item2.setProduct(item.getProduct());
        itemDao.save(item2);
        
        assertEquals(2,getIterableSize(itemDao.findByNameContaining(ITEM_NAME)));
        assertEquals(1,getIterableSize(itemDao.findByNameContaining(ITEM_NAME + "2")));
        
    }
    
    
    /**
     * 
     * @return an instanciated object. The one declared as private field in the test class
     */    
	private void loadItem() {
	   	item = new Item();
        item.setId(new Random().nextLong());
        item.setName(ITEM_NAME);
        item.setImagePath(ITEM_IMAGE_PATH);
        item.setUnitCost(ITEM_PRICE);
        item.setProduct(getProduct());
	}
	
	
	private Item getAnotherItem() {
        Item item2 = new Item();
        item2.setId(new Random().nextLong());
        item2.setName(ITEM_NAME + "2");
        item2.setImagePath(ITEM_IMAGE_PATH + "2");
        item2.setUnitCost(ITEM_PRICE + 10d); 
        item2.setProduct(getProduct());

        return item2;
	}	
    
    
	private Product getProduct() {
	   	Product product = new Product();
        product.setId(new Random().nextLong());
        product.setName(PRODUCT_NAME);
        product.setDescription(PRODUCT_DESCRIPTION);

        Category category = getCategory();
        product.setCategory(category);

        productDao.save(product);        
        
        return product;
	}    
	
	private Product getProduct(String name, String desc) {
	   	Product product = new Product();
        product.setId(new Random().nextLong());
        product.setName(name);
        product.setDescription(desc);

        Category category = getCategory();
        product.setCategory(category);

        productDao.save(product);        
        
        return product;
	}	
	
	private Category getCategory() {
		Category category = new Category();
        category.setId(new Random().nextLong());
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);

        categoryDao.save(category);
		return category;
	}    	
}
