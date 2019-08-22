package com.example.recommendation.service.product.impl;

import com.example.recommendation.data.dto.Product;
import com.example.recommendation.service.product.ProductChecker;
import org.springframework.stereotype.Component;

@Component
class ProductCheckerImpl implements ProductChecker {

    public boolean isComplies(Product product, Integer riskCategory) {
        return product.getRiskCategory().compareTo(riskCategory) <= 0;
    }
}
