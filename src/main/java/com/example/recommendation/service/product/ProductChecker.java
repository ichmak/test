package com.example.recommendation.service.product;

import com.example.recommendation.data.dto.Product;

public interface ProductChecker {

    boolean isComplies(Product product, Integer riskCategory);
}