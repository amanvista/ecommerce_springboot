package com.techstackgo.ecommerce.repository;

import com.techstackgo.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p " +
            "WHERE (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:minDiscount IS NULL OR p.discountPresent >= :minDiscount) " +
            "ORDER BY CASE WHEN :sort = 'price_asc' THEN p.price END ASC, " +
            "         CASE WHEN :sort = 'price_desc' THEN p.price END DESC, " +
            "         CASE WHEN :sort = 'discount_asc' THEN p.discountPresent END ASC, " +
            "         CASE WHEN :sort = 'discount_desc' THEN p.discountPresent END DESC")
    List<Product> filterProducts(
                                 @Param("minPrice") Integer minPrice,
                                 @Param("maxPrice") Integer maxPrice,
                                 @Param("minDiscount") Integer minDiscount,
                                 @Param("sort") String sort);
//    List<Product> findByCategoryName(String category);



}
