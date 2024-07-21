package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value ="select * from productos where precio between ?1 and ?2 ", nativeQuery = true)
    List<Product> priceRangeQuery(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("select c from Product c where c.price between ?1 and ?2 ")
    List<Product> priceRangeQueryHQL(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product>  findProductByPriceBetween(BigDecimal minPrice,BigDecimal maxPrice);
}
