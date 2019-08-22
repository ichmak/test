package com.example.recommendation.web_client.product.impl;

import com.example.recommendation.data.dto.Product;
import com.example.recommendation.web_client.product.ProductWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ProductWebClientImpl implements ProductWebClient {

    private static final String URI = "/product";

    private final WebClient webClient;

    public ProductWebClientImpl(@Value("${service.product.url}") String serverUrl) {
        this.webClient = WebClient.builder().baseUrl(serverUrl).build();

    }

    public Flux<Product> getProducts() {
        return webClient.get().uri(URI).retrieve()
                .bodyToFlux(Product.class);
    }
}
