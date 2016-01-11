package com.store.catalog.service.catalog.impl;

import com.store.catalog.model.*;
import com.store.catalog.service.catalog.CatalogService;
import com.store.catalog.common.exception.CheckException;
import com.store.catalog.dao.CategoryDao;
import com.store.catalog.dao.ItemDao;
import com.store.catalog.dao.ProductDao;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

	Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class.getName());


	@Autowired
	private CategoryDao categoryDao ;
	
	@Autowired
	protected ProductDao productDao ;
	
	@Autowired
	private ItemDao itemDao ;
	
	@Autowired
	private Mapper dozerMapper;



	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

    public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	



	// ======================================
	// = Product Business methoods =
	// ======================================
	public ProductDTO createProduct(ProductDTO productDto) throws CheckException {
		if (productDto == null)
            throw new CheckException("Product object is null");
		logger.info("start");
		Product product = dozerMapper.map(productDto, Product.class);
		
		productDao.save(product);
		
		ProductDTO productworkDto = dozerMapper.map(product, ProductDTO.class);
		
		logger.info("end");
		return productworkDto;
	}

	@Transactional(readOnly=true)
	public List<ProductDTO> findProducts() {
		ArrayList<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		for(Product foundProduct : productDao.findAll()){
			ProductDTO productDTO = dozerMapper.map(foundProduct, ProductDTO.class);
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}

	@Transactional(readOnly=true)
	public List<ProductDTO>  findProducts(Long categoryId) throws CheckException {
		throw new RuntimeException("not yet implemented");
	}

	@Transactional(readOnly=true)
	public ProductDTO findProduct(Long productId) throws CheckException {
		Product foundProduct = productDao.findOne(productId);		
		ProductDTO productDTO = dozerMapper.map(foundProduct, ProductDTO.class);		
		return productDTO;
	}	
	

	@Transactional(readOnly=true)
 	public List<ProductDTO> findProducts(String categoryName) throws CheckException {
		throw new RuntimeException("not yet implemented");
	}	
	
	@Transactional
	public void updateProduct(ProductDTO productDto) throws CheckException {
		if (productDto == null)
            throw new CheckException("Product object is null");
        
		if (productDto.getId() == null)
            throw new CheckException("Invalid id");


		Product foundProduct =  productDao.findOne(productDto.getId());

		if(foundProduct == null){
			throw new CheckException("Unknown id");
		}

		Product product = dozerMapper.map(productDto, Product.class);
		productDao.save(product);
	}

	@Transactional
	public void deleteProduct(Long productId) throws CheckException {
		if (productId == null || "".equals(productId))
            throw new CheckException("Invalid id");
        
		productDao.delete(productId);	
	}
	
    // ======================================
    // =        Item Business methods       =
    // ======================================	
	
	@Transactional
	public ItemDTO createItem(ItemDTO itemDto) throws CheckException {
		if (itemDto == null)
            throw new CheckException("Item object is null");
		logger.info("start");
		
		Item item = dozerMapper.map(itemDto, Item.class);
		itemDao.save(item);
		ItemDTO itemWorkDTO = dozerMapper.map(item, ItemDTO.class);
		
		logger.info("end");
		return itemWorkDTO;
	}

	@Transactional
	public void updateItem(ItemDTO itemDto) throws CheckException {
		if(itemDto == null)
			throw new CheckException("Item object is null");
		if(itemDto.getId() == null)
			throw new CheckException("Invalid ID");
		
		Item foundItem = itemDao.findOne(itemDto.getId());
		
		if(foundItem == null){
			throw new CheckException("Unknown id");
		}
		
		Item item = dozerMapper.map(itemDto, Item.class);
		
		itemDao.save(item);
	}


	@Transactional
	public void deleteItem(Long itemId) throws CheckException {
		if (itemId == null || "".equals(itemId))
            throw new CheckException("Invalid id");
        
		itemDao.delete(itemId);	
	}

	@Transactional(readOnly=true)
	public List<ItemDTO> findItems() {
		List<ItemDTO> itemsDTO = new ArrayList<ItemDTO>();
		for(Item foundItem : itemDao.findAll()){
			ItemDTO itemDTO = dozerMapper.map(foundItem, ItemDTO.class);
			itemsDTO.add(itemDTO);
		}
		return itemsDTO;
	}

	@Transactional(readOnly=true)
	public List<ItemDTO> findItems(Long productId) throws CheckException {
		List<ItemDTO> items = new ArrayList<ItemDTO>();
		
		for(Item foundItem : itemDao.findByProductId(productId)){
			ItemDTO itemDTO = dozerMapper.map(foundItem, ItemDTO.class);
			items.add(itemDTO);
		}		
		return items;
	}

	
	@Transactional(readOnly=true)
	public List<ItemDTO> searchItems(String keyword) throws CheckException{
List<ItemDTO> items = new ArrayList<ItemDTO>();
		
		for(Item foundItem : itemDao.findByNameContaining(keyword)){
			ItemDTO itemDTO = dozerMapper.map(foundItem, ItemDTO.class);
			items.add(itemDTO);
		}		
		return items;
	}



	@Transactional(readOnly=true)
	public ItemDTO findItem(Long itemId) throws CheckException {
		ItemDTO itemDTO;
		Item foundItem = itemDao.findOne(itemId);
		itemDTO = dozerMapper.map(foundItem, ItemDTO.class);
		return itemDTO;
	}

	
    // ======================================
    // =        Category Business methods       =
    // ======================================	
	
	
	@Transactional
	public CategoryDTO createCategory(CategoryDTO categoryDto) throws CheckException {
		if (categoryDto == null)
            throw new CheckException("Category object is null");
        
		logger.info("start");
		Category category = dozerMapper.map(categoryDto, Category.class);
		
		categoryDao.save(category);
		
		CategoryDTO categoryworkDto = dozerMapper.map(category, CategoryDTO.class);
		
		logger.info("end");
		return categoryworkDto;
	}

	@Transactional
	public void deleteCategory(Long categoryId) throws CheckException {
		if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid id");
        
		categoryDao.delete(categoryId);		
	}

	@Transactional
	public void updateCategory(CategoryDTO categoryDto) throws CheckException {
		if (categoryDto == null)
            throw new CheckException("Category object is null");
        
		if (categoryDto.getId() == null)
            throw new CheckException("Invalid id");


		Category foundCategory =  categoryDao.findOne(categoryDto.getId());

		if(foundCategory == null){
			throw new CheckException("unkown id");
		}

		Category category = dozerMapper.map(categoryDto, Category.class);
		categoryDao.save(category);
	}


	@Transactional(readOnly=true)
	public CategoryDTO findCategory(Long categoryId) throws CheckException {
		if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid id");


        Category category = categoryDao.findOne(categoryId);
		
		CategoryDTO categoryDto = dozerMapper.map(category,CategoryDTO.class);
		return categoryDto;
	}
	
	@Transactional(readOnly=true)
	public List<CategoryDTO> findCategories() {

        Iterable<Category> lst =  categoryDao.findAll();

		List<CategoryDTO> lstDto = new ArrayList<CategoryDTO>();

		for (Category obj:lst){
	        CategoryDTO categoryDto = dozerMapper.map(obj, CategoryDTO.class);
	        lstDto.add(categoryDto);
		}


		return lstDto;
	}


}
