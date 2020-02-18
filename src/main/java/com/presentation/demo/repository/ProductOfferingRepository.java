package com.presentation.demo.repository;

import com.presentation.demo.model.card.product_offering.ProductOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOfferingRepository extends JpaRepository<ProductOffering,Integer> {
    ProductOffering findProductOfferingById(Integer id);
    List<ProductOffering> findProductOfferingsByPercentage(Double percentage);
    List<ProductOffering> findProductOfferingsByCashbackPercentage(Double cashbackPercentage);
    List<ProductOffering> findProductOfferingsByLimits(Double limits);
}
