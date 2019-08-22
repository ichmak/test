package com.example.recommendation.api.impl;

import com.example.recommendation.api.RecommendationController;
import com.example.recommendation.service.recommendation.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class RecommendationControllerImpl implements RecommendationController {

    private final RecommendationService recommendationService;

    @Override
    public Mono<?> list(String profileId, BigDecimal amount) {
        return recommendationService.list(profileId, amount);
    }
}
