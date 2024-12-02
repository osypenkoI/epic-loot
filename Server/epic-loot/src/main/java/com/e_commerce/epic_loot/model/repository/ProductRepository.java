package com.e_commerce.epic_loot.model.repository;

import com.e_commerce.epic_loot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
        SELECT p FROM Product p 
        LEFT JOIN p.purchaseOrders po
        WHERE p.discount > 0
        GROUP BY p.id, p.title, p.price, p.discount
        ORDER BY COUNT(po.id) DESC
    """)
    List<Product> findTopDiscountedProducts(Pageable pageable);

    @Query("""
        SELECT p FROM Product p 
        LEFT JOIN p.purchaseOrders po
        GROUP BY p.id, p.title, p.price
        ORDER BY COUNT(po.id) DESC
    """)
    List<Product> findTopPopularProducts(Pageable pageable);

    @Query("""
        SELECT p FROM Product p 
        JOIN p.categories c
        LEFT JOIN p.purchaseOrders po
        WHERE c.id = (
            SELECT c2.id FROM Category c2
            JOIN c2.products cp 
            WHERE cp.id = :currentProductId
        )
        AND p.id != :currentProductId
        GROUP BY p.id, p.title, p.price
        ORDER BY COUNT(po.id) DESC
    """)
    List<Product> findSimilarProducts(@Param("currentProductId") Integer currentProductId, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "JOIN PurchaseOrder po ON po.product.id = p.id " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND po.orderStatus = 'PAID' " +
            "GROUP BY p " +
            "ORDER BY COUNT(po) DESC")
    Page<Product> findTop4ByTitleContainingIgnoreCaseOrderByPopularity(
            @Param("query") String query, Pageable pageable);


    @Query(value = """
        SELECT p.*
        FROM product p
        JOIN category_products cp ON p.id = cp.product_id
        JOIN category c ON cp.category_id = c.id
        LEFT JOIN purchase_order o ON o.product_id = p.id AND o.order_status = 'PAID'
        WHERE c.id = (
            SELECT c2.id
            FROM customer_purchased_games pg
            JOIN product p2 ON pg.product_id = p2.id
            JOIN category_products cp2 ON p2.id = cp2.product_id
            JOIN category c2 ON cp2.category_id = c2.id
            WHERE pg.customer_id = :customerId
            GROUP BY c2.id
            ORDER BY COUNT(c2.id) DESC
            LIMIT 1
        )
        GROUP BY p.id
        ORDER BY COUNT(o.id) DESC
        LIMIT 4
    """, nativeQuery = true)
    List<Product> findRecommendedProducts(@Param("customerId") Integer customerId);

}