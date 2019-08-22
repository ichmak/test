package com.example.recommendation.service.recommendation;

import com.example.recommendation.data.dto.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCombinator {
     List<List<Product>> getCombinations(BigDecimal amount, List<Product> products);
}
