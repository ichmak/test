package com.example.recommendation.service.product;


import com.example.recommendation.data.dto.Product;
import reactor.core.publisher.Flux;

public interface ProductService {

    Flux<Product> getProductsByRiskCategory(Integer riskCategory);
}
