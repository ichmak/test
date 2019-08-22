package com.example.recommendation.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequestMapping("/recommendation")
public interface RecommendationController {
    @GetMapping
    Mono<?> list(String profileId, BigDecimal amount);
}
