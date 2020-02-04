package com.presentation.demo.controller;

import com.presentation.demo.model.Bill;
import com.presentation.demo.model.card.Card;
import com.presentation.demo.model.card.DebetCard;
import com.presentation.demo.model.card.product_offering.ProductOffering;
import com.presentation.demo.service.card.product_offerings.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Random;

@Controller
public class ProductOfferingController {

    @Autowired
    private ProductOfferingService productOfferingService;

    @GetMapping("/createoffering")
    @ResponseBody
    public String createOffering() {
       ProductOffering productOffering = new ProductOffering();
       productOffering.setPercentage(50.0);
       productOffering.setLimits(100000.0);
       productOffering.setCashbackPercentage(1.5);
       productOfferingService.save(productOffering);
       return productOffering.getId().toString();
    }

}
