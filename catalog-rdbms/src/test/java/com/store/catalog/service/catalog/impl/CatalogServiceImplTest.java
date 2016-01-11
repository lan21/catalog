package com.store.catalog.service.catalog.impl;

import com.store.catalog.dao.CategoryDao;
import com.store.catalog.dao.ItemDao;
import com.store.catalog.dao.ProductDao;
import com.store.catalog.model.*;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceImplTest {

	@Mock
	private CategoryDao categoryDaoMock;	
	
	@Mock
	private ProductDao productDaoMock;	
	
	@Mock
	private ItemDao itemDaoMock;

	@Mock
    protected Mapper mockedMapper;

	private CatalogServiceImpl catalogService;
	

	@Before
	public void setUp() throws Exception {
		
		//init implementation CatalogServiceImpl
		catalogService = new CatalogServiceImpl();

		//set dependencies for Daos
		catalogService.setCategoryDao(categoryDaoMock);
		catalogService.setItemDao(itemDaoMock);
		catalogService.setProductDao(productDaoMock);

		//set dependency for dozer mapper
		catalogService.setDozerMapper(mockedMapper);
		
	}

	@After
	public void tearDown() throws Exception {
		catalogService = null;
	}

	
	
	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	
	@Test 
	public void saveCategoryTest() throws Exception {
        Category category = getCategory();
        CategoryDTO categoryDTO = getCategoryDto();

        when(categoryDaoMock.save(category)).thenReturn(category);
        when(mockedMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);
        when(mockedMapper.map(categoryDTO, Category.class)).thenReturn(category);

		CategoryDTO aCategoryDto = catalogService.createCategory(categoryDTO);

        verify(categoryDaoMock).save(category);

		assertNotNull(aCategoryDto);
		assertEquals(categoryDTO, aCategoryDto);
	}
	
	@Test 
	public void udapteCategoryTest() throws Exception {
        Category category = getCategory();
        CategoryDTO categoryDTO = getCategoryDto();

        when(categoryDaoMock.findOne(category.getId())).thenReturn(category);
        when(mockedMapper.map(categoryDTO, Category.class)).thenReturn(category);

		catalogService.updateCategory(categoryDTO);
		
		verify(categoryDaoMock).save(category);
	}
	
	
	@Test 
	public void deleteCategoryTest() throws Exception {
        Long id = Long.valueOf(1);

        doNothing().when(categoryDaoMock).delete(id);

		catalogService.deleteCategory(id);
		
		verify(categoryDaoMock).delete(id);
	}
	

	@Test 
	public void findCategoriesTest() throws Exception {

        Category category = getCategory();
        Category category2 = getCategory2();

        CategoryDTO categoryDTO = getCategoryDto();
        CategoryDTO categoryDTO2 = getCategoryDto();

        List<Category> returnedLst = new ArrayList<Category>();
		
		returnedLst.add(category);
		returnedLst.add(category2);
		
		when(categoryDaoMock.findAll()).thenReturn(returnedLst);
        when(mockedMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);
        when(mockedMapper.map(category2, CategoryDTO.class)).thenReturn(categoryDTO2);

		List<CategoryDTO> categoriesDto = catalogService.findCategories();
		
		assertNotNull(categoriesDto);
		
		assertEquals(2, categoriesDto.size());

        CategoryDTO returnedCategoryDTO1 = categoriesDto.get(0);
        CategoryDTO returnedCategoryDTO2 = categoriesDto.get(1);
        assertNotNull(returnedCategoryDTO1);
        assertNotNull(returnedCategoryDTO2);
	}	

	@Test 
	public void findCategoryTest() throws Exception {
        Category category = getCategory();
        CategoryDTO categoryDTO = getCategoryDto();

        when(categoryDaoMock.findOne(category.getId())).thenReturn(category);
        when(mockedMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO myCategoryDto = catalogService.findCategory(category.getId());
		
		assertNotNull(myCategoryDto);
		
		assertEquals(Long.valueOf(1L), myCategoryDto.getId());
		assertEquals(categoryDTO, myCategoryDto);
	}	
	

	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	@Test 
	public void saveProductTest() throws Exception {
		Product product = getProduct();
		ProductDTO productDto = getProductDto();
		
		when(productDaoMock.save(product)).thenReturn(product);
        when(mockedMapper.map(product, ProductDTO.class)).thenReturn(productDto);
        when(mockedMapper.map(productDto, Product.class)).thenReturn(product);
		
		ProductDTO aProductDto = catalogService.createProduct(productDto);
		
		verify(productDaoMock).save(product);
		
		assertNotNull(aProductDto);
		assertEquals(aProductDto,productDto);
	}	
	
	@Test 
	public void udapteProductTest() throws Exception {
		Product product = getProduct();		
		ProductDTO productDto = getProductDto();
		
		when(productDaoMock.findOne(product.getId())).thenReturn(product);
		when(mockedMapper.map(productDto, Product.class)).thenReturn(product);
		
		catalogService.updateProduct(productDto);
		
		verify(productDaoMock).save(product);
	}
	
	
	@Test 
	public void deleteProductTest() throws Exception {
		Long id = Long.valueOf(1);

        doNothing().when(productDaoMock).delete(id);

		catalogService.deleteProduct(id);
		
		verify(productDaoMock).delete(id);
	}
	

	@Test 
	public void findProductsTest() throws Exception {
		Product product = getProduct();
		Product product2 = getProduct2();

		ProductDTO productDTO = getProductDto();
		ProductDTO productDTO2 = getProductDto();

        List<Product> returnedLst = new ArrayList<Product>();
		
		returnedLst.add(product);
		returnedLst.add(product2);
		
		when(productDaoMock.findAll()).thenReturn(returnedLst);
        when(mockedMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        when(mockedMapper.map(product2, ProductDTO.class)).thenReturn(productDTO2);

		List<ProductDTO> productsDto = catalogService.findProducts(); //fonction à tester
		
		assertNotNull(productsDto);
		
		assertEquals(2, productsDto.size());

        ProductDTO returnedCategoryDTO1 = productsDto.get(0);
        ProductDTO returnedCategoryDTO2 = productsDto.get(1);
        assertNotNull(returnedCategoryDTO1);
        assertNotNull(returnedCategoryDTO2);
	}	

	@Test 
	public void findProductTest() throws Exception {
		Product product = getProduct();
		ProductDTO productDTO = getProductDto();
		
		when(productDaoMock.findOne(product.getId())).thenReturn(product);
		when(mockedMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
		
		ProductDTO foundProduct = catalogService.findProduct(product.getId());
		
		verify(productDaoMock).findOne(product.getId());
		
		assertNotNull(foundProduct);
		assertEquals(productDTO,foundProduct);
	}
	

	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	
	@Test 
	public void saveItemTest() throws Exception {
		Item item = getItem();
		ItemDTO itemDTO = getItemDto();
		
		when(itemDaoMock.save(item)).thenReturn(item);
		when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDTO);
		when(mockedMapper.map(itemDTO, Item.class)).thenReturn(item);
		
		ItemDTO anItemDTO = catalogService.createItem(itemDTO);
		
		verify(itemDaoMock).save(item);
		
		assertNotNull(anItemDTO);
		assertEquals(itemDTO, anItemDTO);		
	}	
	
	@Test 
	public void updateItemTest() throws Exception {
		Item item = getItem();
		ItemDTO itemDTO = getItemDto();

		when(itemDaoMock.findOne(item.getId())).thenReturn(item);
		when(mockedMapper.map(itemDTO, Item.class)).thenReturn(item);
		
		catalogService.updateItem(itemDTO);
		
		verify(itemDaoMock).save(item);
	}
	
	
	@Test 
	public void deleteItemTest() throws Exception {
		Long id = Long.valueOf(1);

        doNothing().when(itemDaoMock).delete(id);

		catalogService.deleteItem(id);
		
		verify(itemDaoMock).delete(id);
	}

    @Test
    public void findItemsTest() throws Exception {
    	Item item = getItem();
		Item item2 = getItem2();

        ItemDTO itemDTO = getItemDto();
        ItemDTO itemDTO2 = getItemDto();

        List<Item> returnedLst = new ArrayList<Item>();
		
		returnedLst.add(item);
		returnedLst.add(item2);
		
		when(itemDaoMock.findAll()).thenReturn(returnedLst);
        when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDTO);
        when(mockedMapper.map(item2, ItemDTO.class)).thenReturn(itemDTO2);

		List<ItemDTO> itemsDto = catalogService.findItems();
		
		assertNotNull(itemsDto);
		
		assertEquals(2, itemsDto.size());

        ItemDTO returnedItemDTO1 = itemsDto.get(0);
        ItemDTO returnedItemDTO2 = itemsDto.get(1);
        assertNotNull(returnedItemDTO1);
        assertNotNull(returnedItemDTO2);
    }
	

	@Test 
	public void findItemTest() throws Exception {
		Item item = getItem();
		ItemDTO itemDTO = getItemDto(item);
		
		when(itemDaoMock.findOne(item.getId())).thenReturn(item);
		when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDTO);
		
		ItemDTO foundItemDTO = catalogService.findItem(item.getId());
		
		
		verify(itemDaoMock).findOne(item.getId());
		assertNotNull(itemDTO);
		assertEquals(foundItemDTO, itemDTO);
	}

	
	@Test 
	public void findItemsByProductIdTest() throws Exception {
		Item item = getItem();
		Item item2 = getItem2();
		
        ItemDTO itemDTO = getItemDto(item);
        ItemDTO itemDTO2 = getItemDto();
        
        List<Item> returnedList = new ArrayList<Item>();
        
        Product product = item.getProduct();
        
        returnedList.add(item);
				
		when(itemDaoMock.findByProductId(product.getId())).thenReturn(returnedList);
		when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDTO);
		when(mockedMapper.map(item2, ItemDTO.class)).thenReturn(itemDTO2);
		
		List<ItemDTO> items = catalogService.findItems(product.getId());
		
		verify(itemDaoMock).findByProductId(product.getId());
		
		assertNotNull(items);
		assertEquals(1, items.size());
		assertEquals(itemDTO,items.get(0));
		assertFalse(itemDTO2.equals(items.get(0)));
	}	
	
	
	@Test 
	public void searchItemsTest() throws Exception {
		Item item = getItem();
		Item item2 = getItem();
		
		ItemDTO itemDTO = getItemDto(item);
		ItemDTO itemDTO2 = getItemDto(item2);
		
		List<Item> foundItems = new ArrayList<Item>();
		foundItems.add(item);
		foundItems.add(item2);
		
		when(itemDaoMock.findByNameContaining(item.getName())).thenReturn(foundItems);
		when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDTO);
		when(mockedMapper.map(item2, ItemDTO.class)).thenReturn(itemDTO2);
		
		List<ItemDTO> itemsDTO = catalogService.searchItems("articleID");
		
		verify(itemDaoMock).findByNameContaining(item.getName());
		
		assertNotNull(itemsDTO);
		assertEquals(2,itemsDTO.size());
	}
	
	
	
	/* ---------------------------------- */
	/* --------- Private methods -------- */
	/* ---------------------------------- */
	
	private CategoryDTO getCategoryDto(){
		CategoryDTO category = new CategoryDTO(Long.valueOf(1L), "categoryName", "categoryDesc");
		return category;
	}

	private Category getCategory(){
		Category category = new Category(Long.valueOf(1L), "categoryName", "categoryDesc");
		return category;
	}
	

	private Category getCategory2(){
		Category category = new Category(Long.valueOf(2L), "categoryName2", "categoryDesc2");
		return category;
	}	

	
	private ProductDTO getProductDto(){
		ProductDTO product = new ProductDTO(Long.valueOf(1L), "productName", "productDesc");
		return product;
	}	
	
	

	private Product getProduct(){
		Product product = new Product(Long.valueOf(1L), "productName", "productDesc");
		return product;
	}	
	
	private Product getProduct2(){
		Product product = new Product(Long.valueOf(2L), "productName2", "productDesc2");
		return product;
	}
	
	
	
	private ItemDTO getItemDto(){
		ItemDTO itemDto = new ItemDTO(Long.valueOf(1L),"articleID",20.0);
		return itemDto;
	}

	private ItemDTO getItemDto(Item item){
		Mapper mapper = new DozerBeanMapper();
		ItemDTO itemDto = mapper.map(item, ItemDTO.class);
		ProductDTO productDto = mapper.map(item.getProduct(), ProductDTO.class);
		//itemDto.setProduct(productDto);
		return itemDto;
	}
	

	private Item getItem(){
		Item item = new Item(Long.valueOf(1L),"articleID",20.0);
		item.setProduct(getProduct());
		return item;
	}
	
	private Item getItem2(){
		Item item = new Item(Long.valueOf(2L),"articleID2",30.0);
		
		return item;
	}	
	
}
