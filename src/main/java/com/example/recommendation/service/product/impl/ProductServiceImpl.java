package com.example.recommendation.service.product.impl;


import com.example.recommendation.data.dto.Product;
import com.example.recommendation.service.product.ProductChecker;
import com.example.recommendation.service.product.ProductService;
import com.example.recommendation.web_client.product.ProductWebClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductWebClient productWebClient;
    private final ProductChecker productChecker;

    public Flux<Product> getProductsByRiskCategory(Integer riskCategory) {
        Flux<Product> productsFlux = productWebClient.getProducts();
        return productsFlux.filter(it -> productChecker.isComplies(it, riskCategory));
    }
}
