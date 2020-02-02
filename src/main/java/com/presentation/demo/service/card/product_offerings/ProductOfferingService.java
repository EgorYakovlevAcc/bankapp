package com.presentation.demo.service.card.product_offerings;

import com.presentation.demo.model.card.product_offering.ProductOffering;

import java.util.List;


public interface ProductOfferingService {
    void save(ProductOffering productOffering);
    void delete(ProductOffering productOffering);
    ProductOffering findProductOfferingsById(Integer id);
    List<ProductOffering> findProductOfferingsByPercentage(Double percentage);
    List<ProductOffering> findProductOfferingsByCashbackPercentage(Double cashbackPercentage);
    List<ProductOffering> findProductOfferingsByLimits(Double limits);

}
