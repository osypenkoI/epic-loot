package com.e_commerce.epic_loot.service;

import com.e_commerce.epic_loot.api.DTO.Page.ProductCardDTO;
import com.e_commerce.epic_loot.api.DTO.Page.ProductGroupDTO;
import com.e_commerce.epic_loot.api.DTO.Product.*;
import com.e_commerce.epic_loot.enumEntity.RamMemory;
import com.e_commerce.epic_loot.model.*;
import com.e_commerce.epic_loot.model.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final SubCategoryRepository subCategoryRepository;
    private final FeatureRepository featureRepository;
    private final RecommendedRequirementsRepository recommendedRequirementsRepository;
    private final MinimumRequirementsRepository minimumRequirementsRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, FeatureRepository featureRepository, RecommendedRequirementsRepository recommendedRequirementsRepository, MinimumRequirementsRepository minimumRequirementsRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.featureRepository = featureRepository;
        this.recommendedRequirementsRepository = recommendedRequirementsRepository;
        this.minimumRequirementsRepository = minimumRequirementsRepository;
        this.reviewRepository = reviewRepository;
    }


    public ProductCardDTO mapToProductCardDTO(Product product) {
        ProductCardDTO productCardDTO = new ProductCardDTO();

        productCardDTO.setProductId(product.getId());
        productCardDTO.setTitle(product.getTitle());
        productCardDTO.setImageUrl(product.getMainPictureUrl());
        productCardDTO.setPrice(product.getPrice());
        productCardDTO.setDiscountPrice(calculateDiscountPrice(product.getPrice(), product.getDiscount()));

        // Check if the product is in the wishlist

        return productCardDTO;
    }

    public RequirementsDTO mapToMinimumRequirementsDTO(MinimumRequirements minimumRequirements) {
        if (minimumRequirements == null) {
            return null; // Если MinimumRequirements пустое, возвращаем null
        }

        RequirementsDTO dto = new RequirementsDTO();
        dto.setId(minimumRequirements.getId());
        dto.setOperatingSystem(minimumRequirements.getOperatingSystem());
        dto.setProcessor(minimumRequirements.getProcessor());
        dto.setRamMemory(minimumRequirements.getRamMemory().toString()); // Преобразуем Enum в строку
        dto.setGraphicCard(minimumRequirements.getGraphicCard());
        dto.setDirectX(minimumRequirements.getDirectX());
        dto.setDiskSpace(minimumRequirements.getDiskSpace());
        dto.setExtra(minimumRequirements.getExtra());

        return dto;
    }

    public RequirementsDTO mapToRecommendedRequirementsDTO(RecommendedRequirements recommendedRequirements) {
        if (recommendedRequirements == null) {
            return null; // Если MinimumRequirements пустое, возвращаем null
        }

        RequirementsDTO dto = new RequirementsDTO();
        dto.setId(recommendedRequirements.getId());
        dto.setOperatingSystem(recommendedRequirements.getOperatingSystem());
        dto.setProcessor(recommendedRequirements.getProcessor());
        dto.setRamMemory(recommendedRequirements.getRamMemory().toString()); // Преобразуем Enum в строку
        dto.setGraphicCard(recommendedRequirements.getGraphicCard());
        dto.setDirectX(recommendedRequirements.getDirectX());
        dto.setDiskSpace(recommendedRequirements.getDiskSpace());
        dto.setExtra(recommendedRequirements.getExtra());

        return dto;
    }
    public RecommendedRequirements mapToRecommendedRequirements(RequirementsDTO dto) {
        if (dto == null) {
            return null; // Если DTO пустое, возвращаем null
        }

        RecommendedRequirements requirements = new RecommendedRequirements();
        requirements.setId(dto.getId());
        requirements.setOperatingSystem(dto.getOperatingSystem());
        requirements.setProcessor(dto.getProcessor());
        requirements.setRamMemory(RamMemory.valueOf(dto.getRamMemory())); // Преобразуем строку в Enum
        requirements.setGraphicCard(dto.getGraphicCard());
        requirements.setDirectX(dto.getDirectX());
        requirements.setDiskSpace(dto.getDiskSpace());
        requirements.setExtra(dto.getExtra());

        return requirements;
    }

    public MinimumRequirements mapToMinimumRequirements(RequirementsDTO dto) {
        if (dto == null) {
            return null; // Если DTO пустое, возвращаем null
        }

        MinimumRequirements requirements = new MinimumRequirements();
        requirements.setId(dto.getId());
        requirements.setOperatingSystem(dto.getOperatingSystem());
        requirements.setProcessor(dto.getProcessor());
        requirements.setRamMemory(RamMemory.valueOf(dto.getRamMemory())); // Преобразуем строку в Enum
        requirements.setGraphicCard(dto.getGraphicCard());
        requirements.setDirectX(dto.getDirectX());
        requirements.setDiskSpace(dto.getDiskSpace());
        requirements.setExtra(dto.getExtra());

        return requirements;
    }



    // ProductPage готовый response надо зарефакторить
    public ProductDetailsDTO getProductDetails(Integer productId) {
        Product product = productRepository.findById(productId).get();
        ProductDetailsDTO productsDetails = new ProductDetailsDTO();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        List<FeatureDTO> featureDTOS = new ArrayList<>();
        System.out.println(product.getTitle());
            ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
            productDetailsDTO.setTitle(product.getTitle());
            productDetailsDTO.setDescription(product.getDescription());
            productDetailsDTO.setPrice(product.getPrice());
            productDetailsDTO.setDiscount(product.getDiscount());
            productDetailsDTO.setMainPictureUrl(product.getMainPictureUrl());
            productDetailsDTO.setOtherPictureUrl(product.getOtherPictureUrl());
            product.getCategories().forEach(category -> {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setTitle(category.getTitle());
                categoryDTOS.add(categoryDTO);
            });
            productDetailsDTO.setCategories(categoryDTOS);
            product.getFeatures().forEach(feature -> {
                        FeatureDTO featureDTO = new FeatureDTO(feature);
                        featureDTO.setId(feature.getId());
                        featureDTO.setTitle(feature.getTitle());
                        featureDTOS.add(featureDTO);
                    }
            );
            productDetailsDTO.setFeatures(featureDTOS);
            productDetailsDTO.setMinimumRequirements(mapToMinimumRequirementsDTO(product.getMinimumRequirements()));
            productDetailsDTO.setRecommendedRequirements(mapToRecommendedRequirementsDTO(product.getRecommendedRequirements()));
        return productDetailsDTO;
    }

    public ProductGroupDTO getProductsByCriteria(CriteriaDTO criteriaDTO) {
        List<Product> products = List.of();
        Pageable pageable = PageRequest.of(0, 4);
        switch (criteriaDTO.getCriteria()) {
            case "Знижки":
                products = productRepository.findTopDiscountedProducts(pageable);
                break;
            case "Популярні ігри":
                products = productRepository.findTopPopularProducts(pageable);
                break;
            case "Ігри для вас":
                products = productRepository.findRecommendedProducts(criteriaDTO.getCustomerId());
                break;
            case "Схожі ігри":
                products = productRepository.findSimilarProducts(criteriaDTO.getProductsId().get(0), pageable);
                break;
            default:
                throw new IllegalArgumentException("Invalid criteria: " + criteriaDTO.getCriteria());
        }
        ProductGroupDTO productGroupDTO = new ProductGroupDTO();
        List<ProductCardDTO> productDTOs = products.stream()
                .map(ProductCardDTO::new)
                .toList();
        productGroupDTO.setTitle(criteriaDTO.getCriteria());
        productGroupDTO.setProducts(productDTOs);

        return productGroupDTO;
    }


    public List<ProductCardDTO> searchProducts(String query) {
        Pageable pageable = PageRequest.of(0, 4);
        Page<Product> products = productRepository.findTop4ByTitleContainingIgnoreCaseOrderByPopularity(query, pageable);

        List<ProductCardDTO> productCardDTOS = products.stream()
                .map(ProductCardDTO::new)
                .toList();

        return productCardDTOS;
    }

    private static Integer calculateDiscountPrice(Integer price, BigDecimal discountPercent) {
        if (discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) <= 0) {
            return price; // Если скидка 0% или отрицательная, возвращаем оригинальную цену
        }

        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountPercent.divide(BigDecimal.valueOf(100))); // Преобразуем процент в множитель
        BigDecimal discountedPrice = BigDecimal.valueOf(price).multiply(discountMultiplier); // Применяем множитель
        return discountedPrice.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // Округляем до целого числа
    }




    @Transactional
    public void productCreate(ProductDetailsDTO productDetailsDTO) {
        // Создание объекта Product
        Product product = new Product();
        product.setTitle(productDetailsDTO.getTitle());
        product.setDescription(productDetailsDTO.getDescription());
        product.setPrice(productDetailsDTO.getPrice());
        product.setDiscount(productDetailsDTO.getDiscount());
        product.setMainPictureUrl(productDetailsDTO.getMainPictureUrl());
        product.setOtherPictureUrl(productDetailsDTO.getOtherPictureUrl());



        // Создание и сохранение зависимостей
        if (productDetailsDTO.getRecommendedRequirements() != null) {
            RecommendedRequirements recommended = mapToRecommendedRequirements(productDetailsDTO.getRecommendedRequirements());
            recommendedRequirementsRepository.save(recommended); // Сохранение зависимой сущности
            product.setRecommendedRequirements(recommended);
        }

        if (productDetailsDTO.getMinimumRequirements() != null) {
            MinimumRequirements minimum = mapToMinimumRequirements(productDetailsDTO.getMinimumRequirements());
            minimumRequirementsRepository.save(minimum); // Сохранение зависимой сущности
            product.setMinimumRequirements(minimum);
        }

        product = productRepository.save(product);

        // Установка категорий
        List<Category> categories = productDetailsDTO.getCategories().stream()
                .map(categoryDTO -> categoryRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Категория не найдена: " + categoryDTO.getId())))
                .toList();
        product.setCategories(new ArrayList<>(categories));
        Product finalProduct = product;
        categories.forEach(category -> category.getProducts().add(finalProduct));

        // Установка подкатегорий
        List<SubCategory> subCategories = productDetailsDTO.getCategories().stream()
                .flatMap(categoryDTO -> categoryDTO.getSubCategoryTitles().stream())
                .map(subCategoryDTO -> subCategoryRepository.findById(subCategoryDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Подкатегория не найдена: " + subCategoryDTO.getId())))
                .toList();
        product.setSubCategories(new ArrayList<>(subCategories));
        Product finalProduct1 = product;
        subCategories.forEach(subCategory -> subCategory.getProducts().add(finalProduct1));

        // Установка фичей (если есть)
        if (!productDetailsDTO.getFeatures().isEmpty()) {
            List<Feature> features = productDetailsDTO.getFeatures().stream()
                    .map(featureDTO -> featureRepository.findById(featureDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Фича не найдена: " + featureDTO.getId())))
                    .toList();
            product.setFeatures(new ArrayList<>(features));
            Product finalProduct2 = product;
            features.forEach(feature -> feature.getProduct().add(finalProduct2));
        }

        // Сохранение продукта
        productRepository.save(product);
    }

    public List<ReviewResponseDTO> getAllReviews(Integer productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        List<ReviewResponseDTO> reviewResponseDTOS = new ArrayList<>();
        reviews.forEach(review -> {
            ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewResponseDTO.setUsername(review.getCustomer().getUsername());
            reviewDTO.setReviewText(review.getReviewText());
            reviewDTO.setRating(review.getRating());
            reviewResponseDTO.setReviewDTO(reviewDTO);
            reviewResponseDTOS.add(reviewResponseDTO);
        });
        return reviewResponseDTOS;
    }


    //TODO Создание товара
    //TODO Удаление товара

    /*
    * нужна функция которая данные из бд переформатирует под те что нужны в хттп запросе
    * в эту функцию передается лист продуктов которые нужно преобразовать
    * алгоритм
    * 1. Делаем скл запрос с репозитория с нужными продуктами
    * 2. Передаем результат в преобразующую функцию она возвращает список
    * 3. передаем к контроллеру
    * */

}
