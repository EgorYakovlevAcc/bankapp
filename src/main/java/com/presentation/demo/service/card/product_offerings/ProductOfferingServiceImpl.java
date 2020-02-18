package com.presentation.demo.service.card.product_offerings;


import com.presentation.demo.model.card.product_offering.ProductOffering;
import com.presentation.demo.repository.ProductOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Override
    public void save(ProductOffering productOffering) {
        productOfferingRepository.save(productOffering);
    }

    @Transactional
    @Override
    public void delete(ProductOffering productOffering) {
        productOfferingRepository.delete(productOffering);
    }

    @Override
    public ProductOffering findProductOfferingsById(Integer id) {
        return productOfferingRepository.findProductOfferingById(id);
    }

    @Override
    public List<ProductOffering> findProductOfferingsByPercentage(Double percentage) {
        return productOfferingRepository.findProductOfferingsByPercentage(percentage);
    }

    @Override
    public List<ProductOffering> findProductOfferingsByCashbackPercentage(Double cashbackPercentage) {
        return productOfferingRepository.findProductOfferingsByCashbackPercentage(cashbackPercentage);
    }

    @Override
    public List<ProductOffering> findProductOfferingsByLimits(Double limits) {
        return productOfferingRepository.findProductOfferingsByLimits(limits);
    }
}
