package com.e_commerce.epic_loot.service;

import com.e_commerce.epic_loot.api.DTO.Page.CategoryPageDTO;
import com.e_commerce.epic_loot.api.DTO.Page.HomePageDTO;
import com.e_commerce.epic_loot.api.DTO.Page.ProductGroupDTO;
import com.e_commerce.epic_loot.api.DTO.Page.ProductListDTO;
import com.e_commerce.epic_loot.api.DTO.Product.CriteriaDTO;
import com.e_commerce.epic_loot.model.Category;
import com.e_commerce.epic_loot.model.Product;
import com.e_commerce.epic_loot.model.SubCategory;
import com.e_commerce.epic_loot.model.repository.CategoryRepository;
import com.e_commerce.epic_loot.model.repository.CustomerRepository;
import com.e_commerce.epic_loot.model.repository.SubCategoryRepository;
import com.e_commerce.epic_loot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CustomerService customerService;

    public HomePageDTO getHomePage(Integer customerId) {
        HomePageDTO homePage = new HomePageDTO();
        homePage.setBannerUrl("Stalker2.png"); // Set the banner URL for the home page, if needed

        List<ProductGroupDTO> productGroups = new ArrayList<>();

        // Fetch popular games
        CriteriaDTO popularCriteria = new CriteriaDTO();
        popularCriteria.setCustomerId(customerId);
        popularCriteria.setCriteria("Популярні ігри");
        productGroups.add(productService.getProductsByCriteria(popularCriteria));

        // Fetch discounted games
        CriteriaDTO discountCriteria = new CriteriaDTO();
        discountCriteria.setCustomerId(customerId);
        discountCriteria.setCriteria("Знижки");
        productGroups.add(productService.getProductsByCriteria(discountCriteria));

        // Fetch personalized games
        CriteriaDTO personalizedCriteria = new CriteriaDTO();
        personalizedCriteria.setCustomerId(customerId);
        personalizedCriteria.setCriteria("Ігри для вас");
        productGroups.add(productService.getProductsByCriteria(personalizedCriteria));

        homePage.setProductGroups(productGroups);
        return homePage;
    }

    public CategoryPageDTO getCategoryPage(Integer id) {
        CategoryPageDTO categoryPage = new CategoryPageDTO();

        // Fetch category details
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category not found with id: " + id)
        );

        categoryPage.setTitle(category.getTitle());
        categoryPage.setBannerUrl(category.getBannerUrl());

        List<ProductGroupDTO> productGroups = new ArrayList<>();

        // Fetch groups for subcategories
        for (SubCategory subCategory : category.getSubCategories()) {
            ProductGroupDTO group = new ProductGroupDTO();
            group.setTitle(subCategory.getTitle());
            group.setProducts(
                    subCategory.getProducts()
                            .stream()
                            .map(productService::mapToProductCardDTO)
                            .toList()
            );
            productGroups.add(group);
        }

        categoryPage.setProductGroups(productGroups);

        return categoryPage;
    }

    public ProductListDTO getSubcategoryPage(Integer id) {
        ProductListDTO productList = new ProductListDTO();

        // Fetch subcategory details
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("SubCategory not found with id: " + id)
        );

        productList.setBannerUrl(subCategory.getImageUrl());

        ProductGroupDTO group = new ProductGroupDTO();
        group.setTitle(subCategory.getTitle());
        group.setProducts(
                subCategory.getProducts()
                        .stream()
                        .map(productService::mapToProductCardDTO)
                        .toList()
        );

        productList.setListProductCard(group);

        return productList;
    }

    public ProductListDTO getWishlist(Integer customerId) {
        ProductListDTO productList = new ProductListDTO();

        // Fetch wishlist items for the customer
        List<Product> wishlistProducts = customerService.getProductsFromWishlist(customerId);

        ProductGroupDTO group = new ProductGroupDTO();
        group.setTitle("Список бажань");
        group.setProducts(
                wishlistProducts.stream()
                        .map(productService::mapToProductCardDTO)
                        .toList()
        );

        productList.setListProductCard(group);

        return productList;
    }

    public ProductListDTO getPurchasedGames(Integer customerId) {
        ProductListDTO productList = new ProductListDTO();

        // Fetch wishlist items for the customer
        List<Product> wishlistProducts = customerService.getProductFromPurchasedGames(customerId);

        ProductGroupDTO group = new ProductGroupDTO();
        group.setTitle("Придбані ігри");
        group.setProducts(
                wishlistProducts.stream()
                        .map(productService::mapToProductCardDTO)
                        .toList()
        );

        productList.setListProductCard(group);

        return productList;
    }
    /*
    public ProductListDTO getSearchResults(String query) {
        ProductListDTO productList = new ProductListDTO();

        // Fetch search results based on the query
        List<Product> searchResults = productService.searchProducts(query);

        ProductGroupDTO group = new ProductGroupDTO();
        group.setGroupTitle("Результати пошуку: " + query);
        group.setProducts(
                searchResults.stream()
                        .map(productService::mapToProductCardDTO)
                        .toList()
        );

        productList.setListProductCard(group);

        return productList;
    }*/
}

