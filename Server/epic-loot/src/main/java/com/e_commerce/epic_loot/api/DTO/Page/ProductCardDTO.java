package com.e_commerce.epic_loot.api.DTO.Page;
import com.e_commerce.epic_loot.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCardDTO {
    private Integer productId;
    private String title;
    private String imageUrl;
    private Integer price;
    private Integer discountPrice;

    public ProductCardDTO(Product product) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.imageUrl = product.getMainPictureUrl();
        this.price = product.getPrice();
        this.discountPrice = calculateDiscountPrice(price, product.getDiscount());
    }

    public ProductCardDTO() {
    }

    private static Integer calculateDiscountPrice(Integer price, BigDecimal discountPercent) {
        if (discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) <= 0) {
            return price; // Если скидка 0% или отрицательная, возвращаем оригинальную цену
        }

        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountPercent.divide(BigDecimal.valueOf(100))); // Преобразуем процент в множитель
        BigDecimal discountedPrice = BigDecimal.valueOf(price).multiply(discountMultiplier); // Применяем множитель
        return discountedPrice.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // Округляем до целого числа
    }
}


