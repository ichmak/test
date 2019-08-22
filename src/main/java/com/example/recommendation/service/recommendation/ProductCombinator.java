package com.example.recommendation.service.recommendation;

import com.example.recommendation.data.dto.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCombinator {

    public List<List<Product>> getCombinations(BigDecimal amount, List<Product> products) {
        List<List<Product>> combinations = new ArrayList<>();
        List<Product> combinationProducts = new ArrayList<>();
        return getCombinations(amount, 0, combinationProducts, combinations, products);
    }

    private List<List<Product>> getCombinations(BigDecimal amount, int index, List<Product> combinationProducts, List<List<Product>> combinations, List<Product> products) {

        if(index == products.size()){
            if(amount.compareTo(BigDecimal.ZERO) == 0){
                combinations.add(combinationProducts);
            }
            return null;
        }

        for(int count = 0; amount.compareTo(products.get(index).getPrice().multiply(BigDecimal.valueOf(count))) >= 0; count++){
            List<Product> temp = new ArrayList<>(combinationProducts);

            for(int i = 0; i < count; i++) {
                temp.add(products.get(index));
            }

            BigDecimal balance = amount.subtract(products.get(index).getPrice().multiply(BigDecimal.valueOf(count)));
            getCombinations(balance,index + 1, temp, combinations,products);
        }

        return combinations;
    }

}
