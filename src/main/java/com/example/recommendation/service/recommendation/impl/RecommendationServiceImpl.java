package com.example.recommendation.service.recommendation.impl;

import com.example.recommendation.data.dto.Product;
import com.example.recommendation.data.dto.ProductType;
import com.example.recommendation.data.dto.Recommendation;
import com.example.recommendation.service.product.ProductService;
import com.example.recommendation.service.profile.ProfileService;
import com.example.recommendation.service.recommendation.ProductCombinator;
import com.example.recommendation.service.recommendation.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final BigDecimal AMOUNT_STEP = BigDecimal.valueOf(1000);

    private final ProfileService profileService;
    private final ProductService productService;
    private final ProductCombinator productCombinator;

    @Override
    public Mono<List<Recommendation>> list(String profileId, BigDecimal amount) {
        return profileService.getProfile(profileId).flatMap(profile -> {
            Flux<Product> productFlux = productService.getProductsByRiskCategory(profile.getRisk());
            return productFlux.collectList().flatMap(it -> Mono.just(calculateOffer(it, amount)));
        });
    }

    private List<Recommendation> calculateOffer(List<Product> products, BigDecimal requestedAmount) {
        List<Product> insuranceProducts = new ArrayList<>();
        List<Product> incomeProducts = new ArrayList<>();

        sortProductsByType(products, insuranceProducts, incomeProducts);

        List<Product> combinedProducts = combine(calcHalfAmount(requestedAmount), insuranceProducts, incomeProducts);

        return productsToRecommendations(combinedProducts);
    }

    private List<Recommendation> productsToRecommendations(List<Product> products) {
        Map<UUID, List<Product>> groupedProducts = products.stream().collect(Collectors.groupingBy(Product::getId));
        List<Recommendation> recommendations = new ArrayList<>();
        for (Map.Entry<UUID, List<Product>> entry : groupedProducts.entrySet()) {
            List<Product> productList = entry.getValue();
            Product product = productList.get(0);
            recommendations.add(
                    new Recommendation(product.getId(), productList.size(), product.getType(), product.getPrice())
            );
        }
        return recommendations;
    }

    private void sortProductsByType(List<Product> products, List<Product> insuranceProducts, List<Product> incomeProducts) {
        for (Product product : products) {
            if (ProductType.INSURANCE.equals(product.getType())) {
                insuranceProducts.add(product);
            } else {
                incomeProducts.add(product);
            }
        }
    }

    /**
     * For realisation of recommendation  used coin change algorithm.
     *
     * @see <a href="https://habr.com/ru/post/109384 </a>
     *
     * @param maxAmount    for one type of products
     * @param insuranceProducts    collection of products with type Insurance {@link ProductType}
     * @param incomeProducts collection of products with type Income {@link ProductType}
     * @return list of recommended products
     */

    private List<Product> combine(BigDecimal maxAmount, List<Product> insuranceProducts, List<Product> incomeProducts) {
        List<Product> result = new ArrayList<>();

        if (maxAmount.compareTo(BigDecimal.ZERO) <= 0 || insuranceProducts.size() == 0 || incomeProducts.size() == 0) {
            return result;
        }

        do {
            if (maxAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return result;
            }

            List<List<Product>> insuranceProductCombinations = productCombinator.getCombinations(maxAmount, insuranceProducts);
            List<List<Product>> incomeProductCombinations = productCombinator.getCombinations(maxAmount, incomeProducts);

            if (insuranceProductCombinations.size() > 0 && incomeProductCombinations.size() > 0) {
                result.addAll(insuranceProductCombinations.get(0));
                result.addAll(incomeProductCombinations.get(0));
                return result;
            } else {
                maxAmount = maxAmount.subtract(AMOUNT_STEP);
            }
        } while (true);
    }


    private BigDecimal calcHalfAmount(BigDecimal amount) {
        BigDecimal halfAmount = amount.divide(BigDecimal.valueOf(2), 0, RoundingMode.DOWN);
        return halfAmount.subtract(halfAmount.remainder(AMOUNT_STEP));
    }

}
