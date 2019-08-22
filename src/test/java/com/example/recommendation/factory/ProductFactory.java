package com.example.recommendation.factory;

import com.example.recommendation.data.dto.Product;
import com.example.recommendation.data.dto.ProductType;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class ProductFactory {

    public Product create(ProductType type, Integer riskCategory, BigDecimal amount) {
        return new Product(
                UUID.randomUUID(),
                amount,
                riskCategory,
                type
        );
    }
}
