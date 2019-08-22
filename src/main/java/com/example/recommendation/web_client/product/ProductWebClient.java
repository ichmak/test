package com.example.recommendation.web_client.product;

import com.example.recommendation.data.dto.Product;
import reactor.core.publisher.Flux;

public interface ProductWebClient {

    public Flux<Product> getProducts();
}
