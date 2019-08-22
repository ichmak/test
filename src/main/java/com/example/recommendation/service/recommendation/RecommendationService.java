package com.example.recommendation.service.recommendation;

import com.example.recommendation.data.dto.Recommendation;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Validated
public interface RecommendationService {
    Mono<List<Recommendation>> list(@NotEmpty String profileId, @NotNull BigDecimal amount);
}
