package com.example.recommendation.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    UUID id;
    BigDecimal price;
    Integer riskCategory;
    ProductType type;
}
