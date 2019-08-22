package com.example.recommendation.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Recommendation {
    private UUID productId;
    private int count;
    @JsonIgnore
    private ProductType productType;
    @JsonIgnore
    private BigDecimal productPrice;

}
