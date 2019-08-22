package com.example.recommendation.factory;

import com.example.recommendation.data.dto.Profile;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfileFactory {

    public Profile create(Integer riskCategory) {
        return new Profile(
                UUID.randomUUID(),
                riskCategory
        );
    }
}
