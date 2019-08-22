package com.example.recommendation.service.recommendation.impl;

import com.example.recommendation.data.dto.Product;
import com.example.recommendation.service.recommendation.ProductCombinator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCombinatorImpl implements ProductCombinator {

    public List<List<Product>> getCombinations(BigDecimal amount, List<Product> products) {
        List<List<Product>> combinations = new ArrayList<>();
        List<Product> combinationProducts = new ArrayList<>();
        return getCombinations(amount, 0, combinationProducts, combinations, products);
    }

    /**
     *
     * @param amount requested amount for combination
     * @param index product position
     * @param combinationProducts list of product in current combination {@link Product}
     * @param combinations  all possible combinations of products for amount
     * @param products list of products for concrete type {@link com.example.recommendation.data.dto.ProductType}
     * @return list of recommended products
     */
    private List<List<Product>> getCombinations(BigDecimal amount, int index, List<Product> combinationProducts, List<List<Product>> combinations, List<Product> products) {

        if (index == products.size()) {
            if (amount.compareTo(BigDecimal.ZERO) == 0) {
                combinations.add(combinationProducts);
            }
            return null;
        }

        for (int count = 0; amount.compareTo(products.get(index).getPrice().multiply(BigDecimal.valueOf(count))) >= 0; count++) {
            List<Product> temp = new ArrayList<>(combinationProducts);

            for (int i = 0; i < count; i++) {
                temp.add(products.get(index));
            }

            BigDecimal balance = amount.subtract(products.get(index).getPrice().multiply(BigDecimal.valueOf(count)));
            getCombinations(balance, index + 1, temp, combinations, products);
        }

        return combinations;
    }

}
